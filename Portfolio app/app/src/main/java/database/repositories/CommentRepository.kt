package database.repositories

import DAO.CommentDao
import android.content.Context
import androidx.lifecycle.LiveData
import database.MuseumAppDatabase
import model.Comment

class CommentRepository(context: Context) {

    private var commentDao: CommentDao

    init {
        val database = MuseumAppDatabase.getDatabase(context)
        commentDao = database!!.commentDao()
    }

    fun getCommentsByArtwork(artworkId: Long?): LiveData<List<Comment>> {
        return commentDao.getCommentsByArtwork(artworkId)
    }

    fun getCommentByAuthor(username: String): List<Comment> {
        return commentDao.getCommentsByAuthor(username)
    }

    fun insertComment(reminder: Comment) {
        commentDao.insertComment(reminder)
    }
}