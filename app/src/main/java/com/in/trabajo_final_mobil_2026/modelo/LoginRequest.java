package com.in.trabajo_final_mobil_2026.modelo;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    public String Email;
    public String Clave;

    public LoginRequest(String email, String clave) {
        this.Email = email;
        this.Clave = clave;
    }
}
