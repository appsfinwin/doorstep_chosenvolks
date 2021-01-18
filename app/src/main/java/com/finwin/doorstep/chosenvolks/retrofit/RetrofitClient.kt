package com.finwin.doorstep.chosenvolks.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    private var instance: Retrofit? = null
   // private var retrofitIfsc: Retrofit? = null

    public fun RetrofitClient(): Retrofit? {
        if (instance == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(300, TimeUnit.SECONDS)
                .build()

            instance = Retrofit.Builder()
                .baseUrl("http://doorstep.rvgn.digicob.in/")
                //.baseUrl("http://doorstep.tunl.digicob.in/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
            return  instance
    }

}