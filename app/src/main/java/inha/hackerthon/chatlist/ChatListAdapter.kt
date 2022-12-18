package inha.hackerthon.chatlist


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import inha.hackerthon.databinding.ItemArticleBinding
import inha.hackerthon.databinding.ItemChatListBinding
import java.text.SimpleDateFormat
import java.util.Date

class ChatListAdapter(val onItemClicked: (ChatList) -> Unit): ListAdapter<ChatList, ChatListAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemChatListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(chatList: ChatList) {
            binding.root.setOnClickListener{
                onItemClicked(chatList)
            }
            //binding.chatRoomTitleTextView.text = chatList.itemTitle
            binding.hostTextView.text = "동동"
            binding.recentChatTextView.text = chatList.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatList>() {
            override fun areItemsTheSame(oldItem: ChatList, newItem: ChatList): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: ChatList, newItem: ChatList): Boolean {
                return oldItem == newItem
            }

        }
    }
}