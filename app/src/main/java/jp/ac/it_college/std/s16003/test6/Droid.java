package jp.ac.it_college.std.s16003.test6;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by s16003 on 17/10/06.
 */

public class Droid {

    private final Paint paint = new Paint();
    private WaveBullet attack;
    private final int SPEED = 8;
    private int pos_y;
    private int pos_x;
    private Rect[] player_src = new Rect[10];
    private Rect player_dst = new Rect();
    private Rect center = new Rect();
    private final int PLAYER_SIZE;
    private final int PLAYER_HALFSIZE;
    private final Bitmap PLAYER;
    private final Bitmap HADOU;
    private int moveX;
    private int porse = 3;
    private final int GRAVITY = 1;
    private int vy;

    private int width;

    private int y_move = 0;
    private int y_prev;
    private int f = 2;
    private final int JUMP_SPEED = 32;
    private WaveBullet wb;

    public Droid(int width, int height, Bitmap player, Bitmap hadou) {
        this.width = width;
        PLAYER_SIZE = player.getWidth() / 3;
        PLAYER_HALFSIZE = PLAYER_SIZE / 2;
        PLAYER = player;
        HADOU = hadou;
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);

        pos_x = 64 * 4;
        pos_y = 64 * 7;

        y_prev = pos_y;

        center.set(
                width / 2 + 13,
                0,
                width / 2 + 13,
                height);


        /*
        left = 64 * 3;
        top = 64 * 6;
        right = 64 * 5 - 17;
        bottom = 64 * 8;
        */

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                player_src[j + (i * 3)] = new Rect(
                    PLAYER_SIZE * (j % 3),
                    PLAYER_SIZE * (i % 3),
                    PLAYER_SIZE * (j % 3 + 1),
                    PLAYER_SIZE * (i % 3 + 1));
            }
        }
    }

    public void drawPlayer(Canvas canvas) {
        player_dst.set(
                pos_x - PLAYER_HALFSIZE + moveX,
                pos_y - PLAYER_HALFSIZE,
                pos_x + PLAYER_HALFSIZE + moveX,
                pos_y + PLAYER_HALFSIZE);
        canvas.drawBitmap(PLAYER, player_src[porse], player_dst, null);

    }

    /*
    public void moveDown() {
        y_move = (pos_y - y_prev) + f;
        //if (y_move > 63) {
        //    y_prev = 63;
        //}
        y_prev = pos_y;
        pos_y += y_move;
        f = 2;
    }
    */
    public void moveDown() {
        pos_y += 8;
    }

    public void moveLeft(int speed) {
        if (getLeft() < 0) {
            return;
        }
        porse++;
        porse %= 3;

        moveX += -speed;

    }

    public void moveRight(int speed) {
        if (getRight() > width) {
            return;
        }
        porse--;
        porse = porse % 3 + 3;
        moveX += speed;
    }

    public void jump(boolean onGround) {
        Log.d("jump", "jump: " + onGround);
        if (onGround) {
            vy = -JUMP_SPEED;
            pos_y += vy;
            return;
        }
        if (!onGround) {
            vy += GRAVITY;
            pos_y += vy;
        }
    }
    /*
    public void jump(boolean onGround) {
        if (onGround) {
            Log.d(TAG, "jump: ok");
            f = -20;
        }

    }
    */

    public boolean checkCenter() {
        return player_dst.intersect(center);
    }

    public int getLeft() {
        return pos_x - PLAYER_HALFSIZE + moveX;
    }

    public int getRight() {
        return pos_x + PLAYER_HALFSIZE + moveX;
    }

    public int getTop() {
        return pos_y - PLAYER_HALFSIZE;
    }

    public int getBottom() {
        return pos_y + PLAYER_HALFSIZE;
    }

}
