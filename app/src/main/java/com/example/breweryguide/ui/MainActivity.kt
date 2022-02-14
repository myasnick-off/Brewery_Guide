package com.example.breweryguide.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.breweryguide.R
import com.example.breweryguide.ui.list.ListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListFragment.newInstance(), "")
            .commit()
    }
}