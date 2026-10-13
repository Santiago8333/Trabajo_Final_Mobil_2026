package com.in.trabajo_final_mobil_2026.ui.vehiculo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.in.trabajo_final_mobil_2026.modelo.Usuario;
import com.in.trabajo_final_mobil_2026.modelo.Vehiculo;

import java.util.List;

public class VehiculoViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Vehiculo>> listaVehiculo = new MutableLiveData<>();
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();



    public VehiculoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Vehiculo>> getListaVehiculo() {
        return listaVehiculo;
    }

    public MutableLiveData<String> getMensaje() {
        return mensaje;
    }

}