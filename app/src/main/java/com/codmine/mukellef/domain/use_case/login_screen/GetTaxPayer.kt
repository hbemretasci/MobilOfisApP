package com.codmine.mukellef.domain.use_case.login_screen

import android.content.Context
import com.codmine.mukellef.R
import com.codmine.mukellef.data.remote.dto.tax_payer.toTaxPayer
import com.codmine.mukellef.domain.model.tax_payer.TaxPayer
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants.QUERY_MUKELLEF
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.presentation.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTaxPayer @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    operator fun invoke(
        gib: String, vk: String, password: String, context: Context
    ): Flow<Resource<TaxPayer>> = flow {
        try {
            emit(Resource.Loading())
            val taxPayer = repository.getTaxPayer(QUERY_MUKELLEF, gib, vk, password).toTaxPayer()
            emit(Resource.Success(taxPayer))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: UiText.StringResources(R.string.unexpected_error).asString(context)))
        } catch(e: IOException) {
            emit(Resource.Error(UiText.StringResources(R.string.internet_error).asString(context)))
        }
    }
}