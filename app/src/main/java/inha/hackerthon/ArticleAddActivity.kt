package inha.hackerthon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
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
    private var whoName:String =""
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DBKey.DB_ARTICLES)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleAddBinding.inflate(layoutInflater)
        createSpinner()
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

    private fun createSpinner() {
        binding.lectureSpinner.adapter = ArrayAdapter.createFromResource(this,R.array.lecture,android.R.layout.simple_spinner_item,)
    }

    private fun selectedBtn(item:String)
    {
        whoName = item
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
                            "?????? ????????? ??????????????????!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        showConfirmDialog()
                    }
                }
            }
            R.id.sameButton-> {
                binding.sameButton.background=resources.getDrawable(R.drawable.view_same)
                binding.youngButton.background= resources.getDrawable(R.drawable.view_not_selected)
                binding.oldButton.background= resources.getDrawable(R.drawable.view_not_selected)
                selectedBtn("??????")
            }
            R.id.youngButton-> {
                binding.youngButton.background=resources.getDrawable(R.drawable.view_young)
                binding.sameButton.background= resources.getDrawable(R.drawable.view_not_selected)
                binding.oldButton.background= resources.getDrawable(R.drawable.view_not_selected)
                selectedBtn("??????")
            }
            R.id.oldButton-> {
                binding.oldButton.background=resources.getDrawable(R.drawable.view_old)
                binding.youngButton.background= resources.getDrawable(R.drawable.view_not_selected)
                binding.sameButton.background= resources.getDrawable(R.drawable.view_not_selected)
                selectedBtn("??????")
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
                this.dialog = CustomDialog(this@ArticleAddActivity, root, "???????????? ??????????????????????").apply {
                    this.setClickListener(object : CustomDialog.DialogClickListener {
                        override fun onConfirm() {
                            Log.e("summer", "save dialog onConfirm ??? ?????????")
                            // ????????? ???????????? post ?????? ?????? ????????? ??????.
                            val title = binding.titleEditText.text.toString()
                            val content = binding.contentEditText.text.toString()
                            val hostId = auth.currentUser?.uid.orEmpty()
                            var subject = ""
                            if (lectureSpinner.selectedItemPosition!=0)
                            {
                                subject = lectureSpinner.selectedItem.toString()
                            }


                            val who = "${whoName}??? ????????????"

                            //who??? ????????? ???????????????
                            showProgress()
                            uploadArticle(hostId, who , subject,title, content)

                        }

                        override fun onClose() {
                            Log.e("summer", "onClose ?????????")
                        }
                    })
                    show()
                }
            }
        }
    }
}