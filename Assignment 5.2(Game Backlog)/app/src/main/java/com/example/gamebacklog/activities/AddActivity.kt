package com.example.gamebacklog.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gamebacklog.EXTRA_GAME
import com.example.gamebacklog.R
import com.example.gamebacklog.database.repositories.GameRepository
import com.example.gamebacklog.int
import com.example.gamebacklog.model.Game
import com.example.gamebacklog.string
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*

class AddActivity : AppCompatActivity() {

    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        gameRepository = GameRepository(this)

        fab.setOnClickListener { createGame() }

        initViews()
    }

    private fun initViews() {
        etTitle.requestFocus()
    }

    private fun createGame() {
        if (!validInput()) return

        println("Input is valid, creating game.")

        val game = Game(
            title = string(etTitle),
            platform = string(etPlatform),
            day = int(etDay),
            month = int(etMonth),
            year = int(etYear)
        )

        println("Created game ${game.title}")

        val intent = Intent()
        intent.putExtra(EXTRA_GAME, game)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun validInput() : Boolean  = when {
        etTitle.text == null || string(etTitle).isEmpty() -> error(etTitle,"Title must not be empty")

        etPlatform.text == null || string(etPlatform).isEmpty() -> error(etPlatform, "Platform must not be empty")

        etDay.text == null -> error(etDay, "Day must not be empty")
        int(etDay) <= 0 || int(etDay) > 31 -> error(etDay, "Should be 0 < day <= 31")

        etMonth.text == null -> error(etMonth, "Month must not be empty")
        int(etMonth) < 1 || int(etMonth) > 12 -> error(etMonth, "Should be 1 <= month <= 12")

        etYear.text == null || string(etYear).isEmpty() -> error(etYear, "Year must not be empty")
        else -> true
    }

    private fun error(et: EditText, msg: String) : Boolean {
        et.setError(msg, ContextCompat.getDrawable(applicationContext,R.drawable.ic_error))
        return false
    }
}