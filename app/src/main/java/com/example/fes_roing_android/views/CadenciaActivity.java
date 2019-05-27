package com.example.fes_roing_android.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
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

public class CadenciaActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, RadioGroup.OnCheckedChangeListener {

    DecimalFormat decimalFormat = new DecimalFormat("#0.00", new DecimalFormatSymbols(new Locale("en", "US")));
    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadencia);

        /*util packages*/
        this.mSecurityPreferences = new SecurityPreferences(this);


//        /*executar para limpar memoria*/
//        File sharedPreferenceFile = new File("/data/data/" + getPackageName() + "/shared_prefs/");
//        File[] listFiles = sharedPreferenceFile.listFiles();
//        for (File file : listFiles) {
//            file.delete();
//        }

        /*TextViews*/
        this.mViewHolder.text_set_treino_01 = (TextView) findViewById(R.id.textView_setTreino_01);
        this.mViewHolder.text_set_treino_02 = (TextView) findViewById(R.id.textView_setTreino_02);
        this.mViewHolder.text_set_treino_03 = (TextView) findViewById(R.id.textView_setTreino_03);

        /*Onclick*/
        this.mViewHolder.checkBox_Voga = (CheckBox) findViewById(R.id.checkBox_Voga);
        this.mViewHolder.checkBox_Voga.setOnClickListener(this);
        this.mViewHolder.checkBox_Drive = (CheckBox) findViewById(R.id.checkBox_Drive);
        this.mViewHolder.checkBox_Drive.setOnClickListener(this);
        this.mViewHolder.set_treino_01 = (Button) findViewById(R.id.btn_setTreino_01);
        this.mViewHolder.set_treino_01.setOnClickListener(this);
        this.mViewHolder.set_treino_02 = (Button) findViewById(R.id.btn_setTreino_02);
        this.mViewHolder.set_treino_02.setOnClickListener(this);
        this.mViewHolder.set_treino_03 = (Button) findViewById(R.id.btn_setTreino_03);
        this.mViewHolder.set_treino_03.setOnClickListener(this);
        this.mViewHolder.mais_cadeira = (Button) findViewById(R.id.btn_mais_Cadeira);
        this.mViewHolder.mais_cadeira.setOnClickListener(this);
        this.mViewHolder.mais_drive = (Button) findViewById(R.id.btn_mais_Drive);
        this.mViewHolder.mais_drive.setOnClickListener(this);
        this.mViewHolder.mais_freq = (Button) findViewById(R.id.btn_mais_Freq);
        this.mViewHolder.mais_freq.setOnClickListener(this);
        this.mViewHolder.mais_voga = (Button) findViewById(R.id.btn_mais_Voga);
        this.mViewHolder.mais_voga.setOnClickListener(this);
        this.mViewHolder.menos_cadeira = (Button) findViewById(R.id.btn_menos_Cadeira);
        this.mViewHolder.menos_cadeira.setOnClickListener(this);
        this.mViewHolder.menos_drive = (Button) findViewById(R.id.btn_menos_Drive);
        this.mViewHolder.menos_drive.setOnClickListener(this);
        this.mViewHolder.menos_freq = (Button) findViewById(R.id.btn_menos_Freq);
        this.mViewHolder.menos_freq.setOnClickListener(this);
        this.mViewHolder.menos_voga = (Button) findViewById(R.id.btn_menos_Voga);
        this.mViewHolder.menos_voga.setOnClickListener(this);


        /*onEditorAction*/
        this.mViewHolder.editText_Cadeira = (EditText) findViewById(R.id.editText_Cadeira);
        this.mViewHolder.editText_Cadeira.setOnEditorActionListener(this);
        this.mViewHolder.editText_Drive = (EditText) findViewById(R.id.editText_Drive);
        this.mViewHolder.editText_Drive.setOnEditorActionListener(this);
        this.mViewHolder.editText_Freq = (EditText) findViewById(R.id.editText_Freq);
        this.mViewHolder.editText_Freq.setOnEditorActionListener(this);
        this.mViewHolder.editText_Voga = (EditText) findViewById(R.id.editText_Voga);
        this.mViewHolder.editText_Voga.setOnEditorActionListener(this);


        /*setEnableViews*/
        this.mViewHolder.area_configDrive = (LinearLayout) findViewById(R.id.area_config_Drive);
        this.mViewHolder.area_configVoga = (LinearLayout) findViewById(R.id.area_config_Voga);
        this.mViewHolder.area_radioButton = (RadioGroup) findViewById(R.id.area_radioButton);
        this.mViewHolder.area_radioButton.setOnCheckedChangeListener(this);
        setEnableViews(this.mViewHolder.area_radioButton, false);


        /*onEditorAction IME_ACTION_DONE*/
        this.mViewHolder.editText_Cadeira.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_Drive.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_Freq.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_Voga.setImeOptions(EditorInfo.IME_ACTION_DONE);

        /*RadioButton*/
        this.mViewHolder.radio_1_1 = (RadioButton) findViewById(R.id.radio_1_1);
        this.mViewHolder.radio_1_1.setChecked(true);
        this.mViewHolder.radio_1_2 = (RadioButton) findViewById(R.id.radio_1_2);
        this.mViewHolder.radio_1_3 = (RadioButton) findViewById(R.id.radio_1_3);


        this.mViewHolder.editText_Voga.setHint("" + this.mSecurityPreferences.getStoreInt(ParametrosConstantes.valorVoga));
        this.mViewHolder.editText_Drive.setHint(decimalFormat.format(this.mSecurityPreferences.getStoreFloat(ParametrosConstantes.valorDrive)));
        this.mViewHolder.editText_Freq.setHint("" + this.mSecurityPreferences.getStoreInt(ParametrosConstantes.valorFreq));
        this.mViewHolder.editText_Cadeira.setHint("" + this.mSecurityPreferences.getStoreInt(ParametrosConstantes.valorCadeirea));

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        calcularParametros();

        /*get variaveis*/
        float drive = this.mSecurityPreferences.getStoreFloat(ParametrosConstantes.valorDrive);
        int freq = this.mSecurityPreferences.getStoreInt(ParametrosConstantes.valorFreq);
        float recovery = this.mSecurityPreferences.getStoreFloat(ParametrosConstantes.valorRecovery);
        int voga = this.mSecurityPreferences.getStoreInt(ParametrosConstantes.valorVoga);
        int cadeira = this.mSecurityPreferences.getStoreInt(ParametrosConstantes.valorCadeirea);
        /*Fim get variaveis*/

        String text_drive = decimalFormat.format(drive);
        String text_recovery = decimalFormat.format(recovery);

        String result = "[D:" + text_drive + " | R:" + text_recovery + " | V:" + voga + " | F:" + freq + "]";


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

        if (id == R.id.btn_setTreino_01) {
            this.mViewHolder.text_set_treino_01.setText(result);
        }
        if (id == R.id.btn_setTreino_02) {
            this.mViewHolder.text_set_treino_02.setText(result);

        }
        if (id == R.id.btn_setTreino_03) {
            this.mViewHolder.text_set_treino_03.setText(result);

        }

        /*Voga*/

        if (id == R.id.btn_mais_Voga) {
            if ((voga % 1) != 0) {
                voga = voga - (voga % 1);
            }

            voga = voga + 1;

        }
        if (id == R.id.btn_menos_Voga) {
            if ((voga % 1) != 0) {
                voga = voga - (voga % 1);
            }
            voga = voga - 1;
        }
        /*Drive*/
        if (id == R.id.btn_mais_Drive) {
            drive = drive * 10;
            if ((drive % 1) != 0) {
                drive = drive - (drive % 1);
            }
            drive = drive + 1f;
            drive = drive / 10;
        }
        if (id == R.id.btn_menos_Drive) {
            drive = drive * 10;
            if ((drive % 1) != 0) {
                drive = drive - (drive % 1);
            }
            drive = drive - 1f;
            drive = drive / 10;
        }
        /*freq*/
        if (id == R.id.btn_mais_Freq) {
            if ((freq % 5) != 0) {
                freq = freq - (freq % 5);
            }
            freq = freq + 5;
        }
        if (id == R.id.btn_menos_Freq) {
            if ((freq % 5) != 0) {
                freq = freq - (freq % 5);
            }
            freq = freq - 5;
        }
        /*cadeira*/
        if (id == R.id.btn_mais_Cadeira) {
            if ((cadeira % 5) != 0) {
                cadeira = cadeira - (cadeira % 5);
            }
            cadeira = cadeira + 5;
        }
        if (id == R.id.btn_menos_Cadeira) {
            if ((cadeira % 5) != 0) {
                cadeira = cadeira - (cadeira % 5);
            }
            cadeira = cadeira - 5;
        }



        /*Atualiza display*/
        text_drive = decimalFormat.format(drive);
        this.mViewHolder.editText_Voga.setText("" + voga);
        this.mViewHolder.editText_Cadeira.setText("" + cadeira);
        this.mViewHolder.editText_Freq.setText("" + freq);
        this.mViewHolder.editText_Drive.setText(text_drive);


        /*Fim Atualiza display*/

        /*Armazena variaveis*/
        this.mSecurityPreferences.storeFloat(ParametrosConstantes.valorDrive, drive);
        this.mSecurityPreferences.storeInt(ParametrosConstantes.valorFreq, freq);
        this.mSecurityPreferences.storeFloat(ParametrosConstantes.valorRecovery, recovery);
        this.mSecurityPreferences.storeInt(ParametrosConstantes.valorVoga, voga);
        this.mSecurityPreferences.storeInt(ParametrosConstantes.valorCadeirea, cadeira);
        /*Fim Armazena variaveis*/


    }// Fim OnClic

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {

        int id = view.getId();

        if (id == R.id.editText_Voga) {
            if (event == null) {
                String text = view.getText().toString();
                int valor = (int) Float.parseFloat(text);
                this.mSecurityPreferences.storeInt(ParametrosConstantes.valorVoga, valor);
                calcularParametros();
            }
        }
        if (id == R.id.editText_Drive) {
            if (event == null) {
                String text = view.getText().toString();
                float valor = Float.parseFloat(text);
                this.mSecurityPreferences.storeFloat(ParametrosConstantes.valorDrive, valor);
                calcularParametros();
            }
        }
        if (id == R.id.editText_Freq) {
            if (event == null) {
                String text = view.getText().toString();
                int valor = (int) Float.parseFloat(text);
                this.mSecurityPreferences.storeInt(ParametrosConstantes.valorFreq, valor);
                calcularParametros();
            }
        }
        if (id == R.id.editText_Cadeira) {
            if (event == null) {
                String text = view.getText().toString();
                int valor = (int) Float.parseFloat(text);
                this.mSecurityPreferences.storeInt(ParametrosConstantes.valorCadeirea, valor);
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
            this.mSecurityPreferences.storeInt(ParametrosConstantes.cadencia, 1);


        } else if (checkedId == R.id.radio_1_2) {
            Toast.makeText(getApplicationContext(), "Escolha: mov 1 para 2",
                    Toast.LENGTH_SHORT).show();
            this.mSecurityPreferences.storeInt(ParametrosConstantes.cadencia, 2);


        } else {
            Toast.makeText(getApplicationContext(), "Escolha: mov 1 para 3",
                    Toast.LENGTH_SHORT).show();
            this.mSecurityPreferences.storeInt(ParametrosConstantes.cadencia, 3);


        }

        calcularParametros();
    }

    public void calcularParametros() {
        int caso = (this.mSecurityPreferences.getStoreInt(ParametrosConstantes.cadencia));
        float drive = this.mSecurityPreferences.getStoreFloat(ParametrosConstantes.valorDrive);
        int voga = this.mSecurityPreferences.getStoreInt(ParametrosConstantes.valorVoga);

        if (this.mViewHolder.checkBox_Voga.isChecked()) {
            float calc1 = 60f / (voga * (1f + caso));
            String text = decimalFormat.format(calc1);
            this.mSecurityPreferences.storeFloat(ParametrosConstantes.valorDrive, calc1);
            this.mViewHolder.editText_Drive.setText(text);
        }
        if (this.mViewHolder.checkBox_Drive.isChecked()) {
            int calc = (int) (60 / (drive * (1 + caso)));
            String text = "" +
                    Math.floor(calc);
            this.mSecurityPreferences.storeInt(ParametrosConstantes.valorVoga, calc);
            this.mViewHolder.editText_Voga.setText(text);
        }

        // calculo do Recovery (por ultimo para pegar as atualizaçoes dos valores)
        float recovery = (60f / voga) - drive;
        this.mSecurityPreferences.storeFloat(ParametrosConstantes.valorRecovery, recovery);


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

    private static class ViewHolder {

        Button set_treino_01;
        Button set_treino_02;
        Button set_treino_03;

        Button mais_voga;
        Button menos_voga;
        Button mais_drive;
        Button menos_drive;
        Button mais_freq;
        Button menos_freq;
        Button mais_cadeira;
        Button menos_cadeira;


        CheckBox checkBox_Drive;
        CheckBox checkBox_Voga;
        EditText editText_Cadeira;
        EditText editText_Drive;
        EditText editText_Freq;
        EditText editText_Voga;
        TextView text_set_treino_01;
        TextView text_set_treino_02;
        TextView text_set_treino_03;

        //Areas layout
        LinearLayout area_configDrive;
        LinearLayout area_configVoga;
        RadioGroup area_radioButton;
        RadioButton radio_1_1;
        RadioButton radio_1_2;
        RadioButton radio_1_3;


    }


}
