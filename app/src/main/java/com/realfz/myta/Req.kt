package com.realfz.myta

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_req.*
import java.util.*

class Req : AppCompatActivity() {

    var select: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_req)

        button2.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }


        btn_semi.setOnClickListener {
            uploadData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was....
            Log.d("nice", "Photo was selected")

            select = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, select)

            img_show.setImageBitmap(bitmap)

            img_show.alpha = 0f
        }
    }

    private fun uploadData() {
        if (select == null) return

        val file = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/image/$file")

        ref.putFile(select!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    saveData(it.toString())
                }
            }
    }

    private fun saveData( imges : String ) {
        val id = txt_sk.text.toString()
        val ref = FirebaseDatabase.getInstance().getReference("/group/$id")

        val default = Ravie(txt_sk.text.toString(), txt_semi.text.toString(), imges)

        ref.setValue(default)
    }
}