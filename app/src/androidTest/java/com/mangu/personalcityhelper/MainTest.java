package com.mangu.personalcityhelper;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mangu.personalcityhelper.ui.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.mangu.personalcityhelper.utils.TestUtilities.elapsedTime;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainTest {
    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Rule
    public ActivityTestRule<MainActivity> mMainActivity =
            new ActivityTestRule<>(MainActivity.class);

    private long mStartTime = 0L;

    @Before
    public void setUp() throws Exception {
        mStartTime = SystemClock.elapsedRealtime();
    }

    @Before
    public void beforeTest() {
        Intents.init();
    }

    @After
    public void afterTest() {
        Intents.release();
    }


    @Test
    public void testPresentItems() {
        onView(withId(R.id.id_beach))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));

        onView(withId(R.id.id_events))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));

        onView(withId(R.id.id_news))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));

        onView(withId(R.id.id_transport))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));

        onView(withId(R.id.id_weather))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.id_important))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));
        onView(withId(R.id.btn_change_language))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()));

        assertTrue(elapsedTime(mStartTime, SystemClock.elapsedRealtime()));

    }

    @Test
    public void testChangeLanguage() {
        onView(withId(R.id.btn_change_language))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click());
        onView(withText("Change"))
                .check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());
        assertTrue(elapsedTime(mStartTime, SystemClock.elapsedRealtime()));

    }
}
