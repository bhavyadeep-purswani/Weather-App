package com.example.weather.Presenter.Retrofit;

import com.example.weather.Model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("weather")
    Call<WeatherResponse> getWeatherInfo(@Query("lat") double latitude, @Query("lon") double longitude, @Query("APPID") String apiKey, @Query("units") String unit);
}
