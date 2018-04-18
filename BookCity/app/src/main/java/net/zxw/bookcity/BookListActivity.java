package net.zxw.bookcity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * @author zxw
 * @Email 18316275391@163.com
 * @describe TODO
 */

public class BookListActivity extends AppCompatActivity{

    private static final String TAG = "zxw";
    private ListView mListView;
    List<BookListResult.Book> mBooks = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        mListView = (ListView) findViewById(R.id.book_list_view);
        String url = "http://www.imooc.com/api/teacher?type=10";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {

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
            BookListResult.Book book = mBooks.get(position);

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
            return convertView;
        }
        class ViewHolder{
            public TextView mNameTextView;
            public Button mButton;
        }
    }
}
