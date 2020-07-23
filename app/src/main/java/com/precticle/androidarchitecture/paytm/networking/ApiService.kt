package com.precticle.androidarchitecture.paytm.networking

import android.content.Context
import android.text.TextUtils
import com.precticle.androidarchitecture.BuildConfig
import com.precticle.androidarchitecture.MyApplication
import com.precticle.test.util.SharedPrefrence
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {


    private var retrofit: Retrofit? = null
    private val REQUEST_TIMEOUT = 15
    private var okHttpClient: OkHttpClient? = null

    val client: Retrofit?
        get() {
            if (okHttpClient == null) initOkHttp()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

    private fun initOkHttp() {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)

        // TODO - add no internet connection interceptor
        httpClient.addInterceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")

            // adding auth token

            // adding auth token
            val token: String? = SharedPrefrence.getAuthToken(MyApplication.instance!!.applicationContext)
              if (!TextUtils.isEmpty(token)) {
              requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        okHttpClient = httpClient.build()
    }
}