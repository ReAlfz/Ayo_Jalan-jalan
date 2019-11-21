package com.realfz.myta.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatModel(
    val nameGroup : String,
    val username : String,/* nama dari user */
    val images : String,
    val text: String,/* text dari input */
    val fromId: String,/* nama dari user */
    val time : String

) : Parcelable {
    constructor() : this("","","","","","")
}