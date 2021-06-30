package com.example.textreader.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.textreader.db.entities.AppInfo
import com.example.textreader.db.entities.TextDetails

@Dao
interface TextInfoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppInfo(appInfo: AppInfo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTextDetails(textDetails: TextDetails): Long

    @Query("SELECT * FROM app_info_table")
    fun getAllAppInfo(): LiveData<List<AppInfo>>

    @Query("SELECT * FROM text_details_table WHERE appName = :appName")
    fun getTextWithAppName(appName: String): LiveData<List<TextDetails>>
}