package com.rtu.welearn.ui.video_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.rtu.welearn.BaseActivity
import com.rtu.welearn.R
import com.rtu.welearn.WeLearnApp
import com.rtu.welearn.WeLearnApp.Companion.dbVersionImpl
import com.rtu.welearn.WeLearnApp.Companion.sqlDelightDB
import com.rtu.welearn.WeLearnApp.Companion.videoListVersionFirebase
import com.rtu.welearn.WeLearnApp.Companion.videoListVersionLocalDB
import com.rtu.welearn.data.video_list.VideoListDataSourceImpl
import com.rtu.welearn.databinding.ActivityVideoListBinding
import com.rtu.welearn.utils.Constants
import com.rtu.welearn.utils.Constants.Companion.TOOL_VIDEO
import com.rtu.welearn.utils.Constants.Companion.VIDEO_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import welearndb.VideoListEntity

class VideoListActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            val intent = Intent(mContext, VideoListActivity::class.java)
            return intent
        }
    }

    private var binding: ActivityVideoListBinding? = null
    private var videoList = listOf<VideoListEntity>()

    private var videoListImpl: VideoListDataSourceImpl? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_list)

        videoListImpl = VideoListDataSourceImpl(sqlDelightDB)

        if (videoListVersionLocalDB == videoListVersionFirebase) {
            videoList =
                (videoListImpl?.getVideoList() ?: arrayListOf()) as ArrayList<VideoListEntity>
            initRecyclerView()
        } else {
            getVideoListFromFirebase()
        }
    }

    private fun getVideoListFromFirebase() {

        val mCloudEndPointVideoList: DatabaseReference? =
            WeLearnApp.mDatabase?.child(Constants.VIDEO_LIST)
        mCloudEndPointVideoList?.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                videoListImpl?.deleteVideoList()

                snapshot.children.forEach {
                    lifecycleScope.launch {
                        videoListImpl?.insertVideoDetails(
                            null,
                            it.child(VIDEO_ID).value.toString(),
                            it.child(Constants.VIDEO_TITLE).value.toString(),
                            it.child(Constants.VIDEO_DESCRIPTION).value.toString()

                        )
                    }
                }

                lifecycleScope.launch {
                    dbVersionImpl?.updateVideoVersion(
                        videoListVersionFirebase.toLong()
                    )
                }

                videoListVersionLocalDB=videoListVersionFirebase

                videoList = videoListImpl?.getVideoList() ?: listOf()
                initRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun initRecyclerView() {
        binding?.rvVideoList?.layoutManager = LinearLayoutManager(mContext)
        binding?.rvVideoList?.adapter = VideoListAdapter(videoList, object :
            VideoListAdapter.OnClickListener {
            override fun onClick(videoID: String?) {
                val intent = VideoPlayerActivity.getIntent(mContext!!)
                intent.putExtra(VIDEO_ID, videoID ?: "")
                launchActivity(intent)
            }
        })
    }
}