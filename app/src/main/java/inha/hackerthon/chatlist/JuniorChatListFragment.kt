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
import inha.hackerthon.databinding.FragmentChatlistBinding
import inha.hackerthon.databinding.FragmentJuniorChatListBinding

class JuniorChatListFragment: Fragment(R.layout.fragment_junior_chat_list) {
    private var binding: FragmentJuniorChatListBinding? = null
    private lateinit var chatListAdapter: JuniorChatListAdapter
    private val chatRoomList = mutableListOf<ChatList>()
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentChatlistBinding = FragmentJuniorChatListBinding.bind(view)
        binding = fragmentChatlistBinding

        chatListAdapter = JuniorChatListAdapter( onItemClicked = { chatRoom ->
            //채팅방으로 이동하는 코드
            context?.let {
                val intent = Intent(it, ChatRoomActivity::class.java)
                intent.putExtra("chatKey", chatRoom.key)
                startActivity(intent)
            }
        })

        chatRoomList.clear()

        fragmentChatlistBinding.JuniorChatListRecyclerView.adapter = chatListAdapter
        fragmentChatlistBinding.JuniorChatListRecyclerView.layoutManager = LinearLayoutManager(context)

        val chatDB =
            Firebase
                .database.reference
                .child(DBKey.DB_USERS)
                .child(auth.currentUser!!.uid)
                .child(DBKey.CHILD_CHAT)
                .child(DBKey.DB_SENIOR)

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