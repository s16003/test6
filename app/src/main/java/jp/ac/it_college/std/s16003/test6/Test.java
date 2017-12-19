package jp.ac.it_college.std.s16003.test6;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by s16003 on 17/10/13.
 */

public class Test {
    private final Paint paint = new Paint();

    private Bitmap bitmap;
    final Rect rect;

    public Test(Bitmap bitmap, int width, int height) {
        this.bitmap = bitmap;

        int left = 20;
        int top = 235;
        int right = 100;
        int bottom = 269;
        rect = new Rect(left, top, right, bottom);

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rect.left, rect.top, paint);
    }


}
