package com.example.localstorageapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { onSaveClick() }
    }

    private fun onSaveClick() {
        val reminderStr = etAddReminder.text.toString()
        if (reminderStr.isNotBlank()) {
            val reminder = Reminder(reminderStr)
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_REMINDER, reminder)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Snackbar.make(etAddReminder, "You must fill in the input field!", Snackbar.LENGTH_SHORT).show()
        }
    }
}
