package com.example.weather.View.Controller;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bluelinelabs.conductor.Controller;
import com.example.weather.R;


public class WeatherDataController extends Controller {
    private ImageView mWeatherImage;
    private TextView mWeatherTemperature;
    private TextView mWeatherTempMin;
    private TextView mWeatherTempMax;
    private TextView mWeatherCity;
    private ProgressBar mProgressBar;
    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.display_weather,container,false);
        mWeatherImage = view.findViewById(R.id.weatherImage);
        mWeatherTemperature = view.findViewById(R.id.weatherTemperature);
        mWeatherCity = view.findViewById(R.id.weatherCity);
        mWeatherTempMin = view.findViewById(R.id.weatherTempMin);
        mWeatherTempMax = view.findViewById(R.id.weatherTempMax);
        mProgressBar = view.findViewById(R.id.progressBar);
        return view;
    }
    public void setWeatherImage(Bitmap bitmap) {
        mWeatherImage.setImageBitmap(bitmap);
    }
    public void setWeatherTemperature(String text) {
        mWeatherTemperature.setText(text);
    }
    public void setWeatherTempMax(String text) {
        mWeatherTempMax.setText(text);
    }
    public void setWeatherTempMin(String text) {
        mWeatherTempMin.setText(text);
    }
    public void setWeatherCity(String text) {
        mWeatherCity.setText(text);
    }
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
