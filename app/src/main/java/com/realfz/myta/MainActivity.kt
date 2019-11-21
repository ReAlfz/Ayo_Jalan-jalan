package com.realfz.myta

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.realfz.myta.loginRegister.Log_in
import com.realfz.myta.loginRegister.Sign_Up
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val desktop = Adapter(supportFragmentManager)

        pager.adapter = desktop
        tabs.setupWithViewPager(pager)

        VerifyUserLog()
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
        menuInflater.inflate(R.menu.sign_out, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.out) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, Log_in::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}

class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val page = arrayOf(
        Jadwal(), Bantuan()
    )

    private val title = arrayOf("Jadwal", "Bantuan")

    override fun getItem(position: Int): Fragment = page[position]

    override fun getCount(): Int = page.size

    override fun getPageTitle(position: Int): CharSequence? = title[position]
}
