package com.example.neurokit.ui.warmup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neurokit.databinding.FragmentWarmupResultBinding

class WarmupResultFragment : Fragment() {

    private var _binding: FragmentWarmupResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWarmupResultBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // TODO: Tampilkan pesan "Benar" atau "Salah" dan logika tombol "Lanjut"

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}