package com.example.climafilm.data.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.climafilm.data.source.local.db.converter.Converters
import com.example.climafilm.data.source.local.db.dao.MovieDao
import com.example.climafilm.data.source.local.db.dao.MovieDetailDao
import com.example.climafilm.data.source.local.db.entity.MovieDetailEntity
import com.example.climafilm.data.source.local.db.entity.MovieEntity

@Database(entities = [MovieEntity::class, MovieDetailEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieDetailDao(): MovieDetailDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDataBase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}