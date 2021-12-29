package com.example.voiceassistent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MessageListAdapter : RecyclerView.Adapter<MessageViewHolder>() {
    var messageList: MutableList<Message?> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view: View
        view = if (viewType == USER_TYPE) {
            LayoutInflater.from(parent.context).inflate(R.layout.user_message, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.assistant_messgae, parent, false)
        }
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messageList[position])
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(index: Int): Int {
        val message = messageList[index]
        return if (message!!.isSend!!) {
            USER_TYPE
        } else ASSISTANT_TYPE
    }

    companion object {
        private const val ASSISTANT_TYPE = 0
        private const val USER_TYPE = 1
    }
}