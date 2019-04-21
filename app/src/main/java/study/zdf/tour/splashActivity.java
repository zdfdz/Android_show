package study.zdf.tour;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import study.zdf.tour.Utils.SpUtils;

/**
 *
 */

public class splashActivity extends AppCompatActivity {

    private ImageView mIvSplash;//splash首页图片
    private TextView mTvDesc;//底部文字描述
    private TextView mTvClock;//倒计时
    private int mCd = 3;//倒计时3S
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒
        //设置动画效果
        AlphaAnimation animation = new AlphaAnimation(0.5f, 1);
        animation.setDuration(3000);
        animation.setFillAfter(true);

        mIvSplash.startAnimation(animation);

//        动画监听,执行完之后跳转页面
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                ifEnter为true是跳转到欢迎界面,为false时跳转到主界面
                boolean ifEnter = SpUtils.getBoolean(splashActivity.this, "if_enter", true);
                if (ifEnter) {
                    Intent intent = new Intent(splashActivity.this, GuideActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(splashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }

    //初始化控件
    private void init() {
        mIvSplash = findViewById(R.id.iv_splash);
        mTvDesc = findViewById(R.id.tv_splashTXT);
        mTvClock = findViewById(R.id.tv_clock);

    }

    //    更新倒计时
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    mCd--;
                    mTvClock.setText("跳过 : " + mCd+"s");
                    if (mCd < 0) {
                        timer.cancel();
                        mTvClock.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };
}
