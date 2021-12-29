package com.example.voiceassistent

import org.jsoup.Jsoup
import java.io.IOException
import java.util.*

object ParsingHtmlService {
    private const val URL = "http://mirkosmosa.ru/holiday/2021"
    @JvmStatic
    @Throws(IOException::class)
    fun getHoliday(date: StringBuilder): String {
        var print = false
        val answer = StringBuilder("")
        val document = Jsoup.connect(URL).get()
        val body = document.body()
        //Elements day = document.select("div.next_phase");
        val months = document.select("div.holiday_month").not("h3.div_center")
        for (m in months) {
            val day = m.select("div.next_phase")
            for (e in day) if (e.select("div.month_cel_date > span").text().contentEquals(date)) {
                val holidayDate = ArrayList<String>()
                holidayDate.add(e.select("div.month_cel_date > span").text())
                val holidayDay = ArrayList<String>()
                holidayDay.add(e.select("div > span.day_week").text())
                val holidays = ArrayList<String>()
                val temp = e.select("li")
                for (t in temp) {
                    holidays.add(t.text())
                }
                if (print == false) {
                    return if (holidays.isEmpty() == true) {
                        answer.append("В этот день нет праздников")
                        println("В этот день нет праздников")
                        answer.toString()
                    } else {
                        for (i in holidayDate.indices) {
                            answer.append(holidayDate[i])
                            answer.append("\n")
                            answer.append(holidayDay[i])
                            answer.append("\n")
                        }
                        for (i in holidays.indices) {
                            answer.append(holidays[i])
                            answer.append("\n")
                        }
                        //System.out.println(holidayDate);
                        //System.out.println(holidayDay);
                        //System.out.println(holidays);
                        print = true
                        answer.toString()
                    }
                }
            }
        }
        return answer.toString()
    }
}