package com.rtu.welearn.ui.test
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rtu.welearn.R

import java.util.ArrayList;

class MyAdapter(var context: Context?, var listQuestions:ArrayList<ModelTestQuestion>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.row_test, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvQuestion.text = listQuestions[position].Question
    }

    override fun getItemCount(): Int {
        return listQuestions.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvQuestion: TextView
        var ans1: TextView
        var ans2: TextView
        var ans3: TextView
        var ans4: TextView
        var ans5: TextView

        init {
            tvQuestion = itemView.findViewById(R.id.tvQuestion)
            ans1 = itemView.findViewById(R.id.tvQuestion)
            ans2 = itemView.findViewById(R.id.tvQuestion)
            ans3 = itemView.findViewById(R.id.tvQuestion)
            ans4 = itemView.findViewById(R.id.tvQuestion)
            ans5 = itemView.findViewById(R.id.tvQuestion)
        }
    }
}