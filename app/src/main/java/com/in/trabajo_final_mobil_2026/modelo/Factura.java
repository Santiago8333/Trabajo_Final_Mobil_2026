package com.in.trabajo_final_mobil_2026.modelo;

import java.io.Serializable;

public class Factura implements Serializable {
    private int id_Factura;
    private int idReparacion;
    private String Total_Factura;
    private boolean Estado;

    public int getId_Factura() {
        return id_Factura;
    }

    public void setId_Factura(int id_Factura) {
        this.id_Factura = id_Factura;
    }

    public int getIdReparacion() {
        return idReparacion;
    }

    public void setIdReparacion(int idReparacion) {
        this.idReparacion = idReparacion;
    }

    public String getTotal_Factura() {
        return Total_Factura;
    }

    public void setTotal_Factura(String total_Factura) {
        this.Total_Factura = total_Factura;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        this.Estado = estado;
    }
}
