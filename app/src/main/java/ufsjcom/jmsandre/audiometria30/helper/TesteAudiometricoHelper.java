package ufsjcom.jmsandre.audiometria30.helper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

import ufsjcom.jmsandre.audiometria30.R;
import ufsjcom.jmsandre.audiometria30.controller.TesteAudiometricoController;
import ufsjcom.jmsandre.audiometria30.database.AudiometriaDatabase;
import ufsjcom.jmsandre.audiometria30.model.Sound;
import ufsjcom.jmsandre.audiometria30.model.Usuario;

import ufsjcom.jmsandre.audiometria30.ui.activities.ConcluirActivity;
import ufsjcom.jmsandre.audiometria30.ui.activities.InicioActivity;
import ufsjcom.jmsandre.audiometria30.ui.activities.TesteAudiometricoDireitoActivity;
import ufsjcom.jmsandre.audiometria30.ui.activities.TesteAudiometricoEsquerdoActivity;

public class TesteAudiometricoHelper {
    private Activity activity;
    private Usuario usuario;
    private Toolbar appbar;

    int side;

    public final static int LEFT = 0;
    public final static int RIGHT = 1;
    public final static int RESULT = 2;

    private TesteAudiometricoController controller = new TesteAudiometricoController();
    private Sound sound;

    private TextView titulo;
    private TextView frequencia;
    private TextView intensidade;

    private Button frequencia_anterior;
    private Button frequencia_seguinte;

    private Button aumentar_intensidade;
    private Button diminuir_intensidade;

    private ImageView play;
    private Button avancar;
    private Button cancelar;

    private LineChart resultChart;
    private Button button_voltar;
    private Button button_pdf;
    private Button button_concluir;

    Drawable iconeLeft;
    Drawable iconeRight;

    private View printView;

    public TesteAudiometricoHelper(Activity activity, Usuario usuario, int side){
        this.activity = activity;
        this.usuario = usuario;
        this.side = side;
        if(side < 2)
            this.sound = new Sound(side);

    }

    public void setAppbar(){
        appbar = activity.findViewById(R.id.appbar);
    }

    public Toolbar getAppbar(){
        return this.appbar;
    }

    public void setTitulo(String valor){
        titulo.setText(valor);
    }

    public void setConcluirButton(){
        avancar.setText("Concluir");
        cancelar.setText("voltar");

        avancar.setOnClickListener(view->{
            Intent intent = new Intent(this.activity, ConcluirActivity.class);
            intent.putExtra("usuario", usuario);
            activity.startActivity(intent);
        });
    }

    public void setDisplays(){
        this.appbar = activity.findViewById(R.id.appbar);
        titulo = appbar.findViewById(R.id.titulo);
        if(activity instanceof TesteAudiometricoEsquerdoActivity
            ||activity instanceof TesteAudiometricoDireitoActivity
        ) {

            frequencia = activity.findViewById(R.id.valor_frequencia);
            intensidade = activity.findViewById(R.id.valor_intensidade);

            frequencia.setText(controller.frequencias.toString());
            intensidade.setText(controller.getStringIntensidade());
        }

        else{
            resultChart = activity.findViewById(R.id.lineChart_resultado);
            button_voltar = activity.findViewById(R.id.button_voltar);
            button_pdf = activity.findViewById(R.id.button_gerar_pdf);
            button_concluir = activity.findViewById(R.id.button_concluir);
            iconeLeft = activity.getDrawable(R.drawable.left_blue_x);
            iconeRight = activity.getDrawable(R.drawable.right_red_circle);
        }
    }

    private void setUserValue(int valor, int indice, int side){
        if(side == LEFT)
            usuario.setValorEsquerda(valor, indice);

        if(side == RIGHT)
            usuario.setValorDireita(valor, indice);
    }

    private void getUserValue(int indice, int side){
        if(side == LEFT)
            controller.setValorAtual(usuario.getValorEsquerda(indice));

        if(side == RIGHT)
            controller.setValorAtual(usuario.getValorDireita(indice));

        intensidade.setText(controller.getStringIntensidade());
    }

