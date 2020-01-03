package com.example.gamebacklog.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gamebacklog.R
import com.example.gamebacklog.model.Game
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        fab.setOnClickListener { createGame() }

        initViews()
    }

    private fun initViews() {
        // Add month values to month dropdown
        ArrayAdapter.createFromResource(
            this,
            R.array.months,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spMonth.adapter = adapter
        }
    }

    private fun createGame() {
        if (!validInput()) return

        println("Input is valid, creating game.")

        val game = Game(
            "title",
            "platform",
            31,
            "January",
            2019
        )

        println("Created game ${game.title}")
    }

    private fun validInput() : Boolean  = when {
        etTitle.text == null || etTitle.text!!.trim().isEmpty() -> error(etTitle,"Title must not be empty")
        etPlatform.text == null || etPlatform.text!!.trim().isEmpty() -> error(etPlatform, "Platform must not be empty")
        etDay.text == null -> error(etDay, "Day must not be empty")
        etDay.text.toString().toInt() <= 0 || etDay.text.toString().toInt() > 31 -> error(etDay, "Should be 0 < day <= 31")
        etYear.text == null || etYear.text.toString().trim().isEmpty() -> error(etYear, "Year must not be empty")
        else -> true
    }

    private fun error(et: EditText, msg: String) : Boolean {
        et.setError(msg, ContextCompat.getDrawable(applicationContext,R.drawable.ic_error))
        return false
    }
}