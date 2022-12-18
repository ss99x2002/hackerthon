package inha.hackerthon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import inha.hackerthon.databinding.ActivityArticleAddBinding
import inha.hackerthon.databinding.DialogConfirmBinding
import inha.hackerthon.dialog.CustomDialog
import inha.hackerthon.home.ArticleModel

class ArticleAddActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityArticleAddBinding
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DBKey.DB_ARTICLES)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun uploadArticle(
        hostId: String,
        who: String,
        subject: String,
        title: String,
        content: String
    ) {
        val model = ArticleModel(hostId, who, subject, title, content, System.currentTimeMillis())
        articleDB.push().setValue(model)
        hideProgress()
        finish()
    }

    private fun showProgress() {
        binding.progressBar.isVisible = true
    }

    private fun hideProgress() {
        binding.progressBar.isVisible = false
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cancelButton -> {
                finish()
            }
            R.id.addButton -> {
                with(binding)
                {
                    if (contentEditText.text.isEmpty() || titleEditText.text.isEmpty()) {
                        Toast.makeText(
                            this@ArticleAddActivity,
                            "모든 내용을 작성해주세요!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {

                        showConfirmDialog()
                    }
                }
            }
        }
    }

    fun showConfirmDialog() {
        Log.e("summer", "save dialog true")
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
                            val title = binding.titleEditText.text.toString()
                            val content = binding.contentEditText.text.toString()
                            val hostId = auth.currentUser?.uid.orEmpty()
                            //who랑 과목명 넣어줘야함
                            showProgress()
                            uploadArticle(hostId, who = "후배" , subject = "데이터베이스",title, content)
                        }

                        override fun onClose() {
                            Log.e("summer", "onClose 들어옴")
                        }
                    })
                    show()
                }
            }
        }
    }
}