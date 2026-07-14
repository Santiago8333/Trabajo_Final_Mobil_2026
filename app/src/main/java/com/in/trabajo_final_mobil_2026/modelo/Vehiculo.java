package com.in.trabajo_final_mobil_2026.modelo;

import java.io.Serializable;

public class Vehiculo implements Serializable {
    private int id_Vehiculo;
    public String Modelo;
    private String Fecha_Creacion;

    public int getId_Vehiculo() {
        return id_Vehiculo;
    }

    public void setId_Vehiculo(int id_Vehiculo) {
        this.id_Vehiculo = id_Vehiculo;
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
