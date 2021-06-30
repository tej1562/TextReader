package com.example.textreader.db.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "app_info_table", indices = [Index(value = ["appName"], unique = true)])
data class AppInfo(
    var appImg: Bitmap? = null,
    @ColumnInfo(name = "appName") val appName: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}