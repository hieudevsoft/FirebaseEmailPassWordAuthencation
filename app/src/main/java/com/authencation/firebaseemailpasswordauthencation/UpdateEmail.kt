package com.authencation.firebaseemailpasswordauthencation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.authencation.firebaseemailpasswordauthencation.databinding.ActivityUpdateEmailBinding
import com.google.firebase.auth.FirebaseAuth

class UpdateEmail : AppCompatActivity() {
    lateinit var _binding:ActivityUpdateEmailBinding
    private var mUser = FirebaseAuth.getInstance().currentUser
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUpdateEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCurrentEmail()
        binding.imgBack.setOnClickListener { finish() }
        binding.btnUpdateEmail.setOnClickListener {
            if(!ValidateInputFields.getInstance().isValidEmail(binding.edtNewEmail.text.toString())){
                Toast.makeText(this, "Email invalid", Toast.LENGTH_SHORT).show()
            }
            else
            {
                mUser.updateEmail(binding.edtNewEmail.text.toString()).addOnCompleteListener {
                    task->
                    if(task.isSuccessful) {
                        setCurrentEmail()
                        Toast.makeText(this, "Email Update Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else Toast.makeText(this, "Email Update Failure", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.tvVerifiedEmail.setOnClickListener {
            sendEmailVerification()
        }
    }

    private fun sendEmailVerification() {
        if(mUser.isEmailVerified){
            binding.tvVerifiedEmail.visibility = View.GONE
        }else{
            binding.tvVerifiedEmail.visibility = View.VISIBLE
            mUser.sendEmailVerification().addOnCompleteListener {
                task->
                if(task.isSuccessful) Toast.makeText(this, "Already send Email verification", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "Send Email verification Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setCurrentEmail() {
        mUser = FirebaseAuth.getInstance().currentUser
        if (mUser!=null){
            binding.edtEmail.setText(mUser.email)
            binding.edtEmail.isEnabled = false
        }
        else{
            Toast.makeText(this, "Login to continue", Toast.LENGTH_SHORT).show()
        }
    }
}