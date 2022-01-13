package kz.tengrilab.frredesign.api

import kz.tengrilab.frredesign.Variables
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private lateinit var retrofit: Retrofit

    fun getRetrofitClient() : Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Variables.url + Variables.port)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}