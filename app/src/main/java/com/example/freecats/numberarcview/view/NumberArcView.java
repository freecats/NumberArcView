package com.example.freecats.numberarcview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.example.freecats.numberarcview.R;
import com.example.freecats.numberarcview.util.ResUtils;
import com.example.freecats.numberarcview.util.ScreenUtils;
import com.example.freecats.numberarcview.util.StringUtils;


/**
 * 圆弧信息显示View
 * Created by freecats on 2017/8/13.
 */

public class NumberArcView extends View {
    private int mStartColor = ResUtils.getInstance().getColor(R.color.colorPrimary);
    private int mEndColor = ResUtils.getInstance().getColor(R.color.colorPrimaryDark);
    private int mShadowColor = ResUtils.getInstance().getColor(R.color.colorAccent);
    private int mNumberColor = ResUtils.getInstance().getColor(R.color.colorPrimary);
    private int mTextColor = ResUtils.getInstance().getColor(R.color.colorPrimary);
    private int mUnitColor = ResUtils.getInstance().getColor(R.color.colorPrimary);
    private float mNumber = -1;
    private boolean isFloat = false;
    private int mRadian = 280;
    private float mArcWidth = ScreenUtils.getInstance().dpToPx(10);
    private float mShadowWidth = ScreenUtils.getInstance().dpToPx(4);
    private float mTextSize = ScreenUtils.getInstance().sp2px(15);
    private float mNumberTextSize = ScreenUtils.getInstance().sp2px(36);
    private float mUnitTextSize = ScreenUtils.getInstance().sp2px(15);
    private float mWidth = ScreenUtils.getInstance().dpToPx(108);
    private float mHeight = ScreenUtils.getInstance().dpToPx(108);
    private float mTextMarginNumber = ScreenUtils.getInstance().dpToPx(15);
    private float mUnitMarginNumber = ScreenUtils.getInstance().dpToPx(10);
    private String mText;
    private String mUnit;
    private Rect mRectText = new Rect();
    private Rect mRectNumber = new Rect();
    private Rect mRectNumberInvalid = new Rect();
    private Rect mRectUnit = new Rect();

    private Paint mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintUnit = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintArc = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintShadow = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintNumber = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float mCenterX;
    private float mCenterY;
    private float mCenterYOfArc;
    private float mUnitY;
    private float mNumberY;


    public NumberArcView(Context context) {
        this(context, null, 0);
    }

