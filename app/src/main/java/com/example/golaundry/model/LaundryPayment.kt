package com.example.golaundry.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaundryPayment(
    val metode: String
) : Parcelable
