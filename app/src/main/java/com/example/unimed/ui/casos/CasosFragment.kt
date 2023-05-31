package com.example.unimed.ui.casos

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.unimed.R
import com.example.unimed.databinding.FragmentCasosBinding
import com.example.unimed.models.Caso
import com.example.unimed.models.Usuario
import com.example.unimed.ui.crearcaso.CrearCasoActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CasosFragment : Fragment() {

    private var _binding: FragmentCasosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val casosViewModel =
            ViewModelProvider(this).get(CasosViewModel::class.java)

        _binding = FragmentCasosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.pantallacrearcasoBTN.setOnClickListener {
            val intent = Intent(activity, CrearCasoActivity::class.java)
            startActivity(intent)
        }

        mAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        var currentUserDb = mAuth.currentUser
        var uid = currentUserDb?.uid
        database.child("users").child(uid.toString()).child("casos").get().addOnCompleteListener(
            requireActivity()
        ) { task ->
            if (task.isSuccessful) {
                task.result.children.forEach() {
                    val caso = it.getValue(Caso::class.java)
                    var nombre = caso?.nombreDelCaso.toString()
                    var archivo = caso?.archivo.toString()
                    var fecha = caso?.FechaDelExamen.toString()

                    val newTextView = Button(activity)
                    newTextView.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                    )
                    newTextView.background = getDrawable(requireContext(), R.drawable.cajatexto)
                    newTextView.setPadding(5, 5, 5, 5)
                    newTextView.textSize = 20f
                    newTextView.minHeight = 170
                    newTextView.setTextColor(resources.getColor(R.color.white))
                    val params2 = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params2.setMargins(0, 20, 0, 0) // Establece el margen superior de 20 píxeles
                    newTextView.layoutParams = params2
                    newTextView.text = nombre

                    newTextView.gravity = Gravity.CENTER
                    binding.casosPersona.addView(newTextView)
                }

            } else {
                Toast.makeText(
                    requireActivity(), "Error al cargar los casos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}