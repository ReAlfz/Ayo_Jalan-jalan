package com.realfz.myta

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.rc_chat.view.*
import kotlinx.android.synthetic.main.rc_get_chat.view.*

class Chat : AppCompatActivity() {

    companion object {
        val TAG = "Chat Logs"
    }

    var getUer: User? = null
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        Listchat.adapter = adapter

        supportActionBar?.title = "Chat Time"

        displayTheChat()

        btn_send.setOnClickListener {
            saveDataToFireBase()
        }
    }

    private fun saveDataToFireBase() {
        val input = edt_input.text.toString()
        val fromId = FirebaseAuth.getInstance().uid
        if (fromId == null) return

        val ref = FirebaseDatabase.getInstance().getReference("/message-group/$fromId").push()
        val chatSaver = ChatSave(ref.key!!, input, fromId)
        ref.setValue(chatSaver)
            .addOnSuccessListener {
                edt_input.text.clear()
                Listchat.scrollToPosition(adapter.itemCount -1)
            }

        val lastMessage = FirebaseDatabase.getInstance().getReference("/last-message/$fromId")
        lastMessage.setValue(chatSaver)
    }

    private fun displayTheChat() {
        val fromId = FirebaseAuth.getInstance().uid
        val refDisplay = FirebaseDatabase.getInstance().getReference("/message-group/$fromId")
        refDisplay.addChildEventListener(object : ChildEventListener {
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val getMessage = p0.getValue(ChatSave::class.java)
                if (getMessage != null) {

                    if (getMessage.fromId == FirebaseAuth.getInstance().uid) {
                        val currentUser = Jadwal.currentUser ?: return
                        adapter.add(ChatOtherItem(getMessage.text, currentUser))
                    } else {
                        adapter.add(ChatGetItem(getMessage.text, getUer!!))
                    }
                }

                Listchat.scrollToPosition(adapter.itemCount -1)
            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
    }

    class ChatOtherItem(val text: String, val user : User) : Item<ViewHolder>() {
        override fun getLayout(): Int {
            return R.layout.rc_chat
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.txt_chat_from.text = text

            val url = user.profileImage
            val ImgView = viewHolder.itemView.img_chat_get
            Picasso.get().load(url).into(ImgView)
        }
    }

    class ChatGetItem(val text: String, val user : User) : Item<ViewHolder>() {
        override fun getLayout(): Int {
            return R.layout.rc_get_chat
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.txt_chat_get.text = text

            val url = user.profileImage
            val ImgView = viewHolder.itemView.img_chat_from
            Picasso.get().load(url).into(ImgView)
        }

    }
}