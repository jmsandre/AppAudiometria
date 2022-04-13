package ufsjcom.jmsandre.audiometria30;

import android.app.Application;

import ufsjcom.jmsandre.audiometria30.database.AudiometriaDatabase;

public class AudiometriaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ObterDados();
    }

    private void ObterDados(){
        AudiometriaDatabase db = new AudiometriaDatabase(this);
        //db.getReadableDatabase();
        //Usuario temp = new Usuario("Claudio", "1136945312");
        //db.inserir(temp);
    }
}
