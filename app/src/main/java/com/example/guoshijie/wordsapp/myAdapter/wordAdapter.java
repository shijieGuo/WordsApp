package com.example.guoshijie.wordsapp.myAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guoshijie.wordsapp.Dialog.ChooseDialog;
import com.example.guoshijie.wordsapp.Dialog.WordDialog;
import com.example.guoshijie.wordsapp.R;
import com.example.guoshijie.wordsapp.Word.word;
import com.example.guoshijie.wordsapp.Word.wordrecord;
import com.example.guoshijie.wordsapp.getwordsFromMysql.getwordrecordFromMysql;

import java.util.ArrayList;

public class wordAdapter extends ArrayAdapter {
    private ArrayList<word> arrayList;
    private getwordrecordFromMysql getwordrecordFromMysql;
    private Context context;
    private int longclick;

    public wordAdapter(Context context, int textViewResourceId, ArrayList<word> objects,int longclick) {
        super(context, textViewResourceId, objects);
        this.context=context;
        this.arrayList = objects;
        this.longclick=longclick;
        this.getwordrecordFromMysql=new getwordrecordFromMysql(context);
    }
    @Override
    public int getCount() {
        return super.getCount();
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.worditem, null);
        ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,120);//设置宽度和高度
        v.setLayoutParams(params);
        v.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
//                Toast.makeText((Activity) context,longclick+"",Toast.LENGTH_SHORT).show();
                if(longclick==1){
                    wordrecord wordrecord;
                    wordrecord=getwordrecordFromMysql.getwordrecord(arrayList.get(position).getWord());
                    ChooseDialog wordDialog=new ChooseDialog(getContext(),arrayList.get(position),new wordrecord(wordrecord.getWord(),wordrecord.getExample(),wordrecord.getExampletranslation()),0.6,0.3);
                    wordDialog.setCancelable(true);
                    wordDialog.show();
                }
                return true;
            }
        });
        v.setClickable(true);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordrecord wordrecord;
                wordrecord=getwordrecordFromMysql.getwordrecord(arrayList.get(position).getWord());
                WordDialog wordDialog=new WordDialog(getContext(),arrayList.get(position),new wordrecord(wordrecord.getWord(),wordrecord.getExample(),wordrecord.getExampletranslation()),0.6,0.75);
                wordDialog.setCancelable(true);
                wordDialog.show();

            }
        });
        TextView textView=(TextView) v.findViewById(R.id.textview);
        textView.setText(arrayList.get(position).getWord()+" "+arrayList.get(position).getTranslation());

        return v;
    }

    public ArrayList getArraryList()
    {
        return arrayList;
    }

}

