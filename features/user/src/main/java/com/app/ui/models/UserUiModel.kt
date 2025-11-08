package com.app.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserUiModel(
    val id: Int,
    val name: String,
    val email: String,
    val photo: String,
    val company: String,
    val country: String,
    val phone: String,
    val username: String
): Parcelable {
}