package com.pakollya.moviecollection.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.pakollya.moviecollection.R
import com.pakollya.moviecollection.SPLASH_TIMER
import com.pakollya.moviecollection.presentation.main.MainActivity

@ExperimentalPagingApi
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIMER)
    }
}