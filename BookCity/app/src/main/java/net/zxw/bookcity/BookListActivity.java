package net.zxw.bookcity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author zxw
 * @Email 18316275391@163.com
 * @describe TODO
 */

public class BookListActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
    }

    public static void start(Context context){
        Intent intent = new Intent(context,BookListActivity.class);
        context.startActivity(intent);
    }
}
