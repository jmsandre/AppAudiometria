package ufsjcom.jmsandre.audiometria30.model;

public class VetorFrequencias {
    private int size = 10;
    private final int[] fr = new int[size];
    private int indiceAtual;

    public VetorFrequencias(){
        this.indiceAtual = 0;
        this.fr[0] = 250;
        this.fr[1] = 500;
        this.fr[2] = 1000;
        this.fr[3] = 2000;
        this.fr[4] = 3000;
        this.fr[5] = 4000;
        this.fr[6] = 5000;
        this.fr[7] = 6000;
        this.fr[8] = 7000;
        this.fr[9] = 8000;

    }

    public String getStringValue(){
        return String.format("%d Hz", fr[indiceAtual]);
    }

    public void seguinte(){
        if(indiceAtual <9)
            indiceAtual++;
    }

    public void anterior(){
        if (indiceAtual >0)
            indiceAtual--;
    }

    public int getValorAtual(){
        return fr[indiceAtual];
    }

    @Override
    public String toString() {
        return String.format("%d Hz", getValorAtual());
    }

    public int getIndiceAtual() {
        return indiceAtual;
    }

    public void setIndiceAtual(int atual) {
        this.indiceAtual = atual;
    }

    public int getFrequencia(int position) {
        return fr[position];
    }
}
