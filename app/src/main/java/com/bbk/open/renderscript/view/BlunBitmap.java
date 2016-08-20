package com.bbk.open.renderscript.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by Administrator on 2016/8/20.
 */
public class BlunBitmap {

    /**
     * 图片缩放比例
     */
    private static final float BITMAP_SCALE = 0.4f;
    /**
     * 最大模糊度
     */
    private static final float BLUR_RADIUS = 25f;

    public static Bitmap blur(Context context, Bitmap image) {
        // 计算图片缩小后的长宽
        int width = Math.round(image.getWidth()*BITMAP_SCALE);
        int height = Math.round(image.getHeight()*BITMAP_SCALE);
        // 产生一个可以缩放的图片
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(BLUR_RADIUS);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);
        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

}
