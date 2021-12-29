package com.example.voiceassistent

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {
    @GET("characters?access_token=3DC5EF38-B102-CB4E-92AD-172D1947FA6CA2F70585-57FF-4F95-8018-834C2D90C8EA")
    fun getInfo(@Query("ids") name: String?): Call<ArrayList<Character>>
}