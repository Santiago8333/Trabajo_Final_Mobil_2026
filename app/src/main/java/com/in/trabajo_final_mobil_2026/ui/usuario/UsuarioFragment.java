package com.in.trabajo_final_mobil_2026.ui.usuario;

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

public class UsuarioFragment extends Fragment {
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




        return b.getRoot();
    }



}