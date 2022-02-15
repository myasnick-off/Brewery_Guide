package com.example.breweryguide.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.*
import androidx.appcompat.app.AppCompatActivity
import com.example.breweryguide.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val handler: Handler by lazy {
        Handler(mainLooper)
    }

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val animationUp = TranslateAnimation(
            Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f,
            Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, -200.0f
        ).apply {
            duration = 400
            startOffset = 100
        }
        val animationDown = TranslateAnimation(
            Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f,
            Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 200.0f
        ).apply {
            duration = 100
            startOffset = 500
        }
        val animationSet = AnimationSet(true).apply {
            addAnimation(animationUp)
            addAnimation(animationDown)
        }
        binding.cupImageView.startAnimation(animationSet)
        binding.foamImageView.startAnimation(animationSet)
        binding.subFoamImageView.startAnimation(animationSet)

        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}