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

package dev.sergiobelda.marvel.domain.usecase

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import dev.sergiobelda.marvel.data.repository.ICharacterRepository
import dev.sergiobelda.marvel.data.testutil.character
import dev.sergiobelda.marvel.testutil.CharacterDiffCallback
import dev.sergiobelda.marvel.testutil.NoopListCallback
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharactersUseCaseTest {

    @MockK
    private val characterRepository = mockk<ICharacterRepository>()

    private val getCharactersUseCase = GetCharactersUseCase(characterRepository)

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetCharactersUseCase() = runTest {
        val characters = listOf(character)

        every { characterRepository.getCharacters() } returns flow {
            emit(PagingData.from(characters))
        }

        val differ = AsyncPagingDataDiffer(
            diffCallback = CharacterDiffCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        val result = getCharactersUseCase.invoke().first()

        differ.submitData(result)

        assertEquals(characters, differ.snapshot().items)
    }
}
