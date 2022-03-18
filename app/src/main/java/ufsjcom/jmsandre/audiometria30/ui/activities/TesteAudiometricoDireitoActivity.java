package ufsjcom.jmsandre.audiometria30.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ufsjcom.jmsandre.audiometria30.R;
import ufsjcom.jmsandre.audiometria30.helper.TesteAudiometricoHelper;
import ufsjcom.jmsandre.audiometria30.model.Usuario;

public class TesteAudiometricoDireitoActivity extends AppCompatActivity {

    Usuario usuario;
    TesteAudiometricoHelper viewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_audiometrico);

        obterUsuario();

        viewHelper = new TesteAudiometricoHelper(this,
                usuario,
                TesteAudiometricoHelper.RIGHT
        );

        viewHelper.setAppbar();
        setSupportActionBar(viewHelper.getAppbar());
        viewHelper.setButtons();
        viewHelper.setDisplays();
        viewHelper.setTitulo("Ouvido Direito");
        viewHelper.setConcluirButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Finalizar");
        menu.add("Inicio");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Finalizar")){
            Intent intent = new Intent(this, ConcluirActivity.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        }

        if(item.getTitle().equals("Inicio")){
            Intent intent = new Intent(this, InicioActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void obterUsuario(){
        Intent intent = getIntent();
        if(intent.hasExtra("usuario")){
            usuario = intent.getParcelableExtra("usuario");
        }
    }
}