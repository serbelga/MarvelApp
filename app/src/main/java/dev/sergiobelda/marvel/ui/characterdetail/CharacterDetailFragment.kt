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

package dev.sergiobelda.marvel.ui.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import coil.load
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import dev.sergiobelda.marvel.R
import dev.sergiobelda.marvel.databinding.CharacterDetailFragmentBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private val binding: CharacterDetailFragmentBinding get() = _binding!!

    private var _binding: CharacterDetailFragmentBinding? = null

    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()

    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = buildContainerTransform(true)
        sharedElementReturnTransition = buildContainerTransform(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CharacterDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.transitionName = args.id.toString()
        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
        binding.toolbar.navigationIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_layer_list)

        binding.characterImage.load(args.imageUrl)

        viewLifecycleOwner.lifecycleScope.launch {
            characterDetailViewModel.characterUiState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { characterUiState ->
                    if (characterUiState.isLoading) {
                        binding.progressIndicator.show()
                    } else {
                        binding.progressIndicator.hide()
                        binding.collapsingToolbar.title =
                            characterUiState.character?.name?.takeIf { it.isNotBlank() } ?: "-"
                        binding.characterDescription.text =
                            characterUiState.character?.description?.takeIf { it.isNotBlank() }
                                ?: getString(R.string.no_description)
                    }
                    characterUiState.errorMessage?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun buildContainerTransform(entering: Boolean) =
        MaterialContainerTransform(requireContext(), entering).apply {
            drawingViewId = R.id.nav_host_fragment
            interpolator = FastOutSlowInInterpolator()
            containerColor = MaterialColors.getColor(
                requireActivity().findViewById(android.R.id.content),
                com.google.android.material.R.attr.colorSurface
            )
            fadeMode = MaterialContainerTransform.FADE_MODE_OUT
            duration = 300
        }
}
