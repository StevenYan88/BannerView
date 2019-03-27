package com.steven.bannerview.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Description:
 * Data：5/10/2018-10:17 AM
 *
 * @author yanzhiwen 类似3D效果
 */
public class ScalePageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        float v = Math.abs(position);
        float v1 = (float) (0.2 * (v * v));
        page.setScaleY(1 - v1);
    }
}