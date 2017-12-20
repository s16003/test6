package jp.ac.it_college.std.s16003.test6;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by s16003 on 17/12/20.
 */

public class Guage {
    private Paint color = new Paint();
    private Paint moldColor = new Paint();
    private Rect guage = new Rect();
    private Rect guageMold = new Rect();
    private int left;
    private int top;
    private int right;
    private int bottom;

    Guage(int left, int top, int right, int bottom, Paint color) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
        moldColor.setColor(Color.GRAY);

        guage.set(left, top, right, bottom);
        guageMold.set(left, top, right, bottom);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(guageMold, moldColor);
        canvas.drawRect(guage, color);
    }

    public void decrease() {
        right--;
        guage.set(left, top, right, bottom);
    }

    public void increase() {

    }
}
