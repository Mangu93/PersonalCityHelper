package com.mangu.personalcityhelper.data.model.lineschedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class Frecuencia {

    @SerializedName("idfrecuencia")
    @Expose
    private String idfrecuencia;
    @SerializedName("acronimo")
    @Expose
    private String acronimo;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    public String getIdfrecuencia() {
        return idfrecuencia;
    }

    public void setIdfrecuencia(String idfrecuencia) {
        this.idfrecuencia = idfrecuencia;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
