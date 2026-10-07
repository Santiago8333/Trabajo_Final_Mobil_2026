package com.in.trabajo_final_mobil_2026.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reparacion implements Serializable {
    @SerializedName(value = "id_Reparacion", alternate = {"Id_Reparacion"})
    private int id_Reparacion;

    @SerializedName(value = "idUsuario", alternate = {"IdUsuario"})
    private int idUsuario;

    @SerializedName(value = "idVehiculo", alternate = {"IdVehiculo"})
    private int idVehiculo;

    @SerializedName(value = "nombre_Cliente", alternate = {"Nombre_Cliente"})
    private String Nombre_Cliente;

    @SerializedName(value = "fecha_Ingreso", alternate = {"Fecha_Ingreso"})
    private String Fecha_Ingreso;

    @SerializedName(value = "descripcion_Trabajo_Realizado", alternate = {"Descripcion_Trabajo_Realizado"})
    private String Descripcion_Trabajo_Realizado;

    @SerializedName(value = "motivo_Ingreso", alternate = {"Motivo_Ingreso"})
    private String Motivo_Ingreso;

    @SerializedName(value = "costo_Mano_De_Obra", alternate = {"Costo_Mano_De_Obra"})
    private double Costo_Mano_De_Obra;

    @SerializedName(value = "fecha_Creacion", alternate = {"Fecha_Creacion"})
    private String Fecha_Creacion;

    public int getId_Reparacion() {
        return id_Reparacion;
    }

    public void setId_Reparacion(int id_Reparacion) {
        this.id_Reparacion = id_Reparacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getNombre_Cliente() {
        return Nombre_Cliente;
    }

    public void setNombre_Cliente(String nombre_Cliente) {
        this.Nombre_Cliente = nombre_Cliente;
    }

    public String getFecha_Ingreso() {
        return Fecha_Ingreso;
    }

    public void setFecha_Ingreso(String fecha_Ingreso) {
        this.Fecha_Ingreso = fecha_Ingreso;
    }

    public String getDescripcion_Trabajo_Realizado() {
        return Descripcion_Trabajo_Realizado;
    }

    public void setDescripcion_Trabajo_Realizado(String descripcion_Trabajo_Realizado) {
        this.Descripcion_Trabajo_Realizado = descripcion_Trabajo_Realizado;
    }

    public String getMotivo_Ingreso() {
        return Motivo_Ingreso;
    }

    public void setMotivo_Ingreso(String motivo_Ingreso) {
        this.Motivo_Ingreso = motivo_Ingreso;
    }

    public double getCosto_Mano_De_Obra() {
        return Costo_Mano_De_Obra;
    }

    public void setCosto_Mano_De_Obra(double costo_Mano_De_Obra) {
        this.Costo_Mano_De_Obra = costo_Mano_De_Obra;
    }

    public String getFecha_Creacion() {
        return Fecha_Creacion;
    }

    public void setFecha_Creacion(String fecha_Creacion) {
        this.Fecha_Creacion = fecha_Creacion;
    }
}
