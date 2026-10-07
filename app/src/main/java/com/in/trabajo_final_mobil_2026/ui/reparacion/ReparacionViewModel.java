package com.in.trabajo_final_mobil_2026.ui.reparacion;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.in.trabajo_final_mobil_2026.modelo.Reparacion;
import com.in.trabajo_final_mobil_2026.modelo.Stock;
import com.in.trabajo_final_mobil_2026.request.ApiClient;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReparacionViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Reparacion>> listaReparacion = new MutableLiveData<>();
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();


    public ReparacionViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getMensaje() {
        return mensaje;
    }

    public MutableLiveData<List<Reparacion>> getListaReparacion() {
        return listaReparacion;
    }

    public void ObtenerReparacion() {
        String token = ApiClient.leerToken(getApplication());
        Call<List<Reparacion>> call = ApiClient.getServicio().getReparacions(token);

        call.enqueue(new Callback<List<Reparacion>>() {
            @Override
            public void onResponse(Call<List<Reparacion>> call, Response<List<Reparacion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaReparacion.setValue(response.body());
                } else {
                    Log.d("ErrorReperacion", "codigo: " + response.code());
                    mensaje.setValue("No se pudo obtener el Reperacion");
                }
            }

            @Override
            public void onFailure(Call<List<Reparacion>> call, Throwable t) {
                Log.d("ErrorReperacion", t.getMessage());
                mensaje.setValue("Error de conexión");
            }
        });



    }




}