package com.example.voiceassistent

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET("/current?access_key=dca34aad5d153452c096a2380ad963e1")
    fun getCurrentWeather(@Query("query") city: String?): Call<Forecast?>?
}