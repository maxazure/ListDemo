package com.railscon.listdemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private CupboardSQLiteOpenHelper helper;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new CupboardSQLiteOpenHelper(this);

        listView = findViewById(R.id.listview);


        addWord("good");

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor words = cupboard().withDatabase(db).query(Word.class).query().getCursor();

        adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,
                words,

                new String[] { "word"},

                new int[] { android.R.id.text1}, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);



    }


    private Long addWord(String wordStr){


        Long id;
        wordStr = wordStr.replace("'","''");
        SQLiteDatabase db = helper.getWritableDatabase();
        Word word;

        word = cupboard().withDatabase(db).query(Word.class).withSelection("word=?",wordStr).get();

            word = new Word();
            word.word = wordStr;

        id = cupboard().withDatabase(db).put(word);
        db.close();
        return  id;
    }
}
