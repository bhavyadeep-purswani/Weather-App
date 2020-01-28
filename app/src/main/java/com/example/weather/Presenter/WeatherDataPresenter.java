package com.example.weather.Presenter;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;
import com.example.weather.Model.ApiResponseFields.MainInfo;
import com.example.weather.Model.Constants;
import com.example.weather.Model.WeatherResponse;
import com.example.weather.Presenter.Location.LocationAPI;
import com.example.weather.Presenter.Retrofit.ApiClient;
import com.example.weather.Presenter.Retrofit.ApiInterface;
import com.example.weather.View.Controller.WeatherDataController;
import java.text.DecimalFormat;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDataPresenter {
    private LocationAPI mLocationManager;
    private IntentHandler mIntentHandler;
    private Location mLocation;
    private WeatherDataController mWeatherDataController;

    private WeatherDataPresenter(LocationAPI locationManager, IntentHandler intentHandler, WeatherDataController weatherDataController) {
        mLocationManager = locationManager;
        mIntentHandler = intentHandler;
        mWeatherDataController = weatherDataController;
    }

    public static WeatherDataPresenter getInstance(LocationAPI locationManager, IntentHandler intentHandler, WeatherDataController weatherDataController) {
        return new WeatherDataPresenter(locationManager, intentHandler, weatherDataController);
    }

    public void setLocation() {
        if (!mLocationManager.checkLocationEnabled()) {
            mIntentHandler.openLocationSettings();
            return;
        }
        getLocation();

    }
    private void getLocation() {
        SingleObserver<Location> singleObserver = new SingleObserver<Location>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Location location) {
                mLocation = location;
                if(mLocation!=null) {
                    getWeather();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        };
        Single<Location> single = new Single<Location>() {
            @Override
            protected void subscribeActual(SingleObserver<? super Location> observer) {
                mLocationManager.getLocation((SingleObserver<Location>) observer);
            }
        };
        single.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleObserver);
    }
    private void getWeather() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<WeatherResponse> call = apiService.getWeatherInfo(mLocation.getLatitude(), mLocation.getLongitude(), Constants.API_KEY, "metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                displayWeather(response);
            }
            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
            }
        });
    }
    private void displayWeather(Response<WeatherResponse> response) {
        MainInfo mainInfo = response.body().getmMainInfo();
        String city = response.body().getmCityName();

        mWeatherDataController.setWeatherCity(city);
        DecimalFormat df = new DecimalFormat("#.#");
        mWeatherDataController.setWeatherTemperature(df.format(mainInfo.getmTemperature()));
        mWeatherDataController.setWeatherTempMax(df.format(mainInfo.getmMaxTemperature()));
        mWeatherDataController.setWeatherTempMin(df.format(mainInfo.getmMinTemperature()));
        mWeatherDataController.hideProgressBar();
    }
    public void onActivityResult() {
        if (!mLocationManager.checkLocationEnabled()) {
            Toast.makeText(mIntentHandler.getContext(),"Please enable Location!", Toast.LENGTH_LONG).show();
        }
        else {
            getLocation();
        }
    }
    public interface IntentHandler {
        void openLocationSettings();
        Context getContext();
    }
}
