package inha.hackerthon.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import inha.hackerthon.DBKey
import inha.hackerthon.DBKey.Companion.DB_PROFILE
import inha.hackerthon.R
import inha.hackerthon.adapter.MyLectureRVAdapter
import inha.hackerthon.databinding.FragmentMypageBinding

class MyPageFragment :Fragment(),View.OnClickListener {
    private lateinit var binding:FragmentMypageBinding
    private var dataList : MutableList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(layoutInflater)

        if (dataList.isEmpty())
        {
            initData()
        }
        createSpinner()
        val lectureRVAdapter = MyLectureRVAdapter(dataList)
        with(binding)
        {
            lectureRV.layoutManager = LinearLayoutManager(context)
            lectureRV.adapter = lectureRVAdapter
            lectureAddButton.setOnClickListener {
                if(lectureSpinner.selectedItemPosition!=0)
                {
                    if (!dataList.contains(lectureSpinner.selectedItem))
                    {
                        dataList.add(lectureSpinner.selectedItem.toString())
                        lectureRVAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        return binding.root
    }

    fun createRV() {
//        val lectureRVAdapter = MyLectureRVAdapter(dataList)
//        with(binding)
//        {
//            lectureRV.layoutManager = LinearLayoutManager(context)
//            lectureRV.adapter = lectureRVAdapter
//        }
    }

    fun createSpinner() {
        binding.lectureSpinner.adapter = ArrayAdapter.createFromResource(requireContext(),R.array.lecture,android.R.layout.simple_spinner_item,)
    }

    fun initData(){
        binding.lectureSpinner.setSelection(0)
        dataList.apply{
            add("????????? ???????????????")
            add("???????????????")
            add("????????????")
            add("??????????????????")
        }
    }

    override fun onClick(w: View?) {
        when(w?.id)
        {
            R.id.addButton-> {
                // ?????? ??? ????????? retrofit ????????????
            }
        }
    }
}



//class MyPageFragment: Fragment(R.layout.fragment_mypage) {
//    private var binding: FragmentMypageBinding? = null
//    private val auth: FirebaseAuth by lazy {
//        Firebase.auth
//    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val fragmentMypageBinding = FragmentMypageBinding.bind(view)
//        binding = fragmentMypageBinding
//
//        fragmentMypageBinding.signInOutButton.setOnClickListener {
//            binding?.let { binding ->
//                val email = binding.emailEditText.text.toString()
//                val password = binding.passwordEditText.text.toString()
//                if (auth.currentUser == null) {
//                    //?????????
//                    auth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(requireActivity()) { task ->
//                            if(task.isSuccessful){
//                                successSignIn()
//                            }else{
//                                Toast.makeText(context, "???????????? ??????????????????.", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                }else{
//                    auth.signOut()
//                    binding.emailEditText.text.clear()
//                    binding.emailEditText.isEnabled = true
//                    binding.passwordEditText.text.clear()
//                    binding.passwordEditText.isEnabled = true
//
//                    binding.signInOutButton.text = "?????????"
//                    binding.signInOutButton.isEnabled = false
//                    binding.signUpButton.isEnabled = false
//                }
//            }
//        }
//        fragmentMypageBinding.signUpButton.setOnClickListener {
//            binding?.let { binding ->
//                val email = binding.emailEditText.text.toString()
//                val password = binding.passwordEditText.text.toString()
//                auth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(requireActivity()) { task ->
//                        if(task.isSuccessful){
//                            binding.emailEditText.text.clear()
//                            binding.passwordEditText.text.clear()
//                            Toast.makeText(context, "??????????????? ??????????????????.",Toast.LENGTH_SHORT).show()
//                        }else{
//                            Toast.makeText(context, "??????????????? ??????????????????.",Toast.LENGTH_SHORT).show()
//                        }
//                    }
//            }
//        }
//        fragmentMypageBinding.emailEditText.addTextChangedListener {
//            binding?.let { binding ->
//                val enable = binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
//                binding.signInOutButton.isEnabled = enable
//                binding.signUpButton.isEnabled = enable
//            }
//        }
//        fragmentMypageBinding.passwordEditText.addTextChangedListener {
//            binding?.let { binding ->
//                val enable = binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
//                binding.signInOutButton.isEnabled = enable
//                binding.signUpButton.isEnabled = enable
//            }
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if(auth.currentUser == null) {
//            //???????????? ??????????????? ??????
//            binding?.let { binding ->
//                binding.emailEditText.text.clear()
//                binding.emailEditText.isEnabled = true
//                binding.passwordEditText.text.clear()
//                binding.passwordEditText.isEnabled = true
//
//                binding.signInOutButton.text = "?????????"
//                binding.signInOutButton.isEnabled = false
//                binding.signUpButton.isEnabled = false
//            }
//        }else{
//            binding?.let { binding ->
//                binding.emailEditText.setText(auth.currentUser?.email)
//                binding.emailEditText.isEnabled = false
//                binding.passwordEditText.setText("**********")
//                binding.passwordEditText.isEnabled =false
//
//                binding.signInOutButton.text = "????????????"
//                binding.signInOutButton.isEnabled = true
//                binding.signUpButton.isEnabled = false
//            }
//        }
//    }
//
//    private fun successSignIn() {
//        if(auth.currentUser == null) {
//            Toast.makeText(context, "???????????? ??????????????????.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        binding?.emailEditText?.isEnabled = false
//        binding?.passwordEditText?.isEnabled = false
//        binding?.signUpButton?.isEnabled = false
//        binding?.signInOutButton?.text = "????????????"
//    }
//}