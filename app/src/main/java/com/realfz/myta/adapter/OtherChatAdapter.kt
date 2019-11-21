package com.realfz.myta.adapter

import com.realfz.myta.R
import com.realfz.myta.model.ChatModel
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.rc_chat.view.*

class OtherChatAdapter(val username: String, val text: String, val image : String) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.other_username_chats.text = username
        viewHolder.itemView.other_text_chat.text = text
        Picasso.get().load(image).into(viewHolder.itemView.other_img_chat)
    }

    override fun getLayout(): Int {
        return R.layout.rc_chat
    }
}