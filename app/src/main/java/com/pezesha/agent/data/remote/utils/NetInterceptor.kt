package com.pezesha.agent.data.remote.utils

import com.pezesha.agent.utils.GlobalMethods
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetInterceptor constructor(
    val globalMethods: GlobalMethods,
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain,
    ): Response {

        var request: Request = chain.request()

        if (!globalMethods.isConnected()) {
            throw NoConnectivityException()
        }
//
//        val host = PezeshaMarketplaceApp.sharedPrefsManager?.apiBaseURL ?: BuildConfig.AUTH_BASE_URL
//
//        val scheme = BuildConfig.URL_SCHEME.replace("://", "")
//        val newUrl = request.url.newBuilder()
//            .host(host)
//            .scheme(scheme)
//            .build()
//
//        request = request.newBuilder()
//            .url(newUrl)
//            .addHeader("Authorization", "Bearer ${PezeshaMarketplaceApp.sharedPrefsManager?.appAccessToken}")
//            .build()
        return chain.proceed(request)
    }
}
