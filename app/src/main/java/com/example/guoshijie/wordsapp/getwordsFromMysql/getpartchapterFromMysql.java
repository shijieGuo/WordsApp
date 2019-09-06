package com.example.guoshijie.wordsapp.getwordsFromMysql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.guoshijie.wordsapp.WordsDatabaseHelper.WordsDatabaseHelper;

import java.util.ArrayList;

public class getpartchapterFromMysql {
    private WordsDatabaseHelper wordsDatabaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ArrayList<String> parts=new ArrayList<String>();
    private ArrayList<String> chapters=new ArrayList<String>();
    private ArrayList<String> getPartAndChapter=new ArrayList<String>();

    public getpartchapterFromMysql(Context context) {
        wordsDatabaseHelper = new WordsDatabaseHelper(context, "Words.db", null, 2);
        db = wordsDatabaseHelper.getWritableDatabase();
    }

    public  ArrayList<String> getParts() {
        parts.clear();
        cursor = db.query("part_table", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String part = cursor.getString(cursor.getColumnIndex("part"));
                parts.add(part);
            } while (cursor.moveToNext());
        }
        return parts;
    }
    public  ArrayList<String> getChapters(int part) {
        chapters.clear();
        cursor = db.query("partchapter_table", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String partchapter = cursor.getString(cursor.getColumnIndex("partchapter"));
                String chapter = cursor.getString(cursor.getColumnIndex("chapter"));
                if(partchapter.startsWith(part+"")){
                    chapters.add(chapter);
                }
            } while (cursor.moveToNext());
        }
        return chapters;
    }
    public ArrayList<String> getPartAndChapter(String PartAndChapter){
        getPartAndChapter.clear();
        cursor = db.query("partchapter_table", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String partchapter = cursor.getString(cursor.getColumnIndex("partchapter"));
                String part = cursor.getString(cursor.getColumnIndex("part"));
                String chapter = cursor.getString(cursor.getColumnIndex("chapter"));
                if(partchapter.compareTo(PartAndChapter)==0){
                    getPartAndChapter.add(part);
                    getPartAndChapter.add(chapter);
                }
            } while (cursor.moveToNext());
        }

        return getPartAndChapter;
    }

}