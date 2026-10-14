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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    public void EliminarVehiculo(int id){
        String token = ApiClient.leerToken(getApplication());

        ApiClient.MiServicioMecanico servicio = ApiClient.getServicio();
        Call<Void> call = servicio.eliminarVehiculo(token,id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mensaje.setValue("Vehiculo Eliminado");
                    ObtenerVehiculo();
                } else {
                    try{

                        Log.d("ErrorVehiculo", "codigo: " + response.code());
                        Log.d("ErrorVehiculo", "mensaje: " + response.message());
                        Log.d("ErrorVehiculo", "body: " + response.errorBody().string());

                    }catch (Exception e){
                        Log.d("ErrorVehiculo",e.toString());
                    }
                    if (response.code() == 404) {
                        mensaje.setValue("El Vehiculo no existe");
                    } else if (response.code() == 403 || response.code() == 401) {
                        mensaje.setValue("No autorizado");
                    } else {
                        mensaje.setValue("No se pudo Eliminar el Vehiculo");
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ErrorVehiculo",t.getMessage());
                mensaje.setValue("Error de conexión");
            }
        });

    }

    public void CrearVehiculo(String matricula, String modelo){
        if (matricula.isEmpty() || modelo.isEmpty()) {
            mensaje.setValue("Datos Faltantes");
            return;
        }

        Vehiculo v = new Vehiculo();
        v.setMatricula(matricula);
        v.setModelo(modelo);
        // fecha de creación = hoy (formato yyyy-MM-dd)
        v.setFecha_Creacion(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicioMecanico servicio = ApiClient.getServicio();
        Call<Void> call = servicio.crearVehiculo(token, v);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mensaje.setValue("Vehiculo creado correctamente");
                    ObtenerVehiculo();
                } else if (response.code() == 401 || response.code() == 403) {
                    mensaje.setValue("No autorizado");
                } else {
                    Log.d("ErrorVehiculo", "codigo crear: " + response.code());
                    mensaje.setValue("No se pudo crear el Vehiculo");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ErrorVehiculo", t.getMessage());
                mensaje.setValue("Error de conexión");
            }
        });
    }

    public void ModificarVehiculo(int id, String matricula, String modelo){
        if (matricula.isEmpty() || modelo.isEmpty()) {
            mensaje.setValue("Datos Faltantes");
            return;
        }

        Vehiculo v = new Vehiculo();
        v.setMatricula(matricula);
        v.setModelo(modelo);

        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicioMecanico servicio = ApiClient.getServicio();
        Call<Void> call = servicio.actualizarVehiculo(token, id, v);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mensaje.setValue("Vehiculo actualizado");
                    ObtenerVehiculo();
                } else if (response.code() == 404) {
                    mensaje.setValue("El Vehiculo no existe");
                } else if (response.code() == 401 || response.code() == 403) {
                    mensaje.setValue("No autorizado");
                } else {
                    Log.d("ErrorVehiculo", "codigo modificar: " + response.code());
                    mensaje.setValue("No se pudo actualizar el Vehiculo");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ErrorVehiculo", t.getMessage());
                mensaje.setValue("Error de conexión");
            }
        });

    }

}
