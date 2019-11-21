@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.realfz.myta.adapter

import com.realfz.myta.R
import com.realfz.myta.model.ChatModel
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.rc_get_chat.view.*

class SelfChatAdapters(val Ausername: String, val Atexts: String, val Aimages : String) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.username_chats.text = Ausername
        viewHolder.itemView.text_chats.text = Atexts
        Picasso.get().load(Aimages).into(viewHolder.itemView.img_chat)
    }

    override fun getLayout(): Int {
        return R.layout.rc_get_chat
    }
}