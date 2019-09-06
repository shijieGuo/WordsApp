package com.example.guoshijie.wordsapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.guoshijie.wordsapp.Word.word;
import com.example.guoshijie.wordsapp.getwordsFromMysql.getwordsFromMysql;
import com.example.guoshijie.wordsapp.myAdapter.wordAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

public class SeekFragment extends android.support.v4.app.Fragment {
    private View view=null;
    private EditText editText;
    private Button cancle;
    private ListView listView;
    private wordAdapter adapter;
    private ArrayList<word> words=new ArrayList();
    private ArrayList<word> Words=new ArrayList();
    private Timer timer;
    private TimerTask timerTask;
    private getwordsFromMysql getwordsFromMysql;
    private String input;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null) {
            view = inflater.inflate(R.layout.fragment_seek, container, false);
            setView();
            startTimer();
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }
public void setView(){
        getwordsFromMysql=new getwordsFromMysql(getContext());
        editText=view.findViewById(R.id.edittext);
        cancle=view.findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
        listView=view.findViewById(R.id.list_view);
        adapter=new wordAdapter(getActivity(),R.layout.worditem,words,0);
        listView.setAdapter(adapter);


}
    public void onResume()
    {
        super.onResume();
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0X111){
                renewlist();
            }
            super.handleMessage(msg);
        }
    };

    private void renewlist() {
        words.clear();
        Words=getwordsFromMysql.getwords(editText.getText().toString());
        SortWords();//对检索结果进行排序
        for(int i=0;i<Words.size();i++){
        words.add(Words.get(i));}
//        Toast.makeText(getActivity(),words.get(0),Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    private void SortWords() {
        Collections.sort(Words, new Comparator<word>(){
            public int compare(word p1, word p2) {
                if(p1.getWord().compareTo(p2.getWord())>0){
                    return 1;
                }
                if(p1.getWord().compareTo(p2.getWord())==0){
                    return 0;
                }
                return -1;
            }
        });
    }

    private void startTimer()
    {
        timerTask=new TimerTask() {
            @Override
            public void run() {
                if(!editText.getText().toString().equals(input)){
                    Message message=new Message();
                    message.what=0x111;
                    handler.sendMessage(message);
                }
                input=editText.getText().toString();
            }
        };
        timer=new Timer();
        timer.schedule(timerTask,0,100);
    }
}
