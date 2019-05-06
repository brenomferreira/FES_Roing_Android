package com.example.fes_roing_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import com.example.fes_roing_android.constantes.ParametrosConstantes;
import com.example.fes_roing_android.R;
import com.example.fes_roing_android.util.SecurityPreferences;

public class ParametrosActivity extends AppCompatActivity implements View.OnClickListener {
    
    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_parametros);
        
        this.mSecurityPreferences = new SecurityPreferences(this);
        this.mViewHolder.btn_voltar = (Button) findViewById(R.id.btn_voltar_ac_Parametros);
        this.mViewHolder.btn_voltar.setOnClickListener(this);
        this.mViewHolder.check_CH12 = (CheckBox) findViewById(R.id.checkboxCH12);
        this.mViewHolder.check_CH12.setOnClickListener(this);
    
    } // Fim onCreate
    
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.checkboxCH12) {
            if (this.mViewHolder.check_CH12.isChecked()) {
                // True
                this.mSecurityPreferences.storeString(ParametrosConstantes.AtiveCH12, ParametrosConstantes.CONFIRMADO_CH); // (Key, valor)

            } // Fim se check true
            else {
                // False
                this.mSecurityPreferences.storeString(ParametrosConstantes.AtiveCH12, ParametrosConstantes.NAO_CONFIRMADO_CH); // (Key, valor)

            } // Fim se check false

        } // Fim Lógica checkboxCH12
        if (id == R.id.btn_voltar_ac_Parametros) {
            Intent intent2 = new Intent(this, MainActivity.class); // chama outra view
            startActivity(intent2);

        } // Fim lógica btn_voltar_ac_Parametros
    
    } // Fim onClick

    private static class ViewHolder {
        Button btn_voltar;
        CheckBox check_CH12;
        CheckBox check_CH34;
        CheckBox check_CH56;
        CheckBox check_CH78;

    }// Fim class ViewHolder
    
}
