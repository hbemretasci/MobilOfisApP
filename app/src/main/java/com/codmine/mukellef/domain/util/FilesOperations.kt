package com.codmine.mukellef.domain.util

import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider.getUriForFile
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.util.Constants.DOWNLOAD_URL
import com.codmine.mukellef.presentation.util.UiText
import java.io.File

fun downloadFile(gib: String, fileName: String, context: Context) {
    val url = "$DOWNLOAD_URL$gib/"
    val request = DownloadManager.Request(Uri.parse(url + fileName))
    request.setTitle(fileName)
    request.setDescription(UiText.StringResources(R.string.document_is_loading).asString(context))
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setAllowedOverMetered(true)
    request.setDestinationInExternalFilesDir(context, null, fileName)

    val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val downloadId = dm.enqueue(request)

    val br = object: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val id : Long? = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
            if (id == downloadId) {
                showFile(fileName, context)
            }
        }
    }
    context.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
}

fun fileExist(fileName: String, context: Context): Boolean {
    val path = context.getExternalFilesDir(null)?.path
    return File(path, fileName).exists()
}

fun showFile(fileName: String, context: Context) {
    val isPdfTypeFile = (fileName.substringAfterLast(".") == "pdf")
    val filePath = context.getExternalFilesDir(null)?.path
    val pdfFile = File(filePath, fileName)
    val uri = getUriFromFile(pdfFile, context)

    val pdfIntent: Intent = if (isPdfTypeFile) {
        Intent(Intent.ACTION_VIEW)
            .setDataAndType(uri, "application/pdf")
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
    } else {
        Intent(Intent.ACTION_VIEW)
            .setDataAndType(uri, "image/*")
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
    }

    val viewerIntent = Intent.createChooser(pdfIntent, "Open File")
    viewerIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    try {
        context.startActivity(viewerIntent)
    } catch (e: ActivityNotFoundException) {
        println(e)
    }
}

private fun getUriFromFile(file: File, context: Context): Uri {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        Uri.fromFile(file)
    } else {
        getUriForFile(context, "com.codmine.mukellef.fileprovider", file)
    }
}