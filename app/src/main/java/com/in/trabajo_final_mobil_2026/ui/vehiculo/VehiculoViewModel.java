package com.in.trabajo_final_mobil_2026.ui.vehiculo;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.in.trabajo_final_mobil_2026.modelo.Vehiculo;
import com.in.trabajo_final_mobil_2026.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void ObtenerVehiculo() {
        String token = ApiClient.leerToken(getApplication());

        ApiClient.MiServicioMecanico servicio = ApiClient.getServicio();
        Call<List<Vehiculo>> call = servicio.getVehiculo(token);

        call.enqueue(new Callback<List<Vehiculo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Vehiculo>> call,
                                   @NonNull Response<List<Vehiculo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaVehiculo.setValue(response.body());
                } else {
                    try{

                        Log.d("ErrorVehiculo", "codigo: " + response.code());
                        Log.d("ErrorVehiculo", "mensaje: " + response.message());
                        Log.d("ErrorVehiculo", "body: " + response.errorBody().string());

                    }catch (Exception e){
                        Log.d("ErrorVehiculo",e.toString());
                    }
                    if(response.code() == 403 || response.code() == 401){
                        Toast.makeText(getApplication(),"No se obtuvieron Vehiculo",Toast.LENGTH_LONG).show();
                        ApiClient.crearToken(getApplication(),"");
                        System.exit(0);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Vehiculo>> call, @NonNull Throwable t) {
                Log.d("ErrorVehiculo",t.getMessage());
                //mensaje.setValue("Error de conexión: " + t.getMessage());
            }
        });
    }


}
