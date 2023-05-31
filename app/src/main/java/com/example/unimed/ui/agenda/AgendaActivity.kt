package com.example.unimed.ui.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes.Margins
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.marginLeft
import com.example.unimed.MainActivity
import com.example.unimed.R
import com.example.unimed.databinding.ActivityAgendaBinding
import com.example.unimed.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AgendaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgendaBinding
    private lateinit var mAuth: FirebaseAuth

    var user = FirebaseAuth.getInstance().currentUser
    private lateinit var database: DatabaseReference

    var myRef: DatabaseReference? = null
    val PATH_USERS = "users/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val texts = listOf("12/12/2022", "14/12/2022")
        val horas = listOf("6 PM", "3 PM")
        mAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        var currentUserDb = mAuth.currentUser
        var uid = currentUserDb?.uid
        database.child("users").child(uid.toString()).get().addOnCompleteListener(
            this
        ) { task ->
            if (task.isSuccessful) {
                val user = task.result.getValue(Usuario::class.java)
                if (user != null) {
                    binding.personName.text = "Hola, "+user.nombre
                }
            } else {
                Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.back.setOnClickListener(){
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }



        for (text in texts) {
            val newTextView = TextView(applicationContext)
            newTextView.layoutParams = LinearLayout.LayoutParams(
                WRAP_CONTENT,
                WRAP_CONTENT,
            )
            newTextView.background = getDrawable(R.drawable.cajatexto)
            newTextView.setPadding(5, 5, 5, 5)
            newTextView.textSize = 20f
            newTextView.setTextColor(getColor(R.color.white))
            val params2 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params2.setMargins(0, 20, 0, 0) // Establece el margen superior de 20 píxeles
            newTextView.layoutParams = params2
            newTextView.text = text
            newTextView.gravity = Gravity.CENTER_HORIZONTAL
            binding.fechaLayoutv.addView(newTextView)
        }

        for (text in horas) {
            val newTextView = TextView(applicationContext)
            newTextView.layoutParams = LinearLayout.LayoutParams(
                WRAP_CONTENT,
                WRAP_CONTENT,
            )
            newTextView.background = getDrawable(R.drawable.cajatexto)
            newTextView.setPadding(5, 5, 5, 5)
            newTextView.textSize = 20f
            newTextView.setTextColor(getColor(R.color.white))
            newTextView.text = text
            newTextView.gravity = Gravity.CENTER_HORIZONTAL

            val params2 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params2.setMargins(0, 20, 0, 0) // Establece el margen superior de 20 píxeles
            newTextView.layoutParams = params2
            binding.horaLayoutv.addView(newTextView)
        }

    }
}