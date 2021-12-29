package com.example.voiceassistent

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voiceassistentimport.DBHelper
import java.util.*

open class MainActivity : AppCompatActivity() {
    private var isLight = true
    private val THEME = "THEME"
    protected lateinit var sendButton: Button
    protected var questionText: EditText? = null
    protected var chatWindow: TextView? = null
    var sPref: SharedPreferences? = null
    var textToSpeech: TextToSpeech? = null
    protected lateinit var chatMessageList: RecyclerView
    protected lateinit var messageListAdapter: MessageListAdapter
    lateinit var dBHelper: DBHelper
    lateinit var database: SQLiteDatabase
    lateinit var cursor: Cursor
    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        messageListAdapter = MessageListAdapter()
        chatMessageList = findViewById(R.id.chatMessageList)
        chatMessageList.setLayoutManager(LinearLayoutManager(this))
        chatMessageList.setAdapter(messageListAdapter)
        super.onCreate(savedInstanceState)
        sendButton = findViewById(R.id.sendButton)
        questionText = findViewById(R.id.questionField)
        isLight = sPref!!.getBoolean(THEME, true)
        if (isLight) delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO else delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech!!.language = Locale("ru")
            } else chatWindow!!.append("Can't text to speech")
        }
        sendButton.setOnClickListener({ onSend() })
        dBHelper = DBHelper(this)
        database = dBHelper.getWritableDatabase()
        if (savedInstanceState == null) {
            cursor = database.query(dBHelper.TABLE_MESSAGES, null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                val messageIndex = cursor.getColumnIndex(dBHelper.FIELD_MESSAGE)
                val dateIndex = cursor.getColumnIndex(dBHelper.FIELD_DATE)
                val sendIndex = cursor.getColumnIndex(dBHelper.FIELD_SEND)
                do {
                    val entity = MessageEntity(cursor.getString(messageIndex),
                            cursor.getString(dateIndex), cursor.getInt(sendIndex))
                    val message = Message(entity)
                    messageListAdapter.messageList.add(message)
                } while (cursor.moveToNext())
                cursor.close()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val messageList = arrayOfNulls<String>(messageListAdapter.messageList.size)
        val dateList = arrayOfNulls<String>(messageListAdapter.messageList.size)
        val sendList = BooleanArray(messageListAdapter.messageList.size)
        for (i in messageList.indices) {
            messageList[i] = messageListAdapter.messageList[i]!!.text
            sendList[i] = messageListAdapter.messageList[i]!!.isSend!!
            dateList[i] = messageListAdapter.messageList[i]!!.date!!.time.toString()
        }
        outState.putStringArray("messageList", messageList)
        outState.putStringArray("dateList", dateList)
        outState.putBooleanArray("sendList", sendList)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val tempMessage = savedInstanceState.getStringArray("messageList")
        val tempDate = savedInstanceState.getStringArray("dateList")
        val tempSend = savedInstanceState.getBooleanArray("sendList")
        if (tempMessage != null) {
            for (i in tempMessage.indices) {
                messageListAdapter.messageList.add(tempDate?.get(i)?.let { Message(tempMessage.get(i), tempSend?.get(i), it) })
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    protected fun onSend() {
        val text = questionText!!.text.toString()
        val temp = AI_MAP()
        temp[text, { s: String? ->
            messageListAdapter.messageList.add(Message(text, true))
            messageListAdapter.messageList.add(Message(s, false))
            messageListAdapter.notifyDataSetChanged()
            chatMessageList.scrollToPosition(messageListAdapter.messageList.size - 1)
            textToSpeech!!.speak(s, TextToSpeech.QUEUE_FLUSH, null, null)
            questionText!!.setText("")
        }]
    }

    override fun onStop() {
        database.delete(dBHelper.TABLE_MESSAGES, null, null)
        for (i in messageListAdapter.messageList.indices) {
            val entity = MessageEntity(messageListAdapter.messageList[i])
            val contentValues = ContentValues()
            contentValues.put(DBHelper.FIELD_MESSAGE, entity.text)
            contentValues.put(DBHelper.FIELD_SEND, entity.isSend)
            contentValues.put(DBHelper.FIELD_DATE, entity.date)
            database.insert(dBHelper.TABLE_MESSAGES, null, contentValues)
        }
        val editor = sPref!!.edit()
        editor.putBoolean(THEME, isLight)
        editor.apply()
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.day_settings -> {
                isLight = true
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
            }
            R.id.night_settings -> {
                isLight = false
                delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getSharedPreferences(name: String, mode: Int): SharedPreferences {
        return super.getSharedPreferences(name, mode)
    }

    companion object {
        private const val URL = "http://mirkosmosa.ru/holiday/2021"
        private const val APP_PREFERENCES = "mysettings"
    }
}