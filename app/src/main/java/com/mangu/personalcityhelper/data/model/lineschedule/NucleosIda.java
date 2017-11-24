package com.mangu.personalcityhelper.data.model.lineschedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class NucleosIda {

    @SerializedName("colspan")
    @Expose
    private Integer colspan;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("color")
    @Expose
    private String color;

    public Integer getColspan() {
        return colspan;
    }

    public void setColspan(Integer colspan) {
        this.colspan = colspan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
