package com.mangu.personalcityhelper;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mangu.personalcityhelper.ui.events.EventsActivity;
import com.mangu.personalcityhelper.ui.main.MainActivity;
import com.mangu.personalcityhelper.ui.news.NewsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.mangu.personalcityhelper.utils.TestUtilities.elapsedTime;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NewsTest {
    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Rule
    public ActivityTestRule<MainActivity> mMainActivity =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Rule
    public ActivityTestRule<NewsActivity> mNewsActivity =
            new ActivityTestRule<>(NewsActivity.class, true, false);

    @Rule
    public ActivityTestRule<EventsActivity> mEventsActivity =
            new ActivityTestRule<>(EventsActivity.class, true, false);

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
    public void testNewsAreVisible() {
        onView(withId(R.id.id_news))
                .check(matches(isDisplayed()))
                .perform(click());

        intended(hasComponent(NewsActivity.class.getName()));
        onView(withId(R.id.news_layout)).
                check(matches(isDisplayed()))
                .perform(click());
        assertTrue(elapsedTime(mStartTime, SystemClock.elapsedRealtime()));
    }

    @Test
    public void testEventsAreVisible() {
        onView(withId(R.id.id_events))
                .check(matches(isDisplayed()))
                .perform(click());

        intended(hasComponent(EventsActivity.class.getName()));
        onView(withId(R.id.news_layout)).
                check(matches(isDisplayed()))
                .perform(click());
        assertTrue(elapsedTime(mStartTime, SystemClock.elapsedRealtime()));
    }
    /*
     * el metodo no va
     *//*
    @Test
    public void testNewsAreNotAvailable() {
        try {
            TestUtilities.setMobileDataEnabled(mContext, false);
        }catch (InvocationTargetException|NoSuchMethodException|IllegalAccessException|
                NoSuchFieldException|ClassNotFoundException e) {
            e.printStackTrace();
            assertTrue(e == null);

        }
        onView(withId(R.id.id_news))
                .check(matches(isDisplayed()))
                .perform(click());

        intended(hasComponent(NewsActivity.class.getName()));
        assertTrue(elapsedTime(mStartTime, SystemClock.elapsedRealtime()));

        onView(withId(R.id.view_error))
                .check(matches(isDisplayed()));
    }*/

}
