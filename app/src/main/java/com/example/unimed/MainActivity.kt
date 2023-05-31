package com.example.unimed

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.unimed.databinding.ActivityMainBinding
import com.example.unimed.models.Usuario
import com.google.android.gms.common.api.Api.Client
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth

    var user = FirebaseAuth.getInstance().currentUser
    private lateinit var database: DatabaseReference

    var myRef: DatabaseReference? = null
    val PATH_USERS = "users/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





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
                    binding.personName.text = "Hola, " + user.nombre
                }
            } else {
                Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_SHORT).show()
            }
        }
        binding.profileImage.setOnClickListener(){
            mAuth.signOut()
            val intent = intent

            finish()
        }




        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_filtros, R.id.navigation_casos,R.id.navigation_settings
            )
        )
        navView.setupWithNavController(navController)
    }
}