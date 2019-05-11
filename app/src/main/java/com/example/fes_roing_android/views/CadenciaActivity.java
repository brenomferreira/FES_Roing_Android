package com.example.fes_roing_android.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.fes_roing_android.R;
import com.example.fes_roing_android.constantes.ParametrosConstantes;
import com.example.fes_roing_android.util.SecurityPreferences;

public class CadenciaActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadencia);



        // Onclick
        this.mViewHolder.checkBox_Voga = (CheckBox) findViewById(R.id.checkBox_Voga);
        this.mViewHolder.checkBox_Voga.setOnClickListener(this);
        this.mViewHolder.checkBox_Drive = (CheckBox) findViewById(R.id.checkBox_Drive);
        this.mViewHolder.checkBox_Drive.setOnClickListener(this);

        // OnKey
        this.mViewHolder.editText_Cadeira = (EditText) findViewById(R.id.editText_Cadeira);
        this.mViewHolder.editText_Cadeira.setOnKeyListener(this);
        this.mViewHolder.editText_Drive = (EditText) findViewById(R.id.editText_Drive);
        this.mViewHolder.editText_Drive.setOnKeyListener(this);
        this.mViewHolder.editText_Freq = (EditText) findViewById(R.id.editText_Freq);
        this.mViewHolder.editText_Freq.setOnKeyListener(this);
        this.mViewHolder.editText_Voga = (EditText) findViewById(R.id.editText_Voga);
        this.mViewHolder.editText_Voga.setOnKeyListener(this);

        //Areas layout
        this.mViewHolder.area_configDrive = (LinearLayout) findViewById(R.id.area_config_Drive);
        this.mViewHolder.area_configVoga = (LinearLayout) findViewById(R.id.area_config_Voga);
        this.mViewHolder.area_radioButton = (LinearLayout) findViewById(R.id.area_radioButton);
        setEnableViews(this.mViewHolder.area_radioButton, false);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.checkBox_Voga) {
            if (this.mViewHolder.checkBox_Voga.isChecked()) {
                setEnableViews(this.mViewHolder.area_configDrive, false);
                setEnableViews(this.mViewHolder.area_radioButton, true);
                this.mViewHolder.checkBox_Drive.setChecked(false);
            } else {
                setEnableViews(this.mViewHolder.area_configDrive, true);
                if (!mViewHolder.checkBox_Drive.isChecked()) {
                    setEnableViews(this.mViewHolder.area_radioButton, false);
                }
            }
        }// Fim lógica CheckBox Voga

        if (id == R.id.checkBox_Drive) {
            if (this.mViewHolder.checkBox_Drive.isChecked()) {
                setEnableViews(this.mViewHolder.area_configVoga, false);
                setEnableViews(this.mViewHolder.area_radioButton, true);
                this.mViewHolder.checkBox_Voga.setChecked(false);

            } else {
                setEnableViews(this.mViewHolder.area_configVoga, true);
                if (!mViewHolder.checkBox_Voga.isChecked()){
                setEnableViews(this.mViewHolder.area_radioButton, false);}
            }
        }// Fim lógica CheckBox Drive

    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        int id = view.getId();

        if (id == R.id.editText_Voga){
           if (keyCode == 66){
               calcularParametros();

           }

        }







        return false;

    }


    private static class ViewHolder {

        CheckBox checkBox_Voga;
        CheckBox checkBox_Drive;
        EditText editText_Voga;
        EditText editText_Drive;
        EditText editText_Freq;
        EditText editText_Cadeira;


        //Areas layout
        LinearLayout area_configDrive;
        LinearLayout area_configVoga;
        LinearLayout area_radioButton;



    }


    public void calcularParametros(){

        if (this.mViewHolder.checkBox_Voga.isChecked()){


            this.mViewHolder.editText_Drive.setText("999");
        }
        if (this.mViewHolder.checkBox_Drive.isChecked()){

            this.mViewHolder.editText_Voga.setText("999");

        }
    }

    public void setEnableViews(View v, boolean enable) {
        v.setEnabled(enable);
        if (v instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                View view = ((ViewGroup) v).getChildAt(i);
                setEnableViews(view, enable);
            }
        }
    }


}
