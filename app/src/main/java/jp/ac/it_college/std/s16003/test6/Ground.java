package jp.ac.it_college.std.s16003.test6;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.List;

/**
 * Created by s16003 on 17/10/06.
 */

public class Ground {
    private final Rect[] BLOCK_SRC = new Rect[16];
    private final int MAPCHIP_SIZE;
    private final Bitmap BLOCKS;
    private final Bitmap BACKGROUND;
    private final int HALF_CHIPLEN;
    private final Rect map_dst = new Rect();
    private Rect collision_rect = new Rect();
    private final Rect screen_dst;
    private final int MAP_LAST;
    private int width;
    private int height;
    private int moveX = 0;
    private int pos_x = 0;
    private boolean bottom1;
    private boolean bottom2;
    private List<List<Integer>> stage;

    private MapCell[] mapcell = {
            new MapCell(0, false),
            new MapCell(1, true),
            new MapCell(2, true),
            new MapCell(3, true),
            new MapCell(4, true),
            new MapCell(5, true),
            new MapCell(6, true)
    };

    public Ground(Bitmap blocks, Bitmap background, int width, int height, List<List<Integer>> stage) {
        BLOCKS = blocks;
        BACKGROUND = background;
        MAPCHIP_SIZE = (BLOCKS.getWidth() / 7);   //64
        this.width = width;
        this.height = height;
        HALF_CHIPLEN = width / MAPCHIP_SIZE;
        MAP_LAST = width / MAPCHIP_SIZE;
        this.stage = stage;

        Log.d("getwidth", "getWidth: " + width);
        Log.d("getheight", "getheight: " + height);
        Log.d("getheight", "mapchip_size: " + MAPCHIP_SIZE);

        screen_dst = new Rect(0, 0, width, height);

        for (int i = 0; i < mapcell.length; i++) {
            BLOCK_SRC[mapcell[i].getGrafic()] = new Rect(MAPCHIP_SIZE * i, 0, MAPCHIP_SIZE * (i + 1), MAPCHIP_SIZE);
        }
    }

    public void drawMap(Canvas canvas) {
        canvas.drawBitmap(BACKGROUND, null, screen_dst, null);

        for (int x = width  / MAPCHIP_SIZE; 0 <= x; x--) {
            for (int y = height / MAPCHIP_SIZE; 0 <= y; y--) {

                if (stage.get(y).get(x + pos_x) == 0) {
                    continue;
                }

                map_dst.set(MAPCHIP_SIZE * x - moveX, (y - 1) * MAPCHIP_SIZE,
                        MAPCHIP_SIZE * (x + 1) - moveX , y * MAPCHIP_SIZE);

                canvas.drawBitmap(BLOCKS, BLOCK_SRC[stage.get(y).get(x + pos_x)], map_dst, null);

            }
        }
    }

    /*
    public boolean collisionBottom(int x, int y) {
        if (y / MAPCHIP_SIZE + 1 > height / MAPCHIP_SIZE) {
            return false;
        }

        return mapcell[stage.get(y / MAPCHIP_SIZE + 1).get(x / MAPCHIP_SIZE + pos_x - 2)].getMoveFlag();
    }

    public boolean collisionRight(int x, int y) {
        return mapcell[stage.get(y / MAPCHIP_SIZE + 4).get((x + (pos_x * MAPCHIP_SIZE))/ MAPCHIP_SIZE)].getMoveFlag();
    }

    public boolean collisionLeft(int x, int y) {
        return mapcell[stage.get(y / MAPCHIP_SIZE + 4).get((x + (moveX * MAPCHIP_SIZE))/ MAPCHIP_SIZE)].getMoveFlag();
    }

    public boolean collisionTop(int x, int y) {
        return mapcell[stage.get(y / MAPCHIP_SIZE - 2).get(x / MAPCHIP_SIZE + moveX)].getMoveFlag();
    }
    */

    //64px collision
    public boolean collisionBottom(int left, int right, int y) {
        if (y / MAPCHIP_SIZE + 1 > height / MAPCHIP_SIZE) {
            return false;
        }
        //Log.d("checkleft", "collisionBottom: left" + left);
        //Log.d("checkright", "collisionBottom: right" + right);
        left = left / MAPCHIP_SIZE + pos_x;
        right = right / MAPCHIP_SIZE + pos_x - 1;



        //Log.d("checkXY", "collisionRight: y, x" + (y / MAPCHIP_SIZE + 1) + "," +(x / MAPCHIP_SIZE + pos_x - 1));
        //Log.d("check_cell", "collisionRight: " + mapcell[stage.get(y / MAPCHIP_SIZE).get(((x - 10) + (pos_x * MAPCHIP_SIZE)) / MAPCHIP_SIZE)].getGrafic());
        //return mapcell[stage.get(y / MAPCHIP_SIZE + 1).get((x - moveX) / MAPCHIP_SIZE + pos_x - 1)].getMoveFlag();
        bottom1 = mapcell[stage.get(y / MAPCHIP_SIZE + 1).get(left)].getMoveFlag();
        bottom2 = mapcell[stage.get(y / MAPCHIP_SIZE + 1).get(right)].getMoveFlag();
        return bottom1 || bottom2;
    }

    public boolean collisionRight(int x, int y) {
        Log.d("checkXY", "collisionRight: y, x" + (y / MAPCHIP_SIZE + 1) + "," + ((x - moveX) / MAPCHIP_SIZE + pos_x));
        //Log.d("check_cell", "collisionRight: " + mapcell[stage.get(y / MAPCHIP_SIZE).get(((x - 10) + (pos_x * MAPCHIP_SIZE)) / MAPCHIP_SIZE)].getGrafic());

        return mapcell[stage.get(y / MAPCHIP_SIZE + 1).get((x - moveX) / MAPCHIP_SIZE + pos_x)].getMoveFlag();
    }

    public boolean collisionLeft(int x, int y) {
        if (x < 0) {
            return true;
        }
        return mapcell[stage.get(y / MAPCHIP_SIZE).get((x + (pos_x * MAPCHIP_SIZE))/ MAPCHIP_SIZE)].getMoveFlag();
    }

    public boolean collisionTop(int x, int y) {
        return mapcell[stage.get(y / MAPCHIP_SIZE - 2).get(x / MAPCHIP_SIZE + moveX)].getMoveFlag();
    }

    public void collisionHadou(int x, int y) {
        x = x / MAPCHIP_SIZE + pos_x;
        y = y / MAPCHIP_SIZE + 1;
        Log.d("checkX", "collisionHadou: x " + x);
        Log.d("checkY", "collisionHadou: x " + y);

        if (stage.get(y).get(x) != 0) {
            stage.get(y).set(x, 0);
            Log.d("check_hadou", "collisionHadou: ok");
        }
    }

    public void moveLeft() {
        if (checkLeftEnd()) {
            return;
        }

        moveX -= 8;
        pos_x += moveX / MAPCHIP_SIZE;
        moveX %= MAPCHIP_SIZE;
    }

    public void moveRight() {
        if (checkRightEnd()) {
            return;
        }

        moveX += 8;
        pos_x += moveX / MAPCHIP_SIZE;
        moveX %= MAPCHIP_SIZE;
    }

    public boolean checkLeftEnd() {
        if (pos_x == 0) {
            return true;
        }
        return false;
    }

    public boolean checkRightEnd() {
        if (pos_x + MAP_LAST + 1 == stage.get(0).size()) {
            return true;
        }
        return false;
    }
}
