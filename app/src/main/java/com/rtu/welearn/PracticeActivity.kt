package com.rtu.welearn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rtu.welearn.databinding.ActivityPracticesBinding

class PracticeActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            var intent = Intent(mContext, PracticeActivity::class.java)
            return intent
        }
    }

    var binding: ActivityPracticesBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_practices)

        binding?.llToolOne?.setOnClickListener {
            if (binding?.expandableLayout1?.isExpanded == true) {
                binding?.expandableLayout1?.collapse()
            } else {
                binding?.expandableLayout1?.expand()
            }
        }
        binding?.llToolTwo?.setOnClickListener {
            if (binding?.expandableLayout2?.isExpanded == true) {
                binding?.expandableLayout2?.collapse()
            } else {
                binding?.expandableLayout2?.expand()
            }
        }
        binding?.llToolThree?.setOnClickListener {
            if (binding?.expandableLayout3?.isExpanded == true) {
                binding?.expandableLayout3?.collapse()
            } else {
                binding?.expandableLayout3?.expand()
            }
        }
        binding?.llToolFour?.setOnClickListener {
            if (binding?.expandableLayout4?.isExpanded == true) {
                binding?.expandableLayout4?.collapse()
            } else {
                binding?.expandableLayout4?.expand()
            }
        }
        binding?.llToolFive?.setOnClickListener {
            if (binding?.expandableLayout5?.isExpanded == true) {
                binding?.expandableLayout5?.collapse()
            } else {
                binding?.expandableLayout5?.expand()
            }
        }

        binding?.llToolSix?.setOnClickListener {
            if (binding?.expandableLayout6?.isExpanded == true) {
                binding?.expandableLayout6?.collapse()
            } else {
                binding?.expandableLayout6?.expand()
            }
        }
        binding?.llToolSeven?.setOnClickListener {
            if (binding?.expandableLayout7?.isExpanded == true) {
                binding?.expandableLayout7?.collapse()
            } else {
                binding?.expandableLayout7?.expand()
            }
        }
        binding?.llToolEight?.setOnClickListener {
            if (binding?.expandableLayout8?.isExpanded == true) {
                binding?.expandableLayout8?.collapse()
            } else {
                binding?.expandableLayout8?.expand()
            }
        }
        binding?.llToolNine?.setOnClickListener {
            if (binding?.expandableLayout9?.isExpanded == true) {
                binding?.expandableLayout9?.collapse()
            } else {
                binding?.expandableLayout9?.expand()
            }
        }
        binding?.llToolTen?.setOnClickListener {
//            if (binding?.expandableLayout10?.isExpanded == true) {
//                binding?.expandableLayout10?.collapse()
//            } else {
//                binding?.expandableLayout10?.expand()
//                binding?.expandableLayout10?.requestFocus()
//            }
            launchActivity(ToolDescriptionActivity.getIntent(this, 10))
        }

        binding?.tvToolDesc1?.setOnClickListener {
            launchActivity(ToolDescriptionActivity.getIntent(this, 1))
        }
        binding?.tvToolDesc2?.setOnClickListener {
            launchActivity(ToolDescriptionActivity.getIntent(this, 2))
        }
        binding?.tvToolDesc3?.setOnClickListener {
            launchActivity(ToolDescriptionActivity.getIntent(this, 3))
        }
        binding?.tvToolDesc4?.setOnClickListener {
            launchActivity(ToolDescriptionActivity.getIntent(this, 4))
        }
        binding?.tvToolDesc5?.setOnClickListener {
            launchActivity(ToolDescriptionActivity.getIntent(this, 5))
        }
        binding?.tvToolDesc6?.setOnClickListener {
            launchActivity(ToolDescriptionActivity.getIntent(this, 6))
        }
        binding?.tvToolDesc7?.setOnClickListener {
            launchActivity(ToolDescriptionActivity.getIntent(this, 7))
        }
        binding?.tvToolDesc8?.setOnClickListener {
            launchActivity(ToolDescriptionActivity.getIntent(this, 8))
        }
        binding?.tvToolDesc9?.setOnClickListener {
            launchActivity(ToolDescriptionActivity.getIntent(this, 9))
        }
        binding?.tvToolDesc10?.setOnClickListener {
            launchActivity(ToolDescriptionActivity.getIntent(this, 10))
        }

    }
}