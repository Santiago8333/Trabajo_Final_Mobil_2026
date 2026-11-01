package com.in.trabajo_final_mobil_2026.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetalleReparacion implements Serializable {
    @SerializedName(value = "id_Detalle", alternate = {"Id_Detalle"})
    private int Id_Detalle;

    @SerializedName(value = "idStock", alternate = {"IdStock"})
    private int idStock;

    @SerializedName(value = "idReparacion", alternate = {"IdReparacion"})
    private int idReparacion;

    @SerializedName(value = "cantidad_Usada", alternate = {"Cantidad_Usada"})
    private int Cantidad_Usada;

    @SerializedName(value = "precio_Unitario_Momento", alternate = {"Precio_Unitario_Momento"})
    private double Precio_Unitario_Momento;

    @SerializedName(value = "subtotal", alternate = {"Subtotal"})
    private double Subtotal;

    @SerializedName(value = "fecha_Consumo", alternate = {"Fecha_Consumo"})
    private String Fecha_Consumo;

    public int getId_Detalle() {
        return Id_Detalle;
    }

    public void setId_Detalle(int id_Detalle) {
        this.Id_Detalle = id_Detalle;
    }

    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public int getIdReparacion() {
        return idReparacion;
    }

    public void setIdReparacion(int idReparacion) {
        this.idReparacion = idReparacion;
    }

    public int getCantidad_Usada() {
        return Cantidad_Usada;
    }

    public void setCantidad_Usada(int cantidad_Usada) {
        this.Cantidad_Usada = cantidad_Usada;
    }

    public double getPrecio_Unitario_Momento() {
        return Precio_Unitario_Momento;
    }

    public void setPrecio_Unitario_Momento(double precio_Unitario_Momento) {
        this.Precio_Unitario_Momento = precio_Unitario_Momento;
    }

    public double getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.Subtotal = subtotal;
    }

    public String getFecha_Consumo() {
        return Fecha_Consumo;
    }

    public void setFecha_Consumo(String fecha_Consumo) {
        this.Fecha_Consumo = fecha_Consumo;
    }
}
