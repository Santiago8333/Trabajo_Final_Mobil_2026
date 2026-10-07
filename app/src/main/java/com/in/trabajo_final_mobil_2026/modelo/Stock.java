package com.in.trabajo_final_mobil_2026.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Stock implements Serializable {
    @SerializedName(value = "id_Stock", alternate = {"Id_Stock"})
    private int id_Stock;

    @SerializedName(value = "nombre_Pieza", alternate = {"Nombre_Pieza"})
    private String Nombre_Pieza;

    @SerializedName(value = "cantidad_Stock", alternate = {"Cantidad_Stock"})
    private int Cantidad_Stock;

    @SerializedName(value = "precio_Unitario", alternate = {"Precio_Unitario"})
    private double Precio_Unitario;

    @SerializedName(value = "fecha_Creacion", alternate = {"Fecha_Creacion"})
    private String Fecha_Creacion;

    public int getId_Stock() {
        return id_Stock;
    }

    public void setId_Stock(int id_Stock) {
        this.id_Stock = id_Stock;
    }

    public String getNombre_Pieza() {
        return Nombre_Pieza;
    }

    public void setNombre_Pieza(String nombre_Pieza) {
        this.Nombre_Pieza = nombre_Pieza;
    }

    public int getCantidad_Stock() {
        return Cantidad_Stock;
    }

    public void setCantidad_Stock(int cantidad_Stock) {
        this.Cantidad_Stock = cantidad_Stock;
    }

    public double getPrecio_Unitario() {
        return Precio_Unitario;
    }

    public void setPrecio_Unitario(double precio_Unitario) {
        this.Precio_Unitario = precio_Unitario;
    }

    public String getFecha_Creacion() {
        return Fecha_Creacion;
    }

    public void setFecha_Creacion(String fecha_Creacion) {
        this.Fecha_Creacion = fecha_Creacion;
    }
}
