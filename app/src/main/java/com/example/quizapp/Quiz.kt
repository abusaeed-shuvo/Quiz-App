package com.example.quizapp

import java.io.Serializable

data class Quiz(
    val question: String,
    val options: List<Any>,
    val point: Int,
    var isCorrect: Boolean = false,
    var isSkipped: Boolean = true
):Serializable{
}

