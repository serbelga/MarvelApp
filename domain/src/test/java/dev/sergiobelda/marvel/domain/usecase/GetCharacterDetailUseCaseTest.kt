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

import dev.sergiobelda.marvel.data.Result
import dev.sergiobelda.marvel.data.repository.ICharacterRepository
import dev.sergiobelda.marvel.data.testutil.character
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharacterDetailUseCaseTest {

    @MockK
    private val characterRepository = mockk<ICharacterRepository>()

    private val getCharacterDetailUseCase = GetCharacterDetailUseCase(characterRepository)

    @Test
    fun testGetCharacterDetailUseCase() = runTest {
        coEvery { characterRepository.getCharacter(1) } returns flow {
            emit(Result.Success(character))
        }

        val result = getCharacterDetailUseCase.invoke(1).firstOrNull()

        assertTrue(result is Result.Success)
        assertEquals((result as Result.Success).value, character)
    }
}
