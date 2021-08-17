package com.books.app.firebase

import com.books.app.firebase.model.ConfigResponse
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.gson.Gson

class FirebaseRemoteConfigRepository  {

    private var remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    private val REMOTE_CONFIG_KEY = "json_data"
    private val gson = Gson()


    fun getJson() = remoteConfig[REMOTE_CONFIG_KEY].asString()

    fun getResponseObject(): ConfigResponse? {
        return gson.fromJson(getJson(), ConfigResponse::class.java)
    }




}