package com.example.textreader.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "text_details_table")
class TextDetails(
    val appName: String,
    val text: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}