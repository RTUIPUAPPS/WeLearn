package com.rtu.welearn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.cachapa.expandablelayout.ExpandableLayout
import net.cachapa.expandablelayout.util.FastOutSlowInInterpolator


class ToolsAdapter(
    var listTitle: ArrayList<String>, var listDescription: ArrayList<String>,
    var onClickListener: OnToolDescriptionClick
) :
    RecyclerView.Adapter<ToolsAdapter.ViewHolder>() {

    private val UNSELECTED = -1

    private val selectedItem = UNSELECTED


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val position: Int = holder.absoluteAdapterPosition
        val isSelected = position == selectedItem

        holder.tvToolTitle?.text = listTitle[position]
        holder.tvToolImplementation?.text = listDescription[position]
        holder.tvToolTitle?.isSelected = isSelected
        holder.expandableLayout?.setExpanded(isSelected, false)
        holder.tvTolsDescription?.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                onClickListener.onClick(holder.absoluteAdapterPosition+1)
            }
        })
    }

    override fun getItemCount(): Int = listTitle.size


    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_tools, parent, false)),
        View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {


        private val UNSELECTED = -1
        private var selectedItem = UNSELECTED

        var tvToolTitle: TextView? = null
        var tvToolImplementation: TextView? = null
        var tvTolsDescription: TextView? = null
        var expandableLayout: ExpandableLayout? = null


        init {
            val position = absoluteAdapterPosition
            val isSelected = position == selectedItem

            tvToolTitle = itemView.findViewById(R.id.tvToolTitle)
            tvTolsDescription = itemView.findViewById(R.id.tvTolsDescription)
            tvToolImplementation = itemView.findViewById(R.id.tvToolImplementation)
            expandableLayout = itemView.findViewById(R.id.expandable_layout)
            expandableLayout?.setInterpolator(FastOutSlowInInterpolator())
            expandableLayout?.setOnExpansionUpdateListener(this)
            tvToolTitle?.setOnClickListener(this)


//            tvToolTitle!!.text = "$position. Tap to expand"
            tvToolTitle!!.isSelected = isSelected
            expandableLayout!!.setExpanded(isSelected, false)

        }


        override fun onClick(p0: View?) {
//              if (holder != null) {
            tvToolTitle?.isSelected = false
            expandableLayout?.collapse()
//            }

            val position = absoluteAdapterPosition
            if (position == selectedItem) {
                selectedItem = UNSELECTED
            } else {
                tvToolTitle!!.isSelected = true
                expandableLayout!!.expand()
                selectedItem = position
            }
        }


        override fun onExpansionUpdate(expansionFraction: Float, state: Int) {

        }
    }

    interface OnToolDescriptionClick {
        fun onClick(position: Int)
    }
}