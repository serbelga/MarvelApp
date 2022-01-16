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

package dev.sergiobelda.marvel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sergiobelda.marvel.data.network.service.CharacterService
import dev.sergiobelda.marvel.data.pagingdatasource.CharacterPagingDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagingDataSourceModule {

    @Provides
    @Singleton
    fun provideCharacterPagingDataSource(characterService: CharacterService): CharacterPagingDataSource =
        CharacterPagingDataSource(characterService)
}
