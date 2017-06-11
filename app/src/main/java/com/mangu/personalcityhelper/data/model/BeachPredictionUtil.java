package com.mangu.personalcityhelper.data.model;

import android.util.Log;

import com.mangu.personalcityhelper.R;

import org.json.JSONException;
import org.json.JSONObject;

public class BeachPredictionUtil {
    public static BeachPrediction preparePrediction(JSONObject jsonObject,
                                                    BeachPrediction prediction)
            throws JSONException {
        String maxim = jsonObject.getJSONObject("t_maxima").toString().split("\\:")[1].
                replace("}", "");
        prediction.setmMaximumTemperature(Integer.parseInt(maxim));
        prediction.setmDate(jsonObject.get("fecha").toString());
        prediction.setmSky(jsonObject.getJSONObject("estado_cielo").get("descripcion1")
                .toString());
        prediction.setmWaterTemperature(Integer.parseInt(jsonObject.getJSONObject("t_agua").
                toString().split("\\:")[1].replace("}", "")));
        prediction.setmSurfMorning(jsonObject.getJSONObject("oleaje").get("descripcion1").
                toString());
        prediction.setmSurfAfternoon(jsonObject.getJSONObject("oleaje").get("descripcion2").
                toString());
        prediction.setmWindMorning(jsonObject.getJSONObject("viento").get("descripcion1").
                toString());
        prediction.setmWindAfternoon(jsonObject.getJSONObject("viento").get("descripcion2").
                toString());
        prediction.setmTermicSensation(jsonObject.getJSONObject("s_termica").
                get("descripcion1").toString());
        prediction.setmMaximumUv(Integer.parseInt(jsonObject.getJSONObject
                ("uv_max").toString().split("\\:")[1].replace("}", "")));
        return prediction;
    }

    public static String getIconPrediction(BeachPrediction prediction) {
        //TODO Implement other icons according to API
        String id;
        switch (prediction.getmSky()) {
            case "despejado":
                id = "http://www.aemet.es/imagenes/png/estado_cielo/11.png";
                break;
            default:
                id = Integer.toString(R.drawable.ic_cloudy);
                Log.i("BeachPredictionUtil", "Non sunny image needed, check API");
                break;
        }
        return id;
    }
}
