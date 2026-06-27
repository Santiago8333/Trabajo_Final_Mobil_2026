package com.in.trabajo_final_mobil_2026.ui.reparacion;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.in.trabajo_final_mobil_2026.R;

public class ReparacionFragment extends Fragment {

    private ReparacionViewModel mViewModel;

    public static ReparacionFragment newInstance() {
        return new ReparacionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reparacion, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReparacionViewModel.class);
        // TODO: Use the ViewModel
    }

}