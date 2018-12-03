package com.example.notepads;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

public class SearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //保存实例的状态，在activity生命周期结束之前保存Activity的状态
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<Map<String,String>>items =(ArrayList<Map<String,String>>)bundle.getSerializable("result");

        SimpleAdapter adapter = new SimpleAdapter(this,items,R.layout.item,
                new String[]{Notes.Note._ID, Notes.Note.COLUMN_NAME_TITLE,
                        Notes.Note.COLUMN_NAME_CONTEXT, Notes.Note.COLUMN_NAME_TIME},
                new int[]{R.id.textViewId,R.id.textViewTitle,
                        R.id.textViewContext,R.id.textViewTime});

        ListView list = (ListView)findViewById(R.id.lstSearchResultNotes);
        list.setAdapter(adapter);
    }

}
