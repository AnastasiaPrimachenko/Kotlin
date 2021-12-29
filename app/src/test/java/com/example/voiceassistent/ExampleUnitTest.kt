package com.example.voiceassistent

import android.os.AsyncTask
import com.example.voiceassistent.ParsingHtmlService.getHoliday
import org.junit.Test
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    @Throws(IOException::class)
    fun htmlparsing() {
        val parse = ParsingHtmlService
        /*final String[] ans = {""};
        ans[0]="23 июня 2021";
        System.out.println(ParsingHtmlService.getHoliday(ans));*/
        var ans = ""
        var date = StringBuilder("3 января 2021")
        object : AsyncTask<String?, Int?, Void?>() {
            protected override fun doInBackground(vararg p0: String?): Void? {
                try {
                    ans = getHoliday(date)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                try {
                    println(ans)
                } finally {
                }
            }
        }
    }
}