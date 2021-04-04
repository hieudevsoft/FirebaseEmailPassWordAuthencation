package com.authencation.firebaseemailpasswordauthencation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.authencation.firebaseemailpasswordauthencation.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding
    lateinit var validateInputFields: ValidateInputFields
    lateinit var builder: AlertDialog.Builder
    lateinit var dialog: AlertDialog
    var mUser: FirebaseUser?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validateInputFields = ValidateInputFields.getInstance()
        builder = AlertDialog.Builder(this)
        mUser = FirebaseAuth.getInstance().currentUser
        binding.tvSignUp.setOnClickListener {
            Intent(this, SignUpActivity::class.java).run {
                startActivity(this)
            }
        }
        binding.btnSignIn.setOnClickListener {
            signInWithUser()
        }


    }

    private fun signInWithUser() {
        if (!validateInputFields.isValidEmail(binding.edtEmail.text.toString()))
            Toast.makeText(this, "Email invalid", Toast.LENGTH_LONG).show()
        if (!validateInputFields.isValidPassword(binding.edtPassword.text.toString()))
            Toast.makeText(this, "Password is too short", Toast.LENGTH_LONG).show()
        if(validateInputFields.isValidUser(binding.edtEmail.text.toString(),binding.edtPassword.text.toString()))
        {
            val firebaseAuth = FirebaseAuth.getInstance()
            loadingDialog()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        dialog.dismiss()
                        Intent(this,HomeScreen::class.java).also { startActivity(it) }
                    }else
                    {
                        Toast.makeText(this, "Fatal Error ${task.exception?.message.toString()}", Toast.LENGTH_SHORT).show()
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

    override fun onStart() {
        mUser = FirebaseAuth.getInstance().currentUser
        if(mUser!=null){
            Intent(this,HomeScreen::class.java).also { startActivity(it) }
        }else{
            Toast.makeText(this, "Please login to continue", Toast.LENGTH_SHORT).show()
        }
        super.onStart()
    }
}
