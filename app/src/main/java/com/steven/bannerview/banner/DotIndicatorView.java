package com.steven.bannerview.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.steven.bannerview.R;
import com.steven.bannerview.utils.DensityUtil;

/**
 * Description:
 * Data：5/10/2018-10:02 AM
 *
 * @author yanzhiwen
 */

public class DotIndicatorView extends View {
    //形状
    private int mShape;
    // 圆形
    public static final int SHAPE_CIRCLE = 0;
    // 矩形
    public static final int SHAPE_REC = 1;

    private Drawable mDrawable;

    public DotIndicatorView(Context context) {
        this(context, null);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DotIndicatorView);
        //默认是圆形
        mShape = typedArray.getInteger(R.styleable.DotIndicatorView_shape, SHAPE_CIRCLE);
        typedArray.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawable != null) {
            Bitmap bitmap = drawableToBitmap(mDrawable);
            if (mShape == SHAPE_CIRCLE) {
                Bitmap circleBitmap = getCircleBitmap(bitmap);
                canvas.drawBitmap(circleBitmap, 0, 0, null);
            } else if (mShape == SHAPE_REC) {
                Bitmap recBitmap = getRecBitmap(bitmap);
                canvas.drawBitmap(recBitmap, 0, 0, null);
            }
        }
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
        invalidate();
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return (( BitmapDrawable ) drawable).getBitmap();
        }
        //其他类型 ColorDrawable
        //创建一个什么也没有的Bitmap；
        Bitmap outBitmap = Bitmap.createBitmap(getMeasuredWidth(),
                getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        //把drawable画到Bitmap上   --》将drawable绘制在canvas内部
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        drawable.draw(canvas);
        return outBitmap;
    }

    public void setShape(int shape) {
        mShape = shape;
    }

    public int getShape() {
        return mShape;
    }

    /**
     * 圆形
     *
     * @param bitmap
     * @return
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        //创建一个Bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(),
                getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        //防止抖动
        paint.setDither(true);
        //在画布上绘制一个圆
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2, paint);
        //设置画笔的图层，PorterDuff.Mode.SRC_IN 取图层交集的上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //在把原来的bitmap绘制到圆上面
        canvas.drawBitmap(bitmap, 0, 0, paint);
        //回收Bitmap
        bitmap.recycle();
        return circleBitmap;
    }

    /**
     * 带圆角的矩形
     *
     * @param bitmap
     * @return
     */
    private Bitmap getRecBitmap(Bitmap bitmap) {
        Bitmap recBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(recBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        //防止抖动
        paint.setDither(true);
        //在画布上绘制一个圆角的矩形
        canvas.drawRoundRect(new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight()),
                DensityUtil.dip2px(this.getContext(), 2), DensityUtil.dip2px(this.getContext(), 2), paint);
        //设置画笔的图层，PorterDuff.Mode.SRC_IN 取图层交集的上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //在把原来的bitmap绘制到圆上面
        canvas.drawBitmap(bitmap, 0, 0, paint);
        //回收Bitmap
        bitmap.recycle();
        return recBitmap;
    }
}
