package com.example.guoshijie.wordsapp.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.guoshijie.wordsapp.R;
import com.example.guoshijie.wordsapp.Word.word;
import com.example.guoshijie.wordsapp.Word.wordrecord;
import com.example.guoshijie.wordsapp.getwordsFromMysql.getpartchapterFromMysql;

import java.io.IOException;
import java.util.ArrayList;

public class WordDialog extends AlertDialog {

    private double dialog_width;
    private double dialog_height;
    private Activity activity;
    private TextView Part;
    private TextView Chapter;
    private TextView Word;
    private TextView Translation;
    private TextView Example;
    private TextView Exampletranslation;
    private ImageButton Play;
    private word word;
    private wordrecord wordrecord;
    private getpartchapterFromMysql getpartchapterFromMysql;
    private ArrayList<String> PartAndChapter=new ArrayList<String>();
    private MediaPlayer mediaPlayer;
    public WordDialog(@NonNull Context context, word word,wordrecord wordrecord, double dialog_height, double dialog_width) {
        super(context);
        //  setContentView(R.layout.custom_dialog);
        this.activity = (Activity) context;
        this.dialog_height = dialog_height;
        this.dialog_width = dialog_width;
        this.word=word;
        this.wordrecord=wordrecord;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //为了锁定app界面的东西是来自哪个xml文件
        setContentView(R.layout.worddialog);



        //设置弹窗的宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int) (size.x * dialog_width);//size.x*0.8是dialog的宽度为app界面的80%
        p.height = (int) (size.y * dialog_height);
        getWindow().setAttributes(p);
        findview();
        setview();
    }

    private void setview() {
        Part.setText(PartAndChapter.get(0));
        Chapter.setText(PartAndChapter.get(1));
        Word.setText(word.getWord());

        String[] tt=word.getTranslation().split("\\s+");
        String translation=tt[0];
        for(int i=1;i<tt.length;i++)
        {
            translation=translation+"\n"+tt[i];
        }
        Translation.setText(translation);
        Example.setText(wordrecord.getExample());
        Exampletranslation.setText(wordrecord.getExampletranslation());
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //播放 assets/a2.mp3 音乐文件
                    AssetFileDescriptor fd = getContext().getAssets().openFd(word.getWord()+".mp3");
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();
        getWindow().setWindowAnimations(R.style.dialogWindowAnim1);
        // 设置显示位置为右部
        getWindow().setGravity(Gravity.RIGHT);
    }
    public void findview() {
        getpartchapterFromMysql=new getpartchapterFromMysql(getContext());
        PartAndChapter=getpartchapterFromMysql.getPartAndChapter(word.getPartchapter());
        Part=(TextView)findViewById(R.id.part);
        Chapter=(TextView)findViewById(R.id.chapter);
        Word=(TextView)findViewById(R.id.word);
        Translation=(TextView)findViewById(R.id.translation);
        Example=(TextView)findViewById(R.id.example);
        Exampletranslation=(TextView)findViewById(R.id.exampletranslation);
        Play=(ImageButton)findViewById(R.id.paly);
    }
}
