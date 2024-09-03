package com.example.quizapp


import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityQuizTestBinding
import java.io.Serializable

class QuizTest : AppCompatActivity() {
    private lateinit var binding: ActivityQuizTestBinding
    private val quizzes: Array<Quiz> = arrayOf(
        Quiz("What is the color of the sky?", listOf(3, "Red", "Green", "Blue", "Black"), 5),
        Quiz("What is the color of the sun?", listOf(1, "Yellow", "Green", "Blue", "Black"), 5),
        Quiz(
            "What does Bangladesh's flag look like?",
            listOf(
                2,
                "Blue background with yellow star in centre",
                "Green background with red circle in centre",
                "Red background with white circle in centre",
                "Green background with red star in centre"
            ),
            3
        ),
        Quiz(
            "What year did Bangladesh finally become independent and gain the name it has today?",
            listOf(1, "1971", "1947", "1952", "2024"), 2
        ),
        Quiz(
            "What is the national flower of Bangladesh?",
            listOf(1, "Water Lily", "Tulip", "Rose", "Shelly"),
            4
        ),
        Quiz(
            "What is the main religion practiced in Bangladesh?",
            listOf(2, "Buddhism", "Islam", "Hinduism", "Christian"),
            3
        ),
        Quiz(
            "On which continent is Bangladesh located?",
            listOf(3, "Africa", "Europe", "Asia", "America"),
            3
        ),
        Quiz(
            "Which district of Bangladesh was part of Assam?",
            listOf(3, "Chittagong", "Khulna", "Shylet", "Noakhali"),
            3
        ),
        Quiz(
            "Which river of Bangladesh originates in Tibet?",
            listOf(1, "Brahmaputra", "Padma", "Surma", "Jamuna"),
            4
        ),
        Quiz(
            "What is the Time Zone of Bangladesh ?",
            listOf(2, "UTC + 5", "UCT + 6", "UCT +7 ", "UCT + 8"),
            2
        )


    )

    private var quizIndex: Int = 0 //Can not be less than 0
    private val quizNum = quizzes.size


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initQuiz()

        binding.btnSubmit.setOnClickListener {
            checkAnswer()
            if (quizIndex < quizNum - 1) {
                loadNextQuestion()
            } else {
                showFinalResult()
            }
        }

    }

    private fun initQuiz() {
        val answers = quizzes[quizIndex].options
        val point = quizzes[quizIndex].point

        binding.apply {
            question.text = quizzes[quizIndex].question
            radioOption1.text = answers[1].toString()
            radioOption2.text = answers[2].toString()
            radioOption3.text = answers[3].toString()
            radioOption4.text = answers[4].toString()
            questionNumber.text = "No:${quizIndex + 1}/${quizNum}  Point:$point"
        }

    }

    private fun loadNextQuestion() {
        quizIndex += 1
        binding.radioQuiz.clearCheck()
        initQuiz()
    }

    private fun checkAnswer() {
        if (binding.radioQuiz.checkedRadioButtonId == -1) {
            quizzes[quizIndex].isSkipped = true
            quizzes[quizIndex].isCorrect = false
        } else {
            val correctIndex = quizzes[quizIndex].options[0].toString().toInt()
            val correctAns = quizzes[quizIndex].options[correctIndex].toString()
            val givenAnswer =
                findViewById<RadioButton>(binding.radioQuiz.checkedRadioButtonId).text.toString()

            quizzes[quizIndex].isCorrect = correctAns == givenAnswer
            quizzes[quizIndex].isSkipped = false
        }

    }

    private fun generateResult(): String {
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

    private fun showFinalResult() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Results:")
        builder.setMessage(generateResult())

        builder.setPositiveButton(
            "Show result"
        ) { p0, p1 -> showExpandedResult() }

        builder.setNeutralButton(
            "Start Again"
        ) { p0, p1 -> returnHome() }

        val alertDialog = builder.create()
        alertDialog.show()

    }

    private fun returnHome() {
        val intent = Intent(this@QuizTest, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showExpandedResult() {
        val intent = Intent(this@QuizTest, ShowResult::class.java)
        intent.putExtra("quizzes", quizzes as Serializable)
        startActivity(intent)

    }


}