package inha.hackerthon.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import inha.hackerthon.ArticleAddActivity
import inha.hackerthon.DBKey.Companion.CHILD_CHAT
import inha.hackerthon.DBKey.Companion.DB_ARTICLES
import inha.hackerthon.DBKey.Companion.DB_USERS
import inha.hackerthon.R
import inha.hackerthon.chatlist.ChatList
import inha.hackerthon.databinding.FragmentHomeBinding

class HomeFragment: Fragment(R.layout.fragment_home) {

    private lateinit var articleDB: DatabaseReference
    private lateinit var userDB: DatabaseReference
    private var binding: FragmentHomeBinding? = null
    private lateinit var articleAdapter: ArticleAdapter
    private val articleList = mutableListOf<ArticleModel>()
    //상품 목록들을 서버에서 받아올거임

    private lateinit var getResultText: ActivityResultLauncher<Intent>

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return
            articleList.add(articleModel)
            articleAdapter.submitList(articleList)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        }
        override fun onChildRemoved(snapshot: DataSnapshot) {
        }
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }
        override fun onCancelled(error: DatabaseError) {
        }
    }


    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)

        binding = fragmentHomeBinding
        articleList.clear()
        articleDB = Firebase.database.reference.child(DB_ARTICLES)
        userDB = Firebase.database.reference.child(DB_USERS)
        articleAdapter = ArticleAdapter(onItemClicked = { articleModel ->
            if(auth.currentUser != null){
                //로그인을 한 상태
                if (auth.currentUser!!.uid != articleModel.hostId) {
                    val chatRoom = ChatList(
                        hostId = auth.currentUser!!.uid,
                        guestId = articleModel.hostId,
                        content = articleModel.title,
                        key = System.currentTimeMillis()
                    )
                    userDB.child(auth.currentUser!!.uid)
                        .child(CHILD_CHAT)
                        .push()
                        .setValue(chatRoom)

                    userDB.child(articleModel.hostId)
                        .child(CHILD_CHAT)
                        .push()
                        .setValue(chatRoom)

                    Snackbar.make(view, "채팅방이 생성되었습니다.", Snackbar.LENGTH_LONG).show()
                }
            }else{
                //로그인을 안 한 상태
                Snackbar.make(view, "로그인 후 사용해주세요.", Snackbar.LENGTH_LONG).show()
            }

        })

        fragmentHomeBinding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        fragmentHomeBinding.articleRecyclerView.adapter = articleAdapter

        fragmentHomeBinding.addFloatingButton.setOnClickListener {
            context?.let {
                if(auth.currentUser != null) {
                    val intent = Intent(it, ArticleAddActivity::class.java)
                    startActivity(intent)
                }else{
                    Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        articleDB.addChildEventListener(listener)
    }

    override fun onResume() {
        super.onResume()
        articleAdapter.notifyDataSetChanged()
    }
    override fun onDestroy() {
        super.onDestroy()
        articleDB.removeEventListener(listener)
    }
}