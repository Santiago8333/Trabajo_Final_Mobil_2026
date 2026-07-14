package com.in.trabajo_final_mobil_2026.ui.stock;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.databinding.FragmentStockBinding;
import com.in.trabajo_final_mobil_2026.databinding.FragmentUsuarioBinding;
import com.in.trabajo_final_mobil_2026.ui.usuario.UsuarioViewModel;

public class StockFragment extends Fragment {
    private FragmentStockBinding b;
    private StockViewModel mv;

    public static StockFragment newInstance() {
        return new StockFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentStockBinding.inflate(getLayoutInflater());
        mv = new ViewModelProvider(this).get(StockViewModel.class);




        return b.getRoot();
    }



}