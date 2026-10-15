package com.in.trabajo_final_mobil_2026.ui.vehiculo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.databinding.FragmentVehiculoBinding;
import com.in.trabajo_final_mobil_2026.modelo.Vehiculo;

public class VehiculoFragment extends Fragment implements VehiculoAdapter.OnVehiculoClickListener {
    private FragmentVehiculoBinding b;

    private VehiculoViewModel mv;

    private final Handler handlerBusqueda = new Handler(Looper.getMainLooper());

    private Runnable buscarRunnable = () -> { };

    public static VehiculoFragment newInstance() {
        return new VehiculoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentVehiculoBinding.inflate(getLayoutInflater());

        mv = new ViewModelProvider(this).get(VehiculoViewModel.class);

        b.rcVehiculo.setLayoutManager(new LinearLayoutManager(getContext()));

        mv.getListaVehiculo().observe(getViewLifecycleOwner(), vehiculos ->
                b.rcVehiculo.setAdapter(new VehiculoAdapter(vehiculos, getLayoutInflater(), this)));

        mv.getMensaje().observe(getViewLifecycleOwner(), msg -> b.tvMensajeVehiculo.setText(msg));

        // botón flotante para crear vehículo
        b.fabCrearVehiculo.setOnClickListener(v -> mostrarDialogoCrear());

        mv.ObtenerVehiculo();

        b.edBuscarVehiculo.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String texto = s.toString();
                // cancela la búsqueda pendiente anterior
                handlerBusqueda.removeCallbacks(buscarRunnable);
                buscarRunnable = () -> mv.BuscarVehiculo(texto);
                // programa la búsqueda 400ms después de la última tecla
                handlerBusqueda.postDelayed(buscarRunnable,400);

            }
        });


        return b.getRoot();
    }

    private void mostrarDialogoCrear() {
        mostrarDialogoVehiculo(null);
    }


    private void mostrarDialogoVehiculo(Vehiculo vehiculo) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_vehiculo, null);

        TextInputEditText edMatricula = dialogView.findViewById(R.id.edVehiculoMatricula);
        TextInputEditText edModelo = dialogView.findViewById(R.id.edVehiculoModelo);

        boolean esModificar = vehiculo != null;
        if (esModificar) {
            edMatricula.setText(vehiculo.getMatricula());
            edModelo.setText(vehiculo.getModelo());
        }

        new AlertDialog.Builder(requireContext())
                .setTitle(esModificar ? "Modificar vehículo" : "Crear vehículo")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String matricula = edMatricula.getText().toString();
                    String modelo = edModelo.getText().toString();
                    if (esModificar) {
                        mv.ModificarVehiculo(vehiculo.getId_Vehiculo(), matricula, modelo);
                    } else {
                        mv.CrearVehiculo(matricula, modelo);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onModificar(Vehiculo vehiculo) {
        mostrarDialogoVehiculo(vehiculo);
    }

    @Override
    public void onEliminar(Vehiculo vehiculo) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Eliminar vehículo")
                .setMessage("¿Seguro que desea eliminar el vehículo " + vehiculo.getMatricula() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> mv.EliminarVehiculo(vehiculo.getId_Vehiculo()))
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
