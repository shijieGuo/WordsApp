package com.example.guoshijie.wordsapp.WordsDatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class WordsDatabaseHelper extends SQLiteOpenHelper {
public static final String part_table="create table part_table ("
            +"id integer primary key autoincrement,"
            +"part varchar(50) )";

public static final String partchapter_table="create table partchapter_table ("
            +"id integer primary key autoincrement,"
            +"partchapter varchar(10),"
            +"part varchar(50),"
            +"chapter varchar(50) )";

public static final String word_table="create table word_table ("
        +"id integer primary key autoincrement,"
        +"partchapter varchar(10),"
        +"word varchar(26),"
        +"translation varchar(50) )";
public static final String wordrecord_table="create table wordrecord_table ("
        +"id integer primary key autoincrement,"
        +"word varchar(26),"
        +"example varchar(200),"
        +"exampletranslation varchar(200) )";
private Context context;
    public WordsDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(word_table);
        sqLiteDatabase.execSQL(wordrecord_table);
        sqLiteDatabase.execSQL(partchapter_table);
        sqLiteDatabase.execSQL(part_table);
        ImportData(sqLiteDatabase);
        ImportpartchapterData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    private void ImportData(SQLiteDatabase sqLiteDatabase) {
        try {
            InputStreamReader in = new InputStreamReader(context.getAssets().open("part5chapter1.csv"),Charset.forName("GBK"));
            BufferedReader br = new BufferedReader(in);
            br.readLine();
            String line = "";
            /**
             * 这里读取csv文件中的前10条数据
             * 如果要读取第10条到30条数据,只需定义i初始值为9,wile中i<10改为i>=9&&i<30即可,其他范围依次类推
             */
            ContentValues values=new ContentValues();
            ContentValues values1=new ContentValues();
            while ((line = br.readLine()) != null ) {
                /**
                 *  csv格式每一列内容以逗号分隔,因此要取出想要的内容,以逗号为分割符分割字符串即可,
                 *  把分割结果存到到数组中,根据数组来取得相应值
                 */
                String buffer[] = line.split(",");// 以逗号分隔
                values.put("partchapter",(buffer[0]+buffer[1]));
                values.put("word",buffer[2]);
                values.put("translation",buffer[3]);
                values1.put("word",buffer[2]);
                values1.put("example",buffer[4]);
                values1.put("exampletranslation",buffer[5]);
                sqLiteDatabase.insert("word_table",null,values);
                values.clear();
                sqLiteDatabase.insert("wordrecord_table",null,values1);
                values1.clear();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void ImportpartchapterData(SQLiteDatabase sqLiteDatabase) {
        try {
            InputStreamReader in = new InputStreamReader(context.getAssets().open("partchapter.csv"),Charset.forName("GBK"));
            BufferedReader br = new BufferedReader(in);
            br.readLine();
            String line = "";
            /**
             * 这里读取csv文件中的前10条数据
             * 如果要读取第10条到30条数据,只需定义i初始值为9,wile中i<10改为i>=9&&i<30即可,其他范围依次类推
             */
            ContentValues values=new ContentValues();
            while ((line = br.readLine()) != null ) {
                /**
                 *  csv格式每一列内容以逗号分隔,因此要取出想要的内容,以逗号为分割符分割字符串即可,
                 *  把分割结果存到到数组中,根据数组来取得相应值
                 */
                String buffer[] = line.split(",");// 以逗号分隔
                values.put("partchapter",(buffer[0]+buffer[2]));
                values.put("part",buffer[1]);
                values.put("chapter",buffer[3]);
                sqLiteDatabase.insert("partchapter_table",null,values);
                values.clear();

//                Toast.makeText(context,buffer[1],Toast.LENGTH_SHORT).show();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ContentValues values1=new ContentValues();
        values1.put("part","Ⅰ:生活常识和社交篇");
        sqLiteDatabase.insert("part_table",null,values1);
        values1.clear();
        values1.put("part","Ⅱ:旅游篇");
        sqLiteDatabase.insert("part_table",null,values1);
        values1.clear();
        values1.put("part","Ⅲ:职场篇");
        sqLiteDatabase.insert("part_table",null,values1);
        values1.clear();
        values1.put("part","Ⅳ:学术篇");
        sqLiteDatabase.insert("part_table",null,values1);
        values1.clear();
        values1.put("part","Ⅴ:自然人文篇");
        sqLiteDatabase.insert("part_table",null,values1);
        values1.clear();
    }
}
