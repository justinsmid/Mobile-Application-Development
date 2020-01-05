package com.example.museumapp.activities

import adapters.CollectionAdapter
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.museumapp.R
import kotlinx.android.synthetic.main.activity_collection.*
import model.Artwork
import model.User

class UserFavoritesActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val initialCollection = arrayListOf<Artwork>()
    private val collection = arrayListOf<Artwork>()
    private val collectionAdapter = CollectionAdapter(collection)
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        setSupportActionBar(findViewById(R.id.collectionToolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.favorites)

        user = intent.getParcelableExtra("user")!!

        svSearch.setOnQueryTextListener(this)

        initViews()
    }

    private fun initViews() {
        rvCollection.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rvCollection.adapter = collectionAdapter

        initialCollection.addAll(user.favoriteArtworks!!)
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