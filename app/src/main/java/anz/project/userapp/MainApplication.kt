package anz.project.userapp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    companion object {
        private const val TAG = "MainApplication"
    }

    override fun onCreate() {
        super.onCreate()
        try {
            Log.d(TAG, "MainApplication initialized successfully")
            // Add any future app-wide initialization here
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing MainApplication", e)
            throw e // Re-throw to ensure crash is reported
        }
    }
}