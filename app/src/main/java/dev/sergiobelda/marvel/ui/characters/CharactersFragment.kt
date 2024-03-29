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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.sergiobelda.marvel.databinding.CharactersFragmentBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private var _binding: CharactersFragmentBinding? = null

    private val binding: CharactersFragmentBinding get() = _binding!!

    private val charactersViewModel: CharactersViewModel by viewModels()

    private val charactersAdapter: CharactersAdapter = CharactersAdapter().apply {
        listener = CharactersAdapter.CharacterClickListener { character, cardView ->
            val extras = FragmentNavigatorExtras(cardView to character.id.toString())
            val action = CharactersFragmentDirections.navToCharacterDetailFragment(
                character.id,
                character.imageUrl
            )
            findNavController().navigate(action, extras)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CharactersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        binding.recyclerView.post { startPostponedEnterTransition() }
        initRecyclerView()
        initAdapterLoadStateListener()
        setGoTopButtonClickListener()
        setRetryButtonClickListener()

        viewLifecycleOwner.lifecycleScope.launch {
            // flowWithLifecycle uses repeatOnLifecycle.
            // The block passed to repeatOnLifecycle is executed when the lifecycle
            // is at least STARTED and is cancelled when the lifecycle is STOPPED.
            // It automatically restarts the block when the lifecycle is STARTED again.
            charactersViewModel.characters
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    charactersAdapter.submitData(it)
                }
        }
    }

    private fun setGoTopButtonClickListener() {
        binding.goTopButton.setOnClickListener {
            val gridLayoutManager = binding.recyclerView.layoutManager as? GridLayoutManager
            val currentPosition = gridLayoutManager?.findFirstVisibleItemPosition() ?: 0
            if (currentPosition > MAX_POSITION_TO_ANIMATE_SCROLL_TO_TOP) {
                binding.recyclerView.scrollToPosition(0)
            } else {
                binding.recyclerView.smoothScrollToPosition(0)
            }
        }
    }

    private fun setRetryButtonClickListener() {
        binding.retryButton.setOnClickListener {
            charactersAdapter.retry()
        }
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = charactersAdapter.withLoadStateFooter(
                footer = CharactersLoadStateAdapter { charactersAdapter.retry() }
            )
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        if (dy < 0 && binding.goTopButton.isGone) binding.goTopButton.show()
                        else if (dy > 0 && binding.goTopButton.isVisible) binding.goTopButton.hide()
                        if (gridLayoutManager.findFirstVisibleItemPosition() == 0) binding.goTopButton.hide()
                    }
                }
            )
        }
        // Center CharactersLoadStateFooter.
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int =
                if (position == charactersAdapter.itemCount && charactersAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
        }
    }

    private fun initAdapterLoadStateListener() {
        charactersAdapter.addLoadStateListener { loadState ->
            val initialLoadProgressIndicator =
                loadState.refresh is LoadState.Loading && charactersAdapter.itemCount == 0
            binding.progressIndicator.isVisible = initialLoadProgressIndicator

            val initialLoadError =
                loadState.refresh is LoadState.Error && charactersAdapter.itemCount == 0
            binding.errorMessage.isVisible = initialLoadError
            binding.retryButton.isVisible = initialLoadError
            (loadState.refresh as? LoadState.Error)?.let {
                binding.errorMessage.text = it.error.message
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val MAX_POSITION_TO_ANIMATE_SCROLL_TO_TOP = 150
    }
}
