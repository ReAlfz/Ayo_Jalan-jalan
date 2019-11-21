package com.realfz.myta.message

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.realfz.myta.R
import com.realfz.myta.adapter.OtherChatAdapter
import com.realfz.myta.adapter.SelfChatAdapters
import com.realfz.myta.model.ChatModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.Listchat
import kotlinx.android.synthetic.main.activity_chat.btn_send
import kotlinx.android.synthetic.main.activity_chat.edt_input

class Chats : AppCompatActivity() {

    val reAdapter = GroupAdapter<ViewHolder>()
    val mAuth = FirebaseAuth.getInstance().currentUser!!.uid
    val userRef = FirebaseDatabase.getInstance().reference.child("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val title = intent.getStringExtra("nGroup")
        supportActionBar?.title = title

        btn_send.setOnClickListener {
            saveDataToFireBase()
            edt_input.text.clear()
            Listchat.scrollToPosition(reAdapter.itemCount -1)
        }
        Listchat.adapter = reAdapter

        display()
    }

    private fun saveDataToFireBase() {
        val refUUID = FirebaseDatabase.getInstance().reference.child("groups")
        val messageText = edt_input.text.toString()

        userRef.child(mAuth).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val currentName = p0.child("username").value.toString()
                    val currentImages = p0.child("profileImage").value.toString()

                    refUUID.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {
                            if (p0.exists()) {
                                val usd = intent.getStringExtra("nGroup")
                                val uid = refUUID.push().key

                                if (messageText.isBlank()){
                                    Toast.makeText(this@Chats, "Pesan tidak boleh Kosong", Toast.LENGTH_SHORT).show()
                                }else {
                                    val ref = FirebaseDatabase.getInstance().getReference("message-group/$usd/$uid")
                                    val data = ChatModel(usd, currentName ,currentImages ,messageText, mAuth,"")
                                    ref.setValue(data)
                                }
                            }
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }
                    })
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }


    private fun display() {
        val currentGroup = intent.getStringExtra("nGroup")
        val ref = FirebaseDatabase.getInstance().reference.child("message-group")

        ref.child(currentGroup).addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val iDatas = p0.getValue(ChatModel::class.java)
                if (iDatas != null){
                    if (iDatas.fromId == mAuth){
                        reAdapter.add(SelfChatAdapters(iDatas.username , iDatas.text, iDatas.images))
                    } else{
                        reAdapter.add(OtherChatAdapter(iDatas.username , iDatas.text, iDatas.images))
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}