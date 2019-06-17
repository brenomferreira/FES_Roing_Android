package com.example.fes_roing_android.views;

import android.graphics.Color;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fes_roing_android.R;
import com.example.fes_roing_android.constantes.ParametrosConstantes;
import com.example.fes_roing_android.util.ConnectedThread;
import com.example.fes_roing_android.util.MyTask;
import com.example.fes_roing_android.util.SecurityPreferences;
import com.example.fes_roing_android.util.SocketHandler;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class CadenciaActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, RadioGroup.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    LineGraphSeries<DataPoint> series;
    Random aleatorio = new Random();
    DecimalFormat decimalFormat = new DecimalFormat("#0.00", new DecimalFormatSymbols(new Locale("en", "US")));
    ConnectedThread connectedThread;
    int cadeira;
    int cadencia;
    int cadencia_old;
    boolean mov_drive;
    boolean run_estim = false;
    boolean solic_estim = false;
    boolean conexaoBT;
    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private MyTask task;
    private float fs, spDrive, spRecovery;
    private ArrayList<Float> vetor_01 = new ArrayList<Float>();
    private ArrayList<Float> vetor_02 = new ArrayList<Float>();
    private ArrayList<Float> vetor_03 = new ArrayList<Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadencia);

        /*util packages*/
        this.mSecurityPreferences = new SecurityPreferences(this);

// /*executar para limpar memoria*/
// File sharedPreferenceFile = new File("/data/data/" + getPackageName() + "/shared_prefs/");
// File[] listFiles = sharedPreferenceFile.listFiles();
// for (File file : listFiles) {
// file.delete();
// }

        /*SeekBar*/
        this.mViewHolder.cadencia = (SeekBar) findViewById(R.id.seekBar_Cadencia);
        this.mViewHolder.posicaoCadeira = (SeekBar) findViewById(R.id.seekBar_PosiçaoCadeira);
        this.mViewHolder.cadencia.setOnSeekBarChangeListener(this);

        /*TextViews*/
        this.mViewHolder.text_set_treino_01 = (TextView) findViewById(R.id.textView_setTreino_01);
        this.mViewHolder.text_set_treino_02 = (TextView) findViewById(R.id.textView_setTreino_02);
        this.mViewHolder.text_set_treino_03 = (TextView) findViewById(R.id.textView_setTreino_03);

        /*Onclick*/

        this.mViewHolder.checkBox_Drive = (CheckBox) findViewById(R.id.checkBox_Drive);
        this.mViewHolder.checkBox_Drive.setOnClickListener(this);
        this.mViewHolder.checkBox_Voga = (CheckBox) findViewById(R.id.checkBox_Voga);
        this.mViewHolder.checkBox_Voga.setOnClickListener(this);
        this.mViewHolder.estim = (CheckBox) findViewById(R.id.checBox_estim);
        this.mViewHolder.estim.setOnClickListener(this);
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
        this.mViewHolder.set_treino_01 = (Button) findViewById(R.id.btn_setTreino_01);
        this.mViewHolder.set_treino_01.setOnClickListener(this);
        this.mViewHolder.set_treino_02 = (Button) findViewById(R.id.btn_setTreino_02);
        this.mViewHolder.set_treino_02.setOnClickListener(this);
        this.mViewHolder.set_treino_03 = (Button) findViewById(R.id.btn_setTreino_03);
        this.mViewHolder.set_treino_03.setOnClickListener(this);
        this.mViewHolder.start_treino_01 = (Button) findViewById(R.id.btn_startTreino01);
        this.mViewHolder.start_treino_01.setOnClickListener(this);
        this.mViewHolder.start_treino_02 = (Button) findViewById(R.id.btn_startTreino02);
        this.mViewHolder.start_treino_02.setOnClickListener(this);
        this.mViewHolder.start_treino_03 = (Button) findViewById(R.id.btn_startTreino03);
        this.mViewHolder.start_treino_03.setOnClickListener(this);
        this.mViewHolder.limpaActivity = (Button) findViewById(R.id.btn_LimparMemoria);
        this.mViewHolder.limpaActivity.setOnClickListener(this);

        /*onEditorAction*/
        this.mViewHolder.textView_Cadeira = (TextView) findViewById(R.id.editText_Cadeira);
