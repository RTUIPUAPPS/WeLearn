package com.rtu.welearn

import android.app.Application
import com.google.firebase.database.*
import com.rtu.welearn.ui.video_list.VideoDetails
import com.rtu.welearn.utils.Constants
import com.rtu.welearn.utils.Constants.Companion.ID
import com.rtu.welearn.utils.Constants.Companion.VIDEO_ID
import com.rtu.welearn.utils.Constants.Companion.VIDEO_TITLE

class WeLearnApp : Application() {
    companion object {
        var mDatabase: DatabaseReference? = null
        private var mCloudEndPointVideoList: DatabaseReference? = null

        var VideoList = arrayListOf<VideoDetails>()
    }

    override fun onCreate() {
        super.onCreate()
        mDatabase = FirebaseDatabase.getInstance().reference

        mCloudEndPointVideoList = mDatabase?.child(Constants.VIDEO_LIST)
        mCloudEndPointVideoList?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    VideoList.add(
                        VideoDetails(
                            it.child(ID).value.toString().toInt(),
                            it.child(VIDEO_ID).value.toString(),
                            it.child(VIDEO_TITLE).value.toString()
                        )
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}