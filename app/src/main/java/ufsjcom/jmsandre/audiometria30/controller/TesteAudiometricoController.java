package ufsjcom.jmsandre.audiometria30.controller;

import ufsjcom.jmsandre.audiometria30.model.VetorFrequencias;

public class TesteAudiometricoController {
    private double[] a = new double[10];
    private double[] b = new double[10];
    private int valorAtualEmDB = 40;

    public VetorFrequencias frequencias = new VetorFrequencias();

    public TesteAudiometricoController() {
        a[0] = -32.9844;    //250
        a[1] = -31.3194;    //500
        a[2] = -32.5593;    //1000
        a[3] = -28.8593;    //2000
        a[4] = -16.0077;    //3000
        a[5] = -23.3496;    //4000
        a[6] = -30.5680;    //5000
        a[7] = -35.7868;    //6000
        a[8] = -38.3513;    //7000
        a[9] = -39.4397;    //8000

        b[0] = 19.2188;     //250
        b[1] = 19.8471;     //500
        b[2] = 20.005;      //1000
        b[3] = 20.005;      //2000
        b[4] = 20.0306;     //3000
        b[5] = 20.003;      //4000
        b[6] = 19.9886;     //5000
        b[7] = 19.9831;     //6000
        b[8] = 19.9826;     //7000
        b[9] = 19.9829;     //8000

    }

    public double getValorIntensidade() {
        int indice = frequencias.getIndiceAtual();
        double e = ((float) valorAtualEmDB - a[indice]) / b[indice];
        return Math.pow(10, e);
    }

    public String getStringIntensidade(){
        return String.format("%d dB", valorAtualEmDB);
    }

    //public String getAbstractValue(){
        //int indice = frequencias.getIndiceAtual();
        //Para testes
        //return String.format("I: %.2f", getValorIntensidade(indice));
    //}

    public void aumentarIntensidade(int valor){
        this.valorAtualEmDB += valor;
        if(valorAtualEmDB>100)
            setValorAtual(100);
    }

    public void diminuirIntensidade(int valor){
        this.valorAtualEmDB -= valor;
        if(valorAtualEmDB < 5)
            setValorAtual(5);
    }

    public int getIntensidadeAtual(){
        return this.valorAtualEmDB;
    }

    public void setValorAtual(int valor){
        this.valorAtualEmDB = valor;
    }
}