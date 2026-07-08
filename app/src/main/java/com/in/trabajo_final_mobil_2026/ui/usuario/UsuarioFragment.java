package com.in.trabajo_final_mobil_2026.ui.usuario;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.databinding.FragmentUsuarioBinding;
import com.in.trabajo_final_mobil_2026.modelo.Usuario;

import java.util.List;

public class UsuarioFragment extends Fragment implements UsuarioAdapter.OnUsuarioClickListener {
    private FragmentUsuarioBinding b;

    private UsuarioViewModel mv;

    public static UsuarioFragment newInstance() {
        return new UsuarioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentUsuarioBinding.inflate(getLayoutInflater());

        mv = new ViewModelProvider(this).get(UsuarioViewModel.class);

        b.rcusuarios.setLayoutManager(
                new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));

        mv.getListaUsuarios().observe(getViewLifecycleOwner(), new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                UsuarioAdapter adapter =
                        new UsuarioAdapter(usuarios, getLayoutInflater(), UsuarioFragment.this);
                b.rcusuarios.setAdapter(adapter);
            }
        });

        mv.ObtenerUsuarios();

        return b.getRoot();
    }

    @Override
    public void onEliminar(Usuario usuario) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Eliminar usuario")
                .setMessage("¿Seguro que desea eliminar a " + usuario.getNombre() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {


                    Toast.makeText(getContext(), "Eliminado (pendiente API)", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onModificar(Usuario usuario) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_modificar_usuario, null);

        TextInputEditText edNombre = dialogView.findViewById(R.id.edDlgNombre);
        TextInputEditText edApellido = dialogView.findViewById(R.id.edDlgApellido);
        TextInputEditText edEmail = dialogView.findViewById(R.id.edDlgEmail);
        TextInputEditText edEspecializacion = dialogView.findViewById(R.id.edDlgEspecializacion);

        edNombre.setText(usuario.getNombre());
        edApellido.setText(usuario.getApellido());
        edEmail.setText(usuario.getEmail());
        edEspecializacion.setText(usuario.getEspecializacion());

        new AlertDialog.Builder(requireContext())
                .setTitle("Modificar usuario")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    usuario.setNombre(edNombre.getText().toString());
                    usuario.setApellido(edApellido.getText().toString());
                    usuario.setEmail(edEmail.getText().toString());
                    usuario.setEspecializacion(edEspecializacion.getText().toString());
                    // TODO: llamar al endpoint de actualizar usuario con estos datos
                    Toast.makeText(getContext(), "Guardado (pendiente API)", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onCambiarClave(Usuario usuario) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_cambiar_clave, null);
        TextInputEditText edClave = dialogView.findViewById(R.id.edDlgClave);

        new AlertDialog.Builder(requireContext())
                .setTitle("Cambiar clave de " + usuario.getNombre())
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String nuevaClave = edClave.getText().toString();
                    if (nuevaClave.isEmpty()) {
                        Toast.makeText(getContext(), "La clave no puede estar vacía", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // TODO: llamar al endpoint de cambiar clave con usuario.getId_Usuario() y nuevaClave
                    Toast.makeText(getContext(), "Clave cambiada (pendiente API)", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
