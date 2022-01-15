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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.sergiobelda.marvel.data.doIfSuccess
import dev.sergiobelda.marvel.databinding.CharactersFragmentBinding

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private var binding: CharactersFragmentBinding? = null

    private val charactersViewModel: CharactersViewModel by viewModels()

    private val charactersAdapter: CharactersAdapter = CharactersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharactersFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyclerView?.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = charactersAdapter
        }
        charactersAdapter.onCharacterItemClick = { character ->
            val action = CharactersFragmentDirections.navToCharacterDetailFragment(character.id)
            findNavController().navigate(action)
        }
        charactersViewModel.characters.observe(viewLifecycleOwner) { result ->
            result?.doIfSuccess {
                charactersAdapter.setItems(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
