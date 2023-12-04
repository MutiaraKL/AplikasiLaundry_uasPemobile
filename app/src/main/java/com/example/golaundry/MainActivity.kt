package com.example.golaundry

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.golaundry.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var progressStatus = 0
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressBar: ProgressBar = binding.progressBar
        val textView: TextView = binding.textView

        Thread {
            while (progressStatus < 100) {
                progressStatus += 1
                // Update the progress bar and display the
                // current value in the text view
                handler.post {
                    progressBar.progress = progressStatus
                    textView.text = "$progressStatus%"
                }
                try {
                    // Sleep for 200 milliseconds.
                    Thread.sleep(25)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            val intent = Intent(this@MainActivity, LoadActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }.start()
    }
}
