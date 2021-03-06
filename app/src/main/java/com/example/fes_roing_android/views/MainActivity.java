package com.example.fes_roing_android.views;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fes_roing_android.R;
import com.example.fes_roing_android.constantes.ParametrosConstantes;
import com.example.fes_roing_android.util.ConnectedThread;
import com.example.fes_roing_android.util.ListaDispositivos;
import com.example.fes_roing_android.util.SecurityPreferences;
import com.example.fes_roing_android.util.SocketHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    /*necessario para a conexao bluetooth*/
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private static final int SOLICITA_CONEXAO = 2;
    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static String MAC = null;
    ConnectedThread connectedThread;
    BluetoothAdapter mBlueAdapter;
    BluetoothDevice mDevice;
    BluetoothSocket mSocket;
    UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    boolean conexaoBT = false;
    boolean btLigado = false;
    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    /* FIM (necessario para a conexao bluetooth)*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);
        //adapter BT
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        this.mViewHolder.conecta_bluetooth = (Button) findViewById(R.id.btn_conectBTh);
        this.mViewHolder.conecta_bluetooth.setOnClickListener(this);
        this.mViewHolder.parametros = (Button) findViewById(R.id.btn_parametros);
        this.mViewHolder.parametros.setOnClickListener(this);
        this.mViewHolder.aquecimento = (Button) findViewById(R.id.btn_aquecimento);
        this.mViewHolder.aquecimento.setOnClickListener(this);

        /*testar se houve conexão bluetooth anteriormente*/
        try {
            connectedThread = new ConnectedThread(SocketHandler.getSocket());
            connectedThread.start();
            this.mSecurityPreferences.storeString(ParametrosConstantes.Status_BT, ParametrosConstantes.Conectado_BT_True);
            conexaoBT = true;
        } catch (Exception e) {
            this.mSecurityPreferences.storeString(ParametrosConstantes.Status_BT, ParametrosConstantes.Conectado_BT_False);
            conexaoBT = false;
        }


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

//todo////////////////////////////////////////////////////
        else if (id == R.id.btn_conectBTh) {
            // logica da conexão


            if (!mBlueAdapter.isEnabled()) {
                /*Ligar Bluetooth*/
                showToast(getString(R.string.Turning_on_BT)); //function
                /*intent to on bluetooth*/
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, REQUEST_ENABLE_BT);

            }

            while (true) {
                if (mBlueAdapter.isEnabled())
                    break;
            }


            if (!conexaoBT) {
                /*Pedir conexao Dispostivos pareados*/
                conexaoBT = !conexaoBT;

                /*conectar*/
                Intent abrelista = new Intent(MainActivity.this, ListaDispositivos.class);
                startActivityForResult(abrelista, SOLICITA_CONEXAO);
                this.mViewHolder.conecta_bluetooth.setText("Desconectar BT");


            } else {
                /*Desconectar dispositvo*/
                connectedThread.enviar("btDesconectado\n");
                this.mSecurityPreferences.storeString(ParametrosConstantes.Status_BT, ParametrosConstantes.Conectado_BT_False);


                try {
                    mSocket.close();
                    conexaoBT = !conexaoBT;
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "ocorreu um erro: " + e, Toast.LENGTH_LONG).show();
                }


                this.mViewHolder.conecta_bluetooth.setText("Conectar BT");

            }


        }// fim btn_conectBTH

        else if (id == R.id.btn_aquecimento) {
            // Lógica de navegação.
            // Intent intent = new Intent(getApplicationContext(), ParametrosActivity.class);
            Intent intent = new Intent(this, CadenciaActivity.class); // chama outra view
            startActivity(intent);
        } // Fim da logica do botão


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {


            case SOLICITA_CONEXAO:
                if (resultCode == Activity.RESULT_OK) {
                    MAC = data.getExtras().getString(ListaDispositivos.ENDERECO_MAC);
                    Toast.makeText(getApplicationContext(), "MAC: " + MAC, Toast.LENGTH_LONG).show();
                    mDevice = mBlueAdapter.getRemoteDevice(MAC);
                    try {
                        /*"00001101-0000-1000-8000-00805f9b34fb"*/
                        mSocket = mDevice.createRfcommSocketToServiceRecord(myUUID); // criar canal de comunicação
                        mSocket.connect();
                        SocketHandler.setSocket(mSocket);

                        conexaoBT = true;

                        connectedThread = new ConnectedThread(mSocket);
                        connectedThread.start();

/*
                        FIXME: ENVIAR A MENSAGEM APÓS CONECTAR
*/
                        connectedThread.enviar("btConectado");
                        this.mSecurityPreferences.storeString(ParametrosConstantes.Status_BT, ParametrosConstantes.Conectado_BT_True);


                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro " + e, Toast.LENGTH_LONG).show();
                        conexaoBT = false;
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Falha ao obter o MAC", Toast.LENGTH_LONG).show();
                    this.mViewHolder.conecta_bluetooth.setText("Conectar BT");
                    conexaoBT = false;

                }


        }
    }

    /**
     * @param msg
     */
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private static class ViewHolder {
        Button conecta_bluetooth;
        Button parametros;
        Button aquecimento;
    } // Fim ViewHolder


}



