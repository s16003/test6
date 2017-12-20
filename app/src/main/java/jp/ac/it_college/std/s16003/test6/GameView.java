package jp.ac.it_college.std.s16003.test6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by s16003 on 17/10/06.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private Ground ground;
    private Droid droid;
    private Map map;
    private List<List<Integer>> stage;
    private int inputFlag = 0x00;
    private int speed = 8;
    private int power;
    private Bitmap hadou;
    private int width;
    private static final long DRAW_INTERVAL = 1000 / 60;
    private DrawThread drawThread;
    private boolean onGround = false;
    private WaveBullet bullet;
    private boolean shoot = false;

    public GameView(Context context, Bitmap blocks, Bitmap background, Bitmap player, Bitmap hadou, int layoutWidth, int layoutHeight, InputStream[] is) {
        super(context);
        width = layoutWidth;
        this.hadou = hadou;
        map = new Map(is);
        stage = map.mapCreate();
        ground = new Ground(blocks, background, layoutWidth, layoutHeight, stage);
        droid = new Droid(layoutWidth, layoutHeight, player, hadou);
        getHolder().addCallback(this);

    }

    private class DrawThread extends Thread {
        private final AtomicBoolean isFinished = new AtomicBoolean();

        public void finish() {
            isFinished.set(true);
        }

        @Override
        public void run() {
            SurfaceHolder holder = getHolder();
            while (!isFinished.get()) {
                if (holder.isCreating()) {
                    continue;
                }

                Canvas canvas = holder.getSurface().lockHardwareCanvas();
                if (canvas != null) {

                    drawGame(canvas);
                    move();

                    holder.getSurface().unlockCanvasAndPost(canvas);
                }

                synchronized (this) {
                    try {
                        wait(DRAW_INTERVAL);
                    } catch (InterruptedException e){
                    }
                }
            }
        }
    }

    private void startDrawThread() {
        stopDrawThread();

        drawThread = new DrawThread();
        drawThread.start();
    }

    private void stopDrawThread() {
        if (drawThread != null) {
            drawThread.finish();
            drawThread = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
            startDrawThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stopDrawThread();
    }

    private void drawGame(Canvas canvas) {
        ground.drawMap(canvas);
        droid.drawPlayer(canvas);
        droid.moveDown();
        if(ground.collisionBottom(droid.getLeft(), droid.getRight(), droid.getBottom())) {
            droid.moveUp();
            onGround = true;
        }
        if (bullet != null) {
            if (bullet.getPosX() > width) {
                bullet = null;
                return;
            }
            bullet.draw(canvas, power);
            bullet.move(shoot);
            ground.collisionHadou(bullet.getPosX(), bullet.getPosY());
        }
    }

    public void left() {
        inputFlag |= 0x01;
    }

    public void right() {
        inputFlag |= 0x02;
    }

    public void jump_up() {
        if (!onGround) {
            return;
        }
        inputFlag |= 0x04;
    }

    public void jump_down() {
        onGround = false;
        inputFlag &= ~0x0F;
    }

    public void attack_push() {
        power = 0;
        droid.attack(6);
        shoot = false;
        inputFlag |= 0x08;
    }

    public void attack_pull() {
        droid.attack(7);
        shoot = true;
        inputFlag &= ~0x0F;
    }

    public void resetFlag() {
        inputFlag &= ~0x0F;
    }

    private void move() {
        if ((inputFlag & 0x01) != 0) {
            if ((ground.checkLeftEnd() || !droid.checkCenter()) && !ground.collisionLeft(droid.getLeft(), droid.getBottom())) {
                droid.moveLeft(speed);
                return;
            }
            ground.moveLeft();
            droid.moveLeft(0);
            if (ground.collisionLeft(droid.getLeft(), droid.getBottom())) {
                ground.moveRight();
            }
        }

        if ((inputFlag & 0x02) != 0) {
            if ((ground.checkRightEnd() || !droid.checkCenter()) && !ground.collisionRight(droid.getRight(), droid.getBottom())) {
                droid.moveRight(speed);
                return;
            }
            ground.moveRight();
            droid.moveRight(0);
            //Log.d("checkboolean", "move: collisionRight" + ground.collisionRight(droid.getRight(), droid.getBottom()));
            if (ground.collisionRight(droid.getRight(), droid.getBottom())) {
                ground.moveLeft();
            }
        }

        if ((inputFlag & 0x04) != 0 ) {
            droid.jump(onGround);
            droid.moveDown();
            onGround = false;
            return;
        }

        if ((inputFlag & 0x08) != 0) {
            if (power > 75) return;
            if (bullet != null) bullet = null;
            power++;
            bullet = new WaveBullet(droid.getLeft(), droid.getTop(), hadou);
            return;
        }
    }
}
