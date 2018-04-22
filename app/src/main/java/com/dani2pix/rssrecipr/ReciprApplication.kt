package com.dani2pix.rssrecipr

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by dandomnica on 2018-04-22.
 */
class ReciprApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(applicationContext)
    }
}