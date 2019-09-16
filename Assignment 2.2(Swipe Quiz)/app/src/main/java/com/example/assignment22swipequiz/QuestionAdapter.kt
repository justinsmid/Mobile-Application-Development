package com.example.assignment22swipequiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.question_item.view.*

class QuestionAdapter(private val questions: List<Question>, private val context: Context): RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false))
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(question: Question) {
            itemView.tvQuestion.text = question.question
            itemView.setOnClickListener {
                Snackbar.make(itemView, context.getString(R.string.clickMessage, question.question, question.isTrue), Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}