package com.rtu.welearn.ui.toolkits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.databinding.DataBindingUtil
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.R
import com.rtu.welearn.databinding.ActivityToolDescriptionBinding
import com.rtu.welearn.utils.Constants.Companion.TOOL_NUMBER


class ToolDescriptionActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context, toolNumber: Int): Intent {
            var intent = Intent(mContext, ToolDescriptionActivity::class.java)
            intent.putExtra(TOOL_NUMBER, toolNumber)
            return intent
        }
    }

    var binding: ActivityToolDescriptionBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tool_description)

        val intToolNumber = intent.getIntExtra(TOOL_NUMBER, 0)


        val listToolsDescription = ArrayList<String>()
        listToolsDescription.add(getString(R.string.offline_tools_description_1))
        listToolsDescription.add(getString(R.string.offline_tools_description_2))
        listToolsDescription.add(getString(R.string.offline_tools_description_3))
        listToolsDescription.add(getString(R.string.offline_tools_description_4))
        listToolsDescription.add(getString(R.string.offline_tools_description_5))
        listToolsDescription.add(getString(R.string.offline_tools_description_6))
        listToolsDescription.add(getString(R.string.offline_tools_description_7))
        listToolsDescription.add(getString(R.string.offline_tools_description_8))
        listToolsDescription.add(getString(R.string.offline_tools_description_9))
        listToolsDescription.add(getString(R.string.offline_tools_description_10))
        listToolsDescription.add(getString(R.string.online_tools_description_1))
        listToolsDescription.add(getString(R.string.online_tools_description_2))
        listToolsDescription.add(getString(R.string.online_tools_description_3))
        listToolsDescription.add("")
        listToolsDescription.add(getString(R.string.online_tools_description_5))
        listToolsDescription.add("")


        when (intToolNumber) {
            1 -> {
                setText(
                    getString(R.string.offline_tools_title1),
                    listToolsDescription[0],
                    intToolNumber
                )

            }
            2 -> {
                setText(
                    getString(R.string.offline_tools_title2),
                    listToolsDescription[1],
                    intToolNumber
                )
            }
            3 -> {

                setText(
                    getString(R.string.offline_tools_title3),
                    listToolsDescription[2],
                    intToolNumber
                )
            }
            4 -> {
                setText(
                    getString(R.string.offline_tools_title4),
                    listToolsDescription[3],
                    intToolNumber
                )

            }
            5 -> {
                setText(
                    getString(R.string.offline_tools_title5),
                    listToolsDescription[4],
                    intToolNumber
                )

            }
            6 -> {
                setText(
                    getString(R.string.offline_tools_title6),
                    listToolsDescription[5],
                    intToolNumber
                )

            }
            7 -> {
                setText(
                    getString(R.string.offline_tools_title7),
                    listToolsDescription[6],
                    intToolNumber
                )

            }
            8 -> {
                setText(
                    getString(R.string.offline_tools_title8),
                    listToolsDescription[7],
                    intToolNumber
                )

            }
            9 -> {
                setText(
                    getString(R.string.offline_tools_title9),
                    listToolsDescription[8],
                    intToolNumber
                )

            }
            10 -> {
                setText(
                    getString(R.string.offline_tools_title10),
                    listToolsDescription[9],
                    intToolNumber
                )
            }
            11 -> {
                setText(
                    getString(R.string.online_tools_title1),
                    listToolsDescription[10],
                    intToolNumber
                )

            }
            12 -> {
                setText(
                    getString(R.string.online_tools_title2),
                    listToolsDescription[11],
                    intToolNumber
                )

            }
            13 -> {
                setText(
                    getString(R.string.online_tools_title3),
                    listToolsDescription[12],
                    intToolNumber
                )

            }
            14 -> {
                setText(
                    getString(R.string.online_tools_title4),
                    listToolsDescription[13],
                    intToolNumber
                )

            }
            15 -> {
                setText(
                    getString(R.string.online_tools_title5),
                    listToolsDescription[14],
                    intToolNumber
                )
            }
            16 -> {
                setText(
                    getString(R.string.online_tools_title6),
                    listToolsDescription[15],
                    intToolNumber
                )
            }
        }
    }

    private fun setText(
        title: String,
        description: String,
        toolNumber:Int

    ) {


        binding?.tvTolsTitle?.text = title

        when(toolNumber){
            14->{
                binding?.tvToolsDescription?.visibility = View.GONE
                binding?.rlTool14?.visibility = View.VISIBLE
                binding?.rlTool16?.visibility = View.GONE
            }
            16->{
                binding?.tvToolsDescription?.visibility = View.GONE
                binding?.rlTool14?.visibility = View.GONE
                binding?.rlTool16?.visibility = View.VISIBLE
            }
            else->{
                binding?.tvToolsDescription?.visibility = View.VISIBLE
                binding?.rlTool14?.visibility = View.GONE
                binding?.rlTool16?.visibility = View.GONE
                binding?.tvToolsDescription?.text =
                    Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)
            }
        }

    }
}