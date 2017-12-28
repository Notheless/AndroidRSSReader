package com.example.admin.drawer;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.admin.drawer.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Setting extends Fragment {

    char[] arrayset = new char[9];
    public Setting() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    public void onResume() {

        super.onResume();
        readSetting();

    }


    public void readSetting(){

        String path = Environment.getExternalStorageDirectory().getPath();
        String fileName = "/Setting.cfg";
        File file = new File(path, fileName);

        if(file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {

            }
            String sett = text.toString();
            char[] x = sett.toCharArray();
            for (int i = 0; i < 9; i++) {
                arrayset[i] = x[i];
            }
            setValue(arrayset);
        }
        else{
            writeInitialSetting();
        }
    }
    public void writeInitialSetting(){


        String setting = "111111111";
        String entar = Character.toString ((char) 13);
        String path = Environment.getExternalStorageDirectory().getPath();
        String fileName = "/Setting.cfg";
        File file = new File(path, fileName);
        try
        {

            FileOutputStream out = new FileOutputStream(file);
            byte[] data = setting.getBytes();
            out.write(data);
            out.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        readSetting();
    }
    public void saveSetting(){

        arrayset = new char[9];
        arrayset[0] = getSwitchValue((Switch) getActivity().findViewById(R.id.Bindo1));
        arrayset[1] = getSwitchValue((Switch) getActivity().findViewById(R.id.Bindo2));
        arrayset[2] = getSwitchValue((Switch) getActivity().findViewById(R.id.Bindo3));

        arrayset[3] = getSwitchValue((Switch) getActivity().findViewById(R.id.Bing1));
        arrayset[4] = getSwitchValue((Switch) getActivity().findViewById(R.id.Bing2));
        arrayset[5] = getSwitchValue((Switch) getActivity().findViewById(R.id.Bing3));

        arrayset[6] = getSwitchValue((Switch) getActivity().findViewById(R.id.bol1));
        arrayset[7] = getSwitchValue((Switch) getActivity().findViewById(R.id.bol2));
        arrayset[8] = getSwitchValue((Switch) getActivity().findViewById(R.id.bol3));



        String setting = new String(arrayset);

        String entar = Character.toString ((char) 13);
        String path = Environment.getExternalStorageDirectory().getPath();
        String fileName = "/Setting.cfg";
        File file = new File(path, fileName);
        try
        {

            FileOutputStream out = new FileOutputStream(file);
            byte[] data = setting.getBytes();
            out.write(data);
            out.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        readSetting();
    }
    char getSwitchValue(Switch s){
        char res ='0';
        if(s.isChecked()) res = '1';
        return res;
    }
    void setValue(char[] input){
        Switch s;

        s = (Switch)getActivity().findViewById(R.id.Bindo1);
        if(input[0]=='1')s.setChecked(true);
        else s.setChecked(false);

        s = (Switch)getActivity().findViewById(R.id.Bindo2);
        if(input[1]=='1')s.setChecked(true);
        else s.setChecked(false);

        s = (Switch)getActivity().findViewById(R.id.Bindo3);
        if(input[2]=='1')s.setChecked(true);
        else s.setChecked(false);


        s = (Switch)getActivity().findViewById(R.id.Bing1);
        if(input[3]=='1')s.setChecked(true);
        else s.setChecked(false);

        s = (Switch)getActivity().findViewById(R.id.Bing2);
        if(input[4]=='1')s.setChecked(true);
        else s.setChecked(false);

        s = (Switch)getActivity().findViewById(R.id.Bing3);
        if(input[5]=='1')s.setChecked(true);
        else s.setChecked(false);


        s = (Switch)getActivity().findViewById(R.id.bol1);
        if(input[6]=='1')s.setChecked(true);
        else s.setChecked(false);

        s = (Switch)getActivity().findViewById(R.id.bol2);
        if(input[7]=='1')s.setChecked(true);
        else s.setChecked(false);

        s = (Switch)getActivity().findViewById(R.id.bol3);
        if(input[8]=='1')s.setChecked(true);
        else s.setChecked(false);
    }



    public void changeSwitchstate(View view){
        saveSetting();
    }
}
