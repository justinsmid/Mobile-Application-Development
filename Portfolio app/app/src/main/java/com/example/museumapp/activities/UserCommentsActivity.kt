package com.example.museumapp.activities

import database.repositories.CommentRepository
import model.User

import adapters.CommentAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.museumapp.R
import kotlinx.android.synthetic.main.activity_comments.*
import model.Comment

class UserCommentsActivity : AppCompatActivity() {

    private var comments = arrayListOf<Comment>()
    private val commentAdapter = CommentAdapter(comments)
    private lateinit var commentRepository: CommentRepository
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        setSupportActionBar(commentsToolbar)

        user = intent.getParcelableExtra("user")!!
        commentRepository = CommentRepository(this)

        initViews()
    }

    private fun initViews() {
        etComment.visibility = View.GONE
        ibSendComment.visibility = View.GONE

        rvComments.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvComments.adapter = commentAdapter

        this.comments.addAll(commentRepository.getCommentByAuthor(user.username))

        commentAdapter.notifyDataSetChanged()
    }
}
