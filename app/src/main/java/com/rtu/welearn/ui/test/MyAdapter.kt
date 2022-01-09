package com.rtu.welearn.ui.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rtu.welearn.R
import java.util.*
import android.widget.RadioButton as RadioButton1


class MyAdapter(
    var context: Context?,
    var listQuestions: ArrayList<ModelTestQuestion>,
    var listener: interfaceAnswerSelected
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.row_test, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val model = listQuestions[position]
        holder.ansRadioGroup.clearCheck()

        if (model.Reply4.isNullOrEmpty()) {
            holder.ans4.visibility = View.GONE
        } else {
            holder.ans4.visibility = View.VISIBLE
        }

        if (model.Reply5.isNullOrEmpty()) {
            holder.ans5.visibility = View.GONE
        } else {
            holder.ans5.visibility = View.VISIBLE
        }
        holder.tvQuestion.text = model.Question
        holder.ans1.text = model.Reply1
        holder.ans2.text = model.Reply2
        holder.ans3.text = model.Reply3
        holder.ans4.text = model.Reply4
        holder.ans5.text = model.Reply5

        holder.ansRadioGroup.setOnCheckedChangeListener { group, checkedId ->

            if (holder.ans1.id == checkedId) {
                listener.onAnswerSelected(position, model.Points1.toInt())
                listQuestions[position].selectedAnswerPosition = 1
            } else if (holder.ans2.id == checkedId) {
                listener.onAnswerSelected(position, model.Points2.toInt())
                listQuestions[position].selectedAnswerPosition = 2
            } else if (holder.ans3.id == checkedId) {
                listener.onAnswerSelected(position, model.Points3.toInt())
                listQuestions[position].selectedAnswerPosition = 3
            } else if (holder.ans4.id == checkedId) {
                listener.onAnswerSelected(position, model.Points4.toInt())
                listQuestions[position].selectedAnswerPosition = 4
            } else if (holder.ans5.id == checkedId) {
                listener.onAnswerSelected(position, model.Points5.toInt())
                listQuestions[position].selectedAnswerPosition = 5
            }
        }

        when (model.selectedAnswerPosition) {
            1 -> {
                holder.ans1.isChecked = true
            }

            2 -> {
                holder.ans2.isChecked = true
            }
            3 -> {
                holder.ans3.isChecked = true
            }
            4 -> {
                holder.ans4.isChecked = true
            }
            5 -> {
                holder.ans5.isChecked = true
            }
        }
    }

    override fun getItemCount(): Int {
        return listQuestions.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvQuestion: TextView = itemView.findViewById(R.id.tvQuestion)
        var ansRadioGroup: RadioGroup = itemView.findViewById(R.id.rgAnswers)
        var ans1: RadioButton1 = itemView.findViewById(R.id.rbAns1)
        var ans2: RadioButton1 = itemView.findViewById(R.id.rbAns2)
        var ans3: RadioButton1 = itemView.findViewById(R.id.rbAns3)
        var ans4: RadioButton1 = itemView.findViewById(R.id.rbAns4)
        var ans5: RadioButton1 = itemView.findViewById(R.id.rbAns5)

    }
}