package com.example.fes_roing_android.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.fes_roing_android.R;
import com.example.fes_roing_android.constantes.ParametrosConstantes;
import com.example.fes_roing_android.util.SecurityPreferences;

import static java.lang.Integer.parseInt;

public class CadenciaActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadencia);

        // util packages
        this.mSecurityPreferences = new SecurityPreferences(this);

        // Onclick
        this.mViewHolder.checkBox_Voga = (CheckBox) findViewById(R.id.checkBox_Voga);
        this.mViewHolder.checkBox_Voga.setOnClickListener(this);
        this.mViewHolder.checkBox_Drive = (CheckBox) findViewById(R.id.checkBox_Drive);
        this.mViewHolder.checkBox_Drive.setOnClickListener(this);


        // onEditorAction
        this.mViewHolder.editText_Cadeira = (EditText) findViewById(R.id.editText_Cadeira);
        this.mViewHolder.editText_Cadeira.setOnEditorActionListener(this);
        this.mViewHolder.editText_Drive = (EditText) findViewById(R.id.editText_Drive);
        this.mViewHolder.editText_Drive.setOnEditorActionListener(this);
        this.mViewHolder.editText_Freq = (EditText) findViewById(R.id.editText_Freq);
        this.mViewHolder.editText_Freq.setOnEditorActionListener(this);
        this.mViewHolder.editText_Voga = (EditText) findViewById(R.id.editText_Voga);
        this.mViewHolder.editText_Voga.setOnEditorActionListener(this);

        //Areas layout
        this.mViewHolder.area_configDrive = (LinearLayout) findViewById(R.id.area_config_Drive);
        this.mViewHolder.area_configVoga = (LinearLayout) findViewById(R.id.area_config_Voga);
        this.mViewHolder.area_radioButton = (RadioGroup) findViewById(R.id.area_radioButton);
        setEnableViews(this.mViewHolder.area_radioButton, false);


        // habilitar botao done
        this.mViewHolder.editText_Cadeira.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_Drive.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_Freq.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_Voga.setImeOptions(EditorInfo.IME_ACTION_DONE);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.checkBox_Voga) {
            if (this.mViewHolder.checkBox_Voga.isChecked()) {
                setEnableViews(this.mViewHolder.area_configDrive, false);
                setEnableViews(this.mViewHolder.area_configVoga, true);
                setEnableViews(this.mViewHolder.area_radioButton, true);
                if (this.mViewHolder.checkBox_Drive.isChecked()) {
                    this.mViewHolder.checkBox_Drive.setChecked(false);
                    setEnableViews(this.mViewHolder.area_configDrive, false);
                }
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
                setEnableViews(this.mViewHolder.area_configDrive, true);
                setEnableViews(this.mViewHolder.area_radioButton, true);
                if (this.mViewHolder.checkBox_Voga.isChecked()) {
                    this.mViewHolder.checkBox_Voga.setChecked(false);
                    setEnableViews(this.mViewHolder.area_configVoga, false);
                }
            } else {
                setEnableViews(this.mViewHolder.area_configVoga, true);
                if (!mViewHolder.checkBox_Voga.isChecked()) {
                    setEnableViews(this.mViewHolder.area_radioButton, false);
                }
            }
        }// Fim lógica CheckBox Voga


    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {

        int id = view.getId();

        if (id == R.id.editText_Voga) {
            if (event == null) {
                String text = view.getText().toString();
                this.mSecurityPreferences.storeString(ParametrosConstantes.valorVoga, text);
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
        RadioGroup area_radioButton;


    }


    public void calcularParametros() {

        if (this.mViewHolder.checkBox_Voga.isChecked()) {
            String text = mSecurityPreferences.getStoreString(ParametrosConstantes.valorVoga);
            int valor = Integer.parseInt(text);
            if(valor == 22)
                this.mViewHolder.editText_Cadeira.setText("doidera");


        }
        if (this.mViewHolder.checkBox_Drive.isChecked()) {
            this.mViewHolder.editText_Cadeira.setText("888");
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
