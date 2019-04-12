package com.steven.bannerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.steven.bannerview.banner.BannerAdapter;
import com.steven.bannerview.banner.BannerView;
import com.steven.bannerview.banner.BannerViewPager;
import com.steven.bannerview.transformer.BackgroundToForegroundTransformer;
import com.steven.bannerview.transformer.CubeOutTransformer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private String[] mUrls = {"http://pb9.pstatp.com/origin/24990000d4c26180d691",
            "http://pb9.pstatp.com/origin/1dcf002c646ac321e698",
            "http://pb9.pstatp.com/origin/1dcf002c646ac321e698"};
    private BannerView mBannerView;
    private BannerViewPager mBannerViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setupBannerView();
    }

    private void initView() {
        mBannerView = findViewById(R.id.bannerView);
        findViewById(R.id.anim1).setOnClickListener(this);
        findViewById(R.id.anim2).setOnClickListener(this);
        findViewById(R.id.dot1).setOnClickListener(this);
        findViewById(R.id.dot2).setOnClickListener(this);
        findViewById(R.id.position1).setOnClickListener(this);
        findViewById(R.id.position2).setOnClickListener(this);
        findViewById(R.id.position3).setOnClickListener(this);
    }

    private void setupBannerView() {
        mBannerViewPager = mBannerView.getBannerViewPager();
        mBannerView.setAdapter(mBannerAdapter);
        mBannerView.setScrollerDuration(1288);
        mBannerView.startLoop();
    }

    private BannerAdapter mBannerAdapter = new BannerAdapter() {
        @Override
        public View getView(int position, View convertView) {
            ImageView bannerIv;
            if (convertView == null) {
                bannerIv = new ImageView(MainActivity.this);
                bannerIv.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                bannerIv = (ImageView) convertView;
                Log.d(TAG, "getView: 界面复用" + bannerIv);
            }
            GlideApp.with(MainActivity.this).load(mUrls[position]).into(bannerIv);
            return bannerIv;
        }

        @Override
        public int getCount() {
            return mUrls.length;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.anim1:
                mBannerViewPager.setPageTransformer(false, new CubeOutTransformer());
                break;
            case R.id.anim2:
                mBannerViewPager.setPageTransformer(false, new BackgroundToForegroundTransformer());
                break;
            case R.id.dot1:
                mBannerView.setDotShape(0);
                break;
            case R.id.dot2:
                mBannerView.setDotShape(1);
                break;
            case R.id.position1:
                mBannerView.setDotGravity(-1);
                break;
            case R.id.position2:
                mBannerView.setDotGravity(0);
                break;
            case R.id.position3:
                mBannerView.setDotGravity(1);
                break;
            default:
                break;
        }
    }
}
