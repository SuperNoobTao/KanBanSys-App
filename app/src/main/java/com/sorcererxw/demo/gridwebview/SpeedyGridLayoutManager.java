package com.sorcererxw.demo.gridwebview;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * @description:
 * @author: SuperNoobTao
 * @date: 2017/2/28
 */

public class SpeedyGridLayoutManager extends GridLayoutManager {
    private static int MILLISECONDS_PER_INCH = 640; //default is 25f (bigger = slower)

    public void setSpeed(int speed) {
        MILLISECONDS_PER_INCH = speed;
    }

    public SpeedyGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
                                   int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SpeedyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public SpeedyGridLayoutManager(Context context, int spanCount, int orientation,
                                   boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                       int position) {

        final LinearSmoothScroller linearSmoothScroller =
                new LinearSmoothScroller(recyclerView.getContext()) {

                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        return SpeedyGridLayoutManager.this
                                .computeScrollVectorForPosition(targetPosition);
                    }

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return 1.0f * MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                    }
                };

        linearSmoothScroller.setTargetPosition(position);

        startSmoothScroll(linearSmoothScroller);

    }
}
