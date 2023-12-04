package com.example.golaundry.model

import androidx.lifecycle.ViewModel

class LaundryViewModel : ViewModel() {
    var beratInput: String = ""
    var selectedPaketId: Int = -1
    var isJasaAntarSelected: Boolean = false
}