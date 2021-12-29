package com.example.voiceassistent

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NumberService {
    @JvmName("getApi1")
    fun getApi(): NumberApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://htmlweb.ru") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build()
        return retrofit.create(NumberApi::class.java) //Создание объекта, при помощи которого будут выполняться запросы
    }

    //Базовая часть адреса
    //Конвертер, необходимый для преобразования JSON'а в объекты
    //Создание объекта, при помощи которого будут выполняться запросы
    val api: NumberApi
        get() {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://htmlweb.ru") //Базовая часть адреса
                    .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                    .build()
            return retrofit.create(NumberApi::class.java) //Создание объекта, при помощи которого будут выполняться запросы
        }
}