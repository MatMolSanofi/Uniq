package com.molette.uniq.UITest

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.molette.uniq.MainActivity
import com.molette.uniq.R
import com.molette.uniq.presentation.home.adapters.CharacterViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeFragmentUITest {

    companion object{
        const val LIST_ITEM_POSITION = 4
    }

    @get:Rule
    val mainActivityTestRule = ActivityTestRule(
        MainActivity::class.java
    )
    @get:Rule
    @JvmField
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.READ_CONTACTS)

    @Test
    fun isRecyclerViewVisible_OnAppLaunch() {
        onView(withId(R.id.homeFragmentContainer)).check(matches(isDisplayed()))
    }
    
    @Test
    fun clickOnCell_isDetailsFragmentVisible() {
        val rv = mainActivityTestRule.activity.findViewById<RecyclerView>(R.id.characterRV)
        val viewHolder = rv.findViewHolderForAdapterPosition(LIST_ITEM_POSITION)
        if (viewHolder != null) {
            val textview = viewHolder.itemView.findViewById<TextView>(R.id.characterName)
            val name = textview.text.toString()

            onView(withId(R.id.characterRV)).perform(
                RecyclerViewActions.actionOnItemAtPosition<CharacterViewHolder>(
                    LIST_ITEM_POSITION, click()
                )
            )
            onView(withId(R.id.detailsName)).check(matches(withText(name)))
        }
    }

    @Test
    fun backNavigation_toHomeFragment() {
        onView(withId(R.id.characterRV)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CharacterViewHolder>(
                LIST_ITEM_POSITION, click()
            )
        )
        pressBack()
        onView(withId(R.id.homeFragmentContainer)).check(matches(isDisplayed()))
    }
}