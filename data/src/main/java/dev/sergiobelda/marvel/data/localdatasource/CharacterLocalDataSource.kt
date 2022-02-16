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
import dev.sergiobelda.marvel.domain.Result
import dev.sergiobelda.marvel.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterLocalDataSource(
    private val characterDao: CharacterDao
) : ICharacterLocalDataSource {

    override fun getCharacter(id: Int): Flow<Result<Character>> =
        characterDao.getCharacter(id).map { characterEntity ->
            characterEntity?.let {
                Result.Success(it.toDomainModel())
            } ?: Result.Error(Exception())
        }

    override suspend fun insertCharacter(character: Character) =
        characterDao.insert(character.toEntity())
}
