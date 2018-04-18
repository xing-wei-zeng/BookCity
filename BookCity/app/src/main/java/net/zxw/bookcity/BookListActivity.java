package net.zxw.bookcity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * @author zxw
 * @Email 18316275391@163.com
 * @describe TODO
 */

public class BookListActivity extends AppCompatActivity{

    private AsyncHttpClient mClient;
    private static final String TAG = "zxw";
    private ListView mListView;
    List<BookListResult.Book> mBooks = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        mListView = (ListView) findViewById(R.id.book_list_view);
        String url = "http://www.imooc.com/api/teacher?type=10";

        mClient = new AsyncHttpClient();
        mClient.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final  String result = new String(responseBody);

                Gson gson = new Gson();
                BookListResult bookListResult = gson.fromJson(result,BookListResult.class);

                mBooks = bookListResult.getMData();

                mListView.setAdapter(new BookListAdapter());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    public static void start(Context context){
        Intent intent = new Intent(context,BookListActivity.class);
        context.startActivity(intent);
    }

    private class BookListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mBooks.size();
        }

        @Override
        public Object getItem(int position) {
            return mBooks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final BookListResult.Book book = mBooks.get(position);

            ViewHolder viewHolder = new ViewHolder();
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.item_book_list_view,null);
                viewHolder.mNameTextView = (TextView) convertView.findViewById(R.id.name_text_view);
                viewHolder.mButton = (Button) convertView.findViewById(R.id.book_button);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.mNameTextView.setText(book.getBookname());
            final String path = Environment.getExternalStorageDirectory()+"/mnt/"+book.getBookname()+".txt";
            final File file = new File(path);
            viewHolder.mButton.setText(file.exists()? R.string.download_success:R.string.button_onclick);

            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int REQUEST_EXTERNAL_STORAGE = 1;
                    String[] PERMISSIONS_STORAGE = {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    };
                    int permission = ActivityCompat.checkSelfPermission(BookListActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        // We don't have permission so prompt the user
                        ActivityCompat.requestPermissions(
                                BookListActivity.this,
                                PERMISSIONS_STORAGE,
                                REQUEST_EXTERNAL_STORAGE
                        );
                    }
                    //下载

                    if(file.exists()){
                        //打开书籍

                    }else {
                        mClient.addHeader("Accept-Encoding", "identity");
                        mClient.get(book.getBookfile(), new FileAsyncHttpResponseHandler(file) {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                                finalViewHolder.mButton.setText(R.string.download_failure);
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, File file) {
                                finalViewHolder.mButton.setText(R.string.download_success);
                            }

                            @Override
                            public void onProgress(long bytesWritten, long totalSize) {
                                super.onProgress(bytesWritten, totalSize);

                                finalViewHolder.mButton.setText(String.valueOf(bytesWritten * 100 / totalSize) + "%");
                            }
                        });
                    }

                }
            });
            return convertView;
        }
        class ViewHolder{
            public TextView mNameTextView;
            public Button mButton;
        }
    }
}
