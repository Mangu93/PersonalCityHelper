package com.mangu.personalcityhelper.data.model.bus;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LineList {
    @SerializedName("lineas")
    public List<Line> lineas;
}
