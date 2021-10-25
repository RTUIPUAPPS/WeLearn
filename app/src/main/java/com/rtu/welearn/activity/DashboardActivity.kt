package com.rtu.welearn.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rtu.welearn.R
import com.rtu.welearn.databinding.ActivityDashboardBinding

class DashboardActivity : BaseActivity() {

    var binding: ActivityDashboardBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        binding?.cvAbout?.setOnClickListener { launchActivity(DetailsActivity.getIntent(this)) }
        binding?.cvMaterials?.setOnClickListener { launchActivity(DetailsActivity.getIntent(this)) }
        binding?.cvPractice?.setOnClickListener { launchActivity(DetailsActivity.getIntent(this)) }
        binding?.cvTest?.setOnClickListener { launchActivity(DetailsActivity.getIntent(this)) }
        binding?.cvTips?.setOnClickListener { launchActivity(DetailsActivity.getIntent(this)) }
        binding?.cvVideo?.setOnClickListener { launchActivity(DetailsActivity.getIntent(this)) }
    }

}