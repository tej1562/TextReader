package com.example.textreader.services

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.textreader.db.entities.AppInfo
import com.example.textreader.db.TextInfoDatabase
import com.example.textreader.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates
import android.graphics.Bitmap
import android.graphics.Canvas

import android.graphics.drawable.BitmapDrawable

import android.graphics.drawable.Drawable
import com.example.textreader.db.entities.TextDetails

class TextReaderService : AccessibilityService() {

    private val TAG = "TEXT_READER_SERVICE"

    private var mDebugDepth by Delegates.notNull<Int>()

    val repository = MainRepository(TextInfoDatabase(this))

    private lateinit var appName: String
    private lateinit var appImg: Bitmap

    private fun printAllViews(mNodeInfo: AccessibilityNodeInfo?) {
        if (mNodeInfo == null) return

        /* Save data to database */
        if (mNodeInfo.text != null) {
            val appInfo = AppInfo(appImg, appName)
            val textDetails = TextDetails(appName, mNodeInfo.text.toString())
            CoroutineScope(Dispatchers.IO).launch {
                repository.insertAppInfo(appInfo)
                repository.insertTextDetails(textDetails)
            }
        }

        if (mNodeInfo.childCount < 1) return
        mDebugDepth++

        for (i in 0 until mNodeInfo.childCount) {
            printAllViews(mNodeInfo.getChild(i))
        }

        mDebugDepth--
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val width = if (!drawable.bounds.isEmpty) drawable
            .bounds.width() else drawable.intrinsicWidth
        val height = if (!drawable.bounds.isEmpty) drawable
            .bounds.height() else drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(
            if (width <= 0) 1 else width,
            if (height <= 0) 1 else height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        mDebugDepth = 0
        val mNodeInfo = event?.source

        val packageName = event?.packageName.toString()
        val packageManager = this.packageManager

        try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            val applicationLabel = packageManager.getApplicationLabel(applicationInfo)

            val icon = packageManager.getApplicationIcon(applicationInfo)
            val result = drawableToBitmap(icon)

            appName = applicationLabel.toString()
            appImg = result

        } catch (ex: PackageManager.NameNotFoundException) {
            ex.printStackTrace()
        }
        printAllViews(mNodeInfo);
    }

    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        val info = AccessibilityServiceInfo()

        info.apply {

            eventTypes =
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED

            feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN

            notificationTimeout = 100
        }

        this.serviceInfo = info
    }
}