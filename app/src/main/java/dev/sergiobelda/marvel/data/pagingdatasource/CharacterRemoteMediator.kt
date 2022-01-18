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

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.sergiobelda.marvel.data.Result
import dev.sergiobelda.marvel.data.database.AppDatabase
import dev.sergiobelda.marvel.data.database.entity.RemoteKeys
import dev.sergiobelda.marvel.data.database.mapper.CharacterMapper.toEntity
import dev.sergiobelda.marvel.data.network.Constants.API_STARTING_PAGE_INDEX
import dev.sergiobelda.marvel.data.network.mapper.CharacterMapper.toDomainModel
import dev.sergiobelda.marvel.data.network.safeApiCall
import dev.sergiobelda.marvel.data.network.service.CharacterService
import dev.sergiobelda.marvel.domain.model.Character

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val characterService: CharacterService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Character>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: API_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        val offset = page * state.config.pageSize
        val result = safeApiCall {
            characterService.getCharacters(offset, state.config.pageSize)
        }
        return when (result) {
            is Result.Success -> {
                val characters = result.value.data.results.map { it.toDomainModel() }
                val endOfPaginationReached = characters.isEmpty()
                appDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        appDatabase.remoteKeysDao().clearRemoteKeys()
                        appDatabase.characterDao().clearCharacters()
                    }
                    val prevKey = if (page == API_STARTING_PAGE_INDEX) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val keys = characters.map {
                        RemoteKeys(characterId = it.id, prevKey, nextKey)
                    }
                    appDatabase.remoteKeysDao().insertAll(keys)
                    appDatabase.characterDao().insertAll(characters.map { it.toEntity() })
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
            is Result.Error -> {
                MediatorResult.Error(result.exception)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Character>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                // Get the remote keys of the first items retrieved
                appDatabase.remoteKeysDao().remoteKeysCharacterId(character.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Character>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                // Get the remote keys of the last item retrieved
                appDatabase.remoteKeysDao().remoteKeysCharacterId(character.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Character>): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { characterId ->
                appDatabase.remoteKeysDao().remoteKeysCharacterId(characterId)
            }
        }
    }
}
