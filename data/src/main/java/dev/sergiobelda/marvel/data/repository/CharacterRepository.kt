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

package dev.sergiobelda.marvel.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.sergiobelda.marvel.data.localdatasource.ICharacterLocalDataSource
import dev.sergiobelda.marvel.data.network.Constants
import dev.sergiobelda.marvel.data.pagingdatasource.CharacterPagingDataSource
import dev.sergiobelda.marvel.data.remotedatasource.ICharacterRemoteDataSource
import dev.sergiobelda.marvel.domain.ICharacterRepository
import dev.sergiobelda.marvel.domain.Result
import dev.sergiobelda.marvel.domain.doIfSuccess
import dev.sergiobelda.marvel.domain.model.Character
import kotlinx.coroutines.flow.Flow

class CharacterRepository(
    private val characterRemoteDataSource: ICharacterRemoteDataSource,
    private val characterPagingDataSource: CharacterPagingDataSource,
    private val characterLocalDataSource: ICharacterLocalDataSource
) : ICharacterRepository {

    override fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.API_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { characterPagingDataSource }
        ).flow
    }

    override suspend fun getCharacter(id: Int): Flow<Result<Character>> {
        val result = characterRemoteDataSource.getCharacter(id)
        result.doIfSuccess { character ->
            character?.let { characterLocalDataSource.insertCharacter(it) }
        }
        return characterLocalDataSource.getCharacter(id)
    }
}
