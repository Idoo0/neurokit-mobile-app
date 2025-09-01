package com.example.neurokit.ui.warmup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neurokit.databinding.FragmentWarmupEndBinding
import java.util.concurrent.TimeUnit

class WarmupEndFragment : Fragment() {

    private var _binding: FragmentWarmupEndBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWarmupEndBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil semua data dari argumen
        val finalScore = arguments?.getInt("totalScore") ?: 0
        val totalCorrect = arguments?.getInt("totalCorrect") ?: 0
        val totalTimeSeconds = arguments?.getLong("totalTimeSeconds") ?: 0L

        // 1. Tampilkan skor (NAMA VARIABEL DIPERBAIKI)
        binding.textStatPoints.text = finalScore.toString()

        // 2. Hitung dan tampilkan waktu total (NAMA VARIABEL DIPERBAIKI)
        val minutes = TimeUnit.SECONDS.toMinutes(totalTimeSeconds)
        val seconds = totalTimeSeconds - TimeUnit.MINUTES.toSeconds(minutes)
        binding.textStatTime.text = String.format("%02d:%02d", minutes, seconds)

        // 3. Hitung dan tampilkan persentase (NAMA VARIABEL DIPERBAIKI)
        val totalQuestions = 8
        val percentage = if (totalQuestions > 0) {
            (totalCorrect * 100) / totalQuestions
        } else {
            0
        }
        binding.textStatAccuracy.text = "$percentage%"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}