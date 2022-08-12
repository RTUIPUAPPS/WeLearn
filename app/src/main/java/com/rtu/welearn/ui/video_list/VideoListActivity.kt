package com.rtu.welearn.ui.video_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.R
import com.rtu.welearn.VideoPlayerActivity
import com.rtu.welearn.WeLearnApp.Companion.VideoList
import com.rtu.welearn.databinding.ActivityVideoListBinding
import com.rtu.welearn.utils.Constants.Companion.VIDEO_ID

class VideoListActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            val intent = Intent(mContext, VideoListActivity::class.java)
            return intent
        }
    }

    var binding: ActivityVideoListBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_list)

        binding?.rvVideoList?.layoutManager = LinearLayoutManager(mContext)
        binding?.rvVideoList?.adapter = VideoListAdapter(VideoList, object :
            VideoListAdapter.OnClickListener {
            override fun onClick(videoID: String?) {
                val intent = VideoPlayerActivity.getIntent(mContext!!)
                intent.putExtra(VIDEO_ID, videoID?:"")
                launchActivity(intent)
            }
        })
    }
}