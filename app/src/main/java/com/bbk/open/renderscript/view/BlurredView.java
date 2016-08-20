package com.bbk.open.renderscript.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bbk.open.renderscript.R;

/**
 * Created by Administrator on 2016/8/20.
 */
public class BlurredView extends RelativeLayout {

    private static final int ALPHA_MAX_VALUE = 255;
    private Context mContext;
    private ImageView mBlurredImg;
    private ImageView mOriginImg;
    private Bitmap mOriginBitmap;
    private Bitmap mBlurredBitmap;
    private boolean isDisableBlurred;
    private boolean isMove;

    public BlurredView(Context context) {
        super(context);
        init(context);
    }

    public BlurredView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttr(context, attrs);
    }

    public BlurredView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttr(context, attrs);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.blurredview, this);
        mOriginImg = (ImageView) findViewById(R.id.blurredview_origin_img);
        mBlurredImg = (ImageView) findViewById(R.id.blurredview_blurred_img);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BlurredView);
        Drawable drawable = typedArray.getDrawable(R.styleable.BlurredView_src);
        isMove = typedArray.getBoolean(R.styleable.BlurredView_move, false);
        isDisableBlurred = typedArray.getBoolean(R.styleable.BlurredView_disableBlurred, false);
        typedArray.recycle();

        if (drawable != null) {
            mOriginBitmap = BlurredUtil.drawableToBitmap(drawable);
            mBlurredBitmap = BlunBitmap.blur(mContext, mOriginBitmap);
        }

        if (!isDisableBlurred) {
            mBlurredImg.setVisibility(VISIBLE);
        }

        if (drawable != null) {
            setMove(context, isMove);
        }
    }
    private static final String TAG = "BlurredView";
    private void setMove(Context context, boolean isMove) {
        if (isMove) {
            WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int height = point.y;
            Log.e(TAG, "setMove: height = " + height);
            setBlurredHeight(height, mOriginImg);
            setBlurredHeight(height, mBlurredImg);
        }
    }

    private void setBlurredHeight(int height, ImageView view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = height + 100;
        view.requestLayout();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setImageView();
    }

    private void setImageView() {
        mBlurredImg.setImageBitmap(mBlurredBitmap);
        mOriginImg.setImageBitmap(mOriginBitmap);
    }

    /**
     * 以代码的方式添加待模糊的图片
     *
     * @param blurredBitmap 待模糊的图片
     */
    public void setBlurredImg(Bitmap blurredBitmap) {
        if (blurredBitmap != null) {
            mOriginBitmap = blurredBitmap;
            mBlurredBitmap = BlunBitmap.blur(mContext, blurredBitmap);
            setImageView();
            setMove(mContext, isMove);
        }
    }

    /**
     * 以代码的方式添加待模糊的图片
     *
     * @param blurDrawable 待模糊的图片
     */
    public void setBlurredImg(Drawable blurDrawable) {
        if (null != blurDrawable) {
            mOriginBitmap = BlurredUtil.drawableToBitmap(blurDrawable);
            mBlurredBitmap = BlunBitmap.blur(mContext, mOriginBitmap);
            setImageView();
            setMove(mContext, isMove);
        }
    }

    /**
     * 设置模糊程度
     *
     * @param level 模糊程度, 数值在 0~100 之间.
     */
    public void setBlurredLevel(int level) {
        if (level < 0 || level > 100) {
            throw new IllegalStateException("No validate level, the value must be 0~100");
        }
        if (isDisableBlurred) {
            return;
        }
        mOriginImg.setAlpha((int)(ALPHA_MAX_VALUE - level * 2.55));
    }

    /**
     * 显示模糊图片
     * @param height
     */
    public void setBlurredTop(int height) {
        mOriginImg.setTop(-height);
        mBlurredImg.setTop(-height);
    }

    /**
     * 显示模糊图片
     */
    public void showBlurredView() {
        mBlurredImg.setVisibility(VISIBLE);
    }

    /**
     * 禁用模糊效果
     */
    public void disableBlurredView() {
        isDisableBlurred = true;
    }

    /**
     * 启用模糊效果
     */
    public void enableBlurredView() {
        isDisableBlurred = false;
        mBlurredImg.setVisibility(VISIBLE);
    }
}
