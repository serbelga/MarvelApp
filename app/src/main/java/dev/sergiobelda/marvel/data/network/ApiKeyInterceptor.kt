/*
 * Copyright 2022 Sergio Belda Galbis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.marvel.data.network

import dev.sergiobelda.marvel.extensions.md5
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class ApiKeyInterceptor(
    private val publicApiKey: String,
    private val privateApiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val ts = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()).toString()
        val hash = generateHash(ts)

        val request = chain.request()

        val newUrl = request.url.newBuilder()
            .addQueryParameter(PARAM_TS, ts)
            .addQueryParameter(PARAM_API_KEY, publicApiKey)
            .addQueryParameter(PARAM_HASH, hash)
            .build()

        val newRequest = request.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }

    /**
     * Generate hash parameter given [ts], [privateApiKey] and [publicApiKey].
     * [ts] is a timestamp (or other long string which can change on a request-by-request basis).
     *
     * [Info](https://developer.marvel.com/documentation/authorization)
     */
    private fun generateHash(ts: String): String =
        (ts + privateApiKey + publicApiKey).md5()

    companion object {
        private const val PARAM_TS = "ts"
        private const val PARAM_API_KEY = "apikey"
        private const val PARAM_HASH = "hash"
    }
}
