package com.example.voiceassistent

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Message {
    var text: String?
    var date: Date? = null
    var isSend: Boolean? = null

    constructor(text: String?, isSend: Boolean?) {
        this.text = text
        this.isSend = isSend
        date = Date()
    }

    constructor(text: String?, isSend: Boolean?, dateString: String) {
        this.text = text
        this.isSend = isSend
        val date = Date(dateString.toLong())
        this.date = date
    }

    constructor(entity: MessageEntity) {
        text = entity.text
        val formatter = SimpleDateFormat("dd.MMM HH:mm:ss", Locale.ENGLISH)
        try {
            date = formatter.parse(entity.date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (entity.isSend == 1) isSend = true else isSend = false
    }
}