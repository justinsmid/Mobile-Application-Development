package com.example.logicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val answerFields: List<EditText> by lazy { listOf(answer1, answer2, answer3, answer4) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitBtn.setOnClickListener { checkAnswers() }
    }

    private fun checkAnswers() {
        if (answerFields[0].text.toString() == getText(R.string.T) && answerFields.subList(1, answerFields.size).find { it.text.toString() != getText(R.string.F) } == null) {
            guessedCorrectly()
        } else {
            guessedIncorrectly()
        }
    }

    private fun guessedCorrectly() {
        Toast.makeText(this, getText(R.string.correct), Toast.LENGTH_SHORT).show()
    }

    private fun guessedIncorrectly() {
        Toast.makeText(this, getText(R.string.incorrect), Toast.LENGTH_SHORT).show()
    }
}