//fixme: this.mViewHolder.textView_Cadeira.setOnEditorActionListener(this);
        this.mViewHolder.editText_Drive = (EditText) findViewById(R.id.editText_Drive);
        this.mViewHolder.editText_Drive.setOnEditorActionListener(this);
        this.mViewHolder.editText_Freq = (EditText) findViewById(R.id.editText_Freq);
        this.mViewHolder.editText_Freq.setOnEditorActionListener(this);
        this.mViewHolder.editText_Voga = (EditText) findViewById(R.id.editText_Voga);
        this.mViewHolder.editText_Voga.setOnEditorActionListener(this);


        /*setEnableViews*/
        this.mViewHolder.area_Pernas = (TextView) findViewById(R.id.textView_Braços);
        this.mViewHolder.area_Bracos = (TextView) findViewById(R.id.textView_Pernas);
        this.mViewHolder.area_configDrive = (LinearLayout) findViewById(R.id.area_config_Drive);
        this.mViewHolder.area_configVoga = (LinearLayout) findViewById(R.id.area_config_Voga);
        this.mViewHolder.area_radioButton = (RadioGroup) findViewById(R.id.area_radioButton);
        this.mViewHolder.area_radioButton.setOnCheckedChangeListener(this);
        setEnableViews(this.mViewHolder.area_radioButton, false);

        /*onEditorAction IME_ACTION_DONE*/
        this.mViewHolder.textView_Cadeira.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_Drive.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_Freq.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.mViewHolder.editText_Voga.setImeOptions(EditorInfo.IME_ACTION_DONE);

        /*RadioButton*/
        this.mViewHolder.area_radio_1_1 = (RadioButton) findViewById(R.id.radio_1_1);
        this.mViewHolder.area_radio_1_1.setChecked(true);
        this.mViewHolder.area_radio_1_2 = (RadioButton) findViewById(R.id.radio_1_2);
        this.mViewHolder.area_radio_1_3 = (RadioButton) findViewById(R.id.radio_1_3);

        this.mViewHolder.editText_Voga.setHint("" + this.mSecurityPreferences.getStoredInt(ParametrosConstantes.valorVoga));
        this.mViewHolder.editText_Drive.setHint(decimalFormat.format(this.mSecurityPreferences.getStoredFloat(ParametrosConstantes.valorDrive)));
        this.mViewHolder.editText_Freq.setHint("" + this.mSecurityPreferences.getStoredInt(ParametrosConstantes.valorFreqAmostra));
        this.mViewHolder.textView_Cadeira.setHint("" + this.mSecurityPreferences.getStoredInt(ParametrosConstantes.valorCadeirea));

        /*Grafico*/
        this.mViewHolder.graph = (GraphView) findViewById(R.id.graph1);

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

//declarar depois de tudo
        this.task = new MyTask(this, this.mViewHolder.cadencia, this.mViewHolder.posicaoCadeira/*, connectedThread*/);

        /*Segurança*/
        this.mViewHolder.estim.setChecked(false);

        /*variaveis*/
        cadeira = mSecurityPreferences.getStoredInt(ParametrosConstantes.valorCadeirea);

        /*Atualiza display*/
        indicaLimites(cadeira); //Redimensiona indicador na tela


        /*Enquanto nao setar treino os botoes de start ficam desabilitados*/
        this.mViewHolder.start_treino_01.setEnabled(false);
        this.mViewHolder.start_treino_02.setEnabled(false);
        this.mViewHolder.start_treino_03.setEnabled(false);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        calcularParametros();

        /*get variaveis*/
        float drive = this.mSecurityPreferences.getStoredFloat(ParametrosConstantes.valorDrive);
        int freq = this.mSecurityPreferences.getStoredInt(ParametrosConstantes.valorFreqAmostra);
        float recovery = this.mSecurityPreferences.getStoredFloat(ParametrosConstantes.valorRecovery);
        int voga = this.mSecurityPreferences.getStoredInt(ParametrosConstantes.valorVoga);
        cadeira = this.mSecurityPreferences.getStoredInt(ParametrosConstantes.valorCadeirea);
        float spDrive = this.mSecurityPreferences.getStoredFloat(ParametrosConstantes.spDrive);
        float spRecov = this.mSecurityPreferences.getStoredFloat(ParametrosConstantes.spRecovery);

        /*Fim get variaveis*/

        String text_drive = decimalFormat.format(drive);
        String text_recovery = decimalFormat.format(recovery);

        String result = "[D:" + text_drive + " | R:" + text_recovery + " | V:" + voga + " | F:" + freq + "]";

        if (id == R.id.checBox_estim) {
            if (this.mViewHolder.estim.isChecked()) {
                solic_estim = true;
            } else {
                solic_estim = false;
                run_estim = false;
                if (conexaoBT)
                    connectedThread.enviar("0");

            }
        }

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
            this.vetor_01.clear();
            this.vetor_01 = geraGrafico((float) voga, drive, (float) freq);
            this.mViewHolder.text_set_treino_01.setTextColor(series.getColor());
            this.mViewHolder.start_treino_01.setEnabled(true);

        }

        if (id == R.id.btn_setTreino_02) {
            this.mViewHolder.text_set_treino_02.setText(result);
            this.vetor_02.clear();
            this.vetor_02 = geraGrafico((float) voga, drive, (float) freq);
            this.mViewHolder.text_set_treino_02.setTextColor(series.getColor());
            this.mViewHolder.start_treino_01.setEnabled(true);


        }

        if (id == R.id.btn_setTreino_03) {
            this.mViewHolder.text_set_treino_03.setText(result);
            this.vetor_03.clear();
            this.vetor_03 = geraGrafico((float) voga, drive, (float) freq);
            this.mViewHolder.text_set_treino_03.setTextColor(series.getColor());
            this.mViewHolder.start_treino_01.setEnabled(true);

        }
