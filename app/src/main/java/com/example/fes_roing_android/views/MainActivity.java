package com.example.fes_roing_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fes_roing_android.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ViewHolder mViewHolder = new ViewHolder();
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mViewHolder.conecta_bluetooth = (Button) findViewById(R.id.btn_conectBTh);
        this.mViewHolder.parametros = (Button) findViewById(R.id.btn_parametros);

        this.mViewHolder.parametros.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_parametros) {
            // Lógica de navegação.
            // Intent intent = new Intent(getApplicationContext(), ParametrosActivity.class);
            Intent intent = new Intent(this, ParametrosActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_conectBTh) {
            // logica da conexão

        }


    }


    private static class ViewHolder {
        Button conecta_bluetooth;
        Button parametros;
    }


}



