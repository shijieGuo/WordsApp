package com.example.guoshijie.wordsapp;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.guoshijie.wordsapp.FindFiles.FindFiles;
import com.example.guoshijie.wordsapp.myAdapter.fileAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ManageFragment extends android.support.v4.app.Fragment {
    private View view=null;
    private List<String> lstFile = new ArrayList<String>();
    private ListView filelist;
    private ImageButton findfiles;
    private File sdcardfile = null;
    private File datafile=null;
    private ArrayList<File> files=new ArrayList<File>();
    private fileAdapter adapter;
    private FindFiles Findfiles;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null) {
            view = inflater.inflate(R.layout.fragment_manage, container, false);
//            textView=view.findViewById(R.id.text);

            findView();
            setView();
        }
        return view;
    }


    private void findView() {
        Findfiles=new FindFiles();
        filelist=view.findViewById(R.id.filelist);
        findfiles=view.findViewById(R.id.findfiles);
    }

    private void setView() {
        adapter=new fileAdapter(getActivity(),R.layout.fileitem,files);
        filelist.setAdapter(adapter);
        findfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sdcardfile = Environment.getExternalStorageDirectory();//获取目录文件
                Findfiles.clearListFile();
                Findfiles.GetFiles(sdcardfile.getAbsolutePath(),"csv",true);
                lstFile=Findfiles.getLstFile();
                files.clear();
                if(lstFile.size()!=0) {
                    for (int i = 0; i < lstFile.size(); i++) {
                        datafile = new File(lstFile.get(i));
                        files.add(datafile);
                    }
                    adapter.notifyDataSetChanged();
                }
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

}
