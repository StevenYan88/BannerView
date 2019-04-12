package com.steven.bannerview.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by zhiwenyan on 4/21/17.
 */

public class ZoomTransformer implements ViewPager.PageTransformer {

    private final float MIN_SCALE = 0.8f;


    @Override
    public void transformPage(View page, float position) {
        if (position >= -1 && position <= 1) {
            page.setScaleY(1.0f - Math.abs(position) * (1 - MIN_SCALE));
        } else {
            page.setScaleY(MIN_SCALE);
        }
    }
}
