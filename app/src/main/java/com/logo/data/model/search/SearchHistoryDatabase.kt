package com.logo.data.model.search

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SearchHistory::class], version = 1, exportSchema = false)
abstract class SearchHistoryDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: SearchHistoryDatabase? = null

        fun getDatabase(context: Context): SearchHistoryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SearchHistoryDatabase::class.java,
                    "search_history"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}