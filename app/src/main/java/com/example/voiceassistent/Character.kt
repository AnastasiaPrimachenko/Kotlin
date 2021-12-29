package com.example.voiceassistent

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Character {
    @Expose
    var name: String? = null
    var race: String? = null
    var profession: String? = null
    var level: String? = null
    var created: String? = null
    var deaths: String? = null
    var age: String? = null

    inner class Profile {
        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("race")
        @Expose
        var race: String? = null

        @SerializedName("profession")
        @Expose
        var profession: String? = null

        @SerializedName("level")
        @Expose
        var level: String? = null

        @SerializedName("created")
        @Expose
        var created: String? = null

        @SerializedName("age")
        @Expose
        var age: String? = null

        @SerializedName("deaths")
        @Expose
        var deaths: String? = null
    }
}