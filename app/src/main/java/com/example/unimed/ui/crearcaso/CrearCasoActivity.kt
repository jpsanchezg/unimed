package com.example.unimed.ui.crearcaso

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.unimed.MainActivity
import com.example.unimed.databinding.ActivityCrearCasoBinding
import com.example.unimed.models.Caso
import com.example.unimed.models.Usuario
import com.example.unimed.ui.casos.CasosFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class CrearCasoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearCasoBinding
    private lateinit var mAuth: FirebaseAuth
    val PICK_PDF_FILE = 2
    var filenameTxt = ""
    var user = FirebaseAuth.getInstance().currentUser
    var usuariopb = Usuario()
    private lateinit var database: DatabaseReference
    var myRef: DatabaseReference? = null
    val PATH_USERS = "users/"
    private val RECORD_REQUEST_CODE = 101
    var Listacasos: ArrayList<Caso> = ArrayList<Caso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCasoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        var currentUserDb = mAuth.currentUser
        var uid = currentUserDb?.uid
        database.child("users").child(uid.toString()).child("casos").get().addOnCompleteListener(
            this
        ) { task ->
            if (task.isSuccessful) {

                val caso = task.result.children.forEach() {
                    val caso = it.getValue(Caso::class.java)
                    var nombre = caso?.nombreDelCaso.toString()
                    var archivo = caso?.archivo.toString()
                    var fecha = caso?.FechaDelExamen.toString()
                    Listacasos.add(
                        Caso(
                            nombre,
                            archivo,
                            fecha
                        )
                    )
                }
            } else {
                Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.suburarchivoBTN.setOnClickListener() {
            Toast.makeText(this, "Subir archivo", Toast.LENGTH_SHORT).show()
            if (checkStoragePermission()) {
                // Ya tienes los permisos, puedes acceder al almacenamiento
                openFile(Uri.EMPTY)

            } else {
                // No tienes los permisos, solicítalos al usuario
                requestStoragePermission()
            }
        }
        binding.back.setOnClickListener() {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.creaarcasoBTN.setOnClickListener() {
            saveCaso()
        }


    }


    private fun createCasoObject(): Caso {
        return Caso(
            binding.nombredelcasoET.getText().toString(),
            filenameTxt,
            binding.fechadelcasoET.getText().toString(),
        )
    }


    private fun saveCaso() {
        Listacasos.add(createCasoObject())
        var currentUserDb = mAuth.currentUser
        var uid = currentUserDb?.uid
        database = Firebase.database.reference
        Toast.makeText(this, "Caso creado", Toast.LENGTH_SHORT).show()
        if (Listacasos.size == 0) {
            database.child("users").child(uid.toString()).child("casos").setValue(Listacasos)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Caso creado", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, CasosFragment::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error al crear caso", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            database.child("users").child(uid.toString()).child("casos").setValue(Listacasos)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Caso creado", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, CasosFragment::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error al crear caso", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    private val STORAGE_PERMISSION_CODE = 1

    private fun checkStoragePermission(): Boolean {
        val readPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
        return readPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES
            ), STORAGE_PERMISSION_CODE
        )
    }

    fun openFile(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"

            // Optionally, specify a URI for the file that should appear in the
            // system file picker when it loads.
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            filenameTxt = getFileName(pickerInitialUri).toString()
        }


        startActivityForResult(intent, PICK_PDF_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PDF_FILE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                val fileName = getFileName(uri)
                if (fileName != null) {
                    // Aquí puedes utilizar el nombre del archivo obtenido
                    filenameTxt = fileName
                } else {
                    Toast.makeText(
                        this,
                        "No se pudo obtener el nombre del archivo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String? {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                return displayName
            }
        }
        return null
    }
}