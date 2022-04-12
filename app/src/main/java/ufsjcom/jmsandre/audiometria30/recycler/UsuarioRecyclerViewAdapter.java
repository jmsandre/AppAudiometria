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
    private OnDeleteClickListener onDeleteClickListener;

    public UsuarioRecyclerViewAdapter(
            List<Usuario> usuarios,
            OnUserClickListener onUserClickListener,
            OnDeleteClickListener onDeleteClickListener){

        this.usuarios = usuarios;
        this.onUserClickListener = onUserClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new UsuarioViewHolder(viewCriada, onUserClickListener, onDeleteClickListener);
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
        private final TextView deletar;

        OnUserClickListener onUserClickListener;
        OnDeleteClickListener onDeleteClickListener;

        public UsuarioViewHolder(@NonNull View itemView, OnUserClickListener onUserClickListener, OnDeleteClickListener onDeleteClickListener) {
            super(itemView);

            inicial = itemView.findViewById(R.id.letra_inicial);
            nome = itemView.findViewById(R.id.nome);
            cpf = itemView.findViewById(R.id.cpf);
            deletar = itemView.findViewById(R.id.button_delete);

            this.onUserClickListener = onUserClickListener;
            this.onDeleteClickListener = onDeleteClickListener;
            deletar.setOnClickListener(v->{
                this.onDeleteClickListener.OnDeleteClick(getAdapterPosition());
            });

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

    public interface OnDeleteClickListener{
        void OnDeleteClick(int position);
    }

}
