package com.cronpay.cronpaysdkexample.di

import android.content.Context
import com.cronpay.cronpaysdkexample.constant.Constants
import com.cronpay.cronpaysdkexample.repository.AuthRepositoryImpl
import com.cronpay.cronpaysdkexample.service.ExampleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDefaultAuthRepository(
        exampleService: ExampleService,
    ): AuthRepositoryImpl{
        return AuthRepositoryImpl( exampleService)
    }

    @Singleton
    @Provides
    fun provideExampleService(): ExampleService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(ExampleService::class.java)
    }


}

