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

package dev.sergiobelda.marvel.pagingdatasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.sergiobelda.marvel.data.Result
import dev.sergiobelda.marvel.model.Character
import dev.sergiobelda.marvel.network.Constants.API_PAGE_SIZE
import dev.sergiobelda.marvel.network.Constants.API_STARTING_PAGE_INDEX
import dev.sergiobelda.marvel.network.mapper.CharacterMapper.toDomainModel
import dev.sergiobelda.marvel.network.safeApiCall
import dev.sergiobelda.marvel.network.service.CharacterService

class CharacterPagingSource(private val characterService: CharacterService) :
    PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = params.key ?: API_STARTING_PAGE_INDEX
        val result = safeApiCall {
            val offset = position * API_PAGE_SIZE
            characterService.getCharacters(offset, params.loadSize)
        }
        return when (result) {
            is Result.Success -> {
                val characters = result.value.data.results.map { it.toDomainModel() }
                val nextKey = if (characters.isEmpty()) {
                    null
                } else {
                    position + (params.loadSize / API_PAGE_SIZE)
                }
                LoadResult.Page(
                    data = characters,
                    prevKey = if (position == API_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = nextKey
                )
            }
            is Result.Error -> {
                LoadResult.Error(result.exception)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
