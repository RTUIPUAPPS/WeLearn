package com.rtu.welearn.ui.tips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rtu.welearn.R
import kotlinx.android.synthetic.main.row_tips.view.*


class TipsAdapter :
    RecyclerView.Adapter<TipsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<TipsResponse>() {

        override fun areItemsTheSame(
            oldItem: TipsResponse,
            newItem: TipsResponse
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: TipsResponse,
            newItem: TipsResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_tips,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tipsData: TipsResponse? = differ.currentList[position]
        holder.itemView.apply {
            tipsData?.let { tips ->
                btnOnline.text = tips.name
            }
        }
    }

    private var onItemClickListener: ((TipsResponse) -> Unit)? = null

    fun setOnItemClickListener(listener: (TipsResponse) -> Unit) {
        onItemClickListener = listener
    }

}