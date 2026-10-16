package com.in.trabajo_final_mobil_2026.ui.reparacion;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.in.trabajo_final_mobil_2026.modelo.Reparacion;
import com.in.trabajo_final_mobil_2026.modelo.Vehiculo;
import com.in.trabajo_final_mobil_2026.request.ApiClient;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReparacionViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Reparacion>> listaReparacion = new MutableLiveData<>();
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();
    private final MutableLiveData<List<Vehiculo>> listaVehiculos = new MutableLiveData<>();


    public ReparacionViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getMensaje() {
        return mensaje;
    }

    public MutableLiveData<List<Reparacion>> getListaReparacion() {
        return listaReparacion;
    }

    public MutableLiveData<List<Vehiculo>> getListaVehiculos() {
        return listaVehiculos;
    }

    // vehículos para el desplegable del formulario de reparación
    public void ObtenerVehiculos() {
        String token = ApiClient.leerToken(getApplication());
        Call<List<Vehiculo>> call = ApiClient.getServicio().getVehiculo(token);

        call.enqueue(new Callback<List<Vehiculo>>() {
            @Override
            public void onResponse(Call<List<Vehiculo>> call, Response<List<Vehiculo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaVehiculos.setValue(response.body());
                } else {
                    Log.d("ErrorReparacion", "codigo vehiculos: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Vehiculo>> call, Throwable t) {
                Log.d("ErrorReparacion", t.getMessage());
            }
        });
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

    public void CrearReparacion(String idVehiculo, String nombre_cliente,
                                String fecha_ingreso, String descripcion_trabajo_realizado,
                                String motivo_ingreso, String costo_mano_de_obra) {

        if (idVehiculo.isEmpty() || nombre_cliente.isEmpty() || fecha_ingreso.isEmpty()
                || descripcion_trabajo_realizado.isEmpty() || motivo_ingreso.isEmpty()
                || costo_mano_de_obra.isEmpty()) {
            mensaje.setValue("Datos Faltantes");
            return;
        }

        // id del mecánico logueado (guardado en el login)
        int idUsuario = ApiClient.leerIdUsuario(getApplication());
        if (idUsuario == -1) {
            mensaje.setValue("Sesión no válida, vuelva a iniciar sesión");
            return;
        }

        try {
            Reparacion r = new Reparacion();
            r.setIdUsuario(idUsuario);
            r.setIdVehiculo(Integer.parseInt(idVehiculo));
            r.setNombre_Cliente(nombre_cliente);
            r.setFecha_Ingreso(fecha_ingreso);   // formato yyyy-MM-dd
            r.setDescripcion_Trabajo_Realizado(descripcion_trabajo_realizado);
            r.setMotivo_Ingreso(motivo_ingreso);
            r.setCosto_Mano_De_Obra(Double.parseDouble(costo_mano_de_obra));
            // fecha de creación = hoy (formato yyyy-MM-dd)
            r.setFecha_Creacion(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

            String token = ApiClient.leerToken(getApplication());
            Call<Void> call = ApiClient.getServicio().crearReparacion(token, r);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        mensaje.setValue("Reparación creada");
                        ObtenerReparacion();
                    } else if (response.code() == 401 || response.code() == 403) {
                        mensaje.setValue("No autorizado");
                    } else {
                        Log.d("ErrorReparacion", "codigo crear: " + response.code());
                        mensaje.setValue("No se pudo crear la reparación");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Log.d("ErrorReparacion", t.getMessage());
                    mensaje.setValue("Error de conexión");
                }
            });

        } catch (NumberFormatException e) {
            mensaje.setValue("El vehículo y el costo deben ser números válidos");
        }
    }




}
