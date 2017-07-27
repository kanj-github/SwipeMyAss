package com.kanj.apps.swipemyass;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.actionWithAssertions;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SwipeAssTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void performClicksAndSwipes() {
        onView(withId(R.id.grey_image)).check(matches(not(isDisplayed())));

        onView(withId(R.id.text_label)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.grey_image)).check(matches(isDisplayed()));

        boolean viewNotFound = false;
        try {
            onView(withId(R.id.thanks_label)).check(matches(not(isDisplayed())));
        } catch (NoMatchingViewException e) {
            viewNotFound = true;
        }

        Assert.assertTrue(viewNotFound);

        onView(withId(R.id.grey_image)).perform(swipeUp());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.thanks_label)).check(matches(isDisplayed()));

        onView(withId(R.id.bottom_sheet)).perform(new GeneralSwipeAction(
            Swipe.FAST,
            GeneralLocation.TOP_CENTER,
            GeneralLocation.CENTER,
            Press.FINGER)
        );
        // swipeDown() swipes all the way down, hiding the bottomsheet

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.thanks_label)).check(matches(not(isDisplayed())));

        onView(withId(R.id.grey_image)).check(matches(isDisplayed()));

        onView(withId(R.id.text_label)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.grey_image)).check(matches(not(isDisplayed())));
    }
}
