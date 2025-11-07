package com.heber.exam

import android.app.Application
import com.heber.exam.data.database.DataBase

class ExamApplication : Application() {

    val database by lazy { DataBase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: ExamApplication
            private set
    }
}
