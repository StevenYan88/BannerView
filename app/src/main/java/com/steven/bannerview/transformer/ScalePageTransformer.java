package com.steven.bannerview.transformer;

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
    public void transformPage(View page, float position) {
        float abs = Math.abs(position - 1 / 3f);
        float scale = (2 * (abs * abs));
        page.setScaleX(1 - scale);
        page.setScaleY(1 - scale);
    }
}