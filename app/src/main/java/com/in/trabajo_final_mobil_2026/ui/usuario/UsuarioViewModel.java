package com.in.trabajo_final_mobil_2026.ui.usuario;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.in.trabajo_final_mobil_2026.modelo.Usuario;
import com.in.trabajo_final_mobil_2026.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Usuario>> listaUsuarios = new MutableLiveData<>();
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Usuario>> getListaUsuarios() {
        return listaUsuarios;
    }

    public MutableLiveData<String> getMensaje() {
        return mensaje;
    }

    public void ObtenerUsuarios() {
        String token = ApiClient.leerToken(getApplication());

        ApiClient.MiServicioMecanico servicio = ApiClient.getServicio();
        Call<List<Usuario>> call = servicio.getUsuarios(token);

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(@NonNull Call<List<Usuario>> call,
                                   @NonNull Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaUsuarios.setValue(response.body());
                } else {
                    try{

                        Log.d("ErrorUsuario", "codigo: " + response.code());
                        Log.d("ErrorUsuario", "mensaje: " + response.message());
                        Log.d("ErrorUsuario", "body: " + response.errorBody().string());

                    }catch (Exception e){
                        Log.d("ErrorUsuario",e.toString());
                    }
                    if(response.code() == 403 || response.code() == 401){
                        Toast.makeText(getApplication(),"No se obtuvieron Usuarios",Toast.LENGTH_LONG).show();
                        ApiClient.crearToken(getApplication(),"");
                        System.exit(0);
                    }
                    //mensaje.setValue("No se pudieron obtener los usuarios");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Usuario>> call, @NonNull Throwable t) {
                Log.d("ErrorUsuario",t.getMessage());
                //mensaje.setValue("Error de conexión: " + t.getMessage());
            }
        });
    }
}