////////////////////////////////////////////////////////////////////////////////////////////////////
        if (id == R.id.btn_startTreino01) {
            if (this.mViewHolder.start_treino_01.getText().toString().equals("Treino (1)")) {
                this.task.cancel(true);
                this.mViewHolder.start_treino_01.setText("Treino (1)");
                this.mViewHolder.start_treino_02.setText("Treino (2)");
                this.mViewHolder.start_treino_03.setText("Treino (3)");
                this.mViewHolder.start_treino_01.setText("Executando");
                this.task = new MyTask(this, this.mViewHolder.cadencia, this.mViewHolder.posicaoCadeira/*, this.connectedThread*/);
                ArrayList<Float> vetor = new ArrayList<Float>();
                this.task.execute(this.vetor_01);
            } else {

                this.mViewHolder.start_treino_01.setText("Treino (1)");
                this.mViewHolder.cadencia.setProgress(0);
                this.mViewHolder.posicaoCadeira.setProgress(0);
                this.task.cancel(true);

                if (run_estim) {
                    run_estim = false;
                    solic_estim = false;
                    connectedThread.enviar("0");
                }
                this.mViewHolder.estim.setChecked(false);

            }
        }

        if (id == R.id.btn_startTreino02) {
            if (this.mViewHolder.start_treino_02.getText().toString().equals("Treino (2)")) {
                this.task.cancel(true);
                this.mViewHolder.start_treino_01.setText("Treino (1)");
                this.mViewHolder.start_treino_02.setText("Treino (2)");
                this.mViewHolder.start_treino_03.setText("Treino (3)");
                this.mViewHolder.start_treino_02.setText("Executando");
                this.task = new MyTask(this, this.mViewHolder.cadencia, this.mViewHolder.posicaoCadeira/*, this.connectedThread*/);
                ArrayList<Float> vetor = new ArrayList<Float>();
                this.task.execute(this.vetor_02);
            } else {

                this.mViewHolder.start_treino_02.setText("Treino (2)");
                this.mViewHolder.cadencia.setProgress(0);
                this.mViewHolder.posicaoCadeira.setProgress(0);
                this.task.cancel(true);

            }
        }

        if (id == R.id.btn_startTreino03) {
            if (this.mViewHolder.start_treino_03.getText().toString().equals("Treino (3)")) {
                this.task.cancel(true);
                this.mViewHolder.start_treino_01.setText("Treino (1)");
                this.mViewHolder.start_treino_02.setText("Treino (2)");
                this.mViewHolder.start_treino_03.setText("Treino (3)");
                this.mViewHolder.start_treino_03.setText("Executando");
                this.task = new MyTask(this, this.mViewHolder.cadencia, this.mViewHolder.posicaoCadeira/*, this.connectedThread*/);
                ArrayList<Float> vetor = new ArrayList<Float>();
                this.task.execute(this.vetor_03);
            } else {

                this.mViewHolder.start_treino_03.setText("Treino (3)");
                this.mViewHolder.cadencia.setProgress(0);
                this.mViewHolder.posicaoCadeira.setProgress(0);
                this.task.cancel(true);

            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////
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
            if (cadeira >= 85)
                cadeira = 85;

            /*Redimensiona indicador na tela*/
            indicaLimites(cadeira);

        }
        if (id == R.id.btn_menos_Cadeira) {
            if ((cadeira % 5) != 0) {
                cadeira = cadeira - (cadeira % 5);
            }
            cadeira = cadeira - 5;
            if (cadeira <= 15)
                cadeira = 15;

            /*Redimensiona indicador na tela*/
            indicaLimites(cadeira);

        }

        /*Atualiza display*/
        text_drive = decimalFormat.format(drive);
        this.mViewHolder.editText_Voga.setText("" + voga);
        this.mViewHolder.textView_Cadeira.setText("" + cadeira);
        this.mViewHolder.editText_Freq.setText("" + freq);
        this.mViewHolder.editText_Drive.setText(text_drive);

        /*Fim Atualiza display*/

        /*Armazena variaveis*/
        this.mSecurityPreferences.storeFloat(ParametrosConstantes.valorDrive, drive);
        this.mSecurityPreferences.storeInt(ParametrosConstantes.valorFreqAmostra, freq);
        this.mSecurityPreferences.storeFloat(ParametrosConstantes.valorRecovery, recovery);
        this.mSecurityPreferences.storeInt(ParametrosConstantes.valorVoga, voga);
        this.mSecurityPreferences.storeInt(ParametrosConstantes.valorCadeirea, cadeira);
        this.mSecurityPreferences.storeFloat(ParametrosConstantes.spDrive, spDrive);
        this.mSecurityPreferences.storeFloat(ParametrosConstantes.spRecovery, spRecov);
        /*Fim Armazena variaveis*/


        this.mViewHolder.editText_Drive.clearFocus();
        this.mViewHolder.editText_Freq.clearFocus();
        this.mViewHolder.editText_Voga.clearFocus();

    }// Fim OnClic

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {

        int id = view.getId();

        if (id == R.id.btn_LimparMemoria) {
        }


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
                this.mSecurityPreferences.storeInt(ParametrosConstantes.valorFreqAmostra, valor);
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
        int caso = (this.mSecurityPreferences.getStoredInt(ParametrosConstantes.cadencia));
        float drive = this.mSecurityPreferences.getStoredFloat(ParametrosConstantes.valorDrive);
        int voga = this.mSecurityPreferences.getStoredInt(ParametrosConstantes.valorVoga);

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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
// this.mViewHolder.posicaoCadeira.setProgress(this.mViewHolder.cadencia.getProgress());
// this.mViewHolder.posicaoCadeirateste.setProgress(this.mViewHolder.cadencia.getProgress());

        /*<define se esta em drive dou nao>*/

        int id = seekBar.getId();
        cadencia = this.mViewHolder.cadencia.getProgress();

        if (cadencia > cadencia_old) {
            /*momento drive*/
            mov_drive = true;
        } else if (cadencia < cadencia_old) {
            /*momento recovery*/
            mov_drive = false;
        } else {
            /*momento de transiçao*/
            /*fixme: isso pode esta enviando um dado que atrapalharia o plot*/
            connectedThread.enviar("0");
        }

        if (id == R.id.seekBar_Cadencia) {

            if (cadencia_old == 0 & solic_estim) {
                run_estim = true;
            }

            if (cadencia <= cadeira) {
                this.mViewHolder.posicaoCadeira.setProgress(cadencia);

                if (mov_drive) {
                    /*Extensão PERNA*/
                    if (run_estim)
                        connectedThread.enviar("1");
                } else {
                    /*Flexão PERNA*/
                    if (run_estim)
                        connectedThread.enviar("2");
                }
            } else {
                this.mViewHolder.posicaoCadeira.setProgress(cadeira);

                if (mov_drive) {
                    /*Flexão BRAÇO*/
                    if (run_estim)
                        connectedThread.enviar("1");
                } else {
                    /*Extensão BRAÇO*/
                    if (run_estim)
                        connectedThread.enviar("0");
                }
            }
        }
        cadencia_old = cadencia;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (conexaoBT)
            connectedThread.enviar("0");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (conexaoBT)
            connectedThread.enviar("0");

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * Função para gerar o gráfico da voga.
     *
     * @param voga
     * @param drive
     * @param fs
     */
    public ArrayList<Float> geraGrafico(Float voga, Float drive, Float fs) {
        series = new LineGraphSeries<DataPoint>();

        ArrayList<Float> vetor = new ArrayList<Float>();

        float cicloSeg, recov, spDrive, spRecovery, t, xd, yd;

        xd = 0;
        t = 1 / fs;
        cicloSeg = 60 / voga;
        recov = cicloSeg - drive;
        spDrive = drive / t;
        spRecovery = recov / t;

        vetor.add(fs);
        vetor.add(spDrive + spRecovery);

        for (int i = 0; i < spDrive - 1; i++) {

            /*func em MatLab: DRIVE(i+1) = floor(50-50*cos(i*2*pi()/fs/Drive_seg/2));*/
            yd = (float) (50 - 50 * Math.cos(i * 2 * Math.PI / (drive - t) / 2 * t));
            series.appendData(new DataPoint(xd, yd), true, (int) (spDrive + spRecovery)); // o mesmo valor do for
            xd = xd + t;
            String txt = "Drive_" + i;
            this.mSecurityPreferences.storeFloat(txt, yd);
            vetor.add(yd);
        }
        for (int i = 0; i < spRecovery - 1; i++) {

            /*func em MatLab: RECOVERY(i+1) = floor(50+50*cos(i*2*pi()/fs/Recovery_seg/2));*/
            yd = (float) (50 - 50 * -Math.cos(i * 2 * Math.PI / (recov - t) / 2 * t));
            series.appendData(new DataPoint(xd, yd), true, (int) (spDrive + spRecovery)); // o mesmo valor do for
            xd = xd + t;
            String txt = "Recov_" + i;
            this.mSecurityPreferences.storeFloat(txt, yd);
            vetor.add(yd);

            this.mViewHolder.graph.addSeries(series);

            this.mViewHolder.graph.getViewport().setMinY(0);
            series.setColor(Color.rgb(aleatorio.nextInt(255), aleatorio.nextInt(255), aleatorio.nextInt(255)));
            this.mViewHolder.graph.getViewport().setMaxY(100);
            this.mViewHolder.graph.getViewport().setMinX(0);
            this.mViewHolder.graph.getViewport().setMaxX(drive + recov);
            this.mViewHolder.graph.getViewport().setYAxisBoundsManual(true);
            this.mViewHolder.graph.getViewport().setXAxisBoundsManual(true);

        }

        this.mSecurityPreferences.storeFloat(ParametrosConstantes.spDrive, spDrive);
        this.mSecurityPreferences.storeFloat(ParametrosConstantes.spRecovery, spRecovery);
        this.mSecurityPreferences.storeFloat(ParametrosConstantes.valorFreqAmostra, fs);

        return vetor;

    }

    public void indicaLimites(Integer cadeira) {
        /*Redimensiona o layout*/
        View v_1 = findViewById(R.id.textView_Pernas);
        LinearLayout.LayoutParams loparams_1 = (LinearLayout.LayoutParams) v_1.getLayoutParams();
//loparams_1.height = 0;
        loparams_1.weight = cadeira;
        v_1.setLayoutParams(loparams_1);

        View v_2 = findViewById(R.id.textView_Braços);
        LinearLayout.LayoutParams loparams_2 = (LinearLayout.LayoutParams) v_2.getLayoutParams();
//loparams_1.height = 0;
        loparams_2.weight = 100 - cadeira;
        v_2.setLayoutParams(loparams_2);
        /*Fim Redimensiona o layout*/

    }

    private static class ViewHolder {


        Button mais_cadeira;
        Button menos_cadeira;
        Button mais_drive;
        Button menos_drive;
        Button mais_freq;
        Button menos_freq;
        Button mais_voga;
        Button menos_voga;
        Button set_treino_01;
        Button set_treino_02;
        Button set_treino_03;
        Button start_treino_01;
        Button start_treino_02;
        Button start_treino_03;
        Button limpaActivity;

        CheckBox checkBox_Drive;
        CheckBox checkBox_Voga;
        CheckBox estim;

        EditText editText_Drive;
        EditText editText_Freq;
        EditText editText_Voga;

        GraphView graph;

        TextView textView_Cadeira;
        TextView text_set_treino_01;
        TextView text_set_treino_02;
        TextView text_set_treino_03;

        SeekBar cadencia;
        SeekBar posicaoCadeira;

        /*Areas layout*/
        LinearLayout area_configDrive;
        LinearLayout area_configVoga;
        RadioButton area_radio_1_1;
        RadioButton area_radio_1_2;
        RadioButton area_radio_1_3;
        RadioGroup area_radioButton;
        TextView area_Bracos;
        TextView area_Pernas;

    }
}// FIM //
