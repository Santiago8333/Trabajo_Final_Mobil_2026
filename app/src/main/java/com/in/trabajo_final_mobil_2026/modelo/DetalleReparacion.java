package com.in.trabajo_final_mobil_2026.modelo;

import java.io.Serializable;

public class DetalleReparacion implements Serializable {
    private int Id_Detalle;
    private int idStock;
    private int idReparacion;
    private int Cantidad_Usada;
    private double Precio_Unitario_Momento;
    private double Subtotal;
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
