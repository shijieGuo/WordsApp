package com.example.guoshijie.wordsapp.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.guoshijie.wordsapp.AppointedWords;
import com.example.guoshijie.wordsapp.ChangeWord;
import com.example.guoshijie.wordsapp.R;
import com.example.guoshijie.wordsapp.Word.word;
import com.example.guoshijie.wordsapp.Word.wordrecord;
import com.example.guoshijie.wordsapp.getwordsFromMysql.getwordrecordFromMysql;
import com.example.guoshijie.wordsapp.getwordsFromMysql.getwordsFromMysql;
public class ChooseDialog extends AlertDialog {

    private double dialog_width;
    private double dialog_height;
    private Activity activity;
    private ImageButton delete;
    private ImageButton update;
    private getwordsFromMysql getwordsFromMysql;
    private getwordrecordFromMysql getwordrecordFromMysql;
    private Context context;
    private word word;
    private wordrecord wordrecord;

    public ChooseDialog(@NonNull Context context, word word, wordrecord wordrecord, double dialog_height, double dialog_width) {
        super(context);
        //  setContentView(R.layout.custom_dialog);
        this.activity = (Activity) context;
        this.dialog_height = dialog_height;
        this.dialog_width = dialog_width;
        this.word=word;
        this.wordrecord=wordrecord;
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //为了锁定app界面的东西是来自哪个xml文件
        setContentView(R.layout.choosedialog);


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
        setviews();
    }
    @Override
    public void show() {
        super.show();
        getWindow().setWindowAnimations(R.style.dialogWindowAnim1);
        // 设置显示位置为右部
        getWindow().setGravity(Gravity.RIGHT);
    }
    public void findview() {
        getwordsFromMysql=new getwordsFromMysql(context);
        getwordrecordFromMysql=new getwordrecordFromMysql(context);
        delete=(ImageButton)findViewById(R.id.delete);
        update=(ImageButton)findViewById(R.id.update);
    }
    public void setviews(){

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getwordsFromMysql.deleteword(word);
                getwordrecordFromMysql.deleteword(word);
                refreshlist();
                dismiss();//关闭
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity,ChangeWord.class);
                String wordletter=word.getWord();
                intent.putExtra("wordletter",wordletter);
                activity.startActivity(intent);
                dismiss();


            }
        });
    }
    private void refreshlist(){
        Message message=new Message();
        message.what=0x111;
        AppointedWords.gethandler().sendMessage(message);
    }
}