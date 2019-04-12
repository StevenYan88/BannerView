package com.steven.bannerview.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.steven.bannerview.R;
import com.steven.bannerview.utils.DensityUtil;


/**
 * Description:
 * Data：5/10/2018-10:02 AM
 *
 * @author yanzhiwen
 */
public class BannerView extends RelativeLayout {
    private BannerViewPager mBannerViewPager;
    //底部的指示器的View
    private LinearLayout mDotContainerView;
    //适配器
    private BannerAdapter mAdapter;
    private Context mContext;
    //选中的drawable
    private Drawable mIndicatorFocusDrawable;
    //未被选中的drawable
    private Drawable mIndicatorNormalDrawable;
    //当前页面的位置
    private int mCurrentPosition;
    //指示器的位置
    private int mDotGravity = -1;
    //指示器的形状
    private int mDotShape = 0;
    //指示器的大小
    private int mDotSize = 6;
    //指示器的间距
    private int mDotDistance = 2;
    //底部颜色默认透明
    private int mBottomColor = Color.TRANSPARENT;
    private View mBannerBottomView;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.banner_layout, this);
        this.mContext = context;
        initAttribute(attrs);
        initView();

    }

    /**
     * 初始化自定义属性
     *
     * @param attrs
     */
    private void initAttribute(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
        mDotGravity = typedArray.getInt(R.styleable.BannerView_dotGravity, -1);
        mDotShape = typedArray.getInt(R.styleable.BannerView_dotShape, 0);
        mIndicatorFocusDrawable = typedArray.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if (mIndicatorFocusDrawable == null) {
            mIndicatorFocusDrawable = new ColorDrawable(Color.RED);
        }
        mIndicatorNormalDrawable = typedArray.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if (mIndicatorNormalDrawable == null) {
            mIndicatorNormalDrawable = new ColorDrawable(Color.WHITE);
        }
        mDotSize = (int) typedArray.getDimension(R.styleable.BannerView_dotSize, DensityUtil.dip2px(mContext, 6));
        mDotDistance = (int) typedArray.getDimension(R.styleable.BannerView_dotDistance, DensityUtil.dip2px(mContext, 2));
        mBottomColor = typedArray.getColor(R.styleable.BannerView_bottomColor, mBottomColor);

        typedArray.recycle();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mBannerViewPager = findViewById(R.id.bannerViewPager);
        mDotContainerView = findViewById(R.id.dot_container);
        mBannerBottomView = findViewById(R.id.bannerBottomView);
        mBannerBottomView.setBackgroundColor(mBottomColor);
    }

    /**
     * 设置适配器adapter
     *
     * @param adapter 适配器
     */
    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;
        mBannerViewPager.setAdapter(adapter);
        mBannerViewPager.setCurrentItem(mBannerViewPager.getChildCount() / 2);
        initDotIndicator();
        mBannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //监听下当前的位置
                super.onPageSelected(position);
                DotIndicatorView dotIndicatorView = (DotIndicatorView) mDotContainerView.
                        getChildAt(mCurrentPosition);
                dotIndicatorView.setDrawable(mIndicatorNormalDrawable);
                mCurrentPosition = position % mAdapter.getCount();
                DotIndicatorView mCurrentIndicatorView = (DotIndicatorView) mDotContainerView.
                        getChildAt(mCurrentPosition);
                mCurrentIndicatorView.setDrawable(mIndicatorFocusDrawable);
            }
        });
    }

    public void startLoop() {
        mBannerViewPager.startLoop();
    }

    public void setScrollerDuration(int scrollerDuration) {
        mBannerViewPager.setScrollerDuration(scrollerDuration);
    }

    /**
     * 初始化指示器
     */
    private void initDotIndicator() {
        mDotContainerView.removeAllViews();
        //获取广告位的数量
        int count = mAdapter.getCount();
        //设置指示器的位置
        mDotContainerView.setGravity(getDotGravity(1));
        for (int i = 0; i < count; i++) {
            DotIndicatorView dot = new DotIndicatorView(mContext);
            LinearLayout.LayoutParams param;
            if (mDotShape == 0) {
                //圆形
                param = new LinearLayout.LayoutParams(mDotSize, mDotSize);
            } else {
                //矩形
                param = new LinearLayout.LayoutParams(mDotSize * 3, DensityUtil.dip2px(this.getContext(), 2));
            }
            dot.setShape(mDotShape);
            //设置间距
            param.leftMargin = param.rightMargin = mDotDistance;
            dot.setLayoutParams(param);
            if (i == 0) {
                dot.setDrawable(mIndicatorFocusDrawable);
            } else {
                dot.setDrawable(mIndicatorNormalDrawable);
            }
            mDotContainerView.addView(dot);
        }
    }

    @SuppressLint("RtlHardcoded")
    public int getDotGravity(int dotGravity) {
        switch (dotGravity) {
            case 0:
                return Gravity.CENTER;
            case 1:
                return Gravity.RIGHT;
            case -1:
                return Gravity.LEFT;
        }
        return Gravity.RIGHT;
    }

    public BannerViewPager getBannerViewPager() {
        return mBannerViewPager;
    }

    public void setDotShape(int dotShape) {
        if (this.mDotShape == dotShape) {
            return;
        }
        this.mDotShape = dotShape;
        initDotIndicator();
    }

    public void setDotGravity(int dotGravity) {
        if (this.mDotGravity == dotGravity) {
            return;
        }
        mDotContainerView.setGravity(getDotGravity(dotGravity));
    }
}
