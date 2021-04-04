package com.authencation.firebaseemailpasswordauthencation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.authencation.firebaseemailpasswordauthencation.databinding.ActivityHomeScreenBinding
import com.google.firebase.auth.FirebaseAuth

class HomeScreen : AppCompatActivity() {
    lateinit var _binding:ActivityHomeScreenBinding
    private var mUser = FirebaseAuth.getInstance().currentUser
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(mUser!=null){
            binding.tvEmail.text = mUser.email
            binding.tvId.text = mUser.uid
            checkAccountVerification()
        }
        binding.btnChangeEmail.setOnClickListener {
            Intent(this,UpdateEmail::class.java).run {
                startActivity(this)
            }
        }
        binding.btnChangePassword.setOnClickListener {
            Intent(this,UpdatePassword::class.java).run {
                startActivity(this)
            }
        }
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
            Toast.makeText(this, "Please log in to continue", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAccountVerification() {
        mUser = FirebaseAuth.getInstance().currentUser
        Toast.makeText(this, mUser.isEmailVerified.toString(), Toast.LENGTH_SHORT).show()
        if(mUser!=null){
            if(mUser.isEmailVerified)
            {
                binding.tvVerifiedEmail.text = "Account Verified"
                binding.tvVerifiedEmail.setTextColor(getColor(R.color.green))
            }
            else{
                binding.tvVerifiedEmail.text = "Account Not Verified"
                binding.tvVerifiedEmail.setTextColor(getColor(R.color.red))
            }
        }
    }

    override fun onBackPressed() {
        val buider = AlertDialog.Builder(this)
        buider.setTitle("Log out ?")
        buider.setMessage("Are you sure you want to Logout?")
        val dialog = buider.create()
        dialog.setButton(AlertDialog.BUTTON_POSITIVE,"Yes"){
            _,_->
            FirebaseAuth.getInstance().signOut()
            finish()
            Toast.makeText(this, "Please log in to continue", Toast.LENGTH_SHORT).show()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE,"No"){
                _,_->
            dialog.dismiss()
        }
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.black))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.black))
        }
        dialog.show()
    }

    override fun onResume() {
        mUser = FirebaseAuth.getInstance().currentUser
        if(mUser!=null){
            binding.tvEmail.text = mUser.email
            binding.tvId.text = mUser.uid
            checkAccountVerification()
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
        }
        super.onResume()
    }
}