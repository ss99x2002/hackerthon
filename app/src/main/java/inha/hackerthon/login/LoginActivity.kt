package inha.hackerthon.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import inha.hackerthon.MainActivity
import inha.hackerthon.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.signInOutButton.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
        binding.signInOutButton.setOnClickListener {
            binding.let { binding ->
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                if (auth.currentUser == null) {
                    //로그인
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if(task.isSuccessful){
                                successSignIn()
                            }else{
                                Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    auth.signOut()
                    binding.emailEditText.text.clear()
                    binding.emailEditText.isEnabled = true
                    binding.passwordEditText.text.clear()
                    binding.passwordEditText.isEnabled = true

                    binding.signInOutButton.text = "로그인"
                    binding.signInOutButton.isEnabled = false
                    binding.signUpButton.isEnabled = false
                }
            }
        }
        binding.signUpButton.setOnClickListener {
            binding.let { binding ->
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful){
                            binding.emailEditText.text.clear()
                            binding.passwordEditText.text.clear()
                            Toast.makeText(this, "회원가입에 성공했습니다.",Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "회원가입에 실패했습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        binding.emailEditText.addTextChangedListener {
            binding.let { binding ->
                val enable = binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
                binding.signInOutButton.isEnabled = enable
                binding.signUpButton.isEnabled = enable
            }
        }
        binding.passwordEditText.addTextChangedListener {
            binding.let { binding ->
                val enable = binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()
                binding.signInOutButton.isEnabled = enable
                binding.signUpButton.isEnabled = enable
            }
        }
    }

    private fun successSignIn() {
        if(auth.currentUser == null) {
            Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            return
        }
//        binding.emailEditText.isEnabled = false
//        binding.passwordEditText.isEnabled = false
//        binding.signUpButton.isEnabled = false
//        binding.signInOutButton.text = "로그아웃"
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}