package com.mangu.personalcityhelper.data.model.bus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Line {
    @SerializedName("idLinea")
    @Expose
    private String idLinea;
    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("hayNoticias")
    @Expose
    private String hayNoticias;
    @SerializedName("modo")
    @Expose
    private String modo;
    @SerializedName("idModo")
    @Expose
    private String idModo;
    @SerializedName("operadores")
    @Expose
    private String operadores;

    public String getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(String idLinea) {
        this.idLinea = idLinea;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHayNoticias() {
        return hayNoticias;
    }

    public void setHayNoticias(String hayNoticias) {
        this.hayNoticias = hayNoticias;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getIdModo() {
        return idModo;
    }

    public void setIdModo(String idModo) {
        this.idModo = idModo;
    }

    public String getOperadores() {
        return operadores;
    }

    public void setOperadores(String operadores) {
        this.operadores = operadores;
    }
}
