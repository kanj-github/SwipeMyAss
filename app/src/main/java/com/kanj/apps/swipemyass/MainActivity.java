package com.kanj.apps.swipemyass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kanj.apps.swipemyass.widget.CollapsibleSectionHeader;

public class MainActivity extends AppCompatActivity {
    private static final String SWIPE_FRAGMENT_TAG = "ASS";

    private FrameLayout bottomSheet, whiteOverlay;
    private ViewPager imageCarousel;
    private BottomSheetBehavior mBottomSheetBehavior;
    private int bottomSheetState;

    private CollapsibleSectionHeader textSectionHeader;
    private TextView textSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomSheetState = -1;

        whiteOverlay = findViewById(R.id.white_overlay);
        bottomSheet = findViewById(R.id.bottom_sheet);
        imageCarousel = findViewById(R.id.image_carousel);
        imageCarousel.setAdapter(new GreyShadeAdapter());

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

        textSectionHeader = findViewById(R.id.text_section_header);
        textSection = findViewById(R.id.text_section);
        textSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Kanj", "Text section clicked");
            }
        });

        // text section is initially open
        //textSectionHeader.setCollapsibleSection(textSection);

        // text section is initially closed
        textSection.setVisibility(View.GONE);
        textSectionHeader.setCollapsed(true);
        textSectionHeader.setCollapsibleSection(textSection);
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
        imageCarousel.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
