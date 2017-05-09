package com.sorcererxw.demo.gridwebview;

/**
 * @description:
 * @author: SuperNoobTao
 * @date: 2017/3/6
 */

public class ScrollInfo {
    public static final int SCROLL_DIRECTION_HORIZONTAL = 1;
    public static final int SCROLL_DIRECTION_VERTICAL = 2;


    private boolean mNeedScroll;
    private int mScrollSpeed;
    private int mScrollDirection;

    public ScrollInfo(boolean needScroll, int scrollSpeed, int scrollDirection) {
        mNeedScroll = needScroll;
        mScrollSpeed = scrollSpeed;
        mScrollDirection = scrollDirection;
    }

    public boolean isNeedScroll() {
        return mNeedScroll;
    }

    public void setNeedScroll(boolean needScroll) {
        mNeedScroll = needScroll;
    }

    public int getScrollSpeed() {
        return mScrollSpeed;
    }

    public void setScrollSpeed(int scrollSpeed) {
        mScrollSpeed = scrollSpeed;
    }

    public int getScrollDirection() {
        return mScrollDirection;
    }

    public void setScrollDirection(int scrollDirection) {
        mScrollDirection = scrollDirection;
    }
}
