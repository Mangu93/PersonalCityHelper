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
import com.mangu.personalcityhelper.ui.transport.TransportActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.mangu.personalcityhelper.utils.TestUtilities.clickXY;
import static com.mangu.personalcityhelper.utils.TestUtilities.elapsedTime;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TransportTest {
    private final Context mContext = InstrumentationRegistry.getTargetContext();

    @Rule
    public ActivityTestRule<MainActivity> mMainActivity =
            new ActivityTestRule<>(MainActivity.class);    @Rule
    public ActivityTestRule<TransportActivity> mTransportActivity =
            new ActivityTestRule<>(TransportActivity.class);
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
    public void tranportsAreDisplayed() {
        onView(withId(R.id.id_transport))
                .check(matches(isDisplayed()))
                .perform(click());

        intended(hasComponent(TransportActivity.class.getName()));
        assertTrue(elapsedTime(mStartTime, SystemClock.elapsedRealtime()));

        /*onView(withId(R.id.relative)).check(matches(isDisplayed())).perform(clickXY(100,100));
        assertTrue(elapsedTime(mStartTime, SystemClock.elapsedRealtime()));*/
    }
}
