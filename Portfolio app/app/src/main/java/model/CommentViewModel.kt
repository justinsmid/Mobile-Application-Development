package model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import database.repositories.CommentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val commentRepository = CommentRepository(application.applicationContext)

    lateinit var comments: LiveData<List<Comment>>

    fun init(artwork: Artwork) {
        comments = commentRepository.getCommentsByArtwork(artwork.id)
    }

    fun insertComment(comment: Comment) {
        ioScope.launch {
            commentRepository.insertComment(comment)
        }
    }
}