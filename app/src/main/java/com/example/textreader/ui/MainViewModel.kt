package com.example.textreader.ui

import androidx.lifecycle.ViewModel
import com.example.textreader.repository.MainRepository

class MainViewModel(
    val mainRepository: MainRepository
) : ViewModel() {

    fun getSavedAppInfo() = mainRepository.getSavedAppInfo()

    fun getTextFromAppName(appName: String) = mainRepository.getSavedTextFromAppName(appName)

}