package com.example.museumapp.activities

import adapters.CollectionAdapter
import com.example.museumapp.R

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import database.repositories.ArtworkRepository
import kotlinx.android.synthetic.main.activity_collection.*
import model.Artwork

class CollectionActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val initialCollection = arrayListOf<Artwork>()
    private val collection = arrayListOf<Artwork>()
    private val collectionAdapter = CollectionAdapter(collection)
    private lateinit var artworkRepository: ArtworkRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        setSupportActionBar(findViewById(R.id.collectionToolbar))

        artworkRepository = ArtworkRepository(this)

        svSearch.setOnQueryTextListener(this)

        initViews()
    }

    private fun initViews() {
        rvCollection.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rvCollection.adapter = collectionAdapter

        this.initialCollection.addAll(artworkRepository.getArtworks())
        this.collection.addAll(initialCollection)
        collectionAdapter.notifyDataSetChanged()
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        val query : String = newText!!
        this.collection.clear()
        this.collection.addAll(
            initialCollection.filter {it.title.contains(query, ignoreCase = true)}
        )
        collectionAdapter.notifyDataSetChanged()
        return true
    }
}
