package com.kanj.apps.swipemyass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThanksFragment extends Fragment implements LoopCarouselScrollHandler.OnScrollChangeListener {
    private static final int ACTUAL_SIZE_OF_PAGER = 5;

    private ViewPager viewPager;
    private TextView page, garbage;
    private FadeInOutAnimator fadeInOutAnimator;

    public ThanksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_thanks, container, false);
        viewPager = v.findViewById(R.id.image_carousel);
        page = v.findViewById(R.id.tv_carousel_page_number);
        garbage = v.findViewById(R.id.tv_garbage_thats_on_carousel);

        viewPager.setAdapter(new GreyShadeLoopAdapter(ACTUAL_SIZE_OF_PAGER));
        viewPager.setCurrentItem(1);
        setPageSelected(1);
        viewPager.addOnPageChangeListener(new LoopCarouselScrollHandler(this));

        fadeInOutAnimator = new FadeInOutAnimator(getContext());

        return v;
    }

    @Override
    public void setDetailsVisibility(boolean show) {
        /*garbage.setVisibility(show ? View.VISIBLE : View.GONE);
        page.setVisibility(show? View.GONE: View.VISIBLE);*/
        fadeInOutAnimator.fadeToVisibility(show, garbage);
        fadeInOutAnimator.fadeToVisibility(!show, page);
    }

    @Override
    public void setPageSelected(int pageNumber) {
        if (pageNumber == 0) {
            viewPager.setCurrentItem(ACTUAL_SIZE_OF_PAGER, false);
        } else if (pageNumber == ACTUAL_SIZE_OF_PAGER + 1) {
            viewPager.setCurrentItem(1, false);
        } else {
            page.setText(getString(R.string.page_number, pageNumber, ACTUAL_SIZE_OF_PAGER));
        }
    }
}
