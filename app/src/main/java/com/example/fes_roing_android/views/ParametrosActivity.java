package com.example.fes_roing_android.views;

import android.content.Intent;
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
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.fes_roing_android.R;
import com.example.fes_roing_android.constantes.ParametrosConstantes;
import com.example.fes_roing_android.util.ConnectedThread;
import com.example.fes_roing_android.util.SecurityPreferences;
import com.example.fes_roing_android.util.SocketHandler;

public class ParametrosActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, SeekBar.OnSeekBarChangeListener {

    ConnectedThread connectedThread;
    boolean conenexao_BT;
    int corrente_CH12, corrente_CH34, corrente_CH56, corrente_CH78, freq, largPulso, modo;
    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parametros);


// util packages
        this.mSecurityPreferences = new SecurityPreferences(this);

//SeekBars
        mViewHolder.seekBar_CH12 = (SeekBar) findViewById(R.id.seekBar_CH12);
        mViewHolder.seekBar_CH12.setOnSeekBarChangeListener(this);
        mViewHolder.seekBar_CH34 = (SeekBar) findViewById(R.id.seekBar_CH34);
        mViewHolder.seekBar_CH34.setOnSeekBarChangeListener(this);
        mViewHolder.seekBar_CH56 = (SeekBar) findViewById(R.id.seekBar_CH56);
        mViewHolder.seekBar_CH56.setOnSeekBarChangeListener(this);
        mViewHolder.seekBar_CH78 = (SeekBar) findViewById(R.id.seekBar_CH78);
        mViewHolder.seekBar_CH78.setOnSeekBarChangeListener(this);
        mViewHolder.seekBar_Freq = (SeekBar) findViewById(R.id.seekBar_Freq);
        mViewHolder.seekBar_Freq.setOnSeekBarChangeListener(this);
        mViewHolder.seekBar_LP = (SeekBar) findViewById(R.id.seekBar_LP);
        mViewHolder.seekBar_LP.setOnSeekBarChangeListener(this);

// TextViews
        mViewHolder.valor_previo = (TextView) findViewById(R.id.textView_valor_previo);

// OnClick
        this.mViewHolder.btn_voltar = (Button) findViewById(R.id.btn_voltar_ac_Parametros);
        this.mViewHolder.btn_voltar.setOnClickListener(this);
        this.mViewHolder.check_CH12 = (CheckBox) findViewById(R.id.checkboxCH12);
        this.mViewHolder.check_CH12.setOnClickListener(this);
        this.mViewHolder.check_CH34 = (CheckBox) findViewById(R.id.checkboxCH34);
        this.mViewHolder.check_CH34.setOnClickListener(this);
        this.mViewHolder.check_CH56 = (CheckBox) findViewById(R.id.checkboxCH56);
        this.mViewHolder.check_CH56.setOnClickListener(this);
        this.mViewHolder.check_CH78 = (CheckBox) findViewById(R.id.checkboxCH78);
        this.mViewHolder.check_CH78.setOnClickListener(this);


// OnKey
        this.mViewHolder.editText_CH12 = (EditText) findViewById(R.id.editText_CH12);
        this.mViewHolder.editText_CH12.setOnEditorActionListener(this);
        this.mViewHolder.editText_CH34 = (EditText) findViewById(R.id.editText_CH34);
        this.mViewHolder.editText_CH34.setOnEditorActionListener(this);
        this.mViewHolder.editText_CH56 = (EditText) findViewById(R.id.editText_CH56);
        this.mViewHolder.editText_CH56.setOnEditorActionListener(this);
        this.mViewHolder.editText_CH78 = (EditText) findViewById(R.id.editText_CH78);
        this.mViewHolder.editText_CH78.setOnEditorActionListener(this);
        this.mViewHolder.editText_Freq = (EditText) findViewById(R.id.editText_Freq);
        this.mViewHolder.editText_Freq.setOnEditorActionListener(this);
        this.mViewHolder.editText_LP = (EditText) findViewById(R.id.editText_LP);
        this.mViewHolder.editText_LP.setOnEditorActionListener(this);

        //Layout
        this.mViewHolder.area_56 = (LinearLayout) findViewById(R.id.area_CH56);
        this.mViewHolder.area_78 = (LinearLayout) findViewById(R.id.area_CH78);


        // habilitar/desabilitar layouts no start
        this.setEnableViews(this.mViewHolder.area_56, false);
        this.setEnableViews(this.mViewHolder.area_78, false);

        // habilitar botao done
        this.mViewHolder.editText_CH12.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_CH34.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_CH56.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_CH78.setImeOptions(EditorInfo.IME_ACTION_DONE);



        /*Conexão Bluetooth*/
        if (this.mSecurityPreferences.getStoredString(ParametrosConstantes.Status_BT) == ParametrosConstantes.Conectado_BT_True) {
            connectedThread = new ConnectedThread(SocketHandler.getSocket());
            connectedThread.start();
            conenexao_BT = true;
        } else {
            conenexao_BT = false;
        }

        getAllMemory();

        /*Atualiza valores no display*/
        this.mViewHolder.seekBar_CH12.setProgress(corrente_CH12);
        this.mViewHolder.seekBar_CH34.setProgress(corrente_CH34);
        this.mViewHolder.seekBar_CH56.setProgress(corrente_CH56);
        this.mViewHolder.seekBar_CH78.setProgress(corrente_CH78);
        this.mViewHolder.seekBar_Freq.setProgress(freq);
        this.mViewHolder.seekBar_LP.setProgress(largPulso);

    } // Fim onCreate

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.checkboxCH12) {
            if (this.mViewHolder.check_CH12.isChecked()) {
// True
                this.mSecurityPreferences.storeString(ParametrosConstantes.AtiveCH12, ParametrosConstantes.CONFIRMADO_CH); // (Key, valor)
                this.setEnableViews(this.mViewHolder.area_56, true);

            } // Fim se check true
            else {
// False
                this.mSecurityPreferences.storeString(ParametrosConstantes.AtiveCH12, ParametrosConstantes.NAO_CONFIRMADO_CH); // (Key, valor)
                this.setEnableViews(this.mViewHolder.area_56, false);

            } // Fim se check false

        } // Fim Lógica checkboxCH12


        if (id == R.id.checkboxCH34) {
            if (this.mViewHolder.check_CH34.isChecked()) {
// True
                this.mSecurityPreferences.storeString(ParametrosConstantes.AtiveCH34, ParametrosConstantes.CONFIRMADO_CH); // (Key, valor)
                this.setEnableViews(this.mViewHolder.area_78, true);

            } // Fim se check true
            else {
// False
                this.mSecurityPreferences.storeString(ParametrosConstantes.AtiveCH34, ParametrosConstantes.NAO_CONFIRMADO_CH); // (Key, valor)
                this.setEnableViews(this.mViewHolder.area_78, false);

            } // Fim se check false

        } // Fim Lógica checkboxCH34

        if (id == R.id.checkboxCH56) {
            if (this.mViewHolder.check_CH56.isChecked()) {
// True
                this.mSecurityPreferences.storeString(ParametrosConstantes.AtiveCH56, ParametrosConstantes.CONFIRMADO_CH); // (Key, valor)

            } // Fim se check true
            else {
// False
                this.mSecurityPreferences.storeString(ParametrosConstantes.AtiveCH56, ParametrosConstantes.NAO_CONFIRMADO_CH); // (Key, valor)

            } // Fim se check false

        } // Fim Lógica checkboxCH56

        if (id == R.id.checkboxCH78) {
            if (this.mViewHolder.check_CH78.isChecked()) {
// True
                this.mSecurityPreferences.storeString(ParametrosConstantes.AtiveCH78, ParametrosConstantes.CONFIRMADO_CH); // (Key, valor)

            } // Fim se check true
            else {
// False
                this.mSecurityPreferences.storeString(ParametrosConstantes.AtiveCH78, ParametrosConstantes.NAO_CONFIRMADO_CH); // (Key, valor)

            } // Fim se check false

        } // Fim Lógica checkboxCH78


        if (id == R.id.btn_voltar_ac_Parametros) {
            Intent intent2 = new Intent(this, MainActivity.class); // chama outra view
            startActivity(intent2);

        } // Fim lógica btn_voltar_ac_Parametros
    } // Fim onClick