    public NumberArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (null != attrs) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.NumberArcView);

            mStartColor = ta.getColor(R.styleable.NumberArcView_nav_startColor, mStartColor);
            mEndColor = ta.getColor(R.styleable.NumberArcView_nav_endColor, mEndColor);
            mShadowColor = ta.getColor(R.styleable.NumberArcView_nav_shadowColor, mShadowColor);
            mNumberColor = ta.getColor(R.styleable.NumberArcView_nav_numberColor, mNumberColor);
            mTextColor = ta.getColor(R.styleable.NumberArcView_nav_textColor, mTextColor);
            mUnitColor = ta.getColor(R.styleable.NumberArcView_nav_unitColor, mUnitColor);

            mArcWidth = ta.getDimension(R.styleable.NumberArcView_nav_arcWidth, mArcWidth);
            mShadowWidth = ta.getDimension(R.styleable.NumberArcView_nav_shadowWidth, mShadowWidth);
            mWidth = ta.getDimension(R.styleable.NumberArcView_nav_width, mWidth);
            mHeight = ta.getDimension(R.styleable.NumberArcView_nav_height, mHeight);

            mTextSize = ta.getDimension(R.styleable.NumberArcView_nav_textSize, mTextSize);
            mNumberTextSize = ta.getDimension(R.styleable.NumberArcView_nav_numberTextSize, mNumberTextSize);
            mUnitTextSize = ta.getDimension(R.styleable.NumberArcView_nav_unitTextSize, mUnitTextSize);

            mTextMarginNumber = ta.getDimension(R.styleable.NumberArcView_nav_textMarginNumber, mTextMarginNumber);
            mUnitMarginNumber = ta.getDimension(R.styleable.NumberArcView_nav_unitMarginNumber, mUnitMarginNumber);

            mText = ta.getString(R.styleable.NumberArcView_nav_text);
            mUnit = ta.getString(R.styleable.NumberArcView_nav_unit);
            mNumber = ta.getFloat(R.styleable.NumberArcView_nav_number, mNumber);
            isFloat = ta.getBoolean(R.styleable.NumberArcView_nav_isFloat, isFloat);
            mRadian = ta.getInteger(R.styleable.NumberArcView_nav_radian, mRadian);

            if (mRadian < 0) {
                mRadian = 300;
            } else if (mRadian > 360) {
                mRadian = mRadian % 360;
            }

            ta.recycle();
        }

        measureText();

    }

    private void measureText() {

        if (!TextUtils.isEmpty(mText) && mTextSize > 0) {
            mPaintText.setTextSize(mTextSize);
            mPaintText.getTextBounds(StringUtils.getTextFromHtml(mText).toString(),
                    0,
                    StringUtils.getTextFromHtml(mText).toString().length(),
                    mRectText);
        }

        String number = mNumber >= 0 ? isFloat ? StringUtils.formatSingleNumberDot(mNumber) : StringUtils.formatNumber((int) mNumber) : "00";
        if (!TextUtils.isEmpty(number) && mNumberTextSize > 0) {
            mPaintNumber.setTextSize(mNumberTextSize);
            mPaintNumber.getTextBounds(number, 0, number.length(), mRectNumber);
        }

        String numberInvalid = "---";
        if (!TextUtils.isEmpty(numberInvalid) && mNumberTextSize > 0) {
            mPaintNumber.setTextSize(mNumberTextSize);
            mPaintNumber.getTextBounds(numberInvalid, 0, numberInvalid.length(), mRectNumberInvalid);
        }

        if (!TextUtils.isEmpty(mUnit) && mUnitTextSize > 0) {
            mPaintUnit.setTextSize(mUnitTextSize);
            mPaintUnit.getTextBounds(mUnit, 0, mUnit.length(), mRectUnit);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        measureText();
        drawUnit(canvas);
        drawNumber(canvas);
        drawText(canvas);
        drawShadow(canvas);
        drawArc(canvas);
    }

    private void drawNumber(Canvas canvas) {
        String number = mNumber >= 0 ? isFloat ? StringUtils.formatSingleNumberDot(mNumber) : StringUtils.formatNumber((int) mNumber) : "---";


        mPaintNumber.setColor(mNumberColor);
        mPaintNumber.setTextSize(mNumberTextSize);

        float x;
        if (mNumber >= 0) {
            x = mCenterX - mRectNumber.width() / 2;
            mNumberY = mCenterYOfArc;
        } else {
            x = mCenterX - mRectNumberInvalid.width() / 2;
            mNumberY = mCenterYOfArc - mRectNumberInvalid.height() / 2;
        }

        mNumberY += 22;

        canvas.drawText(number, x, mNumberY, mPaintNumber);
    }

    private void drawText(Canvas canvas) {
        if (null == mText) mText = "";

        mPaintText.setColor(mTextColor);
        mPaintText.setTextSize(mTextSize);

        float x = mCenterX - mRectText.width() / 2;
        float y = mNumberY - mTextMarginNumber - mRectText.height() / 2 - mRectNumber.height() / 2;

        if (!mText.contains("2")) {
            canvas.drawText(StringUtils.getTextFromHtml(mText),
                    0,
                    StringUtils.getTextFromHtml(mText).length(),
                    x,
                    y,
                    mPaintText);
        } else {
            //哇，烦，用了Spanned但drawText还是没有下标数字效果,故出此下策
            canvas.drawText(StringUtils.getTextFromHtml(mText),
                    0,
                    StringUtils.getTextFromHtml(mText).length() - 1,
                    x,
                    y,
                    mPaintText);

            Rect rect = new Rect();
            mPaintText.getTextBounds("SpO", 0, 3, rect);
            x += rect.width();
            mPaintText.setTextSize(mTextSize * 0.7f);
            canvas.drawText("2",
                    0,
                    1,
                    x,
                    y,
                    mPaintText);
        }
    }

    private void drawUnit(Canvas canvas) {
        if (null == mUnit) mUnit = "";

        mPaintUnit.setColor(mUnitColor);
        mPaintUnit.setTextSize(mUnitTextSize);

        float x = mCenterX - mRectUnit.width() / 2;
        mUnitY = mHeight - mRectUnit.height() - mShadowWidth + 18;

        canvas.drawText(mUnit, x, mUnitY, mPaintUnit);
    }

    private void drawArc(Canvas canvas) {
        mPaintArc.setStrokeWidth(mArcWidth);
        mPaintArc.setStyle(Paint.Style.STROKE);
        mPaintArc.setStrokeCap(Paint.Cap.ROUND);

        int[] colors = new int[]{mStartColor, mEndColor, mEndColor, mEndColor, mStartColor};
        float[] positions = new float[]{0.15f, 0.25f, 0.5f, 0.8f, 1.0f};
        SweepGradient shader;
        if (Build.VERSION.SDK_INT >= 21) {
            shader = new SweepGradient(mCenterX, mCenterY, colors, positions);
        } else {
            shader = new SweepGradient(mCenterX, mCenterY, colors, null);
        }
        mPaintArc.setShader(shader);


        RectF rectF = new RectF();
        rectF.left = mCenterX - mWidth / 2 + mArcWidth;
        rectF.top = mCenterY - mHeight / 2 + mArcWidth + 18;
        rectF.right = mCenterX + mWidth / 2 - mArcWidth;
        rectF.bottom = mCenterY + mHeight / 2 - mArcWidth + 18;

        canvas.drawArc(rectF, 90 + ((360 - mRadian) / 2), mRadian, false, mPaintArc);

    }

    private void drawShadow(Canvas canvas) {

        mPaintShadow.setStrokeWidth(mArcWidth);
        mPaintShadow.setStyle(Paint.Style.STROKE);
        mPaintShadow.setStrokeCap(Paint.Cap.ROUND);
        mPaintShadow.setColor(mShadowColor);

        RectF rectF = new RectF();
        rectF.left = mCenterX - mWidth / 2 + mArcWidth - mShadowWidth;
        rectF.top = mCenterY - mHeight / 2 + mArcWidth - mShadowWidth + 18;
        rectF.right = mCenterX + mWidth / 2 - mArcWidth + mShadowWidth;
        rectF.bottom = mCenterY + mHeight / 2 - mArcWidth + mShadowWidth + 18;

        canvas.drawArc(rectF, 90 + ((360 - mRadian) / 2), mRadian, false, mPaintShadow);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int desiredWidth = (int) Math.ceil((mWidth > 0 ? mWidth :
                2 * mShadowWidth
                        + 2 * mArcWidth
                        + Math.max(mRectNumber.width(), Math.max(mRectText.width(), mRectUnit.width()))
        ));


        int desiredHeight = (int) Math.ceil((mHeight > 0 ? mHeight :
                mShadowWidth
                        + mArcWidth
                        + mRectText.height()
                        + mTextMarginNumber
                        + mRectNumber.height()
                        + mUnitMarginNumber
                        + mRectUnit.height()));

        int width = desiredWidth;
        int height = desiredHeight;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = desiredHeight;
        }

        mCenterYOfArc = height / 2 + mShadowWidth + mArcWidth;
        mCenterY = height / 2;
        mCenterX = width / 2;

        setMeasuredDimension(width, height);
    }

    public int getStartColor() {
        return mStartColor;
    }

    public void setStartColor(int mStartColor) {
        this.mStartColor = mStartColor;
        this.invalidate();
    }

    public int getEndColor() {
        return mEndColor;
    }

    public void setEndColor(int mEndColor) {
        this.mEndColor = mEndColor;
        this.invalidate();
    }

    public int getShadowColor() {
        return mShadowColor;
    }

    public void setShadowColor(int mShadowColor) {
        this.mShadowColor = mShadowColor;
        this.invalidate();
    }

    public int getNumberColor() {
        return mNumberColor;
    }

    public void setNumberColor(int mNumberColor) {
        this.mNumberColor = mNumberColor;
        this.invalidate();
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        this.invalidate();
    }

    public int getUnitColor() {
        return mUnitColor;
    }

    public void setUnitColor(int mUnitColor) {
        this.mUnitColor = mUnitColor;
        this.invalidate();
    }

    public float getNumber() {
        return mNumber;
    }

    public void setNumber(float mNumber) {
        this.mNumber = mNumber;
        this.invalidate();
    }

    public int getRadian() {
        return mRadian;
    }

    public void setRadian(int mRadian) {
        this.mRadian = mRadian;
        this.invalidate();
    }

    public float getArcWidth() {
        return mArcWidth;
    }

    public void setArcWidth(float mArcWidth) {
        this.mArcWidth = mArcWidth;
        this.invalidate();
    }

    public float getShadowWidth() {
        return mShadowWidth;
    }

    public void setShadowWidth(float mShadowWidth) {
        this.mShadowWidth = mShadowWidth;
        this.invalidate();
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        this.invalidate();
    }

    public float getNumberTextSize() {
        return mNumberTextSize;
    }

    public void setNumberTextSize(float mNumberTextSize) {
        this.mNumberTextSize = mNumberTextSize;
        this.invalidate();
    }

    public float getUnitTextSize() {
        return mUnitTextSize;
    }

    public void setUnitTextSize(float mUnitTextSize) {
        this.mUnitTextSize = mUnitTextSize;
        this.invalidate();
    }

    public float getViewWidth() {
        return mWidth;
    }

    public void setViewWidth(float mWidth) {
        this.mWidth = mWidth;
        this.invalidate();
    }

    public float getViewHeight() {
        return mHeight;
    }

    public void setViewHeight(float mHeight) {
        this.mHeight = mHeight;
        this.invalidate();
    }

    public float getTextMarginNumber() {
        return mTextMarginNumber;
    }

    public void setTextMarginNumber(float mTextMarginNumber) {
        this.mTextMarginNumber = mTextMarginNumber;
        this.invalidate();
    }

    public float getUnitMarginNumber() {
        return mUnitMarginNumber;
    }

    public void setUnitMarginNumber(float mUnitMarginNumber) {
        this.mUnitMarginNumber = mUnitMarginNumber;
        this.invalidate();
    }

    public String getmText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
        this.invalidate();
    }

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String mUnit) {
        this.mUnit = mUnit;
        this.invalidate();
    }
}
