package com.vinted.demovinted.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.vinted.demovinted.BuildConfig
import com.vinted.demovinted.data.repository.remote.api.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkingModule {

    @Provides
    fun providesInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )
        return interceptor
    }

    @Provides
    fun providesOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(false)
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    fun providesJson(): Json = Json {
        isLenient = true
        encodeDefaults = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    fun providesRetrofit(
        client: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    fun providesApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
}