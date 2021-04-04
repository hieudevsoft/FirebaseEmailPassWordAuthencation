package com.authencation.firebaseemailpasswordauthencation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.authencation.firebaseemailpasswordauthencation.databinding.ActivityUpdatePasswordBinding
import com.google.firebase.auth.FirebaseAuth

class UpdatePassword : AppCompatActivity() {
    lateinit var _binding: ActivityUpdatePasswordBinding
    private val binding get() = _binding!!
    private var mUser = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUpdatePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imgBack.setOnClickListener { finish() }
        binding.btnUpdatePassword.setOnClickListener {
            if(!ValidateInputFields.getInstance().isValidConfirmPassword(binding.edtNewPassword.text.toString(),binding.edtConfirmPassword.text.toString())){
                Toast.makeText(this, "Password invalid", Toast.LENGTH_SHORT).show()
            }
            else
            {
                mUser.updateEmail(binding.edtNewPassword.text.toString()).addOnCompleteListener {
                        task->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "Password Update Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else Toast.makeText(this, "Password Update Failure", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}