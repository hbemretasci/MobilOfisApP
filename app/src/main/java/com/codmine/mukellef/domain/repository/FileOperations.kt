package com.codmine.mukellef.domain.repository

interface FileOperations {
    fun downloadFile(gib: String, fileName: String)
    fun fileExist(fileName: String): Boolean
    fun showFile(fileName: String)

    companion object {
        const val DOWNLOAD_URL = "http://ipeksu.com/mobil/files/"
        const val AUTH = "com.codmine.mukellef.fileprovider"
    }

}