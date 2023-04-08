package com.farhanharis.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.farhanharis.githubuser.R

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 3000 // Durasi tampilan splash screen dalam milidetik (3 detik)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set layout yang telah dibuat sebagai konten tampilan
        setContentView(R.layout.activity_splash)

        // Menggunakan Handler untuk menunda navigasi ke aktivitas berikutnya
        Handler().postDelayed({
            // Intent untuk mengarahkan ke aktivitas berikutnya setelah splash screen selesai
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // Menutup splash screen activity
        }, SPLASH_DELAY)
    }
}