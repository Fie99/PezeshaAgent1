package com.pezesha.agent.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


class SharedPrefsManager private constructor(context: Context) {
    private val sharedPrefs: SharedPreferences

    init {
        val mainKey = MasterKey.Builder(context.applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPrefs = EncryptedSharedPreferences.create(
            context.applicationContext,
            PZA_FILE,
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    var appToken: String
        get() = sharedPrefs.getString(APP_TOKEN, "") ?: ""
        set(value) = putString(APP_TOKEN, value)

    var countryCode: String
        get() = sharedPrefs.getString(COUNTRY_CODE, "") ?: ""
        set(value) = putString(COUNTRY_CODE, value)

    var appFirstTimeUse: Boolean
        get() = sharedPrefs.getBoolean(APP_FIRST_TIME_USE, true)
        set(value) = putBoolean(APP_FIRST_TIME_USE, value)


    private fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPrefs.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun putString(key: String, value: String?) {
        val editor = sharedPrefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    companion object {
        private lateinit var sharedPrefsManager: SharedPrefsManager

        private const val PZA_FILE = "PZMPrefsFile"
        private const val APP_FIRST_TIME_USE = "app_first_time_use"
        private const val APP_TOKEN = "app_token"
        private const val COUNTRY_CODE = "country_code"

        @Synchronized
        fun getInstance(context: Context): SharedPrefsManager {
            return if (!this::sharedPrefsManager.isInitialized) {
                sharedPrefsManager = SharedPrefsManager(context)
                sharedPrefsManager
            } else sharedPrefsManager
        }
    }
}
