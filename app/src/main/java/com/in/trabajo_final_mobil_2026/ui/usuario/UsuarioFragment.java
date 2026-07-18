package com.in.trabajo_final_mobil_2026.ui.usuario;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

    private ImageView avatarPreview;

    // para el debounce de la búsqueda
    private final Handler handlerBusqueda = new Handler(Looper.getMainLooper());
    private Runnable buscarRunnable = () -> { };

    private ActivityResultLauncher<Intent> selector;
    private Intent intent;

    public static UsuarioFragment newInstance() {
        return new UsuarioFragment();
    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selector = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult resultado) {
                        mv.recibirFoto(resultado);
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentUsuarioBinding.inflate(getLayoutInflater());

        mv = new ViewModelProvider(this).get(UsuarioViewModel.class);

        abrirGaleria();

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

        // cuando cambia la imagen seleccionada, se refleja en la vista previa del diálogo
        mv.getImagenSeleccionada().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null && avatarPreview != null) {
                avatarPreview.setImageURI(uri);
            }
        });

        // mostrar los mensajes del ViewModel
        mv.getMensaje().observe(getViewLifecycleOwner(), msg -> b.tvMensaje.setText(msg));

        // botón flotante para crear usuario
        b.fabCrearUsuario.setOnClickListener(v -> mostrarDialogoCrear());

        // buscar mientras se escribe, con debounce de 400ms
        b.edBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String texto = s.toString();
                // cancela la búsqueda pendiente anterior
                handlerBusqueda.removeCallbacks(buscarRunnable);
                buscarRunnable = () -> mv.BuscarUsuarios(texto);
                // programa la búsqueda 400ms después de la última tecla
                handlerBusqueda.postDelayed(buscarRunnable, 400);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        mv.ObtenerUsuarios();

        return b.getRoot();
    }

    private void mostrarDialogoCrear() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_crear_usuario, null);

        TextInputEditText edNombre = dialogView.findViewById(R.id.edCrearNombre);
        TextInputEditText edApellido = dialogView.findViewById(R.id.edCrearApellido);
        TextInputEditText edEspecializacion = dialogView.findViewById(R.id.edCrearEspecializacion);
        TextInputEditText edEmail = dialogView.findViewById(R.id.edCrearEmail);
        TextInputEditText edClave = dialogView.findViewById(R.id.edCrearClave);
        Spinner spRol = dialogView.findViewById(R.id.spCrearRol);

        new AlertDialog.Builder(requireContext())
                .setTitle("Crear usuario")
                .setView(dialogView)
                .setPositiveButton("Crear", (dialog, which) -> {
                    // posición 0 = Administrador (1), posición 1 = Empleado (2)
                    String rol = String.valueOf(spRol.getSelectedItemPosition() + 1);
                    mv.CrearUsuario(
                            edNombre.getText().toString(),
                            edApellido.getText().toString(),
                            edEspecializacion.getText().toString(),
                            edEmail.getText().toString(),
                            rol,
                            edClave.getText().toString()
                    );
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onEliminar(Usuario usuario) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Eliminar usuario")
                .setMessage("¿Seguro que desea eliminar a " + usuario.getNombre() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    mv.EliminarUsuario(usuario.getId_Usuario());

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
        Spinner spRol = dialogView.findViewById(R.id.spDlgRol);

        edNombre.setText(usuario.getNombre());
        edApellido.setText(usuario.getApellido());
        edEmail.setText(usuario.getEmail());
        edEspecializacion.setText(usuario.getEspecializacion());
        // preseleccionar el rol actual: rol 1 -> posición 0, rol 2 -> posición 1
        spRol.setSelection(usuario.getRol() - 1);

        new AlertDialog.Builder(requireContext())
                .setTitle("Modificar usuario")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String rol = String.valueOf(spRol.getSelectedItemPosition() + 1);
                    mv.ModificarUsuario(
                            usuario.getId_Usuario(),
                            edNombre.getText().toString(),
                            edApellido.getText().toString(),
                            edEspecializacion.getText().toString(),
                            edEmail.getText().toString(),
                            rol
                    );
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
                    mv.CambiarClave(usuario.getId_Usuario(),nuevaClave);
                    Toast.makeText(getContext(), "Clave cambiada (pendiente API)", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onSubirAvatar(Usuario usuario) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_subir_avatar, null);

        avatarPreview = dialogView.findViewById(R.id.ivPreviewAvatar);
        Button btnSeleccionar = dialogView.findViewById(R.id.btnSeleccionarImagen);

        // reiniciar selección previa
        mv.getImagenSeleccionada().setValue(null);

        // abrir la galería
        btnSeleccionar.setOnClickListener(v -> selector.launch(intent));

        new AlertDialog.Builder(requireContext())
                .setTitle("Avatar de " + usuario.getNombre())
                .setView(dialogView)
                .setPositiveButton("Subir", (dialog, which) -> {
                    Uri uri = mv.getImagenSeleccionada().getValue();
                    if (uri == null) {
                        Toast.makeText(getContext(), "Primero seleccione una imagen", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mv.SubirAvatar(usuario.getId_Usuario());
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
