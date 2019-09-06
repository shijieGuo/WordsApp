package com.example.guoshijie.wordsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guoshijie.wordsapp.getwordsFromMysql.getpartchapterFromMysql;

import java.util.ArrayList;

public class All_wordsFragment extends android.support.v4.app.Fragment{
    private View view=null;
    private ListView partlist;
    private ListView chapterlist;
    private getpartchapterFromMysql getpartchapterFromMysql;
    private ArrayList<String> parts=new ArrayList<String>();
    private ArrayList<String> chapters=new ArrayList<String>();
    private ArrayList<String> chapters1=new ArrayList<String>();
    private ArrayAdapter<String> adapter2;
    private String part;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null) {
            view = inflater.inflate(R.layout.fragment_allwords, container, false);
            findView();
            setView();
        }
        return view;
    }

    private void findView() {
        getpartchapterFromMysql=new getpartchapterFromMysql(getContext());
        parts=getpartchapterFromMysql.getParts();
        partlist=view.findViewById(R.id.partlist);
        chapterlist=view.findViewById(R.id.chapterlist);
    }

    private void setView() {
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,parts);
        partlist.setAdapter(adapter1);
        adapter2=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,chapters);
        chapterlist.setAdapter(adapter2);
        partlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                part=(i+1)+"";
                Message message=new Message();
                message.what=0x111;
                message.arg1=i+1;
                handler.sendMessage(message);
//                chapters.clear();
//                chapters1=getpartchapterFromMysql.getParts();
//                for(int j=0;j<chapters1.size();j++){
//                    chapters.add(chapters1.get(j));}
//                Toast.makeText(getActivity(),"fff",Toast.LENGTH_SHORT).show();
//                adapter2.notifyDataSetChanged();
            }
        });
        chapterlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),AppointedWords.class);
                String partchapter=part+(i+1);
                intent.putExtra("partchapter",partchapter);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    public void onResume()
    {
        super.onResume();
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0X111){
                renewchapterlist(msg.arg1);
            }
            super.handleMessage(msg);
        }
    };
    private void renewchapterlist(int part){
                chapters.clear();
                chapters1.clear();
                chapters1=getpartchapterFromMysql.getChapters(part);
                for(int i=0;i<chapters1.size();i++){
                    chapters.add(chapters1.get(i));}
//                Toast.makeText(getActivity(),"fff",Toast.LENGTH_SHORT).show();
                adapter2.notifyDataSetChanged();
//        Toast.makeText(getActivity(),"fff",Toast.LENGTH_SHORT).show();


    }
}