    public void setButtons(){
        if(activity instanceof TesteAudiometricoEsquerdoActivity
                ||activity instanceof TesteAudiometricoDireitoActivity
        ) {
            frequencia_anterior = activity.findViewById(R.id.frequenciaAnterior);
            frequencia_seguinte = activity.findViewById(R.id.frequenciaSeguinte);

            diminuir_intensidade = activity.findViewById(R.id.diminuirIntensidade);
            aumentar_intensidade = activity.findViewById(R.id.aumentarIntensidade);

            play = activity.findViewById(R.id.play);
            avancar = activity.findViewById(R.id.avançar);
            cancelar = activity.findViewById(R.id.cancelar);

            //Botões de Modificar Frequencia
            frequencia_anterior.setOnClickListener((view) -> {
                setUserValue(
                        controller.getIntensidadeAtual(),
                        controller.frequencias.getIndiceAtual(),
                        side);
                controller.frequencias.anterior();
                frequencia.setText(controller.frequencias.toString());

                getUserValue(controller.frequencias.getIndiceAtual(), side);
            });

            frequencia_seguinte.setOnClickListener((view) -> {
                setUserValue(
                        controller.getIntensidadeAtual(),
                        controller.frequencias.getIndiceAtual(),
                        side);
                controller.frequencias.seguinte();
                frequencia.setText(controller.frequencias.toString());

                getUserValue(controller.frequencias.getIndiceAtual(), side);
            });

            //Botões de Modificar Amplitudade
            aumentar_intensidade.setOnClickListener((view) -> {
                controller.aumentarIntensidade(5);
                intensidade.setText(controller.getStringIntensidade());
            });

            diminuir_intensidade.setOnClickListener((view) -> {
                controller.diminuirIntensidade(5);
                intensidade.setText(controller.getStringIntensidade());
            });

            //Tocar
            play.setOnClickListener((view) -> {
                sound.setFrequency(controller.frequencias.getValorAtual());
                sound.setIntensity(controller.getValorIntensidade());
                sound.setAudio();
                sound.playSound();
            });

            cancelar.setOnClickListener((view) -> {
                activity.finish();
            });

            avancar.setOnClickListener((view) -> {
                setUserValue(
                        controller.getIntensidadeAtual(),
                        controller.frequencias.getIndiceAtual(),
                        side);
                Intent intent = new Intent(this.activity, TesteAudiometricoDireitoActivity.class);

                intent.putExtra("usuario", usuario);
                activity.startActivity(intent);
            });
        }
        /////////////////////////////////////////
        else {
            button_voltar.setOnClickListener(view -> {
                activity.finish();
            });

            button_concluir.setOnClickListener(view -> {
                salvar();
                Intent intent = new Intent(activity.getApplicationContext(), InicioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            });
        }
    }

    public void setResultChart(LineChart resultChart) {
        this.resultChart = resultChart;
    }

    public void setChartSettings(){
        XAxis eixoX = resultChart.getXAxis();
        YAxis eixoY = resultChart.getAxisLeft();

        eixoX.setGridColor(R.color.black);
        eixoY.setGridColor(R.color.black);

        eixoY.setAxisMaximum(100);
        eixoY.setAxisMinimum(0);
        eixoY.setLabelCount(11);
        eixoY.setInverted(true);
        resultChart.getAxisRight().setEnabled(false);

        resultChart.setBackgroundResource(R.color.white);
        resultChart.setDragEnabled(true);
        resultChart.setScaleEnabled(true);
        resultChart.setPinchZoom(true);

        Description description = new Description();
        description.setText("dB/Frequência");
        resultChart.setDescription(description);

        eixoX.setPosition(XAxis.XAxisPosition.BOTTOM);
        eixoX.setValueFormatter(new IndexAxisValueFormatter(getIndexFormater()));

        //Obtendo e adicionando os Dados no gráfico
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        LineDataSet leftDataSet = new LineDataSet(getValores("esquerda"), "Ouvido Esquerdo");
        LineDataSet rightDataSet = new LineDataSet(getValores("direita"), "Ouvido Direito");
        leftDataSet.setDrawValues(false);
        rightDataSet.setDrawValues(false);
        leftDataSet.setColor(activity.getResources().getColor(R.color.blue));
        rightDataSet.setColor(activity.getResources().getColor(R.color.medium_red));
        dataSets.add(rightDataSet);
        dataSets.add(leftDataSet);

        LineData data = new LineData(dataSets);
        resultChart.setData(data);
    }

    public ArrayList<Entry> getValores(String side){
        ArrayList<Entry> valores = new ArrayList<>();
        Drawable icone;

        int lista[];
        if(side.equals("esquerda")) {
            lista = usuario.getValoresEsquerda();
            icone = iconeLeft;
        }

        else if(side.equals("direita")) {
            lista = usuario.getValoresDireita();
            icone = iconeRight;
        }

        else return null;

        for(int i = 0; i<lista.length; i++){
            valores.add(new Entry(i+1, lista[i], icone));
        }
        return valores;
    }

    private ArrayList<String> getIndexFormater(){
        ArrayList<String> labels = new ArrayList<>();
        labels.add("0");
        labels.add("250");
        labels.add("500");
        labels.add("1000");
        labels.add("2000");
        labels.add("3000");
        labels.add("4000");
        labels.add("5000");
        labels.add("6000");
        labels.add("7000");
        labels.add("8000");
        labels.add("");
        return labels;
    }

    private void salvar() {
        if(usuario.getNome()!=null){
            AudiometriaDatabase bd = new AudiometriaDatabase(activity);
            bd.getWritableDatabase();
            bd.inserir(usuario);
            Toast.makeText(activity, "Usuario Salvo", Toast.LENGTH_SHORT).show();
        }
    }

    public Button getPDFButton() {
        return this.button_pdf;
    }

    public View getPrintView() {
        printView = activity.findViewById(R.id.print_format);
        return printView;
    }
}
