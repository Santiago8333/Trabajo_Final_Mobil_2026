package com.in.trabajo_final_mobil_2026.ui.usuario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.modelo.Usuario;
import com.in.trabajo_final_mobil_2026.request.ApiClient;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {


    public interface OnUsuarioClickListener {
        void onEliminar(Usuario usuario);
        void onModificar(Usuario usuario);
        void onCambiarClave(Usuario usuario);
        void onSubirAvatar(Usuario usuario);
    }

    private final List<Usuario> usuarios;
    private final LayoutInflater inflater;
    private final OnUsuarioClickListener listener;

    public UsuarioAdapter(List<Usuario> usuarios, LayoutInflater inflater, OnUsuarioClickListener listener) {
        this.usuarios = usuarios;
        this.inflater = inflater;
        this.listener = listener;
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
        holder.tvFecha.setText("Creado: " + usuario.getFecha_Creacion());

        // ruta completa: http://IP:puerto/avatars/usuario_x.jpg
        String urlFinal = ApiClient.BASE_URL + usuario.getAvatar();
        Glide.with(holder.itemView.getContext())
                .load(urlFinal)
                .placeholder(R.drawable.avatar_1)
                .error(R.drawable.avatar_1)
                .into(holder.ivFoto);

        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(usuario));
        holder.btnModificar.setOnClickListener(v -> listener.onModificar(usuario));
        holder.btnCambiarClave.setOnClickListener(v -> listener.onCambiarClave(usuario));
        holder.btnAvatar.setOnClickListener(v -> listener.onSubirAvatar(usuario));
    }

    @Override
    public int getItemCount() {
        return usuarios != null ? usuarios.size() : 0;
    }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvNombre;
        TextView tvEmail;
        TextView tvEspecializacion;
        TextView tvFecha;
        ImageButton btnEliminar;
        ImageButton btnModificar;
        ImageButton btnCambiarClave;
        ImageButton btnAvatar;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvEspecializacion = itemView.findViewById(R.id.tvEspecializacion);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnModificar = itemView.findViewById(R.id.btnModificar);
            btnCambiarClave = itemView.findViewById(R.id.btnCambiarClave);
            btnAvatar = itemView.findViewById(R.id.btnAvatar);
        }
    }
}
