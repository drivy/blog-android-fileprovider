package com.drivy.android.blog.fileprovider;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Create a random bitmap.
 */

class RandomBitmapFactory {

    private static final int BITMAP_SIZE = 256;
    private static final float MAX_STROKE_WIDTH = 20f;
    private static final int STROKE_COUNT = 50;

    Bitmap createRandomBitmap() {
        final Random rnd = new Random(System.currentTimeMillis());

        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        final Bitmap bitmap = Bitmap.createBitmap(BITMAP_SIZE, BITMAP_SIZE, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);

        for (int i = 0; i < STROKE_COUNT; i++) {
            final int startX = rnd.nextInt(BITMAP_SIZE);
            final int startY = rnd.nextInt(BITMAP_SIZE);
            final int stopX = rnd.nextInt(BITMAP_SIZE);
            final int stopY = rnd.nextInt(BITMAP_SIZE);
            final float width = rnd.nextFloat() * MAX_STROKE_WIDTH;
            final int color = rnd.nextInt();

            paint.setColor(color);
            paint.setStrokeWidth(width);

            canvas.drawRect(startX, startY, stopX, stopY, paint);
        }

        return bitmap;
    }
}
