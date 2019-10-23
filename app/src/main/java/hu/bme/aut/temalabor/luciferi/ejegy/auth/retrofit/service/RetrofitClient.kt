package hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy

object RetrofitClient {
    private val cookieManager = CookieManager()
    init {
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

    }

    private val client = OkHttpClient.Builder()
        .cookieJar(object : CookieJar {
            private val cookieStore = HashMap<String, List<Cookie>>()

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore.put(url.host(),cookies)
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val cookies = cookieStore[url.host()]
                return cookies ?: ArrayList<Cookie>()
            }
        }).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://temalabor2019-backend.azurewebsites.net/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: RetrofitApi = retrofit.create(RetrofitApi::class.java)
}