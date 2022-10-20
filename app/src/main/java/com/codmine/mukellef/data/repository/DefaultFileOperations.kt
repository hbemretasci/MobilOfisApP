package com.codmine.mukellef.data.repository

import android.app.Application
import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.repository.FileOperations
import com.codmine.mukellef.domain.repository.FileOperations.Companion.AUTH
import com.codmine.mukellef.domain.repository.FileOperations.Companion.DOWNLOAD_URL
import com.codmine.mukellef.domain.util.UiText
import java.io.File
import javax.inject.Inject

class DefaultFileOperations @Inject constructor (
    private val application: Application
): FileOperations {

    override fun downloadFile(gib: String, fileName: String) {
        val url = "$DOWNLOAD_URL$gib/"
        val request = DownloadManager.Request(Uri.parse(url + fileName))
        request.setTitle(fileName)
        request.setDescription(UiText.StringResources(R.string.document_is_loading).asString(application))
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setAllowedOverMetered(true)
        request.setDestinationInExternalFilesDir(application, null, fileName)

        val dm = application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = dm.enqueue(request)

        val br = object: BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val id : Long? = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
                if (id == downloadId) showFile(fileName)
            }
        }
        application.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun fileExist(fileName: String): Boolean {
        val path = application.getExternalFilesDir(null)?.path
        return File(path, fileName).exists()
    }

    override fun showFile(fileName: String) {
        val isPdfTypeFile = (fileName.substringAfterLast(".") == "pdf")
        val filePath = application.getExternalFilesDir(null)?.path
        val pdfFile = File(filePath, fileName)
        val uri = getUriFromFile(pdfFile)

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
            application.startActivity(viewerIntent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun getUriFromFile(file: File): Uri {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Uri.fromFile(file)
        } else {
            FileProvider.getUriForFile(application, AUTH, file)
        }
    }

}