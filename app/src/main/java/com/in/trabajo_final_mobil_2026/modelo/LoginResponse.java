package com.in.trabajo_final_mobil_2026.modelo;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private String token;
    private Usuario usuario;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
