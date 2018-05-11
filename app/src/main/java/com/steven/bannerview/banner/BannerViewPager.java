package com.steven.bannerview.banner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：5/10/2018-10:02 AM
 *
 * @author yanzhiwen
 */
public class BannerViewPager extends ViewPager {
    private static final String TAG = BannerViewPager.class.getSimpleName();
    private static final int SCROLL_MSG = 0x011;
    private BannerAdapter mBannerAdapter;
    private int mCutDownTime = 3000;
    private BannerScroller mBannerScroller;
    //内存优化界面复用
    private List<View> mConvertView;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCROLL_MSG:
                    setCurrentItem(getCurrentItem() + 1);
                    startLoop();
                    break;
            }
        }
    };

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        //改变ViewPager切换的速率
        try {
            //获取ViewPager的私有的属性mScroller
            Field field = ViewPager.class.getDeclaredField("mScroller");
            mBannerScroller = new BannerScroller(context);
            //设置强制改变
            field.setAccessible(true);
            //设置参数 第一个参数object当前属性的那个类 第二参数需要设置的值
            field.set(this, mBannerScroller);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        mConvertView = new ArrayList<>();
    }

    /**
     * 设置切换页面的持续时间
     *
     * @param scrollerDuration
     */
    public void setScrollerDuration(int scrollerDuration) {
        mBannerScroller.setScrollerDuration(scrollerDuration);
    }

    public void setAdapter(BannerAdapter adapter) {
        this.mBannerAdapter = adapter;
        setAdapter(new BannerPagerAdapter());
        //管理Activity的生命周期
        (( Activity ) (getContext())).getApplication().registerActivityLifecycleCallbacks(mDefaultActivityLifecycleCallbacks);
    }

    /**
     * 开启轮播
     */
    public void startLoop() {
        mHandler.removeMessages(SCROLL_MSG);
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
    }

    /**
     * 销毁Handler
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;

    }

    private class BannerPagerAdapter extends PagerAdapter {
        /**
         * 给一个很大的值，为了实现无限轮播
         * 这个方法是会给ViewPager有多少个View
         */
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            //Adapter设计模式为了完全让用户自定义
            //position 0-2的31次方
            Log.i(TAG, "instantiateItem:position=" + position + "mBannerAdapter.getCount()=" + mBannerAdapter.getCount());
            //position % mBannerAdapter.getCount() 求模
            View bannerItemView = mBannerAdapter.getView(position % mBannerAdapter.getCount(), getConvertView());
            container.addView(bannerItemView);
            return bannerItemView;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(( View ) object);
            mConvertView.add(( View ) object);
        }
    }

    private float mDownX;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mHandler.removeMessages(SCROLL_MSG);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //左滑动到第一张，跳转到最后一张
                if (this.getCurrentItem() == 0) {
                    if (ev.getX() - mDownX > 0) {
                        this.setCurrentItem(mBannerAdapter.getCount() - 1);
                        Log.i(TAG, "onTouchEvent: " + this.getCurrentItem());
                    }
                }
                mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);

                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 处理页面复用
     *
     * @return
     */
    public View getConvertView() {
        for (int i = 0; i < mConvertView.size(); i++) {
            if (mConvertView.get(i).getParent() == null) {
                return mConvertView.get(i);
            }
        }
        return null;
    }

    /**
     * 管理Activity的生命周期
     */
    DefaultActivityLifecycleCallbacks mDefaultActivityLifecycleCallbacks = new DefaultActivityLifecycleCallbacks() {
        @Override
        public void onActivityResumed(Activity activity) {
            super.onActivityResumed(activity);
            if (activity == getContext()) {
                //开启轮播
                mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            super.onActivityPaused(activity);
            if (activity == getContext()) {
                //停止轮播
                mHandler.removeMessages(SCROLL_MSG);
            }
        }
    };
}
