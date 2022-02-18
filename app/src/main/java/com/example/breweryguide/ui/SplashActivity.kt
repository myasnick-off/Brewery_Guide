package com.example.breweryguide.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.*
import androidx.appcompat.app.AppCompatActivity
import com.example.breweryguide.databinding.ActivitySplashBinding

// Активити заставки приложения
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    // лениво создаем поток для отложенного старта основного активити приложения
    private val handler: Handler by lazy { Handler(mainLooper) }

//    private var _binding: ActivitySplashBinding? = null
//    private val binding get() = _binding!!

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // переменные и методы для создания анимации на экране заставки
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
        with(binding) {
            cupImageView.startAnimation(animationSet)
            foamImageView.startAnimation(animationSet)
            subFoamImageView.startAnimation(animationSet)
        }

        // запускаем поток для отложенного старта основного активити приложения
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