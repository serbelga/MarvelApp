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

package dev.sergiobelda.marvel.network.service

import dev.sergiobelda.marvel.network.Constants
import dev.sergiobelda.marvel.network.model.CharacterApiModel
import dev.sergiobelda.marvel.network.model.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int = Constants.API_STARTING_PAGE_INDEX,
        @Query("limit") limit: Int = Constants.API_PAGE_SIZE
    ): Response<MarvelResponse<CharacterApiModel>>

    @GET("/v1/public/characters/{id}")
    suspend fun getCharacterDetail(@Path("id") id: Int): Response<MarvelResponse<CharacterApiModel>>
}
