package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityShowResultBinding

class ShowResult : AppCompatActivity() {
    private lateinit var binding: ActivityShowResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityShowResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val quizzes: Array<Quiz> = intent.getSerializableExtra("quizzes") as Array<Quiz>
        binding.resultTV.text = "Result:\n ${generateResult(quizzes)}"


        binding.answerListTv.text = makeAnswerList(quizzes)

        binding.btnGoHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun generateResult(quizzes: Array<Quiz>): String {
        var correctAnswers = 0
        var wrongAnswers = 0
        var skippedAnswers = 0
        var obtainedPoints = 0

        for (it in quizzes) {
            if (it.isCorrect) {
                correctAnswers++
                obtainedPoints += it.point
            } else if (it.isSkipped) {
                wrongAnswers++
                skippedAnswers++
            } else {
                wrongAnswers++
            }
        }
        val result =
            "$correctAnswers questions answered correctly\nObtained Points: $obtainedPoints\n$wrongAnswers questions answered wrong\n$skippedAnswers questions were skipped."
        return result
    }

    private fun makeAnswerList(quizzes: Array<Quiz>): String {
        var answerList: String = "List of Wrong Answer:"
        var i = 1

        for (quiz in quizzes) {
            binding.answerListTv.visibility = View.VISIBLE
            val correctAns = quiz.options[quiz.options[0].toString().toInt()].toString()
            if (!quiz.isCorrect) {
                val qq = "\n$i.${quiz.question}\n\t$correctAns\n"
                answerList += qq
                i++
            } else {
                i++
            }

        }

        return answerList

    }

}