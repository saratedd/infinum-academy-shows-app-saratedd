package com.example.shows_saratedd

import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit

object ApiModule {
    private const val BASE_URL = "https://tv-shows.infinum.academy/"
    
    lateinit var retrofit: ShowsApiService

    fun initRetrofit(context: Context) {
        val okhttp = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor.Builder(context).build())
            .addInterceptor(AuthInterceptor(
                context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE))
            )
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okhttp)
            .build()
            .create(ShowsApiService::class.java)
    }
}

class AuthInterceptor(sharedPreferences: SharedPreferences): Interceptor {
    companion object {
        private const val TOKEN_TYPE = "Bearer"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
//        builder.addHeader("uid", /* citam iz pref*/)
        builder.addHeader("token-type", TOKEN_TYPE)
        builder.addHeader("access-token", "7LDzAqivNbPjTy5eXGawTA")
        builder.addHeader("client", "0Eqd0YnW1TlDgWV88Va9QQ")
        builder.addHeader("uid", "aa@aa.hr")
        return chain.proceed(builder.build())
    }

}