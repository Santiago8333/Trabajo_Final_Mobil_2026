package com.in.trabajo_final_mobil_2026.ui.stock;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.in.trabajo_final_mobil_2026.modelo.Stock;
import com.in.trabajo_final_mobil_2026.request.ApiClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Stock>> listaStock = new MutableLiveData<>();
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();

    public StockViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Stock>> getListaStock() {
        return listaStock;
    }

    public MutableLiveData<String> getMensaje() {
        return mensaje;
    }

    public void ObtenerStock() {
        String token = ApiClient.leerToken(getApplication());
        Call<List<Stock>> call = ApiClient.getServicio().getStocks(token);

        call.enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(@NonNull Call<List<Stock>> call, @NonNull Response<List<Stock>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaStock.setValue(response.body());
                } else {
                    Log.d("ErrorStock", "codigo: " + response.code());
                    mensaje.setValue("No se pudo obtener el stock");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Stock>> call, @NonNull Throwable t) {
                Log.d("ErrorStock", t.getMessage());
                mensaje.setValue("Error de conexión");
            }
        });
    }

    public void CrearStock(String nombre, String cantidad, String precio) {
        if (nombre.isEmpty() || cantidad.isEmpty() || precio.isEmpty()) {
            mensaje.setValue("Datos Faltantes");
            return;
        }

        try {
            Stock s = new Stock();
            s.setNombre_Pieza(nombre);
            s.setCantidad_Stock(Integer.parseInt(cantidad));
            s.setPrecio_Unitario(Double.parseDouble(precio));
            // fecha de creación = hoy (formato yyyy-MM-dd)
            s.setFecha_Creacion(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

            String token = ApiClient.leerToken(getApplication());
            Call<Void> call = ApiClient.getServicio().crearStock(token, s);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        mensaje.setValue("Pieza creada");
                        ObtenerStock();
                    } else if (response.code() == 401 || response.code() == 403) {
                        mensaje.setValue("No autorizado");
                    } else {
                        Log.d("ErrorStock", "codigo crear: " + response.code());
                        mensaje.setValue("No se pudo crear la pieza");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Log.d("ErrorStock", t.getMessage());
                    mensaje.setValue("Error de conexión");
                }
            });

        } catch (NumberFormatException e) {
            mensaje.setValue("Cantidad y Precio deben ser números válidos");
        }
    }

    public void ModificarStock(int id, String nombre, String cantidad, String precio) {
        if (nombre.isEmpty() || cantidad.isEmpty() || precio.isEmpty()) {
            mensaje.setValue("Datos Faltantes");
            return;
        }

        try {
            Stock s = new Stock();
            s.setNombre_Pieza(nombre);
            s.setCantidad_Stock(Integer.parseInt(cantidad));
            s.setPrecio_Unitario(Double.parseDouble(precio));


            String token = ApiClient.leerToken(getApplication());
            Call<Void> call = ApiClient.getServicio().actualizarStock(token, id, s);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        mensaje.setValue("Pieza actualizada");
                        ObtenerStock();
                    } else if (response.code() == 404) {
                        mensaje.setValue("La pieza no existe");
                    } else if (response.code() == 401 || response.code() == 403) {
                        mensaje.setValue("No autorizado");
                    } else {
                        Log.d("ErrorStock", "codigo modificar: " + response.code());
                        mensaje.setValue("No se pudo actualizar la pieza");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Log.d("ErrorStock", t.getMessage());
                    mensaje.setValue("Error de conexión");
                }
            });

        } catch (NumberFormatException e) {
            mensaje.setValue("Cantidad y Precio deben ser números válidos");
        }
    }

    public void EliminarStock(int id) {
        String token = ApiClient.leerToken(getApplication());
        Call<Void> call = ApiClient.getServicio().eliminarStock(token, id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    mensaje.setValue("Pieza eliminada");
                    ObtenerStock();
                } else if (response.code() == 404) {
                    mensaje.setValue("La pieza no existe");
                } else if (response.code() == 401 || response.code() == 403) {
                    mensaje.setValue("No autorizado");
                } else {
                    Log.d("ErrorStock", "codigo eliminar: " + response.code());
                    mensaje.setValue("No se pudo eliminar la pieza");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("ErrorStock", t.getMessage());
                mensaje.setValue("Error de conexión");
            }
        });
    }
}
