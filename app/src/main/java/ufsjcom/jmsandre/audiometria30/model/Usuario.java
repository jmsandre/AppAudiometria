package ufsjcom.jmsandre.audiometria30.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.StringTokenizer;

public class Usuario implements Parcelable {

    private String cpf;

    private String nome;

    private int valoresEsquerda[] = new int[10];
    private int valoresDireita[] = new int[10];

    public Usuario(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
        setValoresPadrao();
    }

    public Usuario(){
        this.nome = null;
        this.cpf = null;
        setValoresPadrao();
    }

    public Usuario(Parcel parcel){
        nome = parcel.readString();
        cpf = parcel.readString();
        valoresEsquerda = parcel.createIntArray();
        valoresDireita = parcel.createIntArray();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int[] getValoresEsquerda() {
        return valoresEsquerda;
    }

    public int[] getValoresDireita() {
        return valoresDireita;
    }

    public void setValoresDireita(int[] valoresDireita) {
        this.valoresDireita = valoresDireita;
    }

    public void setValoresEsquerda(int[] valoresEsquerda) {
        this.valoresEsquerda = valoresEsquerda;
    }

    public void setValoresPadrao(){
        for(int i = 0; i < 10; i++){
            valoresEsquerda[i] = 40;
            valoresDireita[i] = 40;
        }
    }

    public void setValorEsquerda(int valor, int indice){
        this.valoresEsquerda[indice] = valor;
    }

    public void setValorDireita(int valor, int indice){
        this.valoresDireita[indice] = valor;
    }

    public int getValorEsquerda(int indice){
        return this.valoresEsquerda[indice];
    }

    public int getValorDireita(int indice){
        return this.valoresDireita[indice];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(cpf);
        dest.writeIntArray(valoresEsquerda);
        dest.writeIntArray(valoresDireita);
    }

    public String getStringValoresEsquerda() {
      String valores = "";
      for(int i = 0; i<10; i++){
            valores = valores.concat((valoresEsquerda[i])+" ");
        }
        return valores;
    }

    public String getStringValoresDireita() {
        String valores = "";
        for(int i = 0; i<10; i++){
            valores = valores.concat((valoresDireita[i])+" ");
        }
        return valores;
    }

    public void setValoresEsquerda(String valores){
        StringTokenizer tokenizer = new StringTokenizer(valores);
        for(int i = 0; i < 10; i++){
            valoresEsquerda[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    public void setValoresDireita(String valores){
        StringTokenizer tokenizer = new StringTokenizer(valores);
        for(int i = 0; i < 10; i++){
            valoresDireita[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }
}