//    @Override
//    public boolean onKey(View view, int keyCode, KeyEvent event) {
//        int id = view.getId();
//        int evento_key = event.getKeyCode();
//        //int evento_key = keyCode;
//
//        if (id == R.id.editText_CH12) {
//            if (keyCode == 66) { // 66 corresponde à tecla ENTER
//                corrente_CH12 = (int) Integer.parseInt(String.valueOf(this.mViewHolder.editText_CH12.getText()));
//                this.mViewHolder.seekBar_CH12.setProgress(corrente_CH12);
//            }
//
//        } // Fim dos eventos teclados ch12
//
//        if (id == R.id.editText_CH34) {
//            if (keyCode == 66) { // 66 corresponde à tecla ENTER
//                corrente_CH34 = (int) Integer.parseInt(String.valueOf(this.mViewHolder.editText_CH34.getText()));
//                this.mViewHolder.seekBar_CH34.setProgress(corrente_CH34);
//            }
//
//        } // Fim dos eventos teclados ch34
//
//
//        if (id == R.id.editText_CH56) {
//            if (keyCode == 66) { // 66 corresponde à tecla ENTER
//                corrente_CH56 = (int) Integer.parseInt(String.valueOf(this.mViewHolder.editText_CH56.getText()));
//                this.mViewHolder.seekBar_CH56.setProgress(corrente_CH56);
//            }
//
//        } // Fim dos eventos teclados ch56
//
//        if (id == R.id.editText_CH78) {
//            if (keyCode == 66) { // 66 corresponde à tecla ENTER
//                corrente_CH78 = (int) Integer.parseInt(String.valueOf(this.mViewHolder.editText_CH78.getText()));
//                this.mViewHolder.seekBar_CH78.setProgress(corrente_CH78);
//            }
//
//        } // Fim dos eventos teclados ch78
//
//        return false;
//
//    }// Fim Método onKey

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int id = seekBar.getId();
        getAllMemory();

        if (id == R.id.seekBar_CH12) {
            this.mViewHolder.editText_CH12.setText("" + progress);
            corrente_CH12 = progress;
            this.mViewHolder.editText_CH12.setText(""+ corrente_CH12);

        } // Fim slider ch12

        if (id == R.id.seekBar_CH34) {
            this.mViewHolder.editText_CH34.setText("" + progress);
            corrente_CH34 = progress;
        } // Fim slider ch12

        if (id == R.id.seekBar_CH56) {
            this.mViewHolder.editText_CH56.setText("" + progress);
            corrente_CH56 = progress;
        } // Fim slider ch12

        if (id == R.id.seekBar_CH78) {
            this.mViewHolder.editText_CH78.setText("" + progress);
            corrente_CH78 = progress;
        } // Fim slider ch12

        if (id == R.id.seekBar_Freq) {
            this.mViewHolder.editText_Freq.setText("" + progress);
            freq = progress;
        } // Fim slider ch12

        if (id == R.id.seekBar_LP) {
            this.mViewHolder.editText_LP.setText("" + progress);
            largPulso = progress;
        } // Fim slider ch12

        setAllMemory();
    }// Fim SeekBar OnProgressChanged


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    private void setEnableLinearLayout(boolean enable, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            v.setEnabled(enable);
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

    public void setAllMemory() {

        this.mSecurityPreferences.storeInt(ParametrosConstantes.Valor_CH12, corrente_CH12);
        this.mSecurityPreferences.storeInt(ParametrosConstantes.Valor_CH34, corrente_CH34);
        this.mSecurityPreferences.storeInt(ParametrosConstantes.Valor_CH56, corrente_CH56);
        this.mSecurityPreferences.storeInt(ParametrosConstantes.Valor_CH78, corrente_CH78);
        this.mSecurityPreferences.storeInt(ParametrosConstantes.Valor_Freq_Estim, freq);
        this.mSecurityPreferences.storeInt(ParametrosConstantes.Valor_LP, largPulso);
        this.mSecurityPreferences.storeInt(ParametrosConstantes.Valor_Mode, modo);


    }

    public void getAllMemory() {

        corrente_CH12 = this.mSecurityPreferences.getStoredInt(ParametrosConstantes.Valor_CH12);
        corrente_CH34 = this.mSecurityPreferences.getStoredInt(ParametrosConstantes.Valor_CH34);
        corrente_CH56 = this.mSecurityPreferences.getStoredInt(ParametrosConstantes.Valor_CH56);
        corrente_CH78 = this.mSecurityPreferences.getStoredInt(ParametrosConstantes.Valor_CH78);
        freq = this.mSecurityPreferences.getStoredInt(ParametrosConstantes.Valor_Freq_Estim);
        largPulso = this.mSecurityPreferences.getStoredInt(ParametrosConstantes.Valor_LP);
        modo = this.mSecurityPreferences.getStoredInt(ParametrosConstantes.Valor_Mode);


    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
    int id = view.getId();

        if (id == R.id.editText_CH12) {
            if (event == null) {
                corrente_CH12 = (int) Integer.parseInt(String.valueOf(this.mViewHolder.editText_CH12.getText()));
                this.mViewHolder.seekBar_CH12.setProgress(corrente_CH12);
            }
        }
        if (id == R.id.editText_CH34) {
            if (event == null) {
                corrente_CH34 = (int) Integer.parseInt(String.valueOf(this.mViewHolder.editText_CH34.getText()));
                this.mViewHolder.seekBar_CH34.setProgress(corrente_CH34);
            }
        }
        if (id == R.id.editText_CH56) {
            if (event == null) {
                corrente_CH56 = (int) Integer.parseInt(String.valueOf(this.mViewHolder.editText_CH56.getText()));
                this.mViewHolder.seekBar_CH56.setProgress(corrente_CH56);
            }
        }
        if (id == R.id.editText_CH78) {
            if (event == null) {
                corrente_CH78 = (int) Integer.parseInt(String.valueOf(this.mViewHolder.editText_CH78.getText()));
                this.mViewHolder.seekBar_CH78.setProgress(corrente_CH78);
            }
        }
        if (id == R.id.editText_Freq) {
            if (event == null) {
                freq = (int) Integer.parseInt(String.valueOf(this.mViewHolder.editText_Freq.getText()));
                this.mViewHolder.seekBar_Freq.setProgress(freq);
            }
        }
        if (id == R.id.editText_LP) {
            if (event == null) {
                largPulso = (int) Integer.parseInt(String.valueOf(this.mViewHolder.editText_LP.getText()));
                this.mViewHolder.seekBar_LP.setProgress(largPulso);
            }
        }





        return false;



    }



    private static class ViewHolder {
        Button btn_voltar;
        Button Envi_Parametros;
        CheckBox check_CH12;
        CheckBox check_CH34;
        CheckBox check_CH56;
        CheckBox check_CH78;
        EditText editText_CH12;
        EditText editText_CH34;
        EditText editText_CH56;
        EditText editText_CH78;
        EditText editText_Freq;
        EditText editText_LP;
        SeekBar seekBar_CH12;
        SeekBar seekBar_CH34;
        SeekBar seekBar_CH56;
        SeekBar seekBar_CH78;
        SeekBar seekBar_Freq;
        SeekBar seekBar_LP;
        TextView valor_previo;

        //layout
        LinearLayout area_56;
        LinearLayout area_78;

    }// Fim class ViewHolder

}
