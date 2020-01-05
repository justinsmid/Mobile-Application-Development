package DAO

import androidx.room.*
import model.Artwork

@Dao
interface ArtworkDao {

    @Query("SELECT * FROM artwork")
    fun getAllArtworks(): List<Artwork>

    @Insert
    fun insertArtwork(artwork: Artwork)

    @Insert
    fun insertArtworks(artworks: List<Artwork>)

    @Delete
    fun deleteArtwork(artwork: Artwork)

    @Update
    fun updateArtwork(artwork: Artwork)
}
