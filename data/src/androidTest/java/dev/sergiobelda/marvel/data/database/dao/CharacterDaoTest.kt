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

package dev.sergiobelda.marvel.data.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.sergiobelda.marvel.data.database.AppDatabaseTest
import dev.sergiobelda.marvel.data.database.testutil.characterEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDaoTest : AppDatabaseTest() {
    private lateinit var characterDao: CharacterDao

    @Before
    fun init() {
        characterDao = appDatabase.characterDao()
    }

    @Test
    fun testInsertCharacter() =
        runTest {
            characterDao.insert(characterEntity)
            val character = characterDao.getCharacter(1).first()
            assertEquals(characterEntity, character)
        }

    @Test
    fun testGetCharacter() =
        runTest {
            characterDao.insert(characterEntity)
            val character = characterDao.getCharacter(1).first()
            assertNotNull(character)
        }

    @Test
    fun testGetCharacterNotExist() =
        runTest {
            val character = characterDao.getCharacter(1).first()
            assertNull(character)
        }
}
