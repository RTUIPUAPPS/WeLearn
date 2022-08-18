package com.rtu.welearn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rtu.welearn.databinding.ActivityPracticesBinding

class ToolkitsActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, ToolkitsActivity::class.java)
            return intent
        }
    }

    var binding: ActivityPracticesBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_practices)

        var listTools=ArrayList<String>()
        var listToolsDescription=ArrayList<String>()
        var listToolsImplementation=ArrayList<String>()

        listTools.add(getString(R.string.offline_tools_title1))
        listTools.add(getString(R.string.offline_tools_title2))
        listTools.add(getString(R.string.offline_tools_title3))
        listTools.add(getString(R.string.offline_tools_title4))
        listTools.add(getString(R.string.offline_tools_title5))
        listTools.add(getString(R.string.offline_tools_title6))
        listTools.add(getString(R.string.offline_tools_title7))
        listTools.add(getString(R.string.offline_tools_title8))
        listTools.add(getString(R.string.offline_tools_title9))
        listTools.add(getString(R.string.offline_tools_title10))
        listTools.add(getString(R.string.online_tools_title1))
        listTools.add(getString(R.string.online_tools_title2))
        listTools.add(getString(R.string.online_tools_title3))
        listTools.add(getString(R.string.online_tools_title4))
        listTools.add(getString(R.string.online_tools_title5))
        listTools.add(getString(R.string.online_tools_title6))


        listToolsImplementation.add(getString(R.string.offline_tool1_implementation))
        listToolsImplementation.add(getString(R.string.offline_tool2_implementation))
        listToolsImplementation.add(getString(R.string.offline_tool3_implementation))
        listToolsImplementation.add(getString(R.string.offline_tool4_implementation))
        listToolsImplementation.add(getString(R.string.offline_tool5_implementation))
        listToolsImplementation.add(getString(R.string.offline_tool6_implementation))
        listToolsImplementation.add(getString(R.string.offline_tool7_implementation))
        listToolsImplementation.add(getString(R.string.offline_tool8_implementation))
        listToolsImplementation.add(getString(R.string.offline_tool9_implementation))
        listToolsImplementation.add(getString(R.string.offline_tool10_implementation))
        listToolsImplementation.add(getString(R.string.online_tool1_implementation))
        listToolsImplementation.add(getString(R.string.online_tool2_implementation))
        listToolsImplementation.add(getString(R.string.online_tool3_implementation))
        listToolsImplementation.add(getString(R.string.online_tool4_implementation))
        listToolsImplementation.add(getString(R.string.online_tool5_implementation))
        listToolsImplementation.add(getString(R.string.online_tool6_implementation))



        var toolsAdapter = ToolsAdapter(listTools,listToolsImplementation,object:ToolsAdapter.OnToolDescriptionClick{
            override fun onClick(position: Int) {
                launchActivity(ToolDescriptionActivity.getIntent(baseContext, position))
            }
        })
        binding?.rvTools?.layoutManager = LinearLayoutManager(this)
        binding?.rvTools?.adapter = toolsAdapter
    }
}