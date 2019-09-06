package com.example.guoshijie.wordsapp.getwordsFromMysql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.guoshijie.wordsapp.Word.word;
import com.example.guoshijie.wordsapp.Word.wordrecord;
import com.example.guoshijie.wordsapp.WordsDatabaseHelper.WordsDatabaseHelper;

import java.util.ArrayList;

public class getwordrecordFromMysql {
    private ArrayList<wordrecord> arrayList = new ArrayList();
    private WordsDatabaseHelper wordsDatabaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private wordrecord wordrecord;

    public getwordrecordFromMysql(Context context) {
        wordsDatabaseHelper = new WordsDatabaseHelper(context, "Words.db", null, 2);
        db = wordsDatabaseHelper.getWritableDatabase();
    }

    public wordrecord getwordrecord(String string) {
        cursor = db.query("wordrecord_table", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String example = cursor.getString(cursor.getColumnIndex("example"));
                String exampletranslation = cursor.getString(cursor.getColumnIndex("exampletranslation"));
                if(word.compareTo(string)==0){
                    wordrecord=new wordrecord(word,example,exampletranslation);
                    break;
                }
            } while (cursor.moveToNext());
        }
        return wordrecord;
    }
    public void changewordrecord(String wordletter,wordrecord wordrecord) {
        ContentValues values=new ContentValues();
        values.put("word",wordrecord.getWord());
        values.put("example",wordrecord.getExample());
        values.put("exampletranslation",wordrecord.getExampletranslation());
        db.update("wordrecord_table",values,"word=?",new String[]{wordletter});
    }
    public void deleteword(word word){
        db.delete("wordrecord_table","word=?",new String[]{word.getWord()});

    }
}
