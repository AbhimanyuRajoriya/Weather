package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetApi{
    @GET("data/2.5/weather")
    Call<Model> getWeather(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}