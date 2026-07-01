package com.in.trabajo_final_mobil_2026.ui.usuario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.modelo.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private final List<Usuario> usuarios;
    private final LayoutInflater inflater;

    public UsuarioAdapter(List<Usuario> usuarios, LayoutInflater inflater) {
        this.usuarios = usuarios;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.tvNombre.setText(usuario.getNombre() + " " + usuario.getApellido());
        holder.tvEmail.setText(usuario.getEmail());
        holder.tvEspecializacion.setText(usuario.getEspecializacion());
    }

    @Override
    public int getItemCount() {
        return usuarios != null ? usuarios.size() : 0;
    }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvEmail;
        TextView tvEspecializacion;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvEspecializacion = itemView.findViewById(R.id.tvEspecializacion);
        }
    }
}
