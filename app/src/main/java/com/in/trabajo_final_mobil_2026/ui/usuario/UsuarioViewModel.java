package com.in.trabajo_final_mobil_2026.ui.usuario;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.in.trabajo_final_mobil_2026.modelo.AvatarResponse;
import com.in.trabajo_final_mobil_2026.modelo.ClaveRequest;
import com.in.trabajo_final_mobil_2026.modelo.Usuario;
import com.in.trabajo_final_mobil_2026.request.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Usuario>> listaUsuarios = new MutableLiveData<>();
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();
    private final MutableLiveData<Uri> imagenSeleccionadaM = new MutableLiveData<>();

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Usuario>> getListaUsuarios() {
        return listaUsuarios;
    }

    public MutableLiveData<String> getMensaje() {
        return mensaje;
    }

    public MutableLiveData<Uri> getImagenSeleccionada() {
        return imagenSeleccionadaM;
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            imagenSeleccionadaM.setValue(uri);
        }
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

public void EliminarUsuario(int id){
        String token = ApiClient.leerToken(getApplication());

        ApiClient.MiServicioMecanico servicio = ApiClient.getServicio();
        Call<Void> call = servicio.eliminarUsuario(token,id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mensaje.setValue("Usuario Eliminado");
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
                    mensaje.setValue("No se pudo Eliminar el Usuario");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ErrorUsuario",t.getMessage());
            }
        });

    }

    public void CambiarClave(int id, String clave){
        if(clave.isEmpty()) {
            mensaje.setValue("Datos Faltantes");
            return;
        }

        ClaveRequest c = new ClaveRequest(clave);
        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicioMecanico servicio = ApiClient.getServicio();
        Call<Void> call = servicio.cambiarClave(token, id, c);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mensaje.setValue("Clave actualizada");
                } else {
                    Log.d("ErrorUsuario", "codigo: " + response.code());
                    if (response.code() == 401 || response.code() == 403) {
                        mensaje.setValue("No autorizado");
                    } else {
                        mensaje.setValue("No se pudo cambiar la clave");
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("ErrorUsuario", t.getMessage());
                mensaje.setValue("Error de conexión");
            }
        });
    }

    public void CrearUsuario(String nombre, String apellido, String especializacion,
                             String email, String rol, String clave){

        if (nombre.isEmpty() || apellido.isEmpty() || especializacion.isEmpty()
                || email.isEmpty() || rol.isEmpty() || clave.isEmpty()) {
            mensaje.setValue("Datos Faltantes");
            return;
        }

        try{
            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setEspecializacion(especializacion);
            u.setEmail(email);
            u.setRol(Integer.parseInt(rol));
            u.setClave(clave);

            String token = ApiClient.leerToken(getApplication());
            ApiClient.MiServicioMecanico servicio = ApiClient.getServicio();
            Call<Void> call = servicio.crearUsuario(token, u);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        mensaje.setValue("Usuario creado correctamente");
                    } else if (response.code() == 409) {
                        mensaje.setValue("El email ya está registrado");
                    } else if (response.code() == 401 || response.code() == 403) {
                        mensaje.setValue("No autorizado");
                    } else {
                        Log.d("ErrorUsuario", "codigo crear: " + response.code());
                        mensaje.setValue("No se pudo crear el usuario");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d("ErrorUsuario", t.getMessage());
                    mensaje.setValue("Error de conexión");
                }
            });

        }catch (NumberFormatException e){
            Log.d("ErrorUsuario", e.getMessage());
            mensaje.setValue("Error: el Rol debe ser un número (1 o 2)");
        }
    }

    public void SubirAvatar(int id) {
        byte[] imagen = transformarImagen();
        if (imagen.length == 0) {
            mensaje.setValue("Debe ingresar una imagen");
            return;
        }

        RequestBody requestFile = RequestBody.create(imagen, MediaType.parse("image/jpeg"));
        // "archivo" DEBE coincidir con el nombre que pide la API
        MultipartBody.Part imagenPart = MultipartBody.Part.createFormData(
                "archivo", "usuario_" + id + ".jpg", requestFile);

        String token = ApiClient.leerToken(getApplication());
        ApiClient.MiServicioMecanico servicio = ApiClient.getServicio();
        Call<AvatarResponse> call = servicio.subirAvatar(token, id, imagenPart);

        call.enqueue(new Callback<AvatarResponse>() {
            @Override
            public void onResponse(Call<AvatarResponse> call, Response<AvatarResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mensaje.setValue("Avatar actualizado");
                } else {
                    Log.d("ErrorUsuario", "codigo avatar: " + response.code());
                    mensaje.setValue("No se pudo subir el avatar");
                }
            }

            @Override
            public void onFailure(Call<AvatarResponse> call, Throwable t) {
                Log.d("ErrorUsuario", t.getMessage());
                mensaje.setValue("Error de conexión");
            }
        });
    }

    // pasar la imagen seleccionada a bytes (JPEG)
    private byte[] transformarImagen() {
        try {
            Uri uri = imagenSeleccionadaM.getValue();
            if (uri == null) {
                mensaje.setValue("Debe ingresar una foto");
                return new byte[]{};
            }

            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (FileNotFoundException ex) {
            mensaje.setValue("Archivo de imagen no encontrado");
            return new byte[]{};
        } catch (Exception e) {
            mensaje.setValue("Error al procesar la imagen");
            return new byte[]{};
        }
    }
}
