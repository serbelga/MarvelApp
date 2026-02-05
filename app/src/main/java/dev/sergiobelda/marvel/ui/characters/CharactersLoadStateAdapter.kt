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

package dev.sergiobelda.marvel.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.sergiobelda.marvel.databinding.CharacterLoadStateFooterBinding

class CharactersLoadStateAdapter(
    private val retryClickListener: () -> Unit,
) : LoadStateAdapter<CharactersLoadStateAdapter.ViewHolder>() {
    override fun onBindViewHolder(
        holder: ViewHolder,
        loadState: LoadState,
    ) = holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): ViewHolder =
        ViewHolder(
            CharacterLoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    inner class ViewHolder(
        private val binding: CharacterLoadStateFooterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMessage.text = loadState.error.localizedMessage
            }
            binding.errorMessage.isVisible = loadState is LoadState.Error

            if (loadState is LoadState.Loading) {
                binding.progressIndicator.show()
            } else {
                binding.progressIndicator.hide()
            }

            binding.retryButton.setOnClickListener { retryClickListener.invoke() }
            binding.retryButton.isVisible = loadState is LoadState.Error
        }
    }
}
