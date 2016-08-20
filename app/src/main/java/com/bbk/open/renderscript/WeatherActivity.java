package com.bbk.open.renderscript;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import com.bbk.open.renderscript.view.BlurredView;

/**
 * Created by Administrator on 2016/8/20.
 */
public class WeatherActivity extends AppCompatActivity{

    private BlurredView mBlurredView;
    private RecyclerView mRecyclerView;
    private int mSrollerY;
    private int mAlpha;
    private static final String TAG = "WeatherActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blurred_weather_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mBlurredView = (BlurredView) findViewById(R.id.yahooweather_blurredview);
        mRecyclerView = (RecyclerView) findViewById(R.id.yahooweather_recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(this));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mSrollerY += dy;
                Log.e(TAG, "onScrolled: dy = " + dy + "onScrolled: mSrollerY = " +mSrollerY);

                if (Math.abs(mSrollerY) > 1000) {
                    mBlurredView.setBlurredTop(100);
                    mAlpha = 100;
                } else {
                    mBlurredView.setBlurredTop(mSrollerY / 10);
                    mAlpha = Math.abs(mSrollerY) / 10;
                }
                mBlurredView.setBlurredLevel(mAlpha);
            }
        });
    }
}
