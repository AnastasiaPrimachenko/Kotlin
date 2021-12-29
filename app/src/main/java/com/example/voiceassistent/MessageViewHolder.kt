package com.example.voiceassistent

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.text.SimpleDateFormat

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    protected var messageText: TextView
    protected var messageDate: TextView
    @SuppressLint("SimpleDateFormat")
    fun bind(message: Message?) {
        messageText.text = message!!.text
        val fmt: DateFormat = SimpleDateFormat("k':'m")
        messageDate.text = fmt.format(message.date)
    }

    init {
        messageText = itemView.findViewById(R.id.messageTextView)
        messageDate = itemView.findViewById(R.id.messageDateView)
    }
}