package okanimation.simpledemo.com.okanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private OkAnimation bt_ok_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

        bt_ok_animation = (OkAnimation) findViewById(R.id.bt_ok_animation);
        bt_ok_animation.setOkAnimationListener(new OkAnimation.okAnimationListener() {
            @Override
            public void onClickListener() {
                bt_ok_animation.start();
            }

            @Override
            public void animationFinish() {

            }
        });

    }
}
