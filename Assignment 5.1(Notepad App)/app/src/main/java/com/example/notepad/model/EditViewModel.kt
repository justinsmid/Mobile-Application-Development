package com.example.notepad.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.notepad.database.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val note = MutableLiveData<Note?>()
    val success = MutableLiveData<Boolean>()

    fun updateNote() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                noteRepository.updateNotepad(note.value!!)
            }
            success.value = true
        }
    }
}
