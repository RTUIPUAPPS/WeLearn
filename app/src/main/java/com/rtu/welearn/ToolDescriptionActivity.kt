package com.rtu.welearn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.databinding.DataBindingUtil
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
        listToolsDescription.add(getString(R.string.online_tool2_implementation))
        listToolsDescription.add(getString(R.string.online_tool3_implementation))
        listToolsDescription.add(getString(R.string.online_tool4_implementation))
        listToolsDescription.add(getString(R.string.online_tool5_implementation))
        listToolsDescription.add(getString(R.string.online_tool6_implementation))


        when (intToolNumber) {
            1 -> {
                setText(
                    getString(R.string.offline_tools_title1),
                    listToolsDescription[0],
                )

            }
            2 -> {
                setText(
                    getString(R.string.offline_tools_title2),
                    listToolsDescription[1]
                )
            }
            3 -> {

                setText(
                    getString(R.string.offline_tools_title3),
                    listToolsDescription[2]
                )
            }
            4 -> {
                setText(
                    getString(R.string.offline_tools_title4),
                    listToolsDescription[3]
                )

            }
            5 -> {
                setText(
                    getString(R.string.offline_tools_title5),
                    listToolsDescription[4]
                )

            }
            6 -> {
                setText(
                    getString(R.string.offline_tools_title6),
                    listToolsDescription[5]
                )

            }
            7 -> {
                setText(
                    getString(R.string.offline_tools_title7),
                    listToolsDescription[6]
                )

            }
            8 -> {
                setText(
                    getString(R.string.offline_tools_title8),
                    listToolsDescription[7]
                )

            }
            9 -> {
                setText(
                    getString(R.string.offline_tools_title9),
                    listToolsDescription[8]
                )

            }
            10 -> {
                setText(
                    getString(R.string.offline_tools_title10),
                    listToolsDescription[9]
                )
            }
            11 -> {
                setText(
                    getString(R.string.online_tools_title1),
                    listToolsDescription[10]
                )

            }
            12 -> {
                setText(
                    getString(R.string.online_tools_title2),
                    listToolsDescription[11]
                )

            }
            13 -> {
                setText(
                    getString(R.string.online_tools_title3),
                    listToolsDescription[12]
                )

            }
            14 -> {
                setText(
                    getString(R.string.online_tools_title4),
                    listToolsDescription[13],
                    true,
                    "online_tool_4_description.pdf"
                )

            }
            15 -> {
                setText(
                    getString(R.string.online_tools_title5),
                    listToolsDescription[14]
                )
            }
            16 -> {
                setText(
                    getString(R.string.online_tools_title6),
                    listToolsDescription[15],
                    true,
                    "online_tool_6_description.pdf"
                )
            }
        }


    }

    private fun setText(
        title: String,
        description: String,
        isShowPDF: Boolean = false,
        pdfName: String = ""
    ) {


        binding?.tvTolsTitle?.text = title

        if (isShowPDF) {
            binding?.svTextDescription?.visibility = View.GONE
            binding?.rlPDFView?.visibility = View.VISIBLE

            binding?.pdfView!!.fromAsset(pdfName)

                .showMinimap(true)
                .enableSwipe(true)
                .swipeVertical(true)
                .onPageChange { page, pageCount ->

//                    when(page){
//                        1->{
//                            binding?.btnSwipeLeft?.visibility=View.GONE
//                            binding?.btnSwipeRight?.visibility=View.VISIBLE
//                        }
//                        pageCount->{
//                            binding?.btnSwipeLeft?.visibility=View.VISIBLE
//                            binding?.btnSwipeRight?.visibility=View.GONE
//                        }
//                        else-> {
//                            binding?.btnSwipeLeft?.visibility=View.VISIBLE
//                            binding?.btnSwipeRight?.visibility=View.VISIBLE
//                        }
//                    }
                }
                .load()
        } else {

            binding?.svTextDescription?.visibility = View.VISIBLE
            binding?.rlPDFView?.visibility = View.GONE
            binding?.tvToolsDescription?.text =
                Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)

        }
    }
}