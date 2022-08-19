package com.rtu.welearn.utils

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.hitesh.weatherlogger.view.callback.ItemClickListener
import com.rtu.welearn.R
import com.rtu.welearn.databinding.DialogMessageBinding

fun showMessageDialog(context: Context, title: String,message: String="", listener: ItemClickListener): Dialog? {

    val inflator = LayoutInflater.from(context)
    val binding: DialogMessageBinding =
        DataBindingUtil.inflate(inflator, R.layout.dialog_message, null, false)

    val mDialog: Dialog? = Dialog(context)
    mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
    mDialog?.setContentView(binding.root)
    mDialog?.setCanceledOnTouchOutside(true)
    mDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

    binding.tvTitle.text = title
    binding.tvMessage.text = Html.fromHtml("<i>\"$message\"</i>", Html.FROM_HTML_MODE_LEGACY)


    binding.tvDismiss.setOnClickListener {
        listener.onClick(true)
        mDialog?.dismiss()
    }


    mDialog?.show()
    return mDialog
}
