package com.kanj.apps.swipemyass;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

import java.lang.ref.WeakReference;

/**
 * Created by kanj on 30/01/18.
 */

public class LoopCarouselScrollHandler extends ViewPager.SimpleOnPageChangeListener {
    private static final int MSG_WAIT_OVER = 1;
    private static final int GARBAGE_REAPPEAR_DELAY = 2;

    private OnScrollChangeListener mListener;
    private boolean isViewPagerScrolling;
    private DelayHandler delayHandler;

    public LoopCarouselScrollHandler(OnScrollChangeListener mListener) {
        this.mListener = mListener;
        delayHandler = new DelayHandler(new WeakReference<>(this));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                mListener.setDetailsVisibility(false);
                isViewPagerScrolling = true;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
            case ViewPager.SCROLL_STATE_IDLE:
                if (isViewPagerScrolling) {
                    isViewPagerScrolling = false;
                    delayHandler.removeMessages(MSG_WAIT_OVER);
                    delayHandler.sendMessageDelayed(delayHandler.obtainMessage(MSG_WAIT_OVER),
                            GARBAGE_REAPPEAR_DELAY * 1000);
                }
                break;
        }
    }

    @Override
    public void onPageSelected(int position) {
        mListener.setPageSelected(position);
    }

    void waitOver() {
        mListener.setDetailsVisibility(true);
    }

    static class DelayHandler extends Handler {
        private WeakReference<LoopCarouselScrollHandler> scrollHandlerRef;

        DelayHandler(WeakReference<LoopCarouselScrollHandler> scrollHandler) {
            this.scrollHandlerRef = scrollHandler;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WAIT_OVER:
                    LoopCarouselScrollHandler scrollHandler = scrollHandlerRef.get();
                    if (scrollHandler != null) {
                        scrollHandler.waitOver();
                    }
                    break;
            }
        }
    }

    public interface OnScrollChangeListener {
        void setDetailsVisibility(boolean show);
        void setPageSelected(int page);
    }
}
