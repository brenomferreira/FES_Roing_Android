package com.example.fes_roing_android.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;

import com.example.fes_roing_android.R;

public class CadenciaActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadencia);



        // Onclick
        this.mViewHolder.checkBox_Voga = (CheckBox) findViewById(R.id.checkBox_Voga);
        this.mViewHolder.checkBox_Voga.setOnClickListener(this);
        this.mViewHolder.checkBox_Drive = (CheckBox) findViewById(R.id.checkBox_Drive);
        this.mViewHolder.checkBox_Drive.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.checkBox_Voga){



        }

    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        return false;
    }


    private static class ViewHolder {

        CheckBox checkBox_Voga;
        CheckBox checkBox_Drive;



    }

}
