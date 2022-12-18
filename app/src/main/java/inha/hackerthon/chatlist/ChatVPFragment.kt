package inha.hackerthon.chatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import inha.hackerthon.R
import inha.hackerthon.databinding.FragmentChatvpBinding
import inha.hackerthon.databinding.FragmentMypageBinding

class ChatVPFragment: Fragment() {
    lateinit var binding: FragmentChatvpBinding
    private val information = arrayListOf("후배","동기","선배")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatvpBinding.inflate(inflater, container, false)
        val chatVPAdapter = ChatVPAdapter(this)
        binding.chatViewPager.adapter = chatVPAdapter
        TabLayoutMediator(binding.chatTabLayout, binding.chatViewPager){ tab, position ->
            tab.text = information[position]
        }.attach()
        return binding.root
    }

}