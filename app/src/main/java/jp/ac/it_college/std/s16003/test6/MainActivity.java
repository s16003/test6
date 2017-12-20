package jp.ac.it_college.std.s16003.test6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GameView gameView;
    private MediaPlayer mediaPlayer;
    private int layoutWidth;
    private int layoutHeight;
    private InputStream[] is = new InputStream[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            is[0] = this.getAssets().open("stage01.dat");
        } catch (IOException e) {
        }

        /*
        for (int i = 0; i < 29; i++) {
            stage.add(new ArrayList<Integer>());
        }
        try {
            try {
                is = this.getAssets().open("stage01.dat");
                br = new BufferedReader(new InputStreamReader(is));

                String str;
                int count = 0;
                while((str = br.readLine()) != null) {
                    list = str.split(",");

                    for (int j = 0; j < 153; j++) {
                        stage.get(count).add(Integer.parseInt(list[j]));
                    }

                    //sub.add(Integer.parseInt(str.split(",")));
                    count++;
                }

            } finally {
                is.close();
                br.close();
            }
        } catch (IOException e) {
        }

        */
        setContentView(R.layout.activity_main);


        //if (mediaPlayer == null) {
        //    mediaPlayer = MediaPlayer.create(this, R.raw.famipop3);
        //    mediaPlayer.start();
        //}
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Bitmap blocks = BitmapFactory.decodeResource(getResources(), R.drawable.testmap64);
        Bitmap player = BitmapFactory.decodeResource(getResources(), R.drawable.player_test);
        Bitmap hadou = BitmapFactory.decodeResource(getResources(), R.drawable.hadoudan);
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.bokashi);
        FrameLayout layout = (FrameLayout) findViewById(R.id.ground);

        findViewById(R.id.right).setOnTouchListener(this);
        findViewById(R.id.left).setOnTouchListener(this);
        findViewById(R.id.up).setOnTouchListener(this);
        findViewById(R.id.down).setOnTouchListener(this);
        findViewById(R.id.a_button).setOnTouchListener(this);
        findViewById(R.id.b_button).setOnTouchListener(this);

        layoutWidth = layout.getWidth();
        layoutHeight = layout.getHeight();

        gameView = new GameView(this, blocks, background, player, hadou, layoutWidth, layoutHeight, is);
        layout.addView(gameView);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.left:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    gameView.left();
                    break;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    gameView.resetFlag();
                    break;
                }
                break;
            case R.id.right:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    gameView.right();
                    break;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    gameView.resetFlag();
                    break;
                }
                break;
            case R.id.b_button:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    gameView.jump_up();
                    break;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    gameView.jump_down();
                    break;
                }
                break;
            case R.id.a_button:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    gameView.attack_push();
                    break;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    gameView.attack_pull();
                }
        }
        return false;
    }
    /*
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("getX", "onTouch: " + motionEvent.getX());
                Log.d("getY", "onTouch: " + motionEvent.getY());
        }
            return true;
    }
    */
}
