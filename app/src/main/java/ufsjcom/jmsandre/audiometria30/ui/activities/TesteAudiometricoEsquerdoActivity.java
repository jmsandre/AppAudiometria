package ufsjcom.jmsandre.audiometria30.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.w3c.dom.Text;

import ufsjcom.jmsandre.audiometria30.R;
import ufsjcom.jmsandre.audiometria30.model.Usuario;
import ufsjcom.jmsandre.audiometria30.helper.TesteAudiometricoHelper;

public class TesteAudiometricoEsquerdoActivity extends AppCompatActivity {

    private Usuario usuario;
    private TesteAudiometricoHelper viewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_audiometrico);

        obterUsuario();

        viewHelper = new TesteAudiometricoHelper(
                this,
                usuario,
                TesteAudiometricoHelper.LEFT
        );

        viewHelper.setAppbar();
        setSupportActionBar(viewHelper.getAppbar());
        viewHelper.setButtons();
        viewHelper.setDisplays();
        viewHelper.setTitulo("Ouvido Esquerdo");
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
        else usuario = new Usuario();
    }
}