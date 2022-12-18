package inha.hackerthon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import inha.hackerthon.data.my_lecture
import inha.hackerthon.databinding.ItemMyLectureBinding

class MyLectureRVAdapter(private val lectureList: MutableList<String>) :RecyclerView.Adapter<MyLectureRVAdapter.MyLectureViewHolder>() {
    inner class MyLectureViewHolder(val binding:ItemMyLectureBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(lecture:String){
            with(binding)
            {
                lectureTextView.text = lecture
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLectureViewHolder {
        val binding = ItemMyLectureBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyLectureViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyLectureViewHolder, position: Int) {
        holder.bind(lectureList[position])
    }
    override fun getItemCount(): Int {
       return lectureList.size
    }
}