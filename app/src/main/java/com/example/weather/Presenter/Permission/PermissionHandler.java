package com.example.weather.Presenter.Permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.weather.Model.Constants;

public class PermissionHandler {
    private static PermissionHandler mPermissionHandler;
    private View mView;
    private PermissionHandler(View view) {
        this.mView = view;
    }
    public static PermissionHandler getInstance(View view)
    {
        if (mPermissionHandler == null) {
                mPermissionHandler = new PermissionHandler(view);

        }
        mPermissionHandler.mView = view;
        return mPermissionHandler;
    }
    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(mView.getViewActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(mView.getViewActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(mView.getViewActivity(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                            mView.getViewActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET},
                    Constants.REQUEST_LOCATION_ACCESS_CODE);
        }
        else {
            mView.permissionGranted();
        }
    }
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_LOCATION_ACCESS_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mView.permissionGranted();
                } else {
                    requestPermission();
                }
            }

        }
    }
    public interface View {
        Activity getViewActivity();
        void permissionGranted();
    }
 }
