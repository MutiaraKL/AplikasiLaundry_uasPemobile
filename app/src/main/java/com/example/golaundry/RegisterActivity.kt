package com.example.golaundry

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.golaundry.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        val poppinsFont = ResourcesCompat.getFont(this, R.font.poppins)
        binding.passwordInput.typeface = poppinsFont

        binding.passwordInput.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        binding.showHidePwd2.setOnClickListener {
            togglePasswordVisibility()
        }

        binding.btnDaftar.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (validateInputs(email, username, password)) {
                // Menyimpan data pengguna ke SharedPreferences
                saveUserData(email, username, password)

                Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()

                // Pindah ke aktivitas login setelah registrasi berhasil
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Registrasi Gagal. Harap isi semua data.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun togglePasswordVisibility() {
        val currentInputType = binding.passwordInput.inputType

        if (currentInputType == InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT) {
            // Tampilkan password
            binding.passwordInput.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.showHidePwd2.setImageResource(R.drawable.ic_eyed_open)
        } else {
            // Sembunyikan password
            binding.passwordInput.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.showHidePwd2.setImageResource(R.drawable.ic_eyed_closed)
        }
        // Posisikan kursor di akhir teks
        binding.passwordInput.setSelection(binding.passwordInput.text.length)

        // Log statement untuk memeriksa jika fungsi dipanggil
        Log.d("TogglePasswordVisibility", "Function called")
    }

    private fun validateInputs(email: String, username: String, password: String): Boolean {
        return email.isNotBlank() && username.isNotBlank() && password.isNotBlank()
    }

    private fun saveUserData(email: String, username: String, password: String) {
        // Menyimpan data pengguna ke SharedPreferences
        val editor = sharedPreferences.edit()

        editor.putString("email", email)
        editor.putString("username", username)
        editor.putString("password", password)

        editor.apply()
    }
}
