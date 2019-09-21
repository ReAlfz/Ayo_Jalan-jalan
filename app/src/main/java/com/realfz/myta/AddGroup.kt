package com.realfz.myta

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.add_group.view.*
import kotlinx.android.synthetic.main.theme_group.*

class AddGroup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.theme_group)

        supportActionBar?.title = "Select Group"

        fecthUser()
    }

    companion object {
        val USER_KEY = "USER_KEY"
    }

    private fun fecthUser() {
        val ref = FirebaseDatabase.getInstance().getReference("/group")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p1: DatabaseError) {
            }

            override fun onDataChange(p1: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                p1.children.forEach {
                    Log.d("AddGroup", it.toString())
                    val list = it.getValue(Ravie::class.java)
                    if (list != null){
                        adapter.add(UserItem(list))
                    }
                }

                adapter.setOnItemClickListener { item, view ->
                    val userItem = item as UserItem

                    val intent = Intent(view.context, Chat::class.java)
                    intent.putExtra(USER_KEY, userItem.bulan)
                    startActivity(intent)

                    finish()
                }

                rcycler.adapter = adapter
            }
        })
    }
}

class UserItem(val bulan: Ravie) : Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.add_group
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.username.text = bulan.id

        Picasso.get().load(bulan.imges).into(viewHolder.itemView.imgList)
    }
}