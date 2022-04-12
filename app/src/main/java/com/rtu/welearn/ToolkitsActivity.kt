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
        listToolsDescription.add(getString(R.string.tools_description_1))
        listToolsDescription.add(getString(R.string.tools_description_2))
        listToolsDescription.add(getString(R.string.tools_description_3))
        listToolsDescription.add(getString(R.string.tools_description_4))
        listToolsDescription.add(getString(R.string.tools_description_5))
        listToolsDescription.add(getString(R.string.tools_description_6))
        listToolsDescription.add(getString(R.string.tools_description_7))
        listToolsDescription.add(getString(R.string.tools_description_8))
        listToolsDescription.add(getString(R.string.tools_description_9))
        listToolsDescription.add(getString(R.string.tools_description_10))


        var toolsAdapter = ToolsAdapter(listTools,listToolsDescription,object:ToolsAdapter.OnToolDescriptionClick{
            override fun onClick(position: Int) {
                launchActivity(ToolDescriptionActivity.getIntent(baseContext, position))
            }
        })
        binding?.rvTools?.layoutManager = LinearLayoutManager(this)
        binding?.rvTools?.adapter = toolsAdapter
    }
}