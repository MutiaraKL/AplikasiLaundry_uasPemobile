package com.example.golaundry.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaundryOrder(
    val berat: Double,
    val paketId: Int,
    val jasaAntar: Boolean,
    val alamat: String,
    val biaya: Double
) : Parcelable
