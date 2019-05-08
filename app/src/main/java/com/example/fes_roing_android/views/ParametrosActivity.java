package com.example.fes_roing_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.fes_roing_android.R;
import com.example.fes_roing_android.constantes.ParametrosConstantes;
import com.example.fes_roing_android.util.SecurityPreferences;

public class ParametrosActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener, SeekBar.OnSeekBarChangeListener {

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
        this.mViewHolder.editText_CH12.setOnKeyListener(this);

        //Layout
        this.mViewHolder.area_56 = (LinearLayout) findViewById(R.id.area_CH56);
        this.mViewHolder.area_78 = (LinearLayout) findViewById(R.id.area_CH78);



        // habilitar/desabilitar layouts no start
        this.setEnableViews(this.mViewHolder.area_56, false);
        this.setEnableViews(this.mViewHolder.area_78, false);



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

        } // Fim Lógica checkboxCH12





        if (id == R.id.btn_voltar_ac_Parametros) {
            Intent intent2 = new Intent(this, MainActivity.class); // chama outra view
            startActivity(intent2);

        } // Fim lógica btn_voltar_ac_Parametros

    } // Fim onClick

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        int id = view.getId();
        int evento_key = event.getKeyCode();

        if (id == R.id.editText_CH12) {
            if (evento_key == 66) { // 66 corresponde à tecla ENTER
                this.mViewHolder.valor_previo.setText("teste");
                int value = (int) Integer.parseInt(String.valueOf(this.mViewHolder.editText_CH12.getText()));
                this.mViewHolder.seekBar_CH12.setProgress(value);

            }

        } // Fim dos eventos teclados ch12

        return false;
    }// Fim Método onKey

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int id = seekBar.getId();
        if (id == R.id.seekBar_CH12) {

            this.mViewHolder.editText_CH12.setText("" + progress);

        }
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
        SeekBar seekBar_CH12;
        SeekBar seekBar_CH34;
        SeekBar seekBar_CH56;
        SeekBar seekBar_CH78;
        TextView valor_previo;

        //layout
        LinearLayout area_56;
        LinearLayout area_78;

    }// Fim class ViewHolder

}
