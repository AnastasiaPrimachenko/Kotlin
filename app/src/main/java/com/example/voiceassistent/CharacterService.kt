package com.example.voiceassistent

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CharacterService {
    @JvmName("getApi1")
    fun getApi(): CharacterApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.guildwars2.com/v2/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build()
        return retrofit.create(CharacterApi::class.java) //Создание объекта, при помощи которого будут выполняться запросы

    }
}