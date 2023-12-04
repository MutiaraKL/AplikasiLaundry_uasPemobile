package com.example.golaundry

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.golaundry.databinding.ActivityLoadBinding

class LoadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menimpa gambar dengan hitam transparan
        applyBlackTransparentOverlay()

        binding.btnLoad.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun applyBlackTransparentOverlay() {
        val drawable = binding.bgLoad.drawable

        if (drawable != null) {
            // Setel filter warna ke hitam dengan alpha
            drawable.setColorFilter(Color.parseColor("#80000000"), PorterDuff.Mode.SRC_ATOP)

            // Menyegarkan ImageView untuk menerapkan perubahan
            binding.bgLoad.invalidate()
        }
    }
}
