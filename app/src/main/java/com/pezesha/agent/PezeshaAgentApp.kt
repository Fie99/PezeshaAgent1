package com.pezesha.agent

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.pezesha.agent.data.local.prefs.SharedPrefsManager
import dagger.hilt.android.HiltAndroidApp
import io.sentry.android.core.SentryAndroid
import timber.log.Timber

@HiltAndroidApp
class PezeshaAgentApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPrefsManager = SharedPrefsManager.getInstance(applicationContext)

        // Disable Dark Theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Install a Timber instance - logging for lazy people
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        // Set the environment for Sentry reporting
        SentryAndroid.init(this) { options ->
            options.environment = BuildConfig.APP_ENV
        }
    }

    companion object {
        lateinit var instance: PezeshaAgentApp
        lateinit var sharedPrefsManager: SharedPrefsManager
    }
}
