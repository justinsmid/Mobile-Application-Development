package com.example.museumapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.museumapp.R
import com.example.museumapp.Services.UserService
import database.repositories.UserRepository
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userRepository = UserRepository(this)

        btnSubmit.setOnClickListener { submit() }
    }

    private fun submit() {
        if (!validInput()) return

        val user = UserService.createUser(
            etEmail.text.toString(),
            etUsername.text.toString()
        )

        userRepository.insertUser(user)

        finish()
    }

    private fun validInput() : Boolean  = when {
        etEmail.text == null || etEmail.text!!.trim().isEmpty() -> error(etEmail,"Email must not be empty")
        etUsername.text == null || etUsername.text!!.trim().isEmpty() -> error(etUsername, "Username must not be empty")
        else -> true
    }

    private fun error(et: EditText, msg: String) : Boolean {
        et.setError(msg, ContextCompat.getDrawable(applicationContext,R.drawable.ic_error))
        return false
    }
}
