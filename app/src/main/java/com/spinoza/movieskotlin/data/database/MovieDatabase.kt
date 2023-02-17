package com.spinoza.movieskotlin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.spinoza.movieskotlin.data.database.model.MovieDbModel

@Database(entities = [MovieDbModel::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "movies.db"
        private var db: MovieDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): MovieDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(context, MovieDatabase::class.java, DATABASE_NAME).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun movieDao(): MovieDao
}