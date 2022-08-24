package com.codmine.mukellef.domain.use_case.balance_screen

import com.codmine.mukellef.R
import com.codmine.mukellef.data.remote.dto.balance.toTransaction
import com.codmine.mukellef.domain.model.balance.Transaction
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTransactions @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    operator fun invoke(
        gib: String, vk: String, password: String, sender: String): Flow<Resource<List<Transaction>>> = flow {
        try {
            emit(Resource.Loading())
            val transactions = repository.getBalance(
                Constants.QUERY_BALANCE, gib, vk, password, sender
            ).transactions.map { it.toTransaction() }
            emit(Resource.Success(transactions))
        } catch(e: HttpException) {
            emit(Resource.Error(message = UiText.StringResources(R.string.server_connection_error)))
        } catch(e: IOException) {
            emit(Resource.Error(message = UiText.StringResources(R.string.server_connection_error)))
        }
    }
}