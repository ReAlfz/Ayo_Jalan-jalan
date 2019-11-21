package com.realfz.myta.loginRegister

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.realfz.myta.MainActivity
import com.realfz.myta.R
import com.realfz.myta.model.UserModel
import kotlinx.android.synthetic.main.activity_log_in.*

class Log_in : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val sign = findViewById<TextView>(R.id.sign_up)
        val log = findViewById<Button>(R.id.log)

        sign.setOnClickListener {
            val signin = Intent(this, Sign_Up::class.java)
            startActivity(signin)
        }


        log.setOnClickListener {
            if (emails.text.isEmpty() || passw.text.isEmpty()) {
                emails.error = "isi email anda"
                passw.error = "isi password anda"
                Toast.makeText(this, "Please insert your emails and password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emails.text.toString(), passw.text.toString())
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            return@addOnCompleteListener
                        } else {
                            Toast.makeText(this, "Selamat datang", Toast.LENGTH_SHORT).show()

                            val started = Intent(this, MainActivity::class.java)
                            startActivity(started)
                            finish()
                        }
                    }
                    .addOnFailureListener {
                        Log.d("Main", "Failed to Login : ${it.message}")
                        Toast.makeText(this, "You already have Account ?", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }
        fetchUser()
    }

    override fun onStart() {
        super.onStart()
//
//        val curent = FirebaseAuth.getInstance().currentUser
//        if (curent == null){
//            val intents = Intent(this, MainActivity::class.java)
//            startActivity(intents)
//            finish()
//        }
    }

    private fun fetchUser() {
        val ref = FirebaseDatabase.getInstance().getReference("users/")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(UserModel::class.java)

            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}