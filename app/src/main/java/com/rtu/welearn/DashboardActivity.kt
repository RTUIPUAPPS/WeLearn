package com.rtu.welearn

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rtu.welearn.databinding.ActivityDashboardBinding
import com.rtu.welearn.ui.about.AboutActivity
import com.rtu.welearn.ui.firebase.FirebaseMainActivity
import com.rtu.welearn.ui.materials.MaterialsActivity
import com.rtu.welearn.ui.test.TestActivity
import com.rtu.welearn.ui.tips.TipsActivity
import com.rtu.welearn.ui.toolkits.ToolkitsActivity
import com.rtu.welearn.ui.video_list.VideoListActivity
import com.rtu.welearn.utils.AppUtils.isInternetAvailable
import com.rtu.welearn.utils.AppUtils.showToast

class DashboardActivity : BaseActivity() {

    var binding: ActivityDashboardBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        binding?.cvAbout?.setOnClickListener {
            if (isInternetAvailable(applicationContext)) {
                launchActivity(AboutActivity.getIntent(this))
            } else {
                this.showToast(getString(R.string.please_check_device_internet_connection))
            }
        }
        binding?.cvMaterials?.setOnClickListener { launchActivity(MaterialsActivity.getIntent(this)) }
        binding?.cvTips?.setOnClickListener {
            if (isInternetAvailable(applicationContext)) {
                launchActivity(TipsActivity.getIntent(this))
            } else {
                this.showToast(getString(R.string.please_check_device_internet_connection))
            }
        }
        binding?.cvPractice?.setOnClickListener { launchActivity(ToolkitsActivity.getIntent(this)) }
        binding?.cvTest?.setOnClickListener {

            if (isInternetAvailable(applicationContext)) {
                launchActivity(TestActivity.getIntent(this))
            } else {
                this.showToast(getString(R.string.please_check_device_internet_connection))
            }
        }
        binding?.cvVideo?.setOnClickListener {
            if (isInternetAvailable(applicationContext)) {
                launchActivity(VideoListActivity.getIntent(this))
            } else {
                this.showToast(getString(R.string.please_check_device_internet_connection))
            }
        }
        binding?.ivWeLearn?.setOnClickListener {
            launchActivity(FirebaseMainActivity.getIntent(this))
        }
    }
}