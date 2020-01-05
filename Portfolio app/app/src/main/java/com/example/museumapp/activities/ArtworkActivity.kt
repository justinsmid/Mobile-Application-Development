package com.example.museumapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.museumapp.R
import com.example.museumapp.Services.UserService
import database.repositories.UserRepository
import kotlinx.android.synthetic.main.activity_artwork.*
import model.Artwork
import shareArtwork
import toDrawable

class ArtworkActivity : AppCompatActivity() {

    private lateinit var artwork: Artwork
    private lateinit var collection: ArrayList<Artwork>
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artwork)
        setSupportActionBar(findViewById(R.id.artworkToolbar))

        artwork = intent.getParcelableExtra("artwork")!!
        collection = intent.getParcelableArrayListExtra("collection")!!
        userRepository = UserRepository(this)

        ivPrevArrow.setOnClickListener { showPreviousArtwork() }
        ivNextArrow.setOnClickListener { showNextArtwork() }
        fabLike.setOnClickListener { likeArtwork() }

        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.artwork_menu, menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = artwork.title
        supportActionBar!!.subtitle = "By ${artwork.creator}"
        return super.onCreateOptionsMenu(menu)
    }

    private fun initViews() {
        ivArtworkImage.setImageDrawable(toDrawable(artwork.imageData, resources))
        val user = UserService.attemptGetUser(this)!!
        val artworkIsFavorited = if (user.favoriteArtworks == null) false else user.favoriteArtworks!!.contains(artwork)
        fabLike.setImageDrawable(getDrawable(if (artworkIsFavorited) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star))

        // hide prev/next buttons if there is no available artwork
        val prevArtworkIndex = collection.indexOf(artwork) - 1
        if (prevArtworkIndex < 0) {
            ivPrevArrow.visibility = View.GONE
        }
        val nextArtworkIndex = collection.indexOf(artwork) + 1
        if (collection.size <= nextArtworkIndex) {
            ivNextArrow.visibility = View.GONE
        }
    }

    private fun showPreviousArtwork() {
        val prevArtwork = collection[collection.indexOf(artwork) - 1]
        showArtwork(prevArtwork)
    }

    private fun showNextArtwork() {
        val nextArtwork = collection[collection.indexOf(artwork) + 1]
        showArtwork(nextArtwork)
    }

    private fun showArtwork(artwork: Artwork) {
        val intent = Intent(this, ArtworkActivity::class.java)
        intent.putExtra("artwork", artwork)
        intent.putExtra("collection", collection)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

        startActivity(intent)
    }

    private fun likeArtwork() {
        val user = UserService.user!!
        val favorites = if (user.favoriteArtworks != null) user.favoriteArtworks!! else arrayListOf()

        if (favorites.contains(artwork)) {
            favorites.remove(artwork)
            fabLike.setImageDrawable(getDrawable(android.R.drawable.btn_star))
            Toast.makeText(this, "Unliked artwork", Toast.LENGTH_LONG).show()
        } else {
            favorites.add(artwork)
            fabLike.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_on))
            Toast.makeText(this, "Liked artwork", Toast.LENGTH_LONG).show()
        }

        user.favoriteArtworks = favorites
        userRepository.updateUser(user)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.itemShare -> {
            shareArtwork(this, artwork)
            true
        }
        R.id.itemLearnMore -> {
            Toast.makeText(this,"Learn more clicked",Toast.LENGTH_LONG).show()
            true
        }
        R.id.itemComments -> {
            val intent = Intent(this, CommentActivity::class.java)
            intent.putExtra("artwork", artwork)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
