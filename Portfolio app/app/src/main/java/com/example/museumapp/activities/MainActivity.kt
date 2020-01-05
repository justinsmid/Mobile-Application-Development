package com.example.museumapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.museumapp.R
import com.example.museumapp.Services.UserService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCollection.setOnClickListener { showCollection() }
        btnProfile.setOnClickListener { showProfile() }

        initApp()
    }

    private fun initApp() {
        if (!UserService.isLoggedIn(this)) {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun showCollection() {
        startActivity(Intent(this, CollectionActivity::class.java))
    }

    private fun showProfile() {
        startActivity(Intent(this, ProfileActivity::class.java))
    }
}
