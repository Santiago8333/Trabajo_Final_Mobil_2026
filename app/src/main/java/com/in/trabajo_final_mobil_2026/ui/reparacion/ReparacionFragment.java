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

import com.in.trabajo_final_mobil_2026.databinding.FragmentReparacionBinding;
import com.in.trabajo_final_mobil_2026.modelo.Reparacion;

public class ReparacionFragment extends Fragment implements ReparacionAdapter.OnReparacionClickListener {
    private FragmentReparacionBinding b;
    private ReparacionViewModel mv;

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

        mv.ObtenerReparacion();

        return b.getRoot();
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
