package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.R
import com.codmine.mukellef.data.remote.dto.tax_payer.toRelatedUser
import com.codmine.mukellef.domain.model.chat.UnreadNotification
import com.codmine.mukellef.domain.model.tax_payer.RelatedUser
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
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
            Resource.Error((e.localizedMessage ?: UiText.StringResources(R.string.unexpected_error)) as UiText)
        } catch(e: IOException) {
            Resource.Error(UiText.StringResources(R.string.internet_error))
        }
    }
}