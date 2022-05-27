package com.pakollya.moviecollection.presentation.base

import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.pakollya.moviecollection.R

abstract class BaseActivity: AppCompatActivity(), BaseContract.View {

    fun initializeToolbar(title: String?, clickEnabled: Boolean) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        if (toolbar != null) {
            setActionBar(toolbar)
            if (clickEnabled) {
                toolbar.setNavigationOnClickListener{ onBackPressed() }
            }
            val actionBar = actionBar
            if (actionBar != null) {
                title?.let { actionBar.title = it }
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_close)
            }
        }
    }
}