package com.dc.hailan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dc.hailan.utils.picture.GlideUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoActivity extends AppCompatActivity {

    @Bind(R.id.image1)
    ImageView image1;
    @Bind(R.id.image2)
    ImageView image2;
    @Bind(R.id.image3)
    ImageView image3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);

        //圆角图片
        GlideUtil.loadRoundCornerImage(this, "https://img.alicdn.com/bao/uploaded/i2/TB1N4V2PXXXXXa.XFXXXXXXXXXX_!!0-item_pic.jpg_640x640q50.jpg", image1);
        GlideUtil.loadGifImage(this,"http://ww4.sinaimg.cn/mw690/bcc93f49gw1f6r1nce77jg207x07sx6q.gif",image2);

        GlideUtil.loadCircleImage(this,"https://img.alicdn.com/bao/uploaded/i2/TB1N4V2PXXXXXa.XFXXXXXXXXXX_!!0-item_pic.jpg_640x640q50.jpg",image3);

    }

    @OnClick({R.id.image1, R.id.image2, R.id.image3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image1:
                break;
            case R.id.image2:
                break;
            case R.id.image3:
                break;
        }
    }
}
