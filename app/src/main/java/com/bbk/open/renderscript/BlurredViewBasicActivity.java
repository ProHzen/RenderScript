package com.bbk.open.renderscript;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bbk.open.renderscript.view.BlurredView;

/**
 * Created by Administrator on 2016/8/20.
 */
public class BlurredViewBasicActivity extends AppCompatActivity {

    private SeekBar mSeekBar;
    private BlurredView mBlurredView;
    private TextView mProgressTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blurrediview_basic);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initViews();
        setSeekBar();
    }

    private void initViews() {
        mSeekBar = (SeekBar) findViewById(R.id.activity_main_seekbar);
        mProgressTv = (TextView) findViewById(R.id.activity_main_progress_tv);
        mBlurredView = (BlurredView) findViewById(R.id.activity_main_blurredview);
        // 可以在代码中使用setBlurredImg()方法指定需要模糊的图片
        mBlurredView.setBlurredImg(getResources().getDrawable(R.drawable.dayu));
        // 设置完成后需要调用showBlurredView方法显示要模糊的图片
        mBlurredView.showBlurredView();
    }

    private void setSeekBar() {
        mSeekBar.setMax(100);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBlurredView.setBlurredLevel(progress);
                mProgressTv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
