package com.example.fes_roing_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.fes_roing_android.R;
import com.example.fes_roing_android.util.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.conecta_bluetooth = (Button) findViewById(R.id.btn_conectBTh);
        this.mViewHolder.parametros = (Button) findViewById(R.id.btn_parametros);

        this.mViewHolder.parametros.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

// importante para quando retornar para esta tela (Ciclo de vida )
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_parametros) {
            // Lógica de navegação.
            // Intent intent = new Intent(getApplicationContext(), ParametrosActivity.class);
            Intent intent = new Intent(this, ParametrosActivity.class); // chama outra view
            startActivity(intent);
        } // Fim da logica do botão

        else if (id == R.id.btn_conectBTh) {
            // logica da conexão

        }


    }


    private static class ViewHolder {
        Button conecta_bluetooth;
        Button parametros;
    } // Fim ViewHolder


}



