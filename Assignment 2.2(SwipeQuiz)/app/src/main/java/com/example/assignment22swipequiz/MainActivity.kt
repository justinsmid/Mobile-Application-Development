package com.example.assignment22swipequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questions = Question.QUESTIONS
    private val questionAdapter = QuestionAdapter(questions, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        rvQuestions.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvQuestions.adapter = questionAdapter

        rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder,direction: Int) {
                handleSwipe(questions[viewHolder.adapterPosition], direction)
                questionAdapter.notifyItemChanged(viewHolder.adapterPosition)
            }
        }

        return ItemTouchHelper(callback)
    }

    private fun handleSwipe(question: Question, direction: Int) {
        when (direction) {
            ItemTouchHelper.LEFT -> when {
                question.isTrue -> correct(question)
                else -> incorrect(question)
            }
            ItemTouchHelper.RIGHT -> when {
                question.isTrue -> incorrect(question)
                else -> correct(question)
            }
        }
    }

    private fun correct(question: Question) {
        Snackbar.make(rvQuestions, getString(R.string.correct, question.isTrue), Snackbar.LENGTH_SHORT).show()
    }

    private fun incorrect(question: Question) {
        Snackbar.make(rvQuestions, getString(R.string.incorrect, question.isTrue), Snackbar.LENGTH_SHORT).show()
    }
}
