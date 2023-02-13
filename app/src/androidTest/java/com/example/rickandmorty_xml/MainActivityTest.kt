package com.example.rickandmorty_xml

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register()
        scenario = activityScenarioRule.scenario
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister()
        scenario.close()
    }

    @Test
    fun isActivityLaunched() {
        onView(withId(R.id.mainActivity)).check(matches(isDisplayed()))
    }

    @Test
    fun isMainFragmentDisplayed() {
        onView(withId(R.id.mainFragment)).check(matches(isDisplayed()))
    }
}