package com.example.voiceassistentimport

object AI {
    fun getAnswer(question: String): String {
        var answer = "Вопрос понял. Думаю..."
        if (question.matches(Regex("(?i).*" + "Привет" + ".*"))) answer = "Привет"
        if (question.matches(Regex("(?i).*" + "Как дела" + ".*"))) answer = "Не плохо"
        if (question.matches(Regex("(?i).*" + "Чем занимаешься" + ".*"))) answer = "Отвечаю на вопросы"
        return answer
    }
}