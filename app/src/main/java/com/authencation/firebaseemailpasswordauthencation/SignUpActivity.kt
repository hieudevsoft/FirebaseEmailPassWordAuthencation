package com.authencation.firebaseemailpasswordauthencation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.authencation.firebaseemailpasswordauthencation.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySignUpBinding
    private val binding get() = _binding
    lateinit var validateInputFields: ValidateInputFields
    lateinit var builder:AlertDialog.Builder
    lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validateInputFields = ValidateInputFields.getInstance()
        builder = AlertDialog.Builder(this)
        binding.imgBack.setOnClickListener { finish() }
        binding.btnSignUp.setOnClickListener {
            signUpNewAccount()
        }
        binding.tvSignIn.setOnClickListener {
            Intent(this, MainActivity::class.java).also { startActivity(it) }
        }
    }

    private fun signUpNewAccount() {
        if (!validateInputFields.isValidEmail(binding.edtEmail.text.toString()))
            Toast.makeText(this, "Email invalid", Toast.LENGTH_LONG).show()
        if (!validateInputFields.isValidPassword(binding.edtPassword.text.toString()))
            Toast.makeText(this, "Password is too short", Toast.LENGTH_LONG).show()
        else {
            if (!validateInputFields.isValidConfirmPassword(
                    binding.edtConfirmPassword.text.toString(),
                    binding.edtPassword.text.toString()
                )
            )
                Toast.makeText(this, "Repeat password incorrect", Toast.LENGTH_SHORT).show()
        }
        if (validateInputFields.isValidUserRegister(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString(),
                binding.edtConfirmPassword.text.toString()
            )
        ){
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val firebase = FirebaseAuth.getInstance()
            loadingDialog()
            firebase.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    task->
                    if(task.isSuccessful) {
                        Intent(this, HomeScreen::class.java).also { startActivity(it) }
                        finish()
                    }
                    else {
                        Toast.makeText(this, "Fatal Error", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
        }
    }
    private fun loadingDialog(){
        builder.setView(layoutInflater.inflate(R.layout.activity_loading_layout,null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }
}