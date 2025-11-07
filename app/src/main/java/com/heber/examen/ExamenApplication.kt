package com.heber.examen

import android.app.Application
import com.heber.examen.data.database.DataBase

class ExamenApplication : Application() {

    val database by lazy { DataBase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: ExamenApplication
            private set
    }
}
