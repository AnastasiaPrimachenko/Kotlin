package com.example.voiceassistent

import android.util.Log
import androidx.core.util.Consumer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ForecastToString {
    fun getForecast(city: String?, callback: Consumer<String?>) {
        val api = ForecastService.getApi()
        val call = api.getCurrentWeather(city)
        call!!.enqueue(object : Callback<Forecast?> {
            override fun onResponse(call: Call<Forecast?>, response: Response<Forecast?>) {
                val result = response.body()
                if (result != null) {
                    val temp = result.current!!.temperature!!
                    var end = "kjdns"
                    if (Math.abs(temp) >= 11 && Math.abs(temp) <= 19) end = "градусов" else if (Math.abs(temp) % 10 == 0) end = "градус" else if (Math.abs(temp) % 10 >= 2 && Math.abs(temp) % 10 <= 4) end = "градуса" else if (Math.abs(temp) % 10 == 0 || Math.abs(temp) % 10 >= 5 && Math.abs(temp) % 10 <= 9) end = "градусов"
                    val answer = "сейчас где-то " + result.current!!.temperature + " " + end + " и " + result.current!!.weather_descriptions!![0]
                    callback.accept(answer)
                } else callback.accept("Не могу узнать погоду")
            }

            override fun onFailure(call: Call<Forecast?>, t: Throwable) {
                Log.w("WEATHER", t.message)
                callback.accept("Не могу узнать погоду")
            }
        })
    }
}