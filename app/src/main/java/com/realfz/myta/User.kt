package com.realfz.myta

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid : String , val username: String, val profileImage : String, val nomor : String): Parcelable{
    constructor() : this("","","", "")
}
@Parcelize
class Ravie(val id : String ,val namaGroup : String,val imges : String): Parcelable{
    constructor() : this("","","")
}
@Parcelize
class ChatSave(val id: String, val text: String, val fromId: String) : Parcelable{
    constructor() : this("", "", "")
}