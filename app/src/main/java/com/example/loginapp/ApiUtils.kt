package com.example.loginapp

import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiUtils {
    companion object {

        private var okHttpClient: OkHttpClient? = null
        private var retrofit: Retrofit? = null
        private var gson: Gson? = null
        private var serverApi: ServerApi? = null

        fun getBasicAuthClient(
            email: String,
            password: String,
            newInstance: Boolean
        ): OkHttpClient {
            if (newInstance || okHttpClient == null) {
                val builder = OkHttpClient.Builder()
                builder.authenticator(object : Authenticator {
                    override fun authenticate(route: Route?, response: Response): Request {
                        val credentials: String = Credentials.basic(email, password)
                        return response.request.newBuilder().header("authorization", credentials)
                            .build()
                    }
                })
                builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                okHttpClient = builder.build()
            }
            return okHttpClient!!
        }

        fun getRetrofit(): Retrofit {
            if (gson == null) {
                gson = Gson()
            }
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    .client(getBasicAuthClient("", "", false))
                    .addConverterFactory(GsonConverterFactory.create(gson!!))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return retrofit!!
        }

        fun getApiService(): ServerApi {
            if (serverApi == null) {
                serverApi = getRetrofit().create(ServerApi::class.java)
            }
            return serverApi!!
        }
    }
}


