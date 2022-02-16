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

package dev.sergiobelda.marvel.data.testutil

import dev.sergiobelda.marvel.data.network.model.CharacterApiModel
import dev.sergiobelda.marvel.data.network.model.MarvelData
import dev.sergiobelda.marvel.data.network.model.MarvelResponse
import dev.sergiobelda.marvel.data.network.model.Thumbnail

val thumbnail = Thumbnail("", "")

val characterApiModel = CharacterApiModel(1, "3-D Man", "Description", thumbnail)

fun <T> createMarvelResponse(results: List<T>): MarvelResponse<T> =
    MarvelResponse(
        200, MarvelData(0, 20, results.size, results.size, results)
    )
