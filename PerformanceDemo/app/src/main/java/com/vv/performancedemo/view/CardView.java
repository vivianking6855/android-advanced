package com.vv.performancedemo.view;

/**
 * Created by vivian on 2017/3/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.vv.performancedemo.R;
import com.vv.performancedemo.common.ImageUtils;

public class CardView extends View {
    private final static String TAG = CardView.class.getSimpleName();

    private final static int X_OFFSET = 50; // canvas x offset
    private final static int Y_OFFSET = 100;// canvas y offset
    private final static int IMG_HOR_OFFSET = 200; // image cover start point

    private Bitmap[] mCards = new Bitmap[4];
    private int[] mImgId = new int[]{
            R.drawable.card1,
            R.drawable.card2,
            R.drawable.card3,
            R.drawable.card4
    };

    public CardView(Context context) {
        this(context, null);
    }

    public CardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        Bitmap bm = null;
        for (int i = 0; i < mCards.length; i++) {
            bm = ImageUtils.decodeSampledBitmapFromResource(getResources(), mImgId[i], 400, 600);
            mCards[i] = Bitmap.createScaledBitmap(bm, 400, 600, false);
        }

        setBackgroundColor(Color.parseColor("#F5F5DC"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(X_OFFSET, Y_OFFSET);

        for (int i = 0; i < mCards.length; i++) {
            canvas.translate(IMG_HOR_OFFSET, 0);
            canvas.save();
            if (i < mCards.length - 1) {
                canvas.clipRect(0, 0, IMG_HOR_OFFSET, mCards[i].getHeight());
            }
            canvas.drawBitmap(mCards[i], 0, 0, null);
            canvas.restore();
        }

        // bad code
//        for (Bitmap bitmap : mCards) {
//            canvas.translate(120, 0);
//            canvas.drawBitmap(bitmap, 0, 0, null);
//        }

        canvas.restore();
    }

}
