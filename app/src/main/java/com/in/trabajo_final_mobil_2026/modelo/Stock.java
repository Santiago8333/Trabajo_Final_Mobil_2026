package com.in.trabajo_final_mobil_2026.modelo;

import java.io.Serializable;

public class Stock implements Serializable {
    private int id_Stock;
    private String Nombre_Pieza;
    private int Cantidad_Stock;
    private double Precio_Unitario;
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
