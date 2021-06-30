package com.example.textreader.repository

import com.example.textreader.db.entities.AppInfo
import com.example.textreader.db.entities.TextDetails
import com.example.textreader.db.TextInfoDatabase

class MainRepository(
    val db: TextInfoDatabase
) {
    fun getSavedAppInfo() = db.getArticleDao().getAllAppInfo()

    fun getSavedTextFromAppName(appName: String) = db.getArticleDao().getTextWithAppName(appName)

    suspend fun insertAppInfo(appInfo: AppInfo) = db.getArticleDao().insertAppInfo(appInfo)

    suspend fun insertTextDetails(textDetails: TextDetails) =
        db.getArticleDao().insertTextDetails(textDetails)
}