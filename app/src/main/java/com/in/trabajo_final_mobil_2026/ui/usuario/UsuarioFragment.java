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

import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.databinding.FragmentUsuarioBinding;
import com.in.trabajo_final_mobil_2026.modelo.Usuario;

import java.util.List;

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

        mv.getListaUsuarios().observe(getViewLifecycleOwner(), new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                UsuarioAdapter adapter = new UsuarioAdapter(usuarios,getLayoutInflater());

                b.rcusuarios.setAdapter(adapter);

                GridLayoutManager glm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                b.rcusuarios.setLayoutManager(glm);


            }
        });

        mv.ObtenerUsuarios();


        return b.getRoot();
    }



}