package com.example.unimed

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        Handler().postDelayed({
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
        }, 500)
    }
}