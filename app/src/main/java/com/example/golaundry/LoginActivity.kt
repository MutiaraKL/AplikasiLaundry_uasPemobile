package com.example.golaundry

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.golaundry.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val poppinsFont = ResourcesCompat.getFont(this, R.font.poppins)
        binding.passwordInput.typeface = poppinsFont

        binding.passwordInput.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.showHidePwd.setImageResource(R.drawable.ic_eyed_closed)

        binding.showHidePwd.setOnClickListener {
            togglePasswordVisibility()
        }

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        binding.masuk.setOnClickListener {
            val usernameOrEmail = binding.usernameOrEmailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            // Mengambil data pengguna dari SharedPreferences
            val savedEmail: String? = sharedPreferences.getString("email", null)
            val savedUsername: String? = sharedPreferences.getString("username", null)
            val savedPassword: String? = sharedPreferences.getString("password", null)

            if (performLogin(usernameOrEmail, password, savedUsername, savedEmail, savedPassword)) {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login Gagal. Silakan Coba Lagi!", Toast.LENGTH_SHORT).show()
            }
        }

        // Add click listener to "Lupa Sandi?" TextView
        binding.lupaSandi.setOnClickListener {
            handleForgotPassword()
        }
    }

    private fun togglePasswordVisibility() {
        val currentInputType = binding.passwordInput.inputType

        binding.passwordInput.inputType = if (currentInputType == InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT) {
            // Tampilkan password
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            // Sembunyikan password
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        // Posisikan kursor di akhir teks
        binding.passwordInput.setSelection(binding.passwordInput.text.length)

        // Ubah ikon sesuai dengan status visibilitas password
        val visibilityIcon =
            if (currentInputType == InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT) R.drawable.ic_eyed_open
            else R.drawable.ic_eyed_closed

        binding.showHidePwd.setImageResource(visibilityIcon)

        // Log statement untuk memeriksa jika fungsi dipanggil
        Log.d("TogglePasswordVisibility", "Function called")
    }

    private fun performLogin(
        enteredUsernameOrEmail: String,
        enteredPassword: String,
        validUsername: String?,
        validEmail: String?,
        validPassword: String?
    ): Boolean {
        // Periksa untuk non-empty di data Login
        val isUsernameValid = validUsername?.isNotBlank() ?: false
        val isEmailValid = validEmail?.isNotBlank() ?: false
        val isPasswordValid = validPassword?.isNotBlank() ?: false

        // Menampilkan validasi Login
        return (isUsernameValid && enteredUsernameOrEmail == validUsername) ||
                (isEmailValid && enteredUsernameOrEmail == validEmail) &&
                isPasswordValid && enteredPassword == validPassword
    }

    private fun handleForgotPassword() {
        // Memanggil email dari SharedPreferences
        val savedEmail: String? = sharedPreferences.getString("email", null)

        // Memeriksa jika email ada
        if (savedEmail.isNullOrBlank()) {
            Toast.makeText(this, "Email tidak ditemukan", Toast.LENGTH_SHORT).show()
        } else {
            // Mengirim pesan untuk memperbarui password dari email
            Toast.makeText(this, "Permintaan ubah password sudah dikirim ke email $savedEmail", Toast.LENGTH_SHORT).show()
        }
    }

    fun Signup(view: View) {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }
}
