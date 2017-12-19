package jp.ac.it_college.std.s16003.test6;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by s16003 on 17/12/08.
 */

public class WaveBullet {
    private Bitmap hadou;
    private final int HADOU_SIZE;
    private final int HADOU_HALFSIZE;
    private Rect hadou_src;
    private Rect hadou_dst = new Rect();
    private int pos_x;
    private int pos_y;

    public WaveBullet(int height, int width, Bitmap hadou) {
        this.hadou = hadou;
        HADOU_SIZE = hadou.getWidth();
        HADOU_HALFSIZE = HADOU_SIZE / 2;
        pos_x = width;
        pos_y = height;

        hadou_src = new Rect(0, 0, HADOU_SIZE, HADOU_SIZE);
    }

    public void move(boolean shoot) {
        if (!shoot) {
            return;
        }
        pos_x += 5;
    }

    public void draw(Canvas canvas, int power) {
        hadou_dst.set(
                pos_x - HADOU_HALFSIZE + 75 + power,
                pos_y - HADOU_HALFSIZE - 75 + power,
                pos_x + HADOU_HALFSIZE + 75 + power,
                pos_y + HADOU_HALFSIZE - 75 + power);
        canvas.drawBitmap(hadou, hadou_src, hadou_dst, null);
    }
}
