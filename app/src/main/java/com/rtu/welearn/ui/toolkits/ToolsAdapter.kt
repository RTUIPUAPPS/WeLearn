package com.rtu.welearn.ui.toolkits

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rtu.welearn.R
import net.cachapa.expandablelayout.ExpandableLayout
import net.cachapa.expandablelayout.util.FastOutSlowInInterpolator

class ToolsAdapter(
    var listTitle: ArrayList<String>, var listImplementation: ArrayList<String>,
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

        val absPosition: Int = holder.absoluteAdapterPosition
        val isSelected = absPosition == selectedItem

        if(listTitle[absPosition].contains("offline tool",true)){
            holder.llToolTitle?.setBackgroundResource(R.drawable.bg_blue_rounded)
            holder.tvToolImplementation?.setBackgroundResource(R.color.purple_500_light)
        }else{
            holder.llToolTitle?.setBackgroundResource(R.drawable.bg_blue_rounded_light)
            holder.tvToolImplementation?.setBackgroundResource(R.color.color_steal_light)
        }
        holder.tvToolTitle?.text = listTitle[absPosition]
        holder.tvToolImplementation?.text = Html.fromHtml(listImplementation[absPosition],Html.FROM_HTML_MODE_LEGACY)
        holder.tvToolTitle?.isSelected = isSelected
        holder.expandableLayout?.setExpanded(isSelected, false)
        holder.tvToolsDescription?.setOnClickListener(object:View.OnClickListener{
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

        var llToolTitle: LinearLayout? = null
        var tvToolTitle: TextView? = null
        var tvToolImplementation: TextView? = null
        var tvToolsDescription: TextView? = null
        var expandableLayout: ExpandableLayout? = null

        init {
            val position = absoluteAdapterPosition
            val isSelected = position == selectedItem

            llToolTitle = itemView.findViewById(R.id.llToolTitle)
            tvToolTitle = itemView.findViewById(R.id.tvToolTitle)
            tvToolsDescription = itemView.findViewById(R.id.tvTolsDescription)
            tvToolImplementation = itemView.findViewById(R.id.tvToolImplementation)
            expandableLayout = itemView.findViewById(R.id.expandable_layout)
            expandableLayout?.setInterpolator(FastOutSlowInInterpolator())
            expandableLayout?.setOnExpansionUpdateListener(this)
            llToolTitle?.setOnClickListener(this)
            tvToolTitle!!.isSelected = isSelected
            expandableLayout!!.setExpanded(isSelected, false)
        }

        override fun onClick(p0: View?) {
            tvToolTitle?.isSelected = false
            expandableLayout?.collapse()

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