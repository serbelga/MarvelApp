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

package dev.sergiobelda.marvel.data.localdatasource

import dev.sergiobelda.marvel.data.database.dao.CharacterDao
import dev.sergiobelda.marvel.data.database.mapper.CharacterMapper.toDomainModel
import dev.sergiobelda.marvel.data.database.mapper.CharacterMapper.toEntity
import dev.sergiobelda.marvel.data.testutil.character
import dev.sergiobelda.marvel.data.testutil.characterEntity
import dev.sergiobelda.marvel.domain.Result
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterLocalDataSourceTest {

    @MockK
    private val characterDao: CharacterDao = mockk(relaxed = true)

    private val characterLocalDataSource = CharacterLocalDataSource(characterDao)

    @Test
    fun testGetCharacter() = runTest {
        every { characterDao.getCharacter(1) } returns flow {
            emit(characterEntity)
        }

        val result = characterLocalDataSource.getCharacter(1).firstOrNull()

        assertEquals(characterEntity.toDomainModel(), (result as Result.Success).value)
    }

    @Test
    fun testInsertCharacter() = runTest {
        characterLocalDataSource.insertCharacter(character)

        coVerify { characterDao.insert(character.toEntity()) }
    }
}
