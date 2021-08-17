package com.books.app.ui.splash_screen

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.books.app.MainActivity
import com.books.app.R
import com.books.app.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.progressBar.progressDrawable.setColorFilter(
            Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN
        )
        val context = baseContext
        lifecycleScope.launch {
            for (i in 1..100) {
                binding.progressBar.progress = i
                delay(20)
            }
            startActivity(Intent(context, MainActivity::class.java))
            finish()
        }
    }


}