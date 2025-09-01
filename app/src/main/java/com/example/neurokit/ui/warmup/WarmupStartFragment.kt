package com.example.neurokit.ui.warmup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neurokit.databinding.FragmentWarmupStartBinding

class WarmupStartFragment : Fragment() {

    private var _binding: FragmentWarmupStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWarmupStartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // TODO: Tambahkan logika untuk tombol-tombol di sini

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}