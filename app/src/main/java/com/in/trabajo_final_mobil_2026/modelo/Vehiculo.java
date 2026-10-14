package com.in.trabajo_final_mobil_2026.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vehiculo implements Serializable {
    @SerializedName(value = "id_Vehiculo", alternate = {"Id_Vehiculo"})
    private int id_Vehiculo;

    @SerializedName(value = "matricula", alternate = {"Matricula"})
    private String Matricula;

    @SerializedName(value = "modelo", alternate = {"Modelo"})
    private String Modelo;

    @SerializedName(value = "fecha_Creacion", alternate = {"Fecha_Creacion"})
    private String Fecha_Creacion;

    public int getId_Vehiculo() {
        return id_Vehiculo;
    }

    public void setId_Vehiculo(int id_Vehiculo) {
        this.id_Vehiculo = id_Vehiculo;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getFecha_Creacion() {
        return Fecha_Creacion;
    }

    public void setFecha_Creacion(String fecha_Creacion) {
        Fecha_Creacion = fecha_Creacion;
    }
}
