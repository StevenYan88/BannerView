package com.steven.bannerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.steven.bannerview.banner.BannerAdapter;
import com.steven.bannerview.banner.BannerView;

public class MainActivity extends AppCompatActivity {
    private String[] mUrls = {"http://pb9.pstatp.com/origin/24990000d4c26180d691", "http://pb9.pstatp.com/origin/1dcf002c646ac321e698",
            "http://pb9.pstatp.com/origin/1dcf002c646ac321e698"};
    private BannerView mBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBannerView = findViewById(R.id.bannerView);
        mBannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                ImageView bannerIv;
                if (convertView == null) {
                    bannerIv = new ImageView(MainActivity.this);
                    bannerIv.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    bannerIv = ( ImageView ) convertView;
                    Log.i("TAG", "getView: 界面复用" + bannerIv);
                }
                GlideApp.with(MainActivity.this).load(mUrls[position]).into(bannerIv);
                return bannerIv;
            }

            @Override
            public int getCount() {
                return mUrls.length;
            }
        });
        mBannerView.startLoop();
        mBannerView.setScrollerDuration(1000);
    }

}
