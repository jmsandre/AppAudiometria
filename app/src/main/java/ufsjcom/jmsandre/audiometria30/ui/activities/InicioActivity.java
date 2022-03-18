package ufsjcom.jmsandre.audiometria30.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ufsjcom.jmsandre.audiometria30.R;
import ufsjcom.jmsandre.audiometria30.database.AudiometriaDatabase;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        setButtons();
        AudiometriaDatabase db = new AudiometriaDatabase(this);
        db.getReadableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Teste Rápido");
        menu.add("Testar e salvar");
        return super.onCreateOptionsMenu(menu);
    }

    private void setButtons(){
        final Button teste_rapido = findViewById(R.id.button_teste_rapido);
        final Button cadastrar_usuario = findViewById(R.id.button_cadastrar_usuario);
        final Button listar_usuarios = findViewById(R.id.button_ver_usuarios);
        Toolbar appbar = findViewById(R.id.appbar);

        setSupportActionBar(appbar);

        teste_rapido.setOnClickListener((view)-> {
            Intent intent = new Intent(getApplicationContext(), TesteAudiometricoEsquerdoActivity.class);
            startActivity(intent);
        });


        cadastrar_usuario.setOnClickListener((view)->{
            Intent intent = new Intent(getApplicationContext(), CadastrarActivity.class);
            startActivity(intent);
        });

        listar_usuarios.setOnClickListener((view)->{
            Intent intent = new Intent(getApplicationContext(), ListaUsuariosActivity.class);
            startActivity(intent);
        });
    }
}