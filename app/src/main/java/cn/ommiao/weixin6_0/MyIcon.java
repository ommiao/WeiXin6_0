package cn.ommiao.weixin6_0;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class MyIcon extends View {

    private static final int DEF_COLOR = Color.GRAY;
    private static final int DEF_TEXT_SIZE = 12;

    private int mColor = DEF_COLOR;
    private Bitmap mIconBitmap;
    private String mText = "WeChat";
    private int mTextSize = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            DEF_TEXT_SIZE, getResources().getDisplayMetrics());

    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;

    private float mAlpha;

    private Rect mIconRect;
    private Rect mTextRect;

    private Paint mTextPaint;

    public MyIcon(Context context) {
        this(context, null);
    }

    public MyIcon(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyIcon);
        for(int i = 0; i < ta.getIndexCount(); i++){
            int attr = ta.getIndex(i);
            switch (attr){
                case R.styleable.MyIcon_icon:
                    BitmapDrawable drawable = (BitmapDrawable) ta.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.MyIcon_color:
                    mColor = ta.getColor(attr, DEF_COLOR);
                    break;
                case R.styleable.MyIcon_text:
                    mText = ta.getString(attr);
                    break;
                case R.styleable.MyIcon_textSize:
                    mTextSize = (int)ta.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            DEF_TEXT_SIZE, getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
        }
        ta.recycle();
        mTextRect = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(getResources().getColor(R.color.grey_text));
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextRect.height());
        int left = getMeasuredWidth()/2 - iconWidth / 2;
        int top = (getMeasuredHeight() - mTextRect.height()) / 2 - iconWidth / 2;
        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        int alpha = (int)Math.ceil(255 * mAlpha);
        setupTargetBitmap(alpha);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        drawSourceText(canvas, alpha);
        drawTargetText(canvas, alpha);
    }

    private void setupTargetBitmap(int alpha){
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    private void drawSourceText(Canvas canvas, int alpha){
        mTextPaint.setColor(DEF_COLOR);
        mTextPaint.setAlpha(255 - alpha);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        int x = getMeasuredWidth()/2 - mTextRect.width()/2;
        int y = mIconRect.bottom + mTextRect.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    private void drawTargetText(Canvas canvas, int alpha){
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        int x = getMeasuredWidth()/2 - mTextRect.width()/2;
        int y = mIconRect.bottom + mTextRect.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    public void setIconAlpha(float alpha){
        this.mAlpha = alpha;
        invalidateView();
    }

    private void invalidateView(){
        if(Looper.getMainLooper() == Looper.myLooper()){
            invalidate();
        } else {
            postInvalidate();
        }
    }

    private static final String INSTANCE_STATUS = "instance_status";
    private static final String INSTANCE_ALPHA = "instance_alpha";

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putFloat(INSTANCE_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(INSTANCE_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
        } else {
            super.onRestoreInstanceState(state);
        }
    }
}
