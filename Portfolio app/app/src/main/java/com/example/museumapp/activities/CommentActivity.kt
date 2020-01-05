package com.example.museumapp.activities

import adapters.CommentAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.museumapp.R
import com.example.museumapp.Services.UserService
import database.repositories.CommentRepository
import kotlinx.android.synthetic.main.activity_comments.*
import model.Artwork
import model.Comment
import model.CommentViewModel

class CommentActivity : AppCompatActivity() {

    private var comments = arrayListOf<Comment>()
    private val commentAdapter = CommentAdapter(comments)
    private lateinit var artwork: Artwork
    private lateinit var viewModel: CommentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        setSupportActionBar(commentsToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        artwork = intent.getParcelableExtra("artwork")!!

        ibSendComment.setOnClickListener { sendComment() }

        initViews()
        initViewModel()
    }

    private fun initViews() {
        rvComments.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvComments.adapter = commentAdapter

        this.comments.addAll(comments)

        commentAdapter.notifyDataSetChanged()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommentViewModel::class.java)
        viewModel.init(artwork)

        viewModel.comments.observe(this, Observer { comments ->
            this.comments.clear()
            this.comments.addAll(comments)
            commentAdapter.notifyDataSetChanged()
        })
    }

    private fun sendComment() {
        val comment = Comment(
            value = etComment.text.toString(),
            author = UserService.attemptGetUser(this)!!.username,
            artworkId = artwork.id
        )

        etComment.text.clear()
        rvComments.scrollToPosition(comments.size)

        viewModel.insertComment(comment)
        commentAdapter.notifyDataSetChanged()
    }
}
