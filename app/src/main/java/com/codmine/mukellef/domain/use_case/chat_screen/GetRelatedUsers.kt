package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.data.remote.dto.tax_payer.toRelatedUser
import com.codmine.mukellef.domain.model.chat.UnreadNotification
import com.codmine.mukellef.domain.model.tax_payer.RelatedUser
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRelatedUsers @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    suspend operator fun invoke(
        gib: String, vk: String, password: String, unReadList: List<UnreadNotification>): Resource<List<RelatedUser>> {
        return try {
            val relatedUsers = repository.getTaxPayer(Constants.QUERY_MUKELLEF, gib, vk, password)
                .user?.relatedUsers?.map { relatedUserDto ->
                    val unreadNotification = unReadList.firstOrNull { it.senderUserId == relatedUserDto.id }
                    relatedUserDto.toRelatedUser(unreadNotification?.unReadAmount)
                }
            Resource.Success(relatedUsers ?: emptyList())
        } catch(e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Beklenmeyen hata.")
        } catch(e: IOException) {
            Resource.Error("Sunucuya erişilemiyor, bağlantı hatası.")
        }
    }
}