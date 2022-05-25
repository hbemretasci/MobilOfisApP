package com.codmine.mukellef.di

import com.codmine.mukellef.data.remote.MobileOfficeApi
import com.codmine.mukellef.data.repository.MobileOfficeRepositoryImpl
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMobileOfficeApi(): MobileOfficeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MobileOfficeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMobileOfficeRepository(api: MobileOfficeApi): MobileOfficeRepository {
        return MobileOfficeRepositoryImpl(api)
    }

}