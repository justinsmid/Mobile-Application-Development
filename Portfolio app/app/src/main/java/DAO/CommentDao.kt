package DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import model.Comment

@Dao
interface CommentDao {

    @Query("SELECT * FROM comment INNER JOIN artwork ON comment.artwork_id = artwork.id WHERE artwork.id = :artworkId")
    fun getCommentsByArtwork(artworkId: Long?): LiveData<List<Comment>>

    @Query("SELECT * FROM comment INNER JOIN user ON comment.author = user.username WHERE user.username = :username")
    fun getCommentsByAuthor(username: String): List<Comment>

    @Insert
    fun insertComment(comment: Comment)
}
