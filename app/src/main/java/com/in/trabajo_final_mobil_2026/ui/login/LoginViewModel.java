package com.in.trabajo_final_mobil_2026.ui.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.in.trabajo_final_mobil_2026.modelo.LoginRequest;
import com.in.trabajo_final_mobil_2026.modelo.LoginResponse;
import com.in.trabajo_final_mobil_2026.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mensajem = new MutableLiveData<>();
    private final MutableLiveData<LoginResponse> loginExitoso = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getMensajem() {
        return mensajem;
    }

    public MutableLiveData<LoginResponse> getLoginExitoso() {
        return loginExitoso;
    }

    public void recuperarDatos(String email, String clave) {
        if (email.isEmpty() || clave.isEmpty()) {
            mensajem.setValue("Por favor, complete todos los campos");
            return;
        }

        ApiClient.MiServicioMecanico servicio = ApiClient.getServicio();
        LoginRequest request = new LoginRequest(email, clave);
        Call<LoginResponse> call = servicio.login(request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call,
                                   @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse body = response.body();
                    // guardar el token para las próximas peticiones
                    ApiClient.crearToken(getApplication(), body.getToken());
                    Log.d("Token", body.getToken());
                    mensajem.setValue("Bienvenido " + body.getUsuario().getNombre());
                    loginExitoso.setValue(body);
                } else {
                    mensajem.setValue("Email o clave incorrectos");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                mensajem.setValue("Error de conexión: " + t.getMessage());
            }
        });
    }
}
