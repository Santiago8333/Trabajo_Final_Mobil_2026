package com.in.trabajo_final_mobil_2026.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.in.trabajo_final_mobil_2026.modelo.LoginRequest;
import com.in.trabajo_final_mobil_2026.modelo.LoginResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ApiClient {
    // Debe empezar con http:// (o https://) e incluir el puerto de tu API, y terminar en /
    // IP de la PC donde corre la API (debe escuchar en 0.0.0.0 y tener el firewall abierto)
    public static final String BASE_URL = "http://192.168.0.169:5064/";

    public static MiServicioMecanico getServicio() {
        Gson gson = new GsonBuilder().setLenient().create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(MiServicioMecanico.class);
    }

    public interface MiServicioMecanico{
        @POST("api/Usuario/login")
        Call<LoginResponse> login(@Body LoginRequest request);




    }





    public static void crearToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", "Bearer "+token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }
}
