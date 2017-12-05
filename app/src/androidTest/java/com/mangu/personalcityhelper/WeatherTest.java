package com.mangu.personalcityhelper;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mangu.personalcityhelper.ui.main.MainActivity;
import com.mangu.personalcityhelper.ui.weather.WeatherActivity;

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
import static com.mangu.personalcityhelper.utils.ItemMatcher.withListSize;
import static com.mangu.personalcityhelper.utils.TestUtilities.elapsedTime;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class WeatherTest {
    private final Context mContext = InstrumentationRegistry.getTargetContext();
    @Rule
    public ActivityTestRule<MainActivity> mMainActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<WeatherActivity> mWeatherActivity =
            new ActivityTestRule<>(WeatherActivity.class);

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
    public void testWeatherIsAvailable() {
        onView(withId(R.id.id_weather))
                .check(matches(isDisplayed()))
                .perform(click());
        intended(hasComponent(WeatherActivity.class.getName()));
        onView(withId(R.id.recycler)).check(matches(withListSize(1)));
        onView(withId(R.id.recycler_forecast)).check(matches(withListSize(5)));
        assertTrue(elapsedTime(mStartTime, SystemClock.elapsedRealtime()));

    }
/*
    @Test
    public void testWeatherIsNotAvailable() {
        try {
            TestUtilities.setMobileDataEnabled(mContext, false);
        }catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.id_weather))
                .check(matches(isDisplayed()))
                .perform(click());
        intended(hasComponent(WeatherActivity.class.getName()));

        assertTrue(elapsedTime(mStartTime, SystemClock.elapsedRealtime()));

        onView(withId(R.id.view_error))
                .check(matches(isDisplayed()));

    }*/
}
