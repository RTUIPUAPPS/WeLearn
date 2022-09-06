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
import com.rtu.welearn.WeLearnApp.Companion.dbVersionData
import com.rtu.welearn.WeLearnApp.Companion.roomDB
import com.rtu.welearn.WeLearnApp.Companion.videoListVersionFirebase
import com.rtu.welearn.data.room.videolist.VideoListData
import com.rtu.welearn.databinding.ActivityVideoListBinding
import com.rtu.welearn.utils.Constants
import com.rtu.welearn.utils.Constants.Companion.VIDEO_ID
import kotlinx.coroutines.launch

class VideoListActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            val intent = Intent(mContext, VideoListActivity::class.java)
            return intent
        }
    }

    private var binding: ActivityVideoListBinding? = null
    private var videoList = listOf<VideoListData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_list)


        if (dbVersionData.version_video == videoListVersionFirebase) {
            getVideoListFromLocalStorage()

        } else {
            getVideoListFromFirebase()
        }
    }

    private fun getVideoListFromLocalStorage() {
        lifecycleScope.launch {
            roomDB.VideoListDao().getVideoList().collect {
                videoList = it
                initRecyclerView()
            }
        }
    }

    private fun getVideoListFromFirebase() {

        val mCloudEndPointVideoList: DatabaseReference? =
            WeLearnApp.mDatabase?.child(Constants.VIDEO_LIST)
        mCloudEndPointVideoList?.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lifecycleScope.launch {
                    roomDB.VideoListDao().deleteVideoList()
                }

                snapshot.children.forEach {
                    lifecycleScope.launch {
                        roomDB.VideoListDao().insertVideoData(
                            VideoListData(
                                null,
                                it.child(VIDEO_ID).value.toString(),
                                it.child(Constants.VIDEO_TITLE).value.toString(),
                                it.child(Constants.VIDEO_DESCRIPTION).value.toString()
                            )
                        )

                    }
                }

                lifecycleScope.launch {
                    dbVersionData.version_video = videoListVersionFirebase
                    roomDB.dbVersionDao().updateVersion(dbVersionData)
                }
                getVideoListFromLocalStorage()
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