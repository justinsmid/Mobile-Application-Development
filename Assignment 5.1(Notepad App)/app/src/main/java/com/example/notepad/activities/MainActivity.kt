package com.example.notepad.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.notepad.EXTRA_NOTE
import com.example.notepad.R
import com.example.notepad.model.MainActivityViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { startEditActivity() }

        initViewModel()
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.note.observe(this, Observer { note ->
            if (note != null) {
                tvTitle.text = note.title
                tvLastUpdated.text = getString(R.string.lastUpdated, note.lastUpdated.toString())
                tvNotes.text = note.text
            }
        })
    }

    private fun startEditActivity() {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra(EXTRA_NOTE, mainActivityViewModel.note.value)
        startActivity(intent)
    }
}
