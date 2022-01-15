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

package dev.sergiobelda.marvel.remotedatasource

import dev.sergiobelda.marvel.data.Result
import dev.sergiobelda.marvel.model.Character
import dev.sergiobelda.marvel.network.mapper.CharacterMapper.toDomainModel
import dev.sergiobelda.marvel.network.safeApiCall
import dev.sergiobelda.marvel.network.service.CharacterService

class CharacterRemoteDataSource(private val characterService: CharacterService) :
    ICharacterRemoteDataSource {

    override suspend fun getCharacters(): Result<List<Character>> = safeApiCall {
        characterService.getCharacters()
    }.map { response -> response.data.results.map { it.toDomainModel() } }

    override suspend fun getCharacter(id: Int): Result<Character?> = safeApiCall {
        characterService.getCharacterDetail(id)
    }.map { response -> response.data.results.firstOrNull()?.toDomainModel() }
}
