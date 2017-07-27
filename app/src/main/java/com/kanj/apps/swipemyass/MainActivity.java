package com.kanj.apps.swipemyass;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.transition.Fade;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String SWIPE_FRAGMENT_TAG = "ASS";

    private FrameLayout bottomSheet, whiteOverlay;
    private ImageView greyImage;
    private BottomSheetBehavior mBottomSheetBehavior;
    private int bottomSheetState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomSheetState = -1;

        whiteOverlay = (FrameLayout) findViewById(R.id.white_overlay);
        bottomSheet = (FrameLayout) findViewById(R.id.bottom_sheet);
        greyImage = (ImageView) findViewById(R.id.grey_image);

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        setGreyImageVisibility(false);
                        addFragmentToContainer();
                        bottomSheetState = newState;
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        setGreyImageVisibility(true);
                        bottomSheetState = newState;
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        bottomSheetState = newState;
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                whiteOverlay.setAlpha(slideOffset);
            }
        });
    }

    public void handleButtonClick(View view) {
        if (isBottomSheetVisible()) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private boolean isBottomSheetVisible() {
        return (bottomSheetState == BottomSheetBehavior.STATE_COLLAPSED
            || bottomSheetState == BottomSheetBehavior.STATE_EXPANDED);
    }

    private void addFragmentToContainer() {
        FragmentManager fragmentManager= getSupportFragmentManager();
        Fragment frag = fragmentManager.findFragmentByTag(SWIPE_FRAGMENT_TAG);
        if (frag == null) {
            fragmentManager.beginTransaction()
                .add(R.id.frag_container, new ThanksFragment(), SWIPE_FRAGMENT_TAG)
                .commit();
        }
    }

    private void setGreyImageVisibility(boolean visible) {
        Transition mFadeTransition;
        if (visible) {
            mFadeTransition = new Fade(Fade.IN);
        } else {
            mFadeTransition = new Fade(Fade.OUT);
        }

        TransitionManager.beginDelayedTransition(bottomSheet, mFadeTransition);
        greyImage.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
