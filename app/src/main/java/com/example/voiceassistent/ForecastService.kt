package com.example.voiceassistent

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ForecastService {
    @JvmName("getApi1")
    fun getApi(): ForecastApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.weatherstack.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build()
        return retrofit.create(ForecastApi::class.java) //Создание объекта, при помощи которого будут выполняться запросы

    }

    //Базовая часть адреса
    //Конвертер, необходимый для преобразования JSON'а в объекты
    //Создание объекта, при помощи которого будут выполняться запросы
    val api: ForecastApi
        get() {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://api.weatherstack.com") //Базовая часть адреса
                    .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                    .build()
            return retrofit.create(ForecastApi::class.java) //Создание объекта, при помощи которого будут выполняться запросы
        }
}