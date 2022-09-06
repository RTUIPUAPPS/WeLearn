package com.rtu.welearn.ui.video_list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rtu.welearn.R
import com.rtu.welearn.data.room.videolist.VideoListData


class VideoListAdapter(private val mList: List<VideoListData>, val listener: OnClickListener) :
    RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_video_list, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = mList[position]
        holder.tvVideoTitle.text = model.videoTitle
        holder.tvVideoDescription.text = model.videoDescription

        holder.llRoot.setOnClickListener {
            listener.onClick(model.videoID)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val llRoot: LinearLayout = itemView.findViewById(R.id.llRoot)
        val tvVideoTitle: TextView = itemView.findViewById(R.id.tvVideoTitle)
        val tvVideoDescription: TextView = itemView.findViewById(R.id.tvVideoDescription)
    }


    interface OnClickListener {
        fun onClick(videoID: String?)
    }

}
