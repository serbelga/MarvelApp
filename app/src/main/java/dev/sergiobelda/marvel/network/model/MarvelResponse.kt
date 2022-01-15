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

package dev.sergiobelda.marvel.network.model

import com.squareup.moshi.JsonClass

/**
 * Response from API.
 * @param code The HTTP status code of the returned result.
 * @param data The results returned by the call.
 */
@JsonClass(generateAdapter = true)
data class MarvelResponse<T>(
    val code: Int,
    val data: MarvelData<T>
)
