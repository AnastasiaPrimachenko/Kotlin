package com.example.voiceassistent

import android.util.Log
import androidx.core.util.Consumer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
object CharacterToString {
    fun getInfo(name: String?, callback: Consumer<String?>) {
        val api = CharacterService.getApi()
        val call = name?.let { api.getInfo(it) }//"$number?json"
        call!!.enqueue(object : Callback<ArrayList<Character>> {
            override fun onResponse(call: Call<ArrayList<Character>>, response: Response<ArrayList<Character>>) {

                val result = response.body()
                if (result != null) {
                    var temp = ""
                    for (character in result) {
                        temp = temp + "Name: " + character.name + System.lineSeparator()
                        temp = temp + "Race: " + character.race + System.lineSeparator()
                        temp = temp + "Profession: " + character.profession + System.lineSeparator()
                        temp = temp + "Level: " + character.level + System.lineSeparator()
                        temp = temp + "Created: " + character.created!!.subSequence(0, 10) + System.lineSeparator()
                        temp = temp + "Hours played: " + "%.2f".format(character.age!!.toFloat() / 3600) + System.lineSeparator()
                        temp = temp + "Number of deaths: " + character.deaths + System.lineSeparator()
                        if(result.size > 1) temp = temp + "----------------------------" + System.lineSeparator()
                    }
                    callback.accept(temp)
                } else callback.accept("Нет персонажа с таким именем")
            }

            override fun onFailure(call: Call<ArrayList<Character>>, t: Throwable) {
                Log.w("WEATHER", t.message)
                callback.accept("Не могу найти инфу failure")
            }
        })
    }
}