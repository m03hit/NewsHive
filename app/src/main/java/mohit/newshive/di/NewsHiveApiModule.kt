package mohit.newshive.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mohit.newshive.data.remoteAPI.NewsApi
import mohit.newshive.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NewsHiveApiModule {
    @Singleton
    @Provides
    fun provideRetrofitService(): NewsApi {
        val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient: OkHttpClient =
            OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_FOR_NEWS_API)
            .addConverterFactory(MoshiConverterFactory.create()).client(okHttpClient)
            .build()
            .create(NewsApi::class.java)
    }

}