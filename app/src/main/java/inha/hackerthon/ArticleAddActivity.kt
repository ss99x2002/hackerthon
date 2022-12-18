package inha.hackerthon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import inha.hackerthon.databinding.ActivityArticleAddBinding
import inha.hackerthon.databinding.DialogConfirmBinding
import inha.hackerthon.dialog.CustomDialog

class ArticleAddActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding:ActivityArticleAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cancelButton -> {
               finish()
            }
            R.id.addButton -> {
                with(binding)
                {
                    if (contentEditText.text.isEmpty() || titleEditText.text.isEmpty()){
                        Toast.makeText(this@ArticleAddActivity, "모든 내용을 작성해주세요!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        showConfirmDialog()
                    }
                }
            }
        }
    }

    fun showConfirmDialog(){
        Log.e("summer","save dialog true")
        with(binding) {
            DataBindingUtil.inflate<DialogConfirmBinding>(
                LayoutInflater.from(this@ArticleAddActivity),
                R.layout.dialog_confirm,
                null,
                false
            ).apply {
                this.dialog = CustomDialog(this@ArticleAddActivity, root, "게시물을 올리시겠습니까?").apply {
                    this.setClickListener(object : CustomDialog.DialogClickListener {
                        override fun onConfirm() {
                            Log.e("summer", "save dialog onConfirm 로 들어옴")
                            // 여기에 레트로핏 post 하는 부분 넣으면 될듯.
                        }

                        override fun onClose() {
                            Log.e("summer","onClose 들어옴")
                        }
                    })
                    show()
            }
        }
    }
    }
}