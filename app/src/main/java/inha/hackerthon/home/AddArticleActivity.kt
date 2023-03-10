//package inha.hackerthon.home
//
//import android.app.Activity
//import android.app.AlertDialog
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Toast
//import androidx.core.content.ContextCompat
//import androidx.core.view.isVisible
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.ktx.storage
//import inha.hackerthon.DBKey.Companion.DB_ARTICLES
//import inha.hackerthon.R
//import inha.hackerthon.databinding.ActivityAddArticleBinding
//import inha.hackerthon.databinding.ActivityMainBinding
//import kotlinx.coroutines.selects.select
//
//class AddArticleActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityAddArticleBinding
//    private var selectedUri: Uri? = null
//    private val auth: FirebaseAuth by lazy {
//        Firebase.auth
//    }
//    private val storage: FirebaseStorage by lazy {
//        Firebase.storage
//    }
//    private val articleDB: DatabaseReference by lazy {
//        Firebase.database.reference.child(DB_ARTICLES)
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityAddArticleBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.imageAddButton.setOnClickListener{
//            when {
//                ContextCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE
//                ) == PackageManager.PERMISSION_GRANTED -> {
//                    startContentProvider()
//                }
//                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
//                    showPermissionContextPopup()
//                }
//                else -> {
//                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
//                }
//            }
//        }
//        binding.submitButton.setOnClickListener{
//            val title = binding.titleEditText.text.toString()
//            val price = binding.priceEditText.text.toString()
//            val sellerId = auth.currentUser?.uid.orEmpty()
//            showProgress()
//            if (selectedUri != null){
//                val photoUri = selectedUri ?: return@setOnClickListener
//                uploadPhoto(photoUri,
//                    successHandler = { uri ->
//                        uploadArticle(sellerId ,title, price, uri)
//                    },
//                    errorHandler = {
//                        Toast.makeText(this, "?????? ???????????? ??????????????????.", Toast.LENGTH_SHORT).show()
//                        hideProgress()
//                    }
//                )
//            }else{
//                uploadArticle(sellerId, title, price, "")
//            }
//        }
//    }
//
//    private fun uploadPhoto(uri: Uri, successHandler: (String) -> Unit, errorHandler: () -> Unit) {
//        val fileName = "${System.currentTimeMillis()}.png"
//        storage.reference.child("article/photo").child(fileName)
//            .putFile(uri)
//            .addOnCompleteListener {
//                if(it.isSuccessful) {
//                    storage.reference.child("article/photo").child(fileName)
//                        .downloadUrl
//                        .addOnSuccessListener { uri ->
//                            successHandler(uri.toString())
//                        }
//                        .addOnFailureListener {
//                            errorHandler()
//                        }
//                }else{
//                    errorHandler()
//                }
//            }
//    }
//
////    private fun uploadArticle(userId: String, who: String, subject:String,  title: String, content: String, createdAt: Long){
////        val model = ArticleModel(userId, who,subject, title, content, createdAt)
////        articleDB.push().setValue(model)
////        hideProgress()
////        finish()
////    }
//
//    private fun uploadArticle(sellerId: String, title: String, price:String,  imageUri: String){
//        val model = ArticleModel(sellerId, title, System.currentTimeMillis(), "$price ???")
//        articleDB.push().setValue(model)
//        hideProgress()
//        finish()
//    }
//
//    private fun showProgress() {
//        binding.progressBar.isVisible = true
//    }
//    private fun hideProgress() {
//        binding.progressBar.isVisible = false
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when(requestCode) {
//            1000 ->
//                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    startContentProvider()
//                }else{
//                    Toast.makeText(this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
//                }
//        }
//    }
//
//    private fun startContentProvider() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "image/*"
//        startActivityForResult(intent, 2000)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(resultCode != Activity.RESULT_OK){
//            return
//        }
//        when(requestCode) {
//            2000 -> {
//                val uri = data?.data
//                if(uri != null) {
//                    binding.photoImageView.setImageURI(uri)
//                    selectedUri = uri
//                }else{
//                    Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
//                }
//            }else -> {
//                Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun showPermissionContextPopup() {
//        AlertDialog.Builder(this)
//            .setTitle("????????? ???????????????.")
//            .setMessage("????????? ???????????? ?????? ???????????????.")
//            .setPositiveButton("??????", { _, _ ->
//                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),2000)
//            })
//            .create()
//            .show()
//    }
//}