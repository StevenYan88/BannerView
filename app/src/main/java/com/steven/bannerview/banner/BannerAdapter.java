package com.steven.bannerview.banner;

import android.view.View;
/**
 * Description:
 * Data：5/10/2018-10:01 AM
 *
 * @author yanzhiwen
 */

public abstract class BannerAdapter {
    /**
     * 根据位置获取ViewPager的子View
     *
     * @param position
     * @return
     */
    public abstract View getView(int position, View convertView);

    /**
     * 返回数量
     *
     * @return
     */
    public abstract int getCount();
}
