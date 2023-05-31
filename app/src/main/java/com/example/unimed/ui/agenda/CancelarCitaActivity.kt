package com.example.unimed.ui.agenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.unimed.R
import com.example.unimed.databinding.ActivityCancelarCitaBinding

class CancelarCitaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCancelarCitaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancelarCitaBinding.inflate(layoutInflater)

        setContentView(binding.root)


    }
}