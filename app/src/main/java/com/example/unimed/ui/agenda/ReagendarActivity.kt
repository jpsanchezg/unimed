package com.example.unimed.ui.agenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.unimed.R
import com.example.unimed.databinding.ActivityReagendarBinding

class ReagendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityReagendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReagendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.confirmarreagendarBTN.setOnClickListener {
            finish()
        }
    }
}