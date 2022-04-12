package com.rtu.welearn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rtu.welearn.databinding.ActivityToolDescriptionBinding
import com.rtu.welearn.utils.Constants.Companion.TOOL_NUMBER

class ToolDescriptionActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context, toolNumber:Int): Intent {
            var intent = Intent(mContext, ToolDescriptionActivity::class.java)
            intent.putExtra(TOOL_NUMBER,toolNumber)
            return intent
        }
    }

    var binding: ActivityToolDescriptionBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tool_description)

        var intToolNumber=intent.getIntExtra(TOOL_NUMBER,0)

        when(intToolNumber){
            1->{
              binding?.tvTolsTitle?.text=getString(R.string.offline_tools_title1)
              binding?.tvTolsDescription?.text=getString(R.string.tools_description_1)
            }2->{

            binding?.tvTolsTitle?.text=getString(R.string.offline_tools_title2)
            binding?.tvTolsDescription?.text=getString(R.string.tools_description_2)
            }3->{

            binding?.tvTolsTitle?.text=getString(R.string.offline_tools_title3)
            binding?.tvTolsDescription?.text=getString(R.string.tools_description_3)
            }4->{
            binding?.tvTolsTitle?.text=getString(R.string.offline_tools_title4)
            binding?.tvTolsDescription?.text=getString(R.string.tools_description_4)

            }5->{
            binding?.tvTolsTitle?.text=getString(R.string.offline_tools_title5)
            binding?.tvTolsDescription?.text=getString(R.string.tools_description_5)

            }6->{
            binding?.tvTolsTitle?.text=getString(R.string.offline_tools_title6)
            binding?.tvTolsDescription?.text=getString(R.string.tools_description_6)

            }7->{
            binding?.tvTolsTitle?.text=getString(R.string.offline_tools_title7)
            binding?.tvTolsDescription?.text=getString(R.string.tools_description_7)

            }8->{
            binding?.tvTolsTitle?.text=getString(R.string.offline_tools_title8)
            binding?.tvTolsDescription?.text=getString(R.string.tools_description_8)

            }9->{
            binding?.tvTolsTitle?.text=getString(R.string.offline_tools_title9)
            binding?.tvTolsDescription?.text=getString(R.string.tools_description_9)

            }
            10 -> {
                binding?.tvTolsTitle?.text=getString(R.string.offline_tools_title10)
                binding?.tvTolsDescription?.text=getString(R.string.tools_description_10)

            }
        }
        binding

    }
}