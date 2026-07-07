package com.in.trabajo_final_mobil_2026.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.in.trabajo_final_mobil_2026.modelo.ClaveRequest;
import com.in.trabajo_final_mobil_2026.modelo.LoginRequest;
import com.in.trabajo_final_mobil_2026.modelo.LoginResponse;
import com.in.trabajo_final_mobil_2026.modelo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class ApiClient {

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
        //login usuario
        @POST("api/Usuario/login")
        Call<LoginResponse> login(@Body LoginRequest request);

        //obtener usuarios
        @GET("api/Usuario")
        Call<List<Usuario>> getUsuarios(@Header("Authorization") String token);

        //crear usuario
        @POST("api/Usuario")
        Call<Void> crearUsuario(@Header("Authorization") String token,
                                @Body Usuario usuario);

        //actualizar usuario
        @PUT("api/Usuario/{id}")
        Call<Void> actualizarUsuario(@Header("Authorization") String token,
                                     @Path("id") int id,
                                     @Body Usuario usuario);

        //cambiar clave
        @PUT("api/Usuario/{id}/clave")
        Call<Void> cambiarClave(@Header("Authorization") String token,
                                @Path("id") int id,
                                @Body ClaveRequest clave);

        //eliminar usuario
        @DELETE("api/Usuario/{id}")
        Call<Void> eliminarUsuario(@Header("Authorization") String token,
                                   @Path("id") int id);
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
