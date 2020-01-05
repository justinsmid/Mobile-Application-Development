package com.example.notepad.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.notepad.EXTRA_NOTE
import com.example.notepad.R
import com.example.notepad.model.EditViewModel
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.content_edit.*
import java.util.Date

class EditActivity : AppCompatActivity() {

    private lateinit var editViewModel: EditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(edit_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { createNote() }

        initViewModel()
    }

    private fun createNote() {
        editViewModel.note.value?.apply {
            title = etTitle.text.toString()
            lastUpdated = Date()
            text = etNotes.text.toString()
        }

        editViewModel.updateNote()
    }

    private fun initViewModel() {
        editViewModel = ViewModelProviders.of(this).get(EditViewModel::class.java)
        editViewModel.note.value = intent.extras?.getParcelable(EXTRA_NOTE)!!

        editViewModel.note.observe(this, Observer { note ->
            if (note != null) {
                etTitle.setText(note.title)
                etNotes.setText(note.text)
            }
        })

        editViewModel.success.observe(this, Observer { success ->
            if (success) finish()
        })
    }
}
