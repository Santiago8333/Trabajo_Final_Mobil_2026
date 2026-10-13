package com.in.trabajo_final_mobil_2026.ui.vehiculo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.databinding.FragmentUsuarioBinding;
import com.in.trabajo_final_mobil_2026.databinding.FragmentVehiculoBinding;
import com.in.trabajo_final_mobil_2026.ui.usuario.UsuarioViewModel;

public class VehiculoFragment extends Fragment {
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


        return b.getRoot();
    }



}