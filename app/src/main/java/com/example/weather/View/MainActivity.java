package com.example.weather.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.weather.Model.Constants;
import com.example.weather.Presenter.Location.LocationAPI;
import com.example.weather.Presenter.Permission.PermissionHandler;
import com.example.weather.Presenter.WeatherDataPresenter;
import com.example.weather.R;
import com.example.weather.View.Controller.WeatherDataController;

public class MainActivity extends AppCompatActivity implements PermissionHandler.View, WeatherDataPresenter.IntentHandler {

    private Router mRouter;
    private PermissionHandler mPermissionHandler;
    private WeatherDataPresenter mWeatherDataPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        mRouter = Conductor.attachRouter(this, findViewById(R.id.controller_container),savedInstanceState);
        WeatherDataController mWeatherDataController = new WeatherDataController();
        if(!mRouter.hasRootController()) {
            mRouter.setRoot(RouterTransaction.with(mWeatherDataController));
        }
        mWeatherDataPresenter = WeatherDataPresenter.getInstance(LocationAPI.getInstance(this),this, mWeatherDataController);
        mPermissionHandler = PermissionHandler.getInstance(this);
        mPermissionHandler.requestPermission();

    }
    @Override
    public void onBackPressed() {
        if(!mRouter.handleBack()) {
            super.onBackPressed();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWeatherDataPresenter=null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_LOCATION_ON_CODE) {
            mWeatherDataPresenter.onActivityResult();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionHandler.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public Activity getViewActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void permissionGranted() {

        mWeatherDataPresenter.setLocation();
    }
    @Override
    public void openLocationSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, Constants.REQUEST_LOCATION_ON_CODE);
    }

}
