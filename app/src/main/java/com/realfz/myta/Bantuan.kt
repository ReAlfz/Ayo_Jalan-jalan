package com.realfz.myta

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reservasi.*


class Bantuan : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { return inflater.inflate(R.layout.fragment_reservasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_des.setOnClickListener {
            val destination = Intent(this.context, WebViews::class.java)
            destination.putExtra("init", "file:///android_asset/destinasi/gas1.html")
            startActivity(destination)
        }

        btn_rental.setOnClickListener {
            val rental = Intent(this.context, WebViews::class.java)
            rental.putExtra("init", "file:///android_asset/persewaan/gas1.html")
            startActivity(rental)
        }

        btn_pemandu.setOnClickListener {
            val pemandu = Intent(this.context, WebViews::class.java)
            pemandu.putExtra("init", "file:///android_asset/pemandu/gas1.html")
            startActivity(pemandu)
        }

        btn_tips.setOnClickListener {
            val tips = Intent(this.context, WebViews::class.java)
            tips.putExtra("init", "file:///android_asset/tips/gas1.html")
            startActivity(tips)
        }
    }
}
