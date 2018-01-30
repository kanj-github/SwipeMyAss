package com.kanj.apps.swipemyass;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by kanj on 30/01/18.
 */

public class FadeInOutAnimator {
    private int mAnimationDuration;

    public FadeInOutAnimator(Context context) {
        this.mAnimationDuration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);;
    }

    private void fadeOutView(View... views) {
        for (final View v : views) {
            v.animate()
                    .alpha(0f)
                    .setDuration(mAnimationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            v.setVisibility(INVISIBLE);
                            v.setAlpha(1f);
                        }
                    });
        }
    }

    private void fadeInView(View... views) {
        for (final View v : views) {
            v.setAlpha(0f);
            v.setVisibility(VISIBLE);

            v.animate()
                    .alpha(1f)
                    .setDuration(mAnimationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            v.setVisibility(VISIBLE);
                            // View may have been set invisible by a previously started fade out
                        }
                    });
        }
    }

    public void fadeToVisibility(boolean visible, View... views) {
        if (visible) {
            fadeInView(views);
        } else {
            fadeOutView(views);
        }
    }
}
