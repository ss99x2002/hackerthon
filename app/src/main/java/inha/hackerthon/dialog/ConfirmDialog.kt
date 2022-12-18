package inha.hackerthon.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import inha.hackerthon.R

class CustomDialog(context: Context, view: View, text:String): Dialog(context) {
    private lateinit var listener: DialogClickListener
    private var isClickConfirm:Boolean = false

    init{
        setCanceledOnTouchOutside(true)
        setContentView(view)
        findViewById<TextView>(R.id.tv_dialog_title).text = text
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))//바깥 투명하게인듯
        window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }
    interface DialogClickListener{
        fun onClose()
        fun onConfirm()
    }

    override fun onStart() {
        super.onStart()
    }
    fun setClickListener(listener_ : DialogClickListener)
    {
        this.listener = listener_
    }

    fun onClose()
    {
        Log.e("dialog 함수","onClose 들어옴")
        dismiss()
        listener?.onClose()
    }

    fun onConfirm()
    {
        Log.e("dialog 함수","onConfirm 들어옴")
        dismiss()
        listener?.onConfirm()
    }
}