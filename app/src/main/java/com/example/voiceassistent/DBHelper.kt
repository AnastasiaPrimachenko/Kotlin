package com.example.voiceassistentimport

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context?) : SQLiteOpenHelper(context, DBHelper.Companion.DATABASE_NAME, null, DBHelper.Companion.DATABASE_VERSION) {
    val DATABASE_NAME = "messageDb"
    val TABLE_MESSAGES = "messages"

    val FIELD_ID = "id"
    val FIELD_MESSAGE = "message"
    val FIELD_SEND = "send"
    val FIELD_DATE = "date"


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table " + DBHelper.TABLE_MESSAGES + "(" +
                DBHelper.FIELD_ID + " integer primary key," +
                DBHelper.FIELD_MESSAGE + " text," +
                DBHelper.FIELD_SEND + " integer," +
                DBHelper.FIELD_DATE + " text" + ")")
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("drop table if exists " + DBHelper.TABLE_MESSAGES)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "messageDb"
        const val TABLE_MESSAGES = "messages"
        const val FIELD_ID = "id"
        const val FIELD_MESSAGE = "message"
        const val FIELD_SEND = "send"
        const val FIELD_DATE = "date"
    }
}