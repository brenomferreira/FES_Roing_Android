package com.example.fes_roing_android.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.example.fes_roing_android.R;
import com.example.fes_roing_android.constantes.ParametrosConstantes;
import com.example.fes_roing_android.util.SecurityPreferences;

public class ParametrosActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametros);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.check_CH12 = findViewById(R.id.checkboxCH12);
        // this.mViewHolder.check_CH12 = (CheckBox) findViewById(R.id.checkboxCH12);

        this.mViewHolder.check_CH12.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.checkboxCH12) {
            if (this.mViewHolder.check_CH12.isChecked()) {
                this.mSecurityPreferences.storeString(ParametrosConstantes.AtiveCH12, ParametrosConstantes.CONFIRMADO_CH);
            } else {
                this.mSecurityPreferences.storeString(ParametrosConstantes.AtiveCH12, ParametrosConstantes.NAO_CONFIRMADO_CH);

            }


        }

    }


    private static class ViewHolder {

        CheckBox check_CH12;
        CheckBox check_CH34;
        CheckBox check_CH56;
        CheckBox check_CH78;

    }
}
