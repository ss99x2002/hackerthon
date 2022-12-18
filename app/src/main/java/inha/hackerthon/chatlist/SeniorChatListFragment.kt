package inha.hackerthon.chatlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import inha.hackerthon.DBKey
import inha.hackerthon.R
import inha.hackerthon.chatdetail.ChatRoomActivity
import inha.hackerthon.databinding.FragmentJuniorChatListBinding
import inha.hackerthon.databinding.FragmentSeniorChatlistBinding

class SeniorChatListFragment: Fragment(R.layout.fragment_senior_chatlist) {
    private var binding: FragmentSeniorChatlistBinding? = null
    private lateinit var chatListAdapter: SeniorChatListAdapter
    private val chatRoomList = mutableListOf<ChatList>()
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentChatlistBinding = FragmentSeniorChatlistBinding.bind(view)
        binding = fragmentChatlistBinding

        chatListAdapter = SeniorChatListAdapter( onItemClicked = { chatRoom ->
            //채팅방으로 이동하는 코드
            context?.let {
                val intent = Intent(it, ChatRoomActivity::class.java)
                intent.putExtra("chatKey", chatRoom.key)
                startActivity(intent)
            }
        })

        chatRoomList.clear()

        fragmentChatlistBinding.SeniorChatListRecyclerView.adapter = chatListAdapter
        fragmentChatlistBinding.SeniorChatListRecyclerView.layoutManager = LinearLayoutManager(context)

        val chatDB = Firebase.database.reference.child(DBKey.DB_USERS).child(auth.currentUser!!.uid).child(DBKey.CHILD_CHAT).child(DBKey.DB_JUNIOR)

        chatDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val model = it.getValue(ChatList::class.java)
                    model ?: return
                    chatRoomList.add(model)
                }
                chatListAdapter.submitList(chatRoomList)
                chatListAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        if(auth.currentUser == null)
        {
            return
        }
    }
}