package com.codmine.mukellef.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.codmine.mukellef.data.remote.MobileOfficeApi
import com.codmine.mukellef.data.repository.DatastorePreferences
import com.codmine.mukellef.data.repository.DefaultFileOperations
import com.codmine.mukellef.data.repository.MobileOfficeRepositoryImpl
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.repository.CustomPreferences
import com.codmine.mukellef.domain.repository.CustomPreferences.Companion.DATA_FILE_KEY
import com.codmine.mukellef.domain.repository.FileOperations
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
    fun provideFileOperations(app: Application): FileOperations {
        return DefaultFileOperations(app)
    }

    @Provides
    @Singleton
    fun provideDatastorePreferences(app: Application): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                app.preferencesDataStoreFile(DATA_FILE_KEY)
            }
        )
    }

    @Provides
    @Singleton
    fun provideCustomPreferences(dataStorePreferences: DataStore<Preferences>): CustomPreferences {
        return DatastorePreferences(dataStorePreferences)
    }

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