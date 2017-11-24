package com.mangu.personalcityhelper.data.model.lineschedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Planificadore {

    @SerializedName("idPlani")
    @Expose
    private String idPlani;
    @SerializedName("fechaInicio")
    @Expose
    private String fechaInicio;
    @SerializedName("fechaFin")
    @Expose
    private String fechaFin;
    @SerializedName("muestraFechaFin")
    @Expose
    private String muestraFechaFin;
    @SerializedName("nucleosIda")
    @Expose
    private List<NucleosIda> nucleosIda = null;
    @SerializedName("nucleosVuelta")
    @Expose
    private List<NucleosVueltum> nucleosVuelta = null;
    @SerializedName("bloquesIda")
    @Expose
    private List<BloquesIda> bloquesIda = null;
    @SerializedName("horarioIda")
    @Expose
    private List<HorarioIda> horarioIda = null;
    @SerializedName("bloquesVuelta")
    @Expose
    private List<BloquesVueltum> bloquesVuelta = null;
    @SerializedName("especial")
    @Expose
    private String especial;
    @SerializedName("horarioVuelta")
    @Expose
    private List<HorarioVueltum> horarioVuelta = null;

    public String getIdPlani() {
        return idPlani;
    }

    public void setIdPlani(String idPlani) {
        this.idPlani = idPlani;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMuestraFechaFin() {
        return muestraFechaFin;
    }

    public void setMuestraFechaFin(String muestraFechaFin) {
        this.muestraFechaFin = muestraFechaFin;
    }

    public List<NucleosIda> getNucleosIda() {
        return nucleosIda;
    }

    public void setNucleosIda(List<NucleosIda> nucleosIda) {
        this.nucleosIda = nucleosIda;
    }

    public List<NucleosVueltum> getNucleosVuelta() {
        return nucleosVuelta;
    }

    public void setNucleosVuelta(List<NucleosVueltum> nucleosVuelta) {
        this.nucleosVuelta = nucleosVuelta;
    }

    public List<BloquesIda> getBloquesIda() {
        return bloquesIda;
    }

    public void setBloquesIda(List<BloquesIda> bloquesIda) {
        this.bloquesIda = bloquesIda;
    }

    public List<HorarioIda> getHorarioIda() {
        return horarioIda;
    }

    public void setHorarioIda(List<HorarioIda> horarioIda) {
        this.horarioIda = horarioIda;
    }

    public List<BloquesVueltum> getBloquesVuelta() {
        return bloquesVuelta;
    }

    public void setBloquesVuelta(List<BloquesVueltum> bloquesVuelta) {
        this.bloquesVuelta = bloquesVuelta;
    }

    public String getEspecial() {
        return especial;
    }

    public void setEspecial(String especial) {
        this.especial = especial;
    }

    public List<HorarioVueltum> getHorarioVuelta() {
        return horarioVuelta;
    }

    public void setHorarioVuelta(List<HorarioVueltum> horarioVuelta) {
        this.horarioVuelta = horarioVuelta;
    }

}
