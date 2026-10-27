package com.in.trabajo_final_mobil_2026.ui.reparacion;

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

    // para el debounce de la búsqueda
    private final Handler handlerBusqueda = new Handler(Looper.getMainLooper());
    private Runnable buscarRunnable = () -> { };

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

        // buscar mientras se escribe, con debounce de 400ms
        b.edBuscarReparacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String texto = s.toString();
                // cancela la búsqueda pendiente anterior
                handlerBusqueda.removeCallbacks(buscarRunnable);
                buscarRunnable = () -> mv.BuscarReparaciones(texto);
                // programa la búsqueda 400ms después de la última tecla
                handlerBusqueda.postDelayed(buscarRunnable, 400);

            }
        });


        return b.getRoot();
    }

    private void mostrarDialogoCrear() {
        mostrarDialogoReparacion(null);
    }

    // si reparacion == null -> crear; si no -> modificar (precargado)
    private void mostrarDialogoReparacion(Reparacion reparacion) {
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

        boolean esModificar = reparacion != null;

        // fecha de ingreso: por defecto hoy (o la de la reparación), al tocar se abre un calendario
        Calendar hoy = Calendar.getInstance();
        edFechaIngreso.setText(String.format(Locale.getDefault(), "%04d-%02d-%02d",
                hoy.get(Calendar.YEAR), hoy.get(Calendar.MONTH) + 1, hoy.get(Calendar.DAY_OF_MONTH)));

        if (esModificar) {
            edCliente.setText(reparacion.getNombre_Cliente());
            edMotivo.setText(reparacion.getMotivo_Ingreso());
            edDescripcion.setText(reparacion.getDescripcion_Trabajo_Realizado());
            edCosto.setText(String.valueOf(reparacion.getCosto_Mano_De_Obra()));
            if (reparacion.getFecha_Ingreso() != null) {
                // la API puede devolver "2026-07-14T00:00:00": se deja solo la fecha
                edFechaIngreso.setText(reparacion.getFecha_Ingreso().split("T")[0]);
            }
            // preseleccionar el vehículo de la reparación
            for (int i = 0; i < vehiculos.size(); i++) {
                if (vehiculos.get(i).getId_Vehiculo() == reparacion.getIdVehiculo()) {
                    spVehiculo.setSelection(i);
                    break;
                }
            }
        }

        edFechaIngreso.setOnClickListener(v -> {
            // el calendario abre en la fecha que tiene el campo
            Calendar cal = Calendar.getInstance();
            String[] p = edFechaIngreso.getText().toString().split("-");
            if (p.length == 3) {
                try {
                    cal.set(Integer.parseInt(p[0]), Integer.parseInt(p[1]) - 1, Integer.parseInt(p[2]));
                } catch (NumberFormatException ignored) { }
            }
            new DatePickerDialog(requireContext(),
                    (picker, anio, mes, dia) -> edFechaIngreso.setText(
                            String.format(Locale.getDefault(), "%04d-%02d-%02d", anio, mes + 1, dia)),
                    cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        new AlertDialog.Builder(requireContext())
                .setTitle(esModificar ? "Modificar reparación" : "Nueva reparación")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    // id del vehículo elegido en el desplegable
                    String idVehiculo = String.valueOf(
                            vehiculos.get(spVehiculo.getSelectedItemPosition()).getId_Vehiculo());
                    String cliente = edCliente.getText().toString();
                    String fecha = edFechaIngreso.getText().toString();
                    String descripcion = edDescripcion.getText().toString();
                    String motivo = edMotivo.getText().toString();
                    String costo = edCosto.getText().toString();

                    if (esModificar) {
                        mv.ModificarReparacion(reparacion.getId_Reparacion(), reparacion.getIdUsuario(),
                                idVehiculo, cliente, fecha, descripcion, motivo, costo);
                    } else {
                        mv.CrearReparacion(idVehiculo, cliente, fecha, descripcion, motivo, costo);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onModificar(Reparacion reparacion) {
        mostrarDialogoReparacion(reparacion);
    }

    @Override
    public void onEliminar(Reparacion reparacion) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Eliminar reparación")
                .setMessage("¿Seguro que desea eliminar la reparación de " + reparacion.getNombre_Cliente() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> mv.EliminarReparacion(reparacion.getId_Reparacion()))
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
