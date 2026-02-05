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

package dev.sergiobelda.marvel.data.pagingdatasource

import androidx.paging.PagingSource
import dev.sergiobelda.marvel.data.network.mapper.CharacterMapper.toDomainModel
import dev.sergiobelda.marvel.data.network.service.CharacterService
import dev.sergiobelda.marvel.data.testutil.characterApiModel
import dev.sergiobelda.marvel.data.testutil.createMarvelResponse
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterPagingDataSourceTest {
    @MockK
    private val characterService = mockk<CharacterService>()

    private val characterPagingDataSource = CharacterPagingDataSource(characterService)

    @Test
    fun testLoad() =
        runTest {
            val characters = listOf(characterApiModel)

            coEvery { characterService.getCharacters(0, 1) } returns
                Response.success(
                    createMarvelResponse(characters),
                )

            val charactersDomain = characters.map { it.toDomainModel() }
            assertEquals(
                PagingSource.LoadResult.Page(
                    data = charactersDomain,
                    prevKey = null,
                    nextKey = 0,
                ),
                characterPagingDataSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 1,
                        placeholdersEnabled = false,
                    ),
                ),
            )
        }
}
