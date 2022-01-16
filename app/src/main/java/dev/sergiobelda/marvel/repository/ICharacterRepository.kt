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

import androidx.paging.PagingData
import dev.sergiobelda.marvel.data.Result
import dev.sergiobelda.marvel.model.Character
import kotlinx.coroutines.flow.Flow

interface ICharacterRepository {

    /**
     * Get Characters paging.
     */
    fun getCharacters(): Flow<PagingData<Character>>

    /**
     * Get [Character] given an [id].
     */
    suspend fun getCharacter(id: Int): Result<Character?>
}
