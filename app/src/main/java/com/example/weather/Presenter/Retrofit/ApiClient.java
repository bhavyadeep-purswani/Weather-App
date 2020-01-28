package com.example.weather.Presenter.Retrofit;

import com.example.weather.Model.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit mRetrofit;

    public static Retrofit getClient() {
        if(mRetrofit==null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return mRetrofit;
    }
}
