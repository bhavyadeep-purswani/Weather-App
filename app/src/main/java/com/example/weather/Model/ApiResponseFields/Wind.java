package com.example.weather.Model.ApiResponseFields;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    private float mSpeed;

    @SerializedName("deg")
    private float degrees;

}
