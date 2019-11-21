@file:Suppress("UNREACHABLE_CODE", "UNREACHABLE_CODE")

package com.realfz.myta

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.realfz.myta.adapter.RavieAdapter
import com.realfz.myta.message.Chats
import com.realfz.myta.model.RavieModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_jadwal.*
import kotlinx.android.synthetic.main.list_group_main.view.*
import kotlinx.android.synthetic.main.new_group.*

class Jadwal : Fragment() {

    val adapters = GroupAdapter<ViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_jadwal, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_float.setOnClickListener {
            val dialog = LayoutInflater.from(this.context).inflate(R.layout.new_group, null)
            val builder = AlertDialog.Builder(this.context)
                .setView(dialog)
                .setTitle("Buat Grup")
            val alertDialog = builder.show()

            alertDialog.calendarView.setOnDateChangeListener { view, year, month, day ->
                val sda = "$day/$month/$year"
                Toast.makeText(this.context, sda, Toast.LENGTH_LONG).show()
                alertDialog.text_date.text = sda

            alertDialog.btn_create.setOnClickListener {
                    val currentGroupName = alertDialog.edt_nama_group.text.toString()
                    val imgs = FirebaseStorage.getInstance().getReference("/image/travels.png")

                    if (alertDialog.edt_nama_group.text.isEmpty()) {
                        alertDialog.edt_nama_group.error = "Isi nama group ini"
                    } else {
                        imgs.downloadUrl.addOnSuccessListener {
                            val news = it.toString()
                            val ref = FirebaseDatabase.getInstance().getReference("groups")
                            ref.child(currentGroupName).setValue(RavieModel(currentGroupName, news, sda, ""))
                            alertDialog.dismiss()
                        }
                    }
                }
            }
            alertDialog.btn_cancel.setOnClickListener {
                alertDialog.dismiss()
            }
        }
        list_Group.adapter = adapters
        rested()
    }


    private fun rested() {
        val ref = FirebaseDatabase.getInstance().getReference().child("groups")

        ref.addChildEventListener(object : ChildEventListener{
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                display(p0)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                display(p0)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    companion object {
        val nGroup = "nGroup"
    }

    private fun display(p21 : DataSnapshot) {
        val data = p21.getValue(RavieModel::class.java)
        if (data != null){
            adapters.add(RavieAdapter(data.namaGroup,data.imges, data.date))
        }
        adapters.setOnItemClickListener { item, view ->
            val intent = Intent(view.context, Chats::class.java)
            intent.putExtra(nGroup, view.name_group.text.toString())
            startActivity(intent)
        }
    }
}