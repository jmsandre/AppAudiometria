package ufsjcom.jmsandre.audiometria30.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ufsjcom.jmsandre.audiometria30.R;
import ufsjcom.jmsandre.audiometria30.model.Usuario;

public class CadastrarActivity extends AppCompatActivity {

    private EditText editText_nome;
    private EditText editText_cpf;
    private Button button_iniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        setButtons();
    }

    private void setButtons(){
        editText_nome = findViewById(R.id.edit_text_nome);
        editText_cpf = findViewById(R.id.edit_text_cpf);
        button_iniciar = findViewById(R.id.button_iniciar_teste);

        button_iniciar.setOnClickListener((view)->{
            Usuario usuario = new Usuario(
                    editText_nome.getText().toString(),
                    editText_cpf.getText().toString()
            );
            Intent intent = new Intent(getApplicationContext(), TesteAudiometricoEsquerdoActivity.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
            finish();
        });
    }
}