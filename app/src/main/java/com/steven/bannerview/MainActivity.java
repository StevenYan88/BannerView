package com.steven.bannerview;

import android.annotation.SuppressLint;
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
    private final String[] mUrls = {"https://pics4.baidu.com/feed/3812b31bb051f819979b8581d0ee77e72f73e727.png?token=081a892350d5ae1f023b40045c981cfd",
            "https://pics3.baidu.com/feed/9358d109b3de9c82779c706464dbbd001bd843c0.jpeg?token=5fd819b0f2f70a025397fc761d5d1b78",
            "https://pics4.baidu.com/feed/f9198618367adab47c5ba53b9b8e8e168601e4a7.png?token=25fd2f175bec6555a8bd0def4fb98dbf"};
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
        mBannerViewPager.setPageTransformer(false, new CubeOutTransformer());
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
                bannerIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
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


    @SuppressLint("NonConstantResourceId")
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
