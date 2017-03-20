package com.dc.hailan.modules;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.dc.hailan.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 等待界面   用来加载数据
 */
public class WelcomeActivity extends AppCompatActivity {

    @Bind(R.id.iv_welcome)
    ImageView ivWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        ivWelcome.setImageResource(R.drawable.splash0);

        //延时执行线程
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // 边收消息便进入,不要卡在请求部分
                toApp();
            }
        }, 500);

        // todo  数据初始化 等操作
        //地图定位  等  gps 定期上传

    }

    // 进入APP
    private void toApp() {

     /*   Intent intent = new Intent(WelcomeActivity.this, CloudMainMenuActivity.class);
        startActivity(intent);
        finish();*/
        //动画
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @OnClick(R.id.iv_welcome)
    public void onClick() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }
}
