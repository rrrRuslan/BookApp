package com.books.app

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.books.app.firebase.FirebaseRemoteConfigRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val textView = findViewById<TextView>(R.id.text_view)
//        val rep = FirebaseRemoteConfigRepository()
//
//        textView.text = rep.getJson()
//        Log.i("OBJECT FIREBASE CONF", "onCreate: ${rep.getResponseObject()}")
    }
}