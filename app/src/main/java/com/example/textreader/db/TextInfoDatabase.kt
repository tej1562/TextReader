package com.example.textreader.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.textreader.db.entities.AppInfo
import com.example.textreader.db.entities.TextDetails

@Database(
    entities = [AppInfo::class, TextDetails::class],
    version = 1
)

@TypeConverters(Convertors::class)
abstract class TextInfoDatabase : RoomDatabase() {

    abstract fun getArticleDao(): TextInfoDAO

    companion object {
        @Volatile
        private var instance: TextInfoDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TextInfoDatabase::class.java,
                "text_info_db.db"
            )
                .build()
    }
}