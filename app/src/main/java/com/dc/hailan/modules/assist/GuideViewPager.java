package com.dc.hailan.modules.assist;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dc.hailan.MainActivity;
import com.dc.hailan.R;
import com.dc.hailan.data.WPfCommon;
import com.dc.hailan.data.global.GlobalConstants;
import com.dc.hailan.data.global.Hks;

import java.util.ArrayList;

public class GuideViewPager extends Activity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private ViewPagerAdapter vpAdapter;

    private LinearLayout llDot;// 指示器

    private ArrayList<View> views;

    private static int[] guidePics;// = {R.drawable.wq1, R.drawable.wq2, R.drawable.wq3};// 图片

    private int currentIndex;// 当前位置

    private static int idBegin = 1000;

    private boolean bFromAbout = false;// 关于界面点击引导页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //传递过来的false
        bFromAbout = getIntent().getExtras().getBoolean(GlobalConstants.KEY_IS_FROM_ABOUT);

        int wq1 = R.drawable.splash0;
        int wq3 = R.drawable.splash2;


        guidePics = new int[] {wq1, wq3};

        initView();


    }

    private void initView() {

        views = new ArrayList<View>();
        viewPager = (ViewPager) findViewById(R.id.ac_guide_viewpager);
        llDot = (LinearLayout) findViewById(R.id.ac_guide_dot);


        LinearLayout.LayoutParams mParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);

        int size = guidePics.length; //存放图片集合的长度

        for(int i =0;i<size;i++){

            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            imageView.setBackgroundResource(guidePics[i]);

            views.add(imageView);

            //最后一张图
            if (i == size -1){
                //当传递 值为ture 时
                if (bFromAbout){
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });
                }else {
                    //传递为 false    点击 进入 启动界面
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            WPfCommon.getInstance().put(Hks.is_first, false);
                            Intent intent = new Intent(GuideViewPager.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        }


        vpAdapter = new ViewPagerAdapter(views);
        viewPager.setAdapter(vpAdapter);
        viewPager.setOnPageChangeListener(this);

        //底部圆形 指示器
        initDot();

    }


    //底部圆形的 指示器
    private void initDot() {

        for (int i = 0; i < guidePics.length; i++) {

           ImageView  ivDot = new ImageView(this);

            ivDot.setMinimumHeight(20);
            ivDot.setMinimumWidth(20);
            //保持宽高比  不能单独使用  ImageView调整边框时保持可绘制对象的比例
            ivDot.setAdjustViewBounds(true);
            ivDot.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            ivDot.setPadding(10, 20, 10, 20);

            //设置ImageView的id
            ivDot.setId(i + idBegin);


             //可选择的颜色
            ivDot.setImageResource(R.drawable.dot_blue);

            llDot.addView(ivDot, i);   //添加到LinearLayout中

            if (currentIndex != -1) { //当前滑动到的位置

                if (currentIndex == i) {
                    ivDot.setSelected(true); //切换底部背景
                } else {
                    ivDot.setSelected(false);
                }
            }

        }
    }






    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


        updateTabs(position);


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }




    // 切换
    private void updateTabs(int position) {

        //切换时需要同步
        this.currentIndex = position;


        if (position < 0 || position > views.size()) {
            return;
        }

         //循环所有子view
        for (int i = 0; i < llDot.getChildCount(); i++) {

          ImageView   iv =(ImageView) findViewById(i + idBegin);

            if (iv != null) {
                if (position == i) {
                    iv.setSelected(true); //滑动时底部指示器变色
                } else {
                    iv.setSelected(false);
                }
            }
        }
        viewPager.setCurrentItem(position);


    }



    class    ViewPagerAdapter  extends PagerAdapter{


          private ArrayList<View> views;

          public ViewPagerAdapter(ArrayList<View> views) {
              this.views = views;
          }


          @Override
          public int getCount() {

              if (views != null) {
                  return views.size();
              }
              return 0;
          }


          @Override
          public Object instantiateItem(View container, int position) {

              ((ViewPager) container).addView(views.get(position), 0);

              return views.get(position);
          }


          @Override
          public boolean isViewFromObject(View view, Object object) {
              return view == object;
          }


          @Override
          public void destroyItem(View container, int position, Object object) {
              ((ViewPager) container).removeView(views.get(position));
          }
      }





}
