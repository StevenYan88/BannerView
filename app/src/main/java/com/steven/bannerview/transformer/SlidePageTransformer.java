package com.steven.bannerview.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Description:
 * Data：5/10/2018-10:01 AM
 *
 * @author yanzhiwen 平滑缩放效果
 */
public class SlidePageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position > 0 && position <= 1) {
            page.setPivotX(0);
            page.setScaleX(1 - position);
        } else if (position >= -1 && position < 0) {
            page.setPivotX(page.getWidth());
            page.setScaleX(1 + position);
        }
    }
}
