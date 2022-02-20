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

package dev.sergiobelda.marvel.data.database.mapper

import dev.sergiobelda.marvel.data.database.mapper.CharacterMapper.toDomainModel
import dev.sergiobelda.marvel.data.database.mapper.CharacterMapper.toEntity
import dev.sergiobelda.marvel.data.testutil.character
import dev.sergiobelda.marvel.data.testutil.characterEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterEntityMapperTest {

    @Test
    fun testCharacterEntityToCharacter() {
        val character = characterEntity.toDomainModel()
        assertEquals(characterEntity.id, character.id)
        assertEquals(characterEntity.name, character.name)
        assertEquals(characterEntity.description, character.description)
        assertEquals(characterEntity.imageUrl, character.imageUrl)
    }

    @Test
    fun testCharacterToCharacterEntity() {
        val characterEntity = character.toEntity()
        assertEquals(character.id, characterEntity.id)
        assertEquals(character.name, characterEntity.name)
        assertEquals(character.description, characterEntity.description)
        assertEquals(character.imageUrl, characterEntity.imageUrl)
    }
}
