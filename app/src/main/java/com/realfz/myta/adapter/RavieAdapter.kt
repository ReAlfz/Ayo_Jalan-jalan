@file:Suppress("NAME_SHADOWING")

package com.realfz.myta.adapter

import com.realfz.myta.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.list_group_main.view.*

class RavieAdapter(val nameGroup: String, val img: String, val dates: String) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.name_group.text = nameGroup
        viewHolder.itemView.date_ss.text = dates
//        viewHolder.itemView.last_text.text = lastText
        Picasso.get().load(img).into(viewHolder.itemView.img_group)
    }

    override fun getLayout(): Int {
        return R.layout.list_group_main
    }
}