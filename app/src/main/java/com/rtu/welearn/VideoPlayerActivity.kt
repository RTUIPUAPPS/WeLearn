package com.rtu.welearn

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import com.rtu.welearn.databinding.ActivityDetailsBinding
import com.rtu.welearn.databinding.ActivityVideoPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.rtu.welearn.utils.Constants


class VideoPlayerActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, VideoPlayerActivity::class.java)
            return intent
        }
    }

    var binding: ActivityVideoPlayerBinding? = null
    //lateinit  var player:ExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player)
        val VIDEO_ID=intent.getStringExtra(Constants.VIDEO_ID)

        binding?.youtubePlayerView?.setVideoID(VIDEO_ID?:"")
//        player = ExoPlayer.Builder(this).build()
//        binding?.playerView?.player = player
//        val mediaItem: MediaItem = MediaItem.Builder()
////            .setUri(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
//            .setUri(Uri.parse("https://www.youtube.com/watch?v=5b87NuJCiBc"))
////            .setMediaId(mediaId) //http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4
////            .setTag(metadata)
//            .build()
//        player.setMediaItem(mediaItem)
//        player.prepare()
//        player.play()

    }

    override fun onPause() {
        super.onPause()
       // player.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
      //  player.stop()
    }
}