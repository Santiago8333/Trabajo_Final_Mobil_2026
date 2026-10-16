package com.in.trabajo_final_mobil_2026.ui.reparacion;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.DatePickerDialog;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.databinding.FragmentReparacionBinding;
import com.in.trabajo_final_mobil_2026.modelo.Reparacion;
import com.in.trabajo_final_mobil_2026.modelo.Vehiculo;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReparacionFragment extends Fragment implements ReparacionAdapter.OnReparacionClickListener {
    private FragmentReparacionBinding b;
    private ReparacionViewModel mv;
    private List<Vehiculo> vehiculos; // para el desplegable del formulario

    public static ReparacionFragment newInstance() {
        return new ReparacionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentReparacionBinding.inflate(getLayoutInflater());
        mv = new ViewModelProvider(this).get(ReparacionViewModel.class);

        b.rcReparacion.setLayoutManager(new LinearLayoutManager(getContext()));

        mv.getListaReparacion().observe(getViewLifecycleOwner(), reparaciones ->
                b.rcReparacion.setAdapter(new ReparacionAdapter(reparaciones, getLayoutInflater(), this)));

        mv.getMensaje().observe(getViewLifecycleOwner(), msg -> b.tvMensajeReparacion.setText(msg));

        // vehículos para el desplegable del formulario
        mv.getListaVehiculos().observe(getViewLifecycleOwner(), lista -> vehiculos = lista);

        // botón flotante para crear reparación
        b.fabCrearReparacion.setOnClickListener(v -> mostrarDialogoCrear());

        mv.ObtenerReparacion();
        mv.ObtenerVehiculos();

        return b.getRoot();
    }

    private void mostrarDialogoCrear() {
        if (vehiculos == null || vehiculos.isEmpty()) {
            Toast.makeText(getContext(), "No hay vehículos cargados. Cree uno primero.", Toast.LENGTH_SHORT).show();
            mv.ObtenerVehiculos(); // reintenta por si todavía no habían llegado
            return;
        }

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_reparacion, null);

        TextInputEditText edCliente = dialogView.findViewById(R.id.edRepCliente);
        Spinner spVehiculo = dialogView.findViewById(R.id.spRepVehiculo);

        // desplegable: se muestra "matrícula - modelo", pero se guarda la posición
        List<String> opciones = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            opciones.add(v.getMatricula() + " - " + v.getModelo());
        }
        ArrayAdapter<String> adapterVehiculos = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, opciones);
        adapterVehiculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVehiculo.setAdapter(adapterVehiculos);
        TextInputEditText edFechaIngreso = dialogView.findViewById(R.id.edRepFechaIngreso);
        TextInputEditText edMotivo = dialogView.findViewById(R.id.edRepMotivo);
        TextInputEditText edDescripcion = dialogView.findViewById(R.id.edRepDescripcion);
        TextInputEditText edCosto = dialogView.findViewById(R.id.edRepCosto);

        // fecha de ingreso: por defecto hoy, y al tocar se abre un calendario
        Calendar hoy = Calendar.getInstance();
        edFechaIngreso.setText(String.format(Locale.getDefault(), "%04d-%02d-%02d",
                hoy.get(Calendar.YEAR), hoy.get(Calendar.MONTH) + 1, hoy.get(Calendar.DAY_OF_MONTH)));

        edFechaIngreso.setOnClickListener(v -> new DatePickerDialog(requireContext(),
                (picker, anio, mes, dia) -> edFechaIngreso.setText(
                        String.format(Locale.getDefault(), "%04d-%02d-%02d", anio, mes + 1, dia)),
                hoy.get(Calendar.YEAR), hoy.get(Calendar.MONTH), hoy.get(Calendar.DAY_OF_MONTH)
        ).show());

        new AlertDialog.Builder(requireContext())
                .setTitle("Nueva reparación")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) ->
                        mv.CrearReparacion(
                                // id del vehículo elegido en el desplegable
                                String.valueOf(vehiculos.get(spVehiculo.getSelectedItemPosition()).getId_Vehiculo()),
                                edCliente.getText().toString(),
                                edFechaIngreso.getText().toString(),
                                edDescripcion.getText().toString(),
                                edMotivo.getText().toString(),
                                edCosto.getText().toString()
                        ))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onModificar(Reparacion reparacion) {
        // TODO: abrir diálogo para modificar la reparación
    }

    @Override
    public void onEliminar(Reparacion reparacion) {
        // TODO: abrir diálogo de confirmación para eliminar
    }
}
