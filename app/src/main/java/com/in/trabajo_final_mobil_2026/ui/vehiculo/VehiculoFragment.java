package com.in.trabajo_final_mobil_2026.ui.vehiculo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.in.trabajo_final_mobil_2026.databinding.FragmentVehiculoBinding;
import com.in.trabajo_final_mobil_2026.modelo.Vehiculo;

public class VehiculoFragment extends Fragment implements VehiculoAdapter.OnVehiculoClickListener {
    private FragmentVehiculoBinding b;

    private VehiculoViewModel mv;

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

        mv.ObtenerVehiculo();

        return b.getRoot();
    }

    @Override
    public void onModificar(Vehiculo vehiculo) {
        // TODO: abrir diálogo para modificar el vehículo
    }

    @Override
    public void onEliminar(Vehiculo vehiculo) {
        // TODO: abrir diálogo de confirmación para eliminar
    }
}
