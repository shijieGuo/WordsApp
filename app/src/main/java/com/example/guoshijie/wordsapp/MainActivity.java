package com.example.guoshijie.wordsapp;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.guoshijie.wordsapp.WordsDatabaseHelper.WordsDatabaseHelper;
import com.example.guoshijie.wordsapp.checkpermission.CheckPermissions;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private WordsDatabaseHelper wordsDatabaseHelper;

    private ViewPager viewPager;
    private ArrayList<Fragment> mFragmentList = new ArrayList <Fragment>();
    private FragmentPagerAdapter fragmentPagerAdapter;
    private File sdcardfile = null;
    private File datafile=null;

    private Button[] Buttons=new Button[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        setpermission();
        initFragment();
        setView();
        creatdatabase();
    }


    private void setpermission()
    {
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
//            }
//        }
        if (new CheckPermissions(this,this).checklacks()){
            return;
        }

    }
    private void creatdatabase() {
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) //内存卡存在
//        {
//            sdcardfile = Environment.getExternalStorageDirectory();//获取目录文件
//            datafile=new File(sdcardfile.getAbsoluteFile()+"/Words/data");
//            createDir(datafile.getAbsolutePath());
//        }
        wordsDatabaseHelper=new WordsDatabaseHelper(this,"words.db",null,1);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        com.example.guoshijie.wordsapp.DisplayUtils.hideInputWhenTouchOtherView(this, ev,null);
        return super.dispatchTouchEvent(ev);
    }

    private void initFragment()
    {

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        mFragmentList.add(new com.example.guoshijie.wordsapp.SeekFragment());
        mFragmentList.add(new com.example.guoshijie.wordsapp.All_wordsFragment());
        mFragmentList.add(new ManageFragment());

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragmentList != null ? mFragmentList.get(i) : null;
            }

            @Override
            public int getCount() {
                return mFragmentList != null ? mFragmentList.size() : 0;
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        Buttons[0].setTextColor(Color.BLACK);
                        Buttons[1].setTextColor(Color.WHITE);
                        Buttons[2].setTextColor(Color.WHITE);
                        break;
                    case 1:
                        Buttons[1].setTextColor(Color.BLACK);
                        Buttons[0].setTextColor(Color.WHITE);
                        Buttons[2].setTextColor(Color.WHITE);
                        break;
                    case 2:
                        Buttons[2].setTextColor(Color.BLACK);
                        Buttons[1].setTextColor(Color.WHITE);
                        Buttons[0].setTextColor(Color.WHITE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setView()
    {
        Buttons[0]=findViewById(R.id.seek_imagebutton);
        Buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0,true);
            }
        });
        Buttons[1]=findViewById(R.id.allwords_imagebutton);
        Buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1,true);
            }
        });
        Buttons[2]=findViewById(R.id.cloud_imagebutton);
        Buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2,true);
            }
        });
        Buttons[0].setTextColor(Color.BLACK);
        Buttons[1].setTextColor(Color.WHITE);
        Buttons[2].setTextColor(Color.WHITE);
    }
//    private static boolean createDir(String destDirName) {
//        File dir = new File(destDirName);
//        if (dir.exists()) {
//            return false;
//        }
//        if (!destDirName.endsWith(File.separator)) {//如果没有/ 加上/
//            destDirName = destDirName + File.separator;
//        }
//        //创建目录
//        if (dir.mkdirs()) { return true;  }
//        else { return false; }
//    }
}

