package com.realfz.myta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*

@Suppress("UNREACHABLE_CODE")
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
                Toast.makeText(this, "Please insert your emails and password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emails.text.toString(), passw.text.toString())
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            return@addOnCompleteListener
                        } else {
                            Toast.makeText(this, "Succesfully Login", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
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

    }
}