package com.mangu.personalcityhelper.data.model.lineschedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("ALL")
public class HorarioVueltum {

    @SerializedName("horas")
    @Expose
    private List<String> horas = null;
    @SerializedName("frecuencia")
    @Expose
    private String frecuencia;
    @SerializedName("observaciones")
    @Expose
    private String observaciones;
    @SerializedName("demandahoras")
    @Expose
    private String demandahoras;

    public List<String> getHoras() {
        return horas;
    }

    public void setHoras(List<String> horas) {
        this.horas = horas;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDemandahoras() {
        return demandahoras;
    }

    public void setDemandahoras(String demandahoras) {
        this.demandahoras = demandahoras;
    }

}
