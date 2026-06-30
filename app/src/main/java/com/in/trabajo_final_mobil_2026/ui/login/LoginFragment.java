package com.in.trabajo_final_mobil_2026.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.databinding.FragmentLoginBinding;
import com.in.trabajo_final_mobil_2026.modelo.LoginResponse;
import com.in.trabajo_final_mobil_2026.ui.reparacion.ReparacionViewModel;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding b;

    private LoginViewModel mv;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
            b = FragmentLoginBinding.inflate(getLayoutInflater());
            mv= new ViewModelProvider(this).get(LoginViewModel.class);

            b.btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = b.edEmail.getText().toString();
                    String clave = b.edClave.getText().toString();
                    mv.recuperarDatos(email,clave);
                }
            });

            mv.getMensajem().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    b.tvMensaje.setText(s);
                }
            });

            mv.getLoginExitoso().observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
                @Override
                public void onChanged(LoginResponse loginResponse) {
                    if (loginResponse != null) {
                        NavHostFragment.findNavController(LoginFragment.this)
                                .navigate(R.id.action_login_to_reparacion);
                    }
                }
            });



        return b.getRoot();
    }



}