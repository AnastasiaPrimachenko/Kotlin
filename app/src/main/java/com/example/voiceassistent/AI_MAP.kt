package com.example.voiceassistent

import android.annotation.SuppressLint
import androidx.core.util.Consumer
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class AI_MAP {
    var answer: MutableMap<String?, String> = LinkedHashMap()
    var time = "!"
    var weather = "!"
    fun set() {
        answer["Привет"] = "Привет" //0
        answer["Как дела"] = "Не плохо" //1
        answer["Чем занимаешься"] = "Отвечаю на вопросы" //2
        answer["Hi"] = "Hi" //3
        answer["How are you"] = "Not bad" //4
        answer["What are you doing"] = "Answering questions" //5
    }

    @SuppressLint("StaticFieldLeak")
    operator fun get(question: String, callback: Consumer<String?>) {
        set()
        val keys: List<*> = ArrayList<Any?>(answer.keys)
        for (i in 0 until answer.size) {
            if (question.lowercase().contains("какой сегодня день") || question.lowercase().contains("дата")) {
                date()
                callback.accept("Сегодня $time")
                break
            } else if (question.lowercase().contains("день недели")) {
                dayOfWeek()
                callback.accept("Сегодня $time")
                break
            } else if (question.lowercase().contains("час")) {
                hour()
                if (time == "1" || time == "13") {
                    callback.accept("Сейчас " + time + "час")
                    break
                } else if (time == "2" || time == "3" || time == "4" || time == "14" || time == "15" || time == "16") {
                    callback.accept("Сейчас $time часа")
                    break
                } else {
                    callback.accept("Сейчас $time часов")
                    break
                }
            } else if (question.lowercase().contains("врем")) {
                timeNow()
                callback.accept("Сейчас $time")
                break
            } else if (question.lowercase().contains("дней до")) {
                timeToDate(question)
                if (time == "0") {
                    callback.accept("Этот день уже наступил")
                    break
                } else {
                    callback.accept("До этой даты $time дня/дней")
                    break
                }
            } else if (question.matches(Regex("(?i).*" + keys[i] + ".*"))) {
                callback.accept(answer[keys[i]])
                break
            } else {
                try {
                    val cityPattern = Pattern.compile("погода в городе (\\p{L}+)", Pattern.CASE_INSENSITIVE)
                    val numberPattern = Pattern.compile("(\\p{Digit}+) в строку", Pattern.CASE_INSENSITIVE)
                    val holidayPattern = Pattern.compile("праздники ([\\s\\S]+)", Pattern.CASE_INSENSITIVE)
                    val characterPattern = Pattern.compile("info ([\\s\\S]+)", Pattern.CASE_INSENSITIVE)
                    var matcher = cityPattern.matcher(question)
                    if (matcher.find()) {
                        val cityName = matcher.group(1)
                        ForecastToString.getForecast(cityName) { s: String? -> if (s != null) callback.accept(s) else callback.accept("Нет такого города") }
                        break
                    }
                    matcher = numberPattern.matcher(question)
                    if (matcher.find()) {
                        val number = matcher.group(1)
                        NumberToString.getNumber(number) { s: String? -> if (s != null) callback.accept(s) else callback.accept("Нельзя") }
                        break
                    }
                    matcher = holidayPattern.matcher(question)
                    if (matcher.find()) {
                        val temp = StringBuilder()
                        val data = matcher.group(1)
                        val checkdate1 = Pattern.compile("(\\d{1,2}(\\.|-)\\d{1,2})")
                        matcher = checkdate1.matcher(data)
                        if (matcher.find()) {
                            val ar = data.split("\\.|-").toTypedArray()
                            if (ar[0].matches("01".toRegex())) temp.append("1") else if (ar[0].matches("02".toRegex())) temp.append("2") else if (ar[0].matches("03".toRegex())) temp.append("3") else if (ar[0].matches("04".toRegex())) temp.append("4") else if (ar[0].matches("05".toRegex())) temp.append("5") else if (ar[0].matches("06".toRegex())) temp.append("6") else if (ar[0].matches("07".toRegex())) temp.append("7") else if (ar[0].matches("08".toRegex())) temp.append("8") else if (ar[0].matches("09".toRegex())) temp.append("9") else temp.append(ar[0])
                            temp.append(" ")
                            if (ar[1].matches("1".toRegex())) temp.append("января") else if (ar[1].matches("2".toRegex())) temp.append("февраля") else if (ar[1].matches("3".toRegex())) temp.append("марта") else if (ar[1].matches("4".toRegex())) temp.append("апреля") else if (ar[1].matches("5".toRegex())) temp.append("мая") else if (ar[1].matches("6".toRegex())) temp.append("июня") else if (ar[1].matches("7".toRegex())) temp.append("июля") else if (ar[1].matches("8".toRegex())) temp.append("августа") else if (ar[1].matches("9".toRegex())) temp.append("сентября") else if (ar[1].matches("02".toRegex())) temp.append("февраля") else if (ar[1].matches("03".toRegex())) temp.append("марта") else if (ar[1].matches("04".toRegex())) temp.append("апреля") else if (ar[1].matches("05".toRegex())) temp.append("мая") else if (ar[1].matches("06".toRegex())) temp.append("июня") else if (ar[1].matches("07".toRegex())) temp.append("июля") else if (ar[1].matches("08".toRegex())) temp.append("августа") else if (ar[1].matches("09".toRegex())) temp.append("сентября") else if (ar[1].matches("10".toRegex())) temp.append("октября") else if (ar[1].matches("11".toRegex())) temp.append("ноября") else if (ar[1].matches("12".toRegex())) temp.append("декабря")
                            temp.append(" 2021")
                        }
                        val checkdate2 = Pattern.compile("(\\d{1,2} ((янв)|(фев)|(мар)|(апр)|(мая)|(июн)|(июл)|(авг)|(сен)|(окт)|(ноя)|(дек)))")
                        matcher = checkdate2.matcher(data)
                        if (matcher.find()) {
                            val ar = data.split(" ").toTypedArray()
                            if (ar[0].matches("01".toRegex())) temp.append("1") else if (ar[0].matches("02".toRegex())) temp.append("2") else if (ar[0].matches("03".toRegex())) temp.append("3") else if (ar[0].matches("04".toRegex())) temp.append("4") else if (ar[0].matches("05".toRegex())) temp.append("5") else if (ar[0].matches("06".toRegex())) temp.append("6") else if (ar[0].matches("07".toRegex())) temp.append("7") else if (ar[0].matches("08".toRegex())) temp.append("8") else if (ar[0].matches("09".toRegex())) temp.append("9") else temp.append(ar[0])
                            temp.append(" ")
                            if (ar[1].matches("янв".toRegex())) temp.append("января") else if (ar[1].matches("фев".toRegex())) temp.append("февраля") else if (ar[1].matches("мар".toRegex())) temp.append("марта") else if (ar[1].matches("апр".toRegex())) temp.append("апреля") else if (ar[1].matches("мая".toRegex())) temp.append("мая") else if (ar[1].matches("июн".toRegex())) temp.append("июня") else if (ar[1].matches("июл".toRegex())) temp.append("июля") else if (ar[1].matches("авг".toRegex())) temp.append("августа") else if (ar[1].matches("сен".toRegex())) temp.append("сентября") else if (ar[1].matches("окт".toRegex())) temp.append("октября") else if (ar[1].matches("ноя".toRegex())) temp.append("ноября") else if (ar[1].matches("дек".toRegex())) temp.append("декабря") else temp.append(ar[1])
                            temp.append(" 2021")
                        }
                        val checkdate3 = Pattern.compile("сегодня")
                        matcher = checkdate3.matcher(data)
                        if (matcher.find()) {
                            date()
                            val ar = time.split("\\.").toTypedArray()
                            if (ar[0].matches("01".toRegex())) temp.append("1") else if (ar[0].matches("02".toRegex())) temp.append("2") else if (ar[0].matches("03".toRegex())) temp.append("3") else if (ar[0].matches("04".toRegex())) temp.append("4") else if (ar[0].matches("05".toRegex())) temp.append("5") else if (ar[0].matches("06".toRegex())) temp.append("6") else if (ar[0].matches("07".toRegex())) temp.append("7") else if (ar[0].matches("08".toRegex())) temp.append("8") else if (ar[0].matches("09".toRegex())) temp.append("9") else temp.append(ar[0])
                            temp.append(" ")
                            if (ar[1].matches("1".toRegex())) temp.append("января") else if (ar[1].matches("2".toRegex())) temp.append("февраля") else if (ar[1].matches("3".toRegex())) temp.append("марта") else if (ar[1].matches("4".toRegex())) temp.append("апреля") else if (ar[1].matches("5".toRegex())) temp.append("мая") else if (ar[1].matches("6".toRegex())) temp.append("июня") else if (ar[1].matches("7".toRegex())) temp.append("июля") else if (ar[1].matches("8".toRegex())) temp.append("августа") else if (ar[1].matches("9".toRegex())) temp.append("сентября") else if (ar[1].matches("02".toRegex())) temp.append("февраля") else if (ar[1].matches("03".toRegex())) temp.append("марта") else if (ar[1].matches("04".toRegex())) temp.append("апреля") else if (ar[1].matches("05".toRegex())) temp.append("мая") else if (ar[1].matches("06".toRegex())) temp.append("июня") else if (ar[1].matches("07".toRegex())) temp.append("июля") else if (ar[1].matches("08".toRegex())) temp.append("августа") else if (ar[1].matches("09".toRegex())) temp.append("сентября") else if (ar[1].matches("10".toRegex())) temp.append("октября") else if (ar[1].matches("11".toRegex())) temp.append("ноября") else if (ar[1].matches("12".toRegex())) temp.append("декабря")
                            temp.append(" " + ar[2])
                        }
                        val checkdate4 = Pattern.compile("завтра")
                        matcher = checkdate4.matcher(data)
                        if (matcher.find()) {
                            val calendar: Calendar = GregorianCalendar()
                            time = calendar[Calendar.DAY_OF_MONTH].toString() + "." + (calendar[Calendar.MONTH] + 1).toString() + "." + calendar[Calendar.YEAR].toString()
                            calendar.add(Calendar.DAY_OF_MONTH, 1)
                            time = calendar[Calendar.DAY_OF_MONTH].toString() + "." + (calendar[Calendar.MONTH] + 1).toString() + "." + calendar[Calendar.YEAR].toString()
                            val ar = time.split("\\.").toTypedArray()
                            if (ar[0].matches("01".toRegex())) temp.append("1") else if (ar[0].matches("02".toRegex())) temp.append("2") else if (ar[0].matches("03".toRegex())) temp.append("3") else if (ar[0].matches("04".toRegex())) temp.append("4") else if (ar[0].matches("05".toRegex())) temp.append("5") else if (ar[0].matches("06".toRegex())) temp.append("6") else if (ar[0].matches("07".toRegex())) temp.append("7") else if (ar[0].matches("08".toRegex())) temp.append("8") else if (ar[0].matches("09".toRegex())) temp.append("9") else temp.append(ar[0])
                            temp.append(" ")
                            if (ar[1].matches("1".toRegex())) temp.append("января") else if (ar[1].matches("2".toRegex())) temp.append("февраля") else if (ar[1].matches("3".toRegex())) temp.append("марта") else if (ar[1].matches("4".toRegex())) temp.append("апреля") else if (ar[1].matches("5".toRegex())) temp.append("мая") else if (ar[1].matches("6".toRegex())) temp.append("июня") else if (ar[1].matches("7".toRegex())) temp.append("июля") else if (ar[1].matches("8".toRegex())) temp.append("августа") else if (ar[1].matches("9".toRegex())) temp.append("сентября") else if (ar[1].matches("02".toRegex())) temp.append("февраля") else if (ar[1].matches("03".toRegex())) temp.append("марта") else if (ar[1].matches("04".toRegex())) temp.append("апреля") else if (ar[1].matches("05".toRegex())) temp.append("мая") else if (ar[1].matches("06".toRegex())) temp.append("июня") else if (ar[1].matches("07".toRegex())) temp.append("июля") else if (ar[1].matches("08".toRegex())) temp.append("августа") else if (ar[1].matches("09".toRegex())) temp.append("сентября") else if (ar[1].matches("10".toRegex())) temp.append("октября") else if (ar[1].matches("11".toRegex())) temp.append("ноября") else if (ar[1].matches("12".toRegex())) temp.append("декабря")
                            temp.append(" " + ar[2])
                        }
                        var ans = arrayOf("")
                        var date = temp
                        /*new AsyncTask<String,Integer,Void>(){

                          @Override
                          protected Void doInBackground(String... strings) {
                              try {
                                  ans[0] = ParsingHtmlService.getHoliday(date);
                              } catch (IOException e) {
                                  e.printStackTrace();
                              }
                              return null;
                          }

                          @Override
                          protected void onPostExecute(Void aVoid) {
                              callback.accept(ans[0]);

                          }
                      }.execute(date.toString());*/Observable.fromCallable {
                            try {
                                ans[0] = ParsingHtmlService.getHoliday(date)
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            ans
                        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { result: Array<String>? -> callback.accept(ans[0]) }
                        break
                    }
                    matcher = characterPattern.matcher(question)
                    if (matcher.find()) {
                        val number = matcher.group(1)
                        CharacterToString.getInfo(number) { s: String? -> if (s != null) callback.accept(s) else callback.accept("Что-то пошло не так") }
                        break
                    }
                } catch (e: Exception) {
                    callback.accept("Не получается узнать :(")
                    break
                }
            }
        }
        //callback.accept("Вопрос понял. Думаю...");
    }

    private fun date() {
        val calendar: Calendar = GregorianCalendar()
        time = calendar[Calendar.DAY_OF_MONTH].toString() + "." + (calendar[Calendar.MONTH] + 1).toString() + "." + calendar[Calendar.YEAR].toString()
    }

    private fun timeNow() {
        val calendar: Calendar = GregorianCalendar()
        time = calendar[Calendar.HOUR_OF_DAY].toString() + ":" + calendar[Calendar.MINUTE].toString()
    }

    private fun hour() {
        val calendar: Calendar = GregorianCalendar()
        val temp = calendar[Calendar.HOUR_OF_DAY]
        time = temp.toString()
    }

    private fun dayOfWeek() {
        val calendar: Calendar = GregorianCalendar()
        val temp = calendar[Calendar.DAY_OF_WEEK]
        if (temp == 1) time = "воскресенье" else if (temp == 2) time = "понедельник" else if (temp == 3) time = "вторник" else if (temp == 4) time = "среда" else if (temp == 5) time = "четверг" else if (temp == 6) time = "пятница" else if (temp == 7) time = "суббота"
    }

    private fun timeToDate(date: String) {
        val newDate: Array<String>
        var temp = date.replace("дней до ", "")
        temp = temp.replace(" ", "")
        temp = temp.replace("?", "")
        newDate = temp.split("\\.").toTypedArray()
        val calendar1: Calendar = GregorianCalendar()
        val calendar2: Calendar = GregorianCalendar(newDate[2].toInt(), newDate[1].toInt() - 1, newDate[0].toInt())
        val temp1 = calendar1.timeInMillis
        val temp2 = calendar2.timeInMillis
        val timeLeft: Long
        if (calendar1.before(calendar2)) {
            timeLeft = Math.abs(temp2 - temp1)
            TimeUnit.MILLISECONDS.toDays(timeLeft)
            time = (TimeUnit.MILLISECONDS.toDays(timeLeft) + 1).toString()
        } else time = "0"
    }
}

