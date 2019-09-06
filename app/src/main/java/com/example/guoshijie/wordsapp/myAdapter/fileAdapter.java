package com.example.guoshijie.wordsapp.myAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guoshijie.wordsapp.R;
import com.example.guoshijie.wordsapp.WordsDatabaseHelper.WordsDatabaseHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class fileAdapter extends ArrayAdapter {
    private ArrayList<File> arrayList;
    private Context context;
    private ImageButton importcsv;
    private WordsDatabaseHelper wordsDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    public fileAdapter(Context context, int textViewResourceId, ArrayList<File> objects) {
        super(context, textViewResourceId, objects);
        this.context=context;
        this.arrayList = objects;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.fileitem, null);
        ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,120);//设置宽度和高度
        v.setLayoutParams(params);
        importcsv=v.findViewById(R.id.importcsv);
        v.setLongClickable(true);
        v.setClickable(true);
        importcsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    wordsDatabaseHelper=new WordsDatabaseHelper(context,"Words.db",null,2);
                    sqLiteDatabase=wordsDatabaseHelper.getWritableDatabase();
//                    Toast.makeText((Activity) context,arrayList.get(position).getAbsolutePath(),Toast.LENGTH_SHORT).show();
                    InputStreamReader in = new InputStreamReader(new FileInputStream(new File(arrayList.get(position).getAbsolutePath())),Charset.forName("GBK"));
                    BufferedReader br = new BufferedReader(in);
//                    Toast.makeText((Activity) context,br.toString(),Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText((Activity) context,buffer[2],Toast.LENGTH_SHORT).show();
                        values.put("partchapter",buffer[0]+buffer[1]);
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
                    Toast.makeText(context,"单词导入成功",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                Toast.makeText((Activity) context,arrayList.get(position).getAbsolutePath(),Toast.LENGTH_SHORT).show();
            }
        });
        TextView textView=(TextView) v.findViewById(R.id.filename);
        textView.setText(arrayList.get(position).getName());

        return v;
    }

    public ArrayList getArraryList()
    {
        return arrayList;
    }

}
