package com.example.unimed

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unimed.databinding.ActivityLoginBinding
import com.example.unimed.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    var user = FirebaseAuth.getInstance().currentUser
    var database = FirebaseDatabase.getInstance()
    var myRef: DatabaseReference? = null
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin?.setOnClickListener {
            if (binding.emailET?.text.toString().isEmpty()) {
                binding.emailET?.error = "Por favor, ingresa un email"
                binding.emailET?.requestFocus()
                return@setOnClickListener
            }
            if (binding.PasswordET?.text.toString().isEmpty()) {
                binding.PasswordET?.error = "Por favor, ingresa una contraseña"
                binding.PasswordET?.requestFocus()
                return@setOnClickListener
            } else {

                login(binding.emailET.getText().toString(),binding.PasswordET.getText().toString())


            }

        }
        binding.registrarBTN?.setOnClickListener {
            val intent = Intent(applicationContext, RegistroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(email:String,password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }

    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            myRef = FirebaseDatabase.getInstance().getReference("users").child(user.uid)
            myRef!!.get().addOnCompleteListener { task ->
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        val usuario: Usuario = task.getResult().getValue(Usuario::class.java)!!
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}