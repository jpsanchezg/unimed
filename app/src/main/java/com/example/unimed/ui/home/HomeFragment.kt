package com.example.unimed.ui.home

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.unimed.databinding.FragmentHomeBinding
import com.example.unimed.ui.agenda.AgendaActivity
import com.example.unimed.ui.filtros.FiltrosFragment


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.citasmedicasBTN.setOnClickListener {
           Intent(activity, AgendaActivity::class.java).also {
               startActivity(it)


           }

        }

        val texts = listOf("12/12/2022", "14/12/2022")
        val horas = listOf("6 PM", "3 PM")


        for (text in texts) {
            val newTextView = TextView(activity)
            newTextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
            newTextView.background = requireContext().getDrawable(R.drawable.btn_default)
            newTextView.setPadding(5, 5, 5, 5)
            newTextView.textSize = 20f
            newTextView.setTextColor(requireContext().getColor(R.color.black))
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
            val newTextView = TextView(requireContext())
            newTextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            newTextView.background = requireContext().getDrawable(R.drawable.btn_default)
            newTextView.setPadding(5, 5, 5, 5)
            newTextView.textSize = 20f
            newTextView.setTextColor(requireContext().getColor(R.color.black))
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






        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}