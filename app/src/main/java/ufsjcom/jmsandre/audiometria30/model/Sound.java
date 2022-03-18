package ufsjcom.jmsandre.audiometria30.model;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Sound {
    private final int samplerate;
    private int t = 3;
    private int frequency;
    private int duration = 44100*t;
    private double intensity;
    private int side;

    private double[] sound = new double[duration];
    private short[] buffer = new short[duration];

    AudioTrack audio;
    private static Sound instance;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Sound(int side){
        this.samplerate = 44100;
        this.frequency = 1000;
        this.intensity = 10;
        this.duration = samplerate*t;
        this.side = side;
        this.audio = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                44100,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                duration,
                AudioTrack.MODE_STREAM
        );

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setAudio(){

        if(this.side == 0){
            audio.setStereoVolume(1.0f, 0.0f);

        }
        else{
            audio.setStereoVolume(0.0f, 1.0f);
        }


        float k = this.frequency / (float)44100;
        //escala em 1/4000
        double v = 0.000001*this.intensity;    // Diminui a Escala da amplitude de acordo com a intensidade

        for(int i = 0; i < sound.length; i++){
            this.sound[i] = Math.sin((2.0*Math.PI * (double)i * k))*v;
            this.buffer[i] = (short) (sound[i] * Short.MAX_VALUE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Sound getInstance(int side) {
        instance = new Sound(side);
        return instance;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public int getTime(){
        return this.t;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playSound(){
        //Setando Objeto do tipo AudioTrack, para tocar WAV
        setAudio();
        audio.play();
        audio.write(buffer, 0, sound.length);
        audio.stop();
        //audio.release();

    }

    public int getSide(){
        return this.side;
    }
}