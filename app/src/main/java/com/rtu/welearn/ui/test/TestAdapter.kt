package com.rtu.welearn.ui.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rtu.welearn.R
import kotlinx.android.synthetic.main.row_test.view.*
import kotlinx.android.synthetic.main.row_tips.view.*


class TestAdapter( var listQuestions : ArrayList<ModelTestQuestion>) :
    RecyclerView.Adapter<TestAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<ModelTestQuestion>() {

        override fun areItemsTheSame(
            oldItem: ModelTestQuestion,
            newItem: ModelTestQuestion
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ModelTestQuestion,
            newItem: ModelTestQuestion
        ): Boolean {
            return oldItem == newItem
        }
    }

//    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_test,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listQuestions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val questionData: ModelTestQuestion = listQuestions[position]
        holder.itemView.apply {
            questionData?.let { question ->
                tvQuestion.text = question.Question
//                tvTitle.setOnClickListener {
//                    onItemClickListener?.let { it(tipsData) }
//                }
            }
        }
    }

    private var onItemClickListener: ((ModelTestQuestion) -> Unit)? = null

    fun setOnItemClickListener(listener: (ModelTestQuestion) -> Unit) {
        onItemClickListener = listener
    }

}