package com.example.voiceassistent

import java.text.SimpleDateFormat
import java.util.*

class MessageEntity {
    var text: String?
    var date: String
    var isSend = 0

    constructor(text1: String?, date1: String, isSend1: Int) {
        isSend = isSend1
        date = date1
        text = text1
    }

    constructor(message: Message?) {
        text = message!!.text
        if (message.isSend == true) isSend = 1 else isSend = 0
        val formatter = SimpleDateFormat("dd.MMM HH:mm:ss", Locale.ENGLISH)
        date = formatter.format(message.date)
    }
}