package com.realfz.myta.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val uid: String, /* uid user register */
    val username: String, /* username user register */
    val profileImage: String, /* image user register */
    val nomor: String /* nomor user */
) : Parcelable {
    constructor() : this("","","","")
}