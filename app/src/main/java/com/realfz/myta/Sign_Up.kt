package com.realfz.myta

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_sign__up.*
import java.util.*

class Sign_Up : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign__up)

        img_btn.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        btn_register.setOnClickListener {
            val main = Intent(this, Log_in::class.java)

            val email = edtEmail.text.toString()
            val pass = edtPass.text.toString()

            if (email.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Please insert your emails and password", Toast.LENGTH_SHORT).show()
            }else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) return@addOnCompleteListener

                    }
                    .addOnFailureListener {
                        Log.d("Sign", "Failed : ${it.message}")
                        Toast.makeText(this, "Failed to create : ${it.message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                uploadImageFirebase()
                startActivity(main)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d("Sign", "Photo selected")

            selected = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selected)
            val bitmapDrawable = BitmapDrawable(bitmap)
            avt.setBackgroundDrawable(bitmapDrawable)

            img_btn.alpha = 0f
        }
    }

    var selected : Uri? = null

    private fun uploadImageFirebase() {
        if (selected == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/image/$filename")

        ref.putFile(selected!!)
            .addOnSuccessListener {
                Log.d("Sign", "Img ok : ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("sign", "file location $it")

                    saveUsertoFirebase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d("fail", "Failed to upload ${it.message}")
            }
    }

    private fun saveUsertoFirebase(profileImage: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, edtName.text.toString(), profileImage, edtNomor.text.toString())

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Sign", "account saved")

                val intent = Intent(this, Log_in::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("fail", "Failed save ${it.message}")
            }
    }
}
