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

package dev.sergiobelda.marvel.data

sealed class Result<out A> {
    data class Success<out A>(val value: A) : Result<A>()
    data class Error(
        val code: Int? = null,
        val message: String? = null,
        val exception: Throwable? = null
    ) : Result<Nothing>()
    object Loading : Result<Nothing>()

    fun <B> map(m: ((A) -> B)): Result<B> = when (this) {
        is Success -> Success(m(this.value))
        is Error -> Error(this.code, this.message, this.exception)
        is Loading -> Loading
    }
}
