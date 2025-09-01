package com.example.neurokit.ui.warmup

import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.neurokit.R
import com.example.neurokit.databinding.FragmentWarmupQuestionBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class WarmupQuestionFragment : Fragment(), TextToSpeech.OnInitListener {

    private var _binding: FragmentWarmupQuestionBinding? = null
    private val binding get() = _binding!!

    // Variabel state kuis
    private var currentQuestionIndex = 0
    private var totalScore = 0
    private val totalQuestions = 8
    private lateinit var currentDifficulty: String
    private lateinit var currentAnswer: String

    // Variabel baru untuk melacak statistik
    private var totalCorrectAnswers = 0
    private var quizStartTime = 0L

    // Variabel Suara dan Timer
    private var tts: TextToSpeech? = null
    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWarmupQuestionBinding.inflate(inflater, container, false)
        currentDifficulty = arguments?.getString("difficulty") ?: "easy"
        tts = TextToSpeech(requireContext(), this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startNewQuestion()

        binding.buttonSubmit.setOnClickListener {
            timer?.cancel()
            val userAnswer = binding.editTextAnswer.text.toString()
            val isCorrect = userAnswer == currentAnswer

            if (isCorrect) {
                totalScore += currentAnswer.length * 10
                totalCorrectAnswers++ // Tambahkan jumlah jawaban benar
            }

            if (currentQuestionIndex >= totalQuestions) {
                // Hitung total waktu yang berlalu
                val totalTimeMillis = System.currentTimeMillis() - quizStartTime
                val totalTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(totalTimeMillis)

                // Kirim semua data ke layar akhir
                val bundle = Bundle().apply {
                    putInt("totalScore", totalScore)
                    putInt("totalCorrect", totalCorrectAnswers)
                    putLong("totalTimeSeconds", totalTimeSeconds)
                }
                findNavController().navigate(R.id.action_warmupQuestionFragment_to_warmupEndFragment, bundle)
            } else {
                val bundle = Bundle().apply {
                    putBoolean("isCorrect", isCorrect)
                }
                findNavController().navigate(R.id.action_warmupQuestionFragment_to_warmupResultFragment, bundle)
            }
        }
    }

    private fun startNewQuestion() {
        // Catat waktu mulai saat soal pertama dimulai
        if (currentQuestionIndex == 0) {
            quizStartTime = System.currentTimeMillis()
        }
        currentQuestionIndex++

        timer?.cancel()

        binding.editTextAnswer.text.clear()
        binding.progressBar.max = totalQuestions
        binding.progressBar.progress = currentQuestionIndex
        currentAnswer = generateDigits(currentDifficulty)
        displayDigitsSequentially()
    }

    private fun displayDigitsSequentially() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.editTextAnswer.visibility = View.INVISIBLE
            binding.buttonSubmit.isEnabled = false
            binding.textViewDigit.visibility = View.VISIBLE
            delay(1000)

            for (digit in currentAnswer) {
                binding.textViewDigit.text = digit.toString()
                tts?.speak(digit.toString(), TextToSpeech.QUEUE_ADD, null, null)
                delay(750)
                binding.textViewDigit.text = ""
                delay(250)
            }

            binding.editTextAnswer.visibility = View.VISIBLE
            binding.buttonSubmit.isEnabled = true
            startAnswerTimer()
        }
    }

    private fun startAnswerTimer() {
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Tampilkan sisa waktu
            }
            override fun onFinish() {
                binding.editTextAnswer.setText("waktu habis")
                binding.buttonSubmit.performClick()
            }
        }.start()
    }

    private fun generateDigits(difficulty: String): String {
        val length = when (difficulty) {
            "easy" -> Random.nextInt(3, 6)
            "medium" -> Random.nextInt(5, 8)
            "hard" -> Random.nextInt(7, 10)
            else -> 3
        }
        return (1..length).map { Random.nextInt(0, 10) }.joinToString("")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale("id", "ID"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Bahasa Indonesia tidak didukung.")
            }
        } else {
            Log.e("TTS", "Inisialisasi TTS Gagal!")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tts?.stop()
        tts?.shutdown()
        timer?.cancel()
        _binding = null
    }
}