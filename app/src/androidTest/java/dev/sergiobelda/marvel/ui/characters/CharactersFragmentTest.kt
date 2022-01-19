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

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.sergiobelda.marvel.R
import dev.sergiobelda.marvel.data.network.service.CharacterService
import dev.sergiobelda.marvel.ui.HiltTestActivity
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class CharactersFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val rule = activityScenarioRule<HiltTestActivity>()

    @MockK
    private val characterService: CharacterService = mockk()

    @Before
    fun setup() {
        rule.scenario.onActivity { activity ->
            val charactersFragment = CharactersFragment()
            activity.supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, charactersFragment, "")
                .commitNow()
        }
    }

    @Test
    fun testRecyclerView() {
        coEvery { characterService.getCharacters(0, 1) } returns Response.success(
            null
        )
        onView(withId(R.id.recycler_view)).check { view, _ ->
            val recyclerView = view as RecyclerView
            assertEquals(1, recyclerView.adapter?.itemCount)
        }
    }
}
