@file:OptIn(ExperimentalPagingApi::class)

package com.pakollya.moviecollection.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pakollya.moviecollection.utils.BASE_URL
import com.pakollya.moviecollection.data.api.Api
import com.pakollya.moviecollection.data.api.MovieApiService
import com.pakollya.moviecollection.data.database.CacheDataSource.BaseDataSource
import com.pakollya.moviecollection.domain.MovieInteractor
import com.pakollya.moviecollection.presentation.detail.DetailContract
import com.pakollya.moviecollection.presentation.detail.DetailPresenter
import com.pakollya.moviecollection.presentation.main.MainContract
import com.pakollya.moviecollection.presentation.main.MainPresenter
import com.pakollya.moviecollection.utils.API_KEY
import com.pakollya.moviecollection.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideMovieApiService(api: Api): MovieApiService = MovieApiService(api, API_KEY)
}

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context) = BaseDataSource(context.applicationContext, DATABASE_NAME)
}

@Module(includes = [ApiModule::class, DatabaseModule::class])
class RemoteMediatorModule

@Module(includes = [ApiModule::class])
class PagingSourceModule

@Module(includes = [RemoteMediatorModule::class])
class RepositoryModule

@Module(includes = [RepositoryModule::class])
class InteractorModule

@Module(includes = [InteractorModule::class])
class PresenterModule {
    @Provides
    fun provideMainPresenter(movieInteractor: MovieInteractor): MainContract.Presenter = MainPresenter(movieInteractor)

    @Provides
    fun provideDetailPresenter(movieInteractor: MovieInteractor): DetailContract.Presenter = DetailPresenter(movieInteractor)
}

@Module(includes = [PresenterModule::class])
class AppModule