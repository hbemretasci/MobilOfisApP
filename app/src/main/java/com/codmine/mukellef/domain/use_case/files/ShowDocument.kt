package com.codmine.mukellef.domain.use_case.files

import com.codmine.mukellef.domain.repository.FileOperations
import javax.inject.Inject

class ShowDocument @Inject constructor(
    private val fileOperations: FileOperations
) {
    operator fun invoke(gib: String, documentName: String) {
        if (!fileOperations.fileExist(documentName)) {
            fileOperations.downloadFile(gib, documentName)
        } else {
            fileOperations.showFile(documentName)
        }
    }
}