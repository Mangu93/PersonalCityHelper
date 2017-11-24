package com.mangu.personalcityhelper.data.model.lineschedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LineSchedule {

    @SerializedName("planificadores")
    @Expose
    private List<Planificadore> planificadores = null;
    @SerializedName("frecuencias")
    @Expose
    private List<Frecuencia> frecuencias = null;
    @SerializedName("horaCorte")
    @Expose
    private String horaCorte;

    public List<Planificadore> getPlanificadores() {
        return planificadores;
    }

    public void setPlanificadores(List<Planificadore> planificadores) {
        this.planificadores = planificadores;
    }

    public List<Frecuencia> getFrecuencias() {
        return frecuencias;
    }

    public void setFrecuencias(List<Frecuencia> frecuencias) {
        this.frecuencias = frecuencias;
    }

    public String getHoraCorte() {
        return horaCorte;
    }

    public void setHoraCorte(String horaCorte) {
        this.horaCorte = horaCorte;
    }

}
