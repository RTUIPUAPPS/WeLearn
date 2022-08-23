package com.rtu.welearn.ui.materials

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rtu.welearn.R


class MaterialsAdapter(
    var listTitle: ArrayList<String>,
    var onClickListener: OnToolDescriptionClick
) :
    RecyclerView.Adapter<MaterialsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val position: Int = holder.absoluteAdapterPosition

        holder.tvTitle?.text = listTitle[position]
        holder.tvTitle?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                onClickListener.onClick(holder.absoluteAdapterPosition)
            }
        })
    }

    override fun getItemCount(): Int = listTitle.size


    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_materials, parent, false)) {

        var tvTitle: TextView? = null
        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
        }
    }

    interface OnToolDescriptionClick {
        fun onClick(position: Int)
    }

}

