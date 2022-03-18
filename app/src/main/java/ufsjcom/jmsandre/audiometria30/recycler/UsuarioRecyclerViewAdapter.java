package ufsjcom.jmsandre.audiometria30.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import ufsjcom.jmsandre.audiometria30.R;
import ufsjcom.jmsandre.audiometria30.model.Usuario;

public class UsuarioRecyclerViewAdapter
        extends RecyclerView.Adapter<UsuarioRecyclerViewAdapter.UsuarioViewHolder> {

    private final List<Usuario> usuarios;
    private OnUserClickListener onUserClickListener;

    public UsuarioRecyclerViewAdapter(List<Usuario> usuarios, OnUserClickListener onUserClickListener){
        this.usuarios = usuarios;
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new UsuarioViewHolder(viewCriada, onUserClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.bind(usuario);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    //Implementação do ViewHolder
    class UsuarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView inicial;
        private final TextView nome;
        private final TextView cpf;
        OnUserClickListener onUserClickListener;

        public UsuarioViewHolder(@NonNull View itemView, OnUserClickListener onUserClickListener) {
            super(itemView);
            inicial = itemView.findViewById(R.id.letra_inicial);
            nome = itemView.findViewById(R.id.nome_print);
            cpf = itemView.findViewById(R.id.cpf_print);
            this.onUserClickListener = onUserClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Usuario usuario){
            inicial.setText(usuario.getNome().substring(0,1));
            nome.setText(usuario.getNome());
            cpf.setText(usuario.getCpf());
        }

        @Override
        public void onClick(View v) {
            onUserClickListener.OnUserClick(getAdapterPosition());
        }

    }

    public interface OnUserClickListener{
        void OnUserClick(int position);
    }
}
