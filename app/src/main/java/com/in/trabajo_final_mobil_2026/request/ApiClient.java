package com.in.trabajo_final_mobil_2026.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.in.trabajo_final_mobil_2026.modelo.AvatarResponse;
import com.in.trabajo_final_mobil_2026.modelo.ClaveRequest;
import com.in.trabajo_final_mobil_2026.modelo.LoginRequest;
import com.in.trabajo_final_mobil_2026.modelo.LoginResponse;
import com.in.trabajo_final_mobil_2026.modelo.Reparacion;
import com.in.trabajo_final_mobil_2026.modelo.Stock;
import com.in.trabajo_final_mobil_2026.modelo.Usuario;
import com.in.trabajo_final_mobil_2026.modelo.Vehiculo;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiClient {

    public static final String BASE_URL = "https://mecanicomax.runasp.net/";

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

        //subir avatar (multipart/form-data)
        @Multipart
        @POST("api/Usuario/{id}/avatar")
        Call<AvatarResponse> subirAvatar(@Header("Authorization") String token,
                                         @Path("id") int id,
                                         @Part MultipartBody.Part archivo);

        //obtener lista de stock
        @GET("api/Stock")
        Call<List<Stock>> getStocks(@Header("Authorization") String token);

        //obtener stock por id
        @GET("api/Stock/{id}")
        Call<Stock> getStock(@Header("Authorization") String token,
                             @Path("id") int id);

        //crear stock
        @POST("api/Stock")
        Call<Void> crearStock(@Header("Authorization") String token,
                              @Body Stock stock);

        //actualizar stock
        @PUT("api/Stock/{id}")
        Call<Void> actualizarStock(@Header("Authorization") String token,
                                   @Path("id") int id,
                                   @Body Stock stock);

        //eliminar stock
        @DELETE("api/Stock/{id}")
        Call<Void> eliminarStock(@Header("Authorization") String token,
                                 @Path("id") int id);


        //buscar usuario por email nombre apellido
        @GET("api/Usuario/buscar")
        Call<List<Usuario>> getBuscar(@Header("Authorization") String token,
                                      @Query("texto") String texto);

        //buscar stock por nombre de pieza
        @GET("api/Stock/buscar")
        Call<List<Stock>> getBuscarStock(@Header("Authorization") String token,
                                      @Query("texto") String texto);

        //reparacion obtener
        @GET("api/Reparacion")
        Call<List<Reparacion>> getReparacions(@Header("Authorization") String token);

        //buscar reparacion
        @GET("api/Reparacion/buscar")
        Call<List<Reparacion>> getBuscarReparacion(@Header("Authorization") String token,
                                      @Query("texto") String texto);

        //crear reparacion
        @POST("api/Reparacion")
        Call<Void> crearReparacion(@Header("Authorization") String token,
                                @Body Reparacion reparacion);

        //actualizar reparacion
        @PUT("api/Reparacion/{id}")
        Call<Void> actualizarReparacion(@Header("Authorization") String token,
                                        @Path("id") int id,
                                        @Body Reparacion reparacion);

        //eliminar reparacion
        @DELETE("api/Reparacion/{id}")
        Call<Void> eliminarReparacion(@Header("Authorization") String token,
                                 @Path("id") int id);

        //obtener vehiculos
        @GET("api/Vehiculo")
        Call<List<Vehiculo>> getVehiculo(@Header("Authorization") String token);

        //eliminar vehiculo
        @DELETE("api/Vehiculo/{id}")
        Call<Void> eliminarVehiculo(@Header("Authorization") String token,
                                      @Path("id") int id);
        //crear vehiculo
        @POST("api/Vehiculo")
        Call<Void> crearVehiculo(@Header("Authorization") String token,
                              @Body Vehiculo vehiculo);

        //actualizar vehiculo
        @PUT("api/Vehiculo/{id}")
        Call<Void> actualizarVehiculo(@Header("Authorization") String token,
                                   @Path("id") int id,
                                   @Body Vehiculo vehiculo);

        //buscar vehiculo por nombre de matricula
        @GET("api/Vehiculo/matricula/{matricula}")
        Call<Vehiculo> getBuscarVehiculoMatricula(@Header("Authorization") String token,
                                                  @Path("matricula") String matricula);

        //buscar vehiculos por coincidencia parcial en matricula o modelo
        @GET("api/Vehiculo/buscar")
        Call<List<Vehiculo>> getBuscarVehiculos(@Header("Authorization") String token,
                                                @Query("texto") String texto);
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

    // id del usuario logueado (se guarda al hacer login)
    public static void guardarIdUsuario(Context context, int idUsuario) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        sp.edit().putInt("idUsuario", idUsuario).apply();
    }

    public static int leerIdUsuario(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getInt("idUsuario", -1); // -1 = no hay usuario guardado
    }
}
