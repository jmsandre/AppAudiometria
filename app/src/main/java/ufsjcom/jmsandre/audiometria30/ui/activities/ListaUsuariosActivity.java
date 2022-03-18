package ufsjcom.jmsandre.audiometria30.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ufsjcom.jmsandre.audiometria30.R;
import ufsjcom.jmsandre.audiometria30.database.AudiometriaDatabase;
import ufsjcom.jmsandre.audiometria30.model.Usuario;
import ufsjcom.jmsandre.audiometria30.recycler.UsuarioRecyclerViewAdapter;

public class ListaUsuariosActivity extends AppCompatActivity
        implements UsuarioRecyclerViewAdapter.OnUserClickListener {

    private AudiometriaDatabase db;

    private RecyclerView lista;

    private List<Usuario> usuarios;

    private EditText pesquisa_nome;
    private ImageButton button_pesquisa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        db = new AudiometriaDatabase(this);
        linkarViews();
        setListeners();

    }

    private void setListeners() {
        button_pesquisa.setOnClickListener(pesquisa->{
            String nome = pesquisa_nome.getText().toString();
            usuarios = db.pesquisa(nome);
            lista.setAdapter(new UsuarioRecyclerViewAdapter(
                    usuarios,
                    this::OnUserClick
            ));
        });
    }

    private void linkarViews() {
        lista = findViewById(R.id.lista);
        pesquisa_nome = findViewById(R.id.editPesquisa);
        button_pesquisa = findViewById(R.id.pesquisa_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        usuarios = db.todos();
        lista.setAdapter(new UsuarioRecyclerViewAdapter(
                usuarios,
                this::OnUserClick
        ));

    }

    @Override
    public void OnUserClick(int position) {
        Usuario usuario = usuarios.get(position);
        Boolean acessarUsuario = true;
        Intent intent = new Intent(this, ConcluirActivity.class);
        intent.putExtra("usuario", usuario);
        intent.putExtra("acessarUsuario", acessarUsuario);

        startActivity(intent);
    }
}