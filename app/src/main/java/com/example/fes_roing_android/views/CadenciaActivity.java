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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fes_roing_android.R;
import com.example.fes_roing_android.constantes.ParametrosConstantes;
import com.example.fes_roing_android.util.SecurityPreferences;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class CadenciaActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, RadioGroup.OnCheckedChangeListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    DecimalFormat decimalFormat = new DecimalFormat("#.00", new DecimalFormatSymbols(new Locale("en", "US")));


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
        this.mViewHolder.area_radioButton.setOnCheckedChangeListener(this);
        setEnableViews(this.mViewHolder.area_radioButton, false);


        // habilitar botao done
        this.mViewHolder.editText_Cadeira.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_Drive.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_Freq.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_Voga.setImeOptions(EditorInfo.IME_ACTION_DONE);

        this.mViewHolder.radio_1_1 = (RadioButton) findViewById(R.id.radio_1_1);
        this.mViewHolder.radio_1_1.setChecked(true);
        this.mViewHolder.radio_1_2 = (RadioButton) findViewById(R.id.radio_1_2);
        this.mViewHolder.radio_1_3 = (RadioButton) findViewById(R.id.radio_1_3);
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
        if (id == R.id.editText_Drive) {
            if (event == null) {
                String text = view.getText().toString();
                this.mSecurityPreferences.storeString(ParametrosConstantes.valorDrive, text);
                calcularParametros();
            }
        }


        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {


        if (checkedId == R.id.radio_1_1) {
            Toast.makeText(getApplicationContext(), "Escolha: mov 1 para 1",
                    Toast.LENGTH_SHORT).show();
            this.mSecurityPreferences.storeString(ParametrosConstantes.cadencia, "1");


        } else if (checkedId == R.id.radio_1_2) {
            Toast.makeText(getApplicationContext(), "Escolha: mov 1 para 2",
                    Toast.LENGTH_SHORT).show();
            this.mSecurityPreferences.storeString(ParametrosConstantes.cadencia, "2");


        } else {
            Toast.makeText(getApplicationContext(), "Escolha: mov 1 para 3",
                    Toast.LENGTH_SHORT).show();
            this.mSecurityPreferences.storeString(ParametrosConstantes.cadencia, "3");


        }

        calcularParametros();
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
        RadioButton radio_1_1;
        RadioButton radio_1_2;
        RadioButton radio_1_3;


    }


    public void calcularParametros() {
        float caso = (float) parseInt(this.mSecurityPreferences.getStoreString(ParametrosConstantes.cadencia));

        if (this.mViewHolder.checkBox_Voga.isChecked()) {
            float voga = (float) parseInt(this.mSecurityPreferences.getStoreString(ParametrosConstantes.valorVoga));
            float calc = 60 / (voga * (1 + caso));
            String text = decimalFormat.format(calc);
            this.mSecurityPreferences.storeString(ParametrosConstantes.valorDrive, text);
            this.mViewHolder.editText_Drive.setText(text);

        }
        if (this.mViewHolder.checkBox_Drive.isChecked()) {
            float drive = Float.parseFloat(this.mSecurityPreferences.getStoreString(ParametrosConstantes.valorDrive));
            float calc = 60 / (drive * (1 + caso));
            String text =  "" +
                    Math.floor(calc);
            this.mSecurityPreferences.storeString(ParametrosConstantes.valorVoga, text);
            this.mViewHolder.editText_Voga.setText(text);

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
