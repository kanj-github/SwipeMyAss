package com.kanj.apps.swipemyass;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kanj Narayan on 02/08/17.
 */

public class GreyShadeAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View image = LayoutInflater.from(container.getContext())
            .inflate(R.layout.grey_image, null);
        image.setBackgroundColor(ContextCompat.getColor(container.getContext(),
            getColourId(position)));
        container.addView(image);
        return image;
    }

    private int getColourId(int position) {
        switch (position) {
            case 0:
                return R.color.grey1;
            case 1:
                return R.color.grey2;
            case 2:
                return R.color.grey3;
            case 3:
                return R.color.grey4;
            case 4:
                return R.color.grey5;
            default:
                return android.R.color.white;
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
