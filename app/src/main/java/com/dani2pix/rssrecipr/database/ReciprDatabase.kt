package com.dani2pix.rssrecipr.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by dandomnica on 2018-04-22.
 */
@Database(entities = arrayOf(Articles::class), version = 1)
abstract class ReciprDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao

    companion object {
        private var INSTANCE: ReciprDatabase? = null

        fun getInstance(context: Context): ReciprDatabase? {
            if (INSTANCE == null) {
                synchronized(ReciprDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ReciprDatabase::class.java, "reciprDB")
                            .build()
                }
            }
            return INSTANCE
        }
    }
}