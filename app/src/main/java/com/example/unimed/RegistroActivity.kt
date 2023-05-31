package com.example.unimed

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unimed.databinding.ActivityRegistroBinding
import com.example.unimed.models.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var mAuth: FirebaseAuth

    var user = FirebaseAuth.getInstance().currentUser
    private lateinit var database: DatabaseReference

    var myRef: DatabaseReference? = null
    val PATH_USERS = "users/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister?.setOnClickListener {
            if (binding.nombreET?.text.toString().isEmpty()) {
                binding.nombreET?.error = "Por favor, ingresa un nombre"
                binding.nombreET?.requestFocus()
                return@setOnClickListener
            }
            if (binding.emailET?.text.toString().isEmpty()) {
                binding.emailET?.error = "Por favor, ingresa un email"
                binding.emailET?.requestFocus()
                return@setOnClickListener
            }
            if (binding.PasswordET?.text.toString().isEmpty()) {
                binding.PasswordET?.error = "Por favor, ingresa una contraseña"
                binding.PasswordET?.requestFocus()
                return@setOnClickListener
            }
            if (binding.PasswordET?.text.toString().length < 6) {
                binding.PasswordET?.error = "La contraseña debe tener al menos 6 caracteres"
                binding.PasswordET?.requestFocus()
                return@setOnClickListener
            }
            if (binding.PasswordET?.text.toString() != binding.confContraET?.text.toString()) {
                binding.confContraET?.error = "Las contraseñas no coinciden"
                binding.confContraET?.requestFocus()
                return@setOnClickListener
            } else {
                validateIfUsersAlreadyExists();
            }

        }
        binding.loginTvBTN?.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    private fun validateIfUsersAlreadyExists() {
        mAuth = FirebaseAuth.getInstance()
        mAuth.fetchSignInMethodsForEmail(binding.emailET.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.signInMethods?.isEmpty() == true) {
                        createFirebaseAuthUser(
                            binding.emailET.text.toString(),
                            binding.PasswordET.text.toString()
                        )
                    } else {
                        // Existe el usuario
                        Toast.makeText(
                            applicationContext,
                            "El usuario ya existe",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
    }


    private fun createFirebaseAuthUser(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    saveUser()
                }
            }
    }

    private fun createUserObject(): Usuario? {
        var user = mAuth.currentUser;
        var uid = user?.uid


        return Usuario(
            uid.toString(),
            binding.nombreET.text.toString(),
            binding.emailET.text.toString(),
            binding.EpsET.text.toString(),
        )
    }


    private fun saveUser() {
        val Client = createUserObject()
        var currentUserDb = mAuth.currentUser
        var uid = currentUserDb?.uid
        database = Firebase.database.reference
        database.child("users").child(uid.toString()).setValue(Client).addOnSuccessListener {
            Toast.makeText(applicationContext, "Usuario registrado", Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(applicationContext, MainActivity::class.java)
            )
        }.addOnFailureListener {
            // Write failed
            // ...
            Toast.makeText(
                applicationContext,
                "Error al registrar usuario",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}