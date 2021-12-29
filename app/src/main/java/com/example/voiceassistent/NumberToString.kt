package com.example.voiceassistent

import android.util.Log
import androidx.core.util.Consumer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NumberToString {
    fun getNumber(number: String?, callback: Consumer<String?>) {
        val api = NumberService.getApi()
        val call = api!!.getNumber(number)
        call!!.enqueue(object : Callback<Number?> {
            override fun onResponse(call: Call<Number?>, response: Response<Number?>) {
                val result = response.body()
                if (result != null) {
                    if (result.number != null) callback.accept(result.number) else callback.accept("Не могу перевести result.str")
                } else callback.accept("Не могу перевести result")
            }

            override fun onFailure(call: Call<Number?>, t: Throwable) {
                Log.w("NUMBER", t.message)
                callback.accept("Не могу перевести failure")
            }
        })
    }
}