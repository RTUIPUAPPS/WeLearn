package com.rtu.welearn.ui.video_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.R
import com.rtu.welearn.databinding.ActivityVideoPlayerBinding
import com.rtu.welearn.utils.Constants


class VideoPlayerActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            return Intent(mContext, VideoPlayerActivity::class.java)
        }
    }

    var binding: ActivityVideoPlayerBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player)
        val videoID = intent.getStringExtra(Constants.VIDEO_ID)
        binding?.youtubePlayerView?.setVideoID(videoID ?: "")
    }
}