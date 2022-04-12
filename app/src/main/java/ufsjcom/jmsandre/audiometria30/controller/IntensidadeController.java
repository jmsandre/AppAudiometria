package ufsjcom.jmsandre.audiometria30.controller;

public class IntensidadeController {
    private final int[] vetor = new int[64];
    private int current = 0;

    private static IntensidadeController instance;

    public IntensidadeController(){
        int i;
        for(i = 0; i < 10; i++){    //1 a 10;
            vetor[i] = i+1;
        }

        for(i = 10; i < 19; i++){  //20 a 100;
            vetor[i] = (i-8)*10;
        }

        for(i = 19; i < 28; i++){  //200 a 1000
            vetor[i] = (i-17)*100;
        }

        for(i = 28; i < 37; i++){        //2.000 a 10.000
            vetor[i] = (i-26)*1000;
        }

        for(i = 37; i < 46; i++){       //20.000 a 100.000
            vetor[i] = (i-35)*10000;
        }

        for(i = 46; i < 55; i++){
            vetor[i] = (i-44)*100000;
        }

        for(i = 55; i< 64; i++){
            vetor[i] = (i-53)*1000000;
        }

    }

    public String getDecibelGain(){
        double x = this.vetor[current];
        double decibels = 20*Math.log10(x);
        return String.format("+%.2f", decibels);
    }

    public int getCurrent(){
        return this.current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getValue(int current){
        return this.vetor[current];
    }
}
