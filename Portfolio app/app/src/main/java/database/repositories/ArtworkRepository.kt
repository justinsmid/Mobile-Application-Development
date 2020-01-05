package database.repositories

import DAO.ArtworkDao
import android.content.Context
import database.MuseumAppDatabase
import model.Artwork
import model.User

class ArtworkRepository(context: Context) {

    private var artworkDao: ArtworkDao

    init {
        val database = MuseumAppDatabase.getDatabase(context)
        artworkDao = database!!.artworkDao()
    }

    fun getArtworks(): List<Artwork> {
        return artworkDao.getAllArtworks()
    }

    fun insertArtwork(artwork: Artwork) {
        artworkDao.insertArtwork(artwork)
    }
    
    fun insertArtworks(artworks: List<Artwork>) {
        artworkDao.insertArtworks(artworks)
    }
}
