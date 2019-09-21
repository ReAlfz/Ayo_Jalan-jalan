@file:Suppress("UNREACHABLE_CODE", "UNREACHABLE_CODE")

package com.realfz.myta

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.add_group.view.*
import kotlinx.android.synthetic.main.fragment_jadwal.*
import kotlinx.android.synthetic.main.fragment_jadwal.view.*
import kotlinx.android.synthetic.main.list_group_main.view.*

class Jadwal : Fragment() {

    companion object{
        var currentUser : User? = null
    }

    val lastedMessageMap = HashMap<String, ChatSave>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.setOnItemClickListener { item, view ->
            Log.d("Last-Message", "it ok's ?")
            val intent = Intent(this.context, Chat::class.java)
            val row = item as LastRow
            intent.putExtra(AddGroup.USER_KEY, row.ravie)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_jadwal, container, false)
//        list_Group.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))

        view.btn_float.setOnClickListener {
            val intent = Intent(this.context, AddGroup::class.java)
            startActivity(intent)
        }
        return view

        fetchCurrentUser()

        forLastView()
    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                MainActivity.currentUser = p0.getValue(User::class.java)
                Log.d("Main", "Current user ${MainActivity.currentUser?.profileImage}")
            }

        })
    }

    private fun forLastView() {
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/last-message/$fromId")
        ref.addChildEventListener(object : ChildEventListener{
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val lastChat = p0.getValue(ChatSave::class.java) ?: return
                lastedMessageMap[p0.key!!] = lastChat
                reloadRecycler()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val lastChat = p0.getValue(ChatSave::class.java) ?: return
                lastedMessageMap[p0.key!!] = lastChat
                reloadRecycler()
            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })

        list_Group.adapter = adapter
    }

    private fun reloadRecycler() {
        adapter.clear()
        lastedMessageMap.values.forEach {
            adapter.add(LastRow(it))
        }
    }

    val adapter = GroupAdapter<ViewHolder>()

    class LastRow(val chatMessage: ChatSave) : Item<ViewHolder>() {
        var ravie : Ravie? = null

        override fun getLayout(): Int {
            return R.layout.list_group_main
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.last_text.text = chatMessage.text

            val ref = FirebaseDatabase.getInstance().getReference("/group/")
            ref.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    ravie = p0.getValue(Ravie::class.java)
                    viewHolder.itemView.name_group.text = ravie?.id

                    val targetImg = viewHolder.itemView.img_group
                    Picasso.get().load(ravie?.imges).into(targetImg)
                }

            })

        }

    }
}