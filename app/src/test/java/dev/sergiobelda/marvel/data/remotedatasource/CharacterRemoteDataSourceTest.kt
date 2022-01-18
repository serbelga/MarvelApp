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

package dev.sergiobelda.marvel.data.remotedatasource

import dev.sergiobelda.marvel.data.Result
import dev.sergiobelda.marvel.data.network.mapper.CharacterMapper.toDomainModel
import dev.sergiobelda.marvel.data.network.service.CharacterService
import dev.sergiobelda.marvel.data.testutil.characterApiModel
import dev.sergiobelda.marvel.data.testutil.createMarvelResponse
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterRemoteDataSourceTest {

    @MockK
    private val characterService = mockk<CharacterService>()

    @MockK
    private val responseBody = mockk<ResponseBody>(relaxed = true)

    private val characterRemoteDataSource: ICharacterRemoteDataSource =
        CharacterRemoteDataSource(characterService)

    @Test
    fun testGetCharacter() = runTest {
        coEvery { characterService.getCharacter(1) } returns Response.success(
            createMarvelResponse(listOf(characterApiModel))
        )

        val result = characterRemoteDataSource.getCharacter(1)

        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).value, characterApiModel.toDomainModel())
    }

    @Test
    fun testGetCharacterError() = runTest {

        coEvery { characterService.getCharacter(1) } returns Response.error(404, responseBody)

        val result = characterRemoteDataSource.getCharacter(1)

        assertTrue(result is Result.Error)
    }
}
