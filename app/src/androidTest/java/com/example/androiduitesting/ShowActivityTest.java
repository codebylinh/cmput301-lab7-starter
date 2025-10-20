package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for:
 * 1) Activity switch from MainActivity -> ShowActivity
 * 2) City name consistency
 * 3) Back button returns to MainActivity
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    /** Helper: add a city through the UI so it appears in the ListView. */
    private void addCity(String name) {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());
    }

    /** 1) Check whether the activity correctly switched */
    @Test
    public void activitySwitchesOnCityTap() {
        addCity("Edmonton");

        // Tap the first row in the list
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // If ShowActivity is shown, its TextView should be visible
        onView(withId(R.id.text_city_name)).check(matches(isDisplayed()));
    }

    /** 2) Test whether the city name is consistent */
    @Test
    public void selectedCityNameIsDisplayed() {
        addCity("Vancouver");

        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        onView(withId(R.id.text_city_name)).check(matches(withText("Vancouver")));
    }

    /** 3) Test the "back" button */
    @Test
    public void backButtonReturnsToMainActivity() {
        addCity("Calgary");

        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Click explicit Back button in ShowActivity
        onView(withId(R.id.button_back)).perform(click());

        // MainActivity is visible again (e.g., the Add button)
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }
}
