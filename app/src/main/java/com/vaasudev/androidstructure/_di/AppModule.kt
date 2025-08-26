package com.vaasudev.androidstructure._di

import android.app.Application
import android.content.Context
import com.google.gson.GsonBuilder
import com.vaasudev.androidstructure.BuildConfig
import com.vaasudev.androidstructure.data.datastore.DataStore
import com.vaasudev.androidstructure.data.remote.ApiCallInterface
import com.vaasudev.androidstructure.data.remote.ApiObject
import com.vaasudev.androidstructure.data.repository.AuthRepositoryImpl
import com.vaasudev.androidstructure.domain.repository.AuthRepository
import com.vaasudev.androidstructure.domain.utility.printLog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideMyDataStore(context: Context): DataStore {
        return DataStore(context)
    }

    @Provides
    @Singleton
    fun provideShoppingateApi(dataStore: DataStore,context: Context): ApiCallInterface {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.callTimeout(60, TimeUnit.SECONDS)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.writeTimeout(60, TimeUnit.SECONDS)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)

        httpClient.networkInterceptors().add(Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
                .header(ApiObject.ApiHeaderKey.KEY, ApiObject.ApiHeaderValue.KEY_VALUE)

            val xApiKey = runBlocking {
                dataStore.getStringData(dataStore.apiKey).first()
            }

            if (xApiKey.isNotEmpty()) {
                requestBuilder.header(ApiObject.ApiHeaderKey.X_API_KEY, xApiKey)
            }

            printLog(tag = "Header", value = "${ApiObject.ApiHeaderKey.KEY} - ${ApiObject.ApiHeaderValue.KEY_VALUE}")
            printLog(tag = "Header", value = "${ApiObject.ApiHeaderKey.X_API_KEY} - $xApiKey")

            chain.proceed(requestBuilder.build())
        })

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient.build())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiCallInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(context: Context, api: ApiCallInterface): AuthRepository {
        return AuthRepositoryImpl(context, api)
    }
}