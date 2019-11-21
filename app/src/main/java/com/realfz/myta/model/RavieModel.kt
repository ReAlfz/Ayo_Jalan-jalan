package com.realfz.myta.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RavieModel(
    val namaGroup: String, /*nama group dibuat*/
    val imges: String, /*image*/
    val date : String, /*tanggal*/
    val lastText : String
) : Parcelable {
    constructor() : this("", "", "","")
}