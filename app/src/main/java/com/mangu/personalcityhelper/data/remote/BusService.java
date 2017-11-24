package com.mangu.personalcityhelper.data.remote;

import com.mangu.personalcityhelper.data.model.bus.LineList;
import com.mangu.personalcityhelper.data.model.lineschedule.LineSchedule;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("SameParameterValue")
public interface BusService {


    @GET("Consorcios/4/nucleos/{id}/lineas")
    Observable<LineList> getBusLines(@Path("id") String id);

    @GET("Consorcios/4/horarios_lineas")
    Observable<LineSchedule> getLineSchedule(@Query("dia") String day, @Query("frecuencia")
            String frequency, @Query("lang") String lang, @Query("linea") String line,
                                             @Query("mes") String month);
}
