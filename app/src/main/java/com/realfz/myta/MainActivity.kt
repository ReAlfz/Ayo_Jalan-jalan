package com.realfz.myta

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_jadwal.view.*

class MainActivity : AppCompatActivity() {

    companion object {
        var currentUser : User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val desktop = Adapter(supportFragmentManager)

        pager.adapter = desktop
        tabs.setupWithViewPager(pager)

        VerifyUserLog()

        val adapter = GroupAdapter<ViewHolder>()


    }

    private fun VerifyUserLog() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, Sign_Up::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.about, menu)
        menuInflater.inflate(R.menu.sign_out, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.damn) {
            val vc = Intent(this, About::class.java)
            startActivity(vc)
        }

        if (id == R.id.out) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, Log_in::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}

class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val page = arrayOf(
        Jadwal(), Reservasi()
    )

    private var cantSwipe = false



    private val title = arrayOf("Jadwal", "Reservasi")

    override fun getItem(position: Int): Fragment = page[position]

    override fun getCount(): Int = page.size

    override fun getPageTitle(position: Int): CharSequence? = title[position]
}
