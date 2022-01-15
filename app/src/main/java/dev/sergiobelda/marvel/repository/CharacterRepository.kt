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

package dev.sergiobelda.marvel.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.sergiobelda.marvel.data.Result
import dev.sergiobelda.marvel.model.Character
import dev.sergiobelda.marvel.network.Constants
import dev.sergiobelda.marvel.pagingdatasource.CharacterPagingSource
import dev.sergiobelda.marvel.remotedatasource.ICharacterRemoteDataSource
import kotlinx.coroutines.flow.Flow

class CharacterRepository(
    private val characterRemoteDataSource: ICharacterRemoteDataSource,
    private val characterPagingSource: CharacterPagingSource
) : ICharacterRepository {

    override suspend fun getCharacters(): Result<List<Character>> =
        characterRemoteDataSource.getCharacters()

    override fun getCharactersPaging(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.API_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { characterPagingSource }
        ).flow
    }

    override suspend fun getCharacter(id: Int): Result<Character?> =
        characterRemoteDataSource.getCharacter(id)
}
