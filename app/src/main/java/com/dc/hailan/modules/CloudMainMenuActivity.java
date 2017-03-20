package com.dc.hailan.modules;


/**
 * Created by dc on 17/3/18.
 * 仿网易云风格页面
 */
/*public class CloudMainMenuActivity extends BaseActivity {

  *//*  @Bind(R.id.view_status)
    View viewStatus; //状态栏

    @Bind(R.id.ll_title_menu)
    FrameLayout llTitleMenu;//打开侧边栏

    @Bind(R.id.iv_title_gank)
    ImageView ivTitleGank; //toolbar上按钮
    @Bind(R.id.iv_title_one)
    ImageView ivTitleOne;
    @Bind(R.id.iv_title_dou)
    ImageView ivTitleDou;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.vp_content)
    ViewPager vpContent;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.nv_menu)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    //侧滑栏 按钮
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.ll_header_bg)
    LinearLayout llHeaderBg;
    @Bind(R.id.ll_nav_homepage)
    LinearLayout llNavHomepage;
    @Bind(R.id.ll_nav_scan_download)
    LinearLayout llNavScanDownload;
    @Bind(R.id.ll_nav_deedback)
    LinearLayout llNavDeedback;
    @Bind(R.id.ll_nav_about)
    LinearLayout llNavAbout;
    @Bind(R.id.ll_nav_login)
    LinearLayout llNavLogin;
    @Bind(R.id.day_night_switch)
    SwitchCompat dayNightSwitch;
    @Bind(R.id.ll_nav_night)
    LinearLayout llNavNight;
    @Bind(R.id.ll_nav_exit)
    LinearLayout llNavExit;*//*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cloud_main_menu;
    }

    @Override
    public void initToolBar() {

    }


    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

      *//*  gone(fab);

        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new GankFragment());
        mFragmentList.add(new OneFragment());
        mFragmentList.add(new BookFragment());

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);

        vpContent.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        vpContent.setOffscreenPageLimit(2);
        vpContent.addOnPageChangeListener(this);
        ivTitleGank.setSelected(true);
        vpContent.setCurrentItem(0);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }

        //设置侧滑页
        navView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navView.getHeaderView(0);
        ButterKnife.bind(this, headerView);

        dayNightSwitch.setSelected(false);
        GlideUtil.loadCircleImage(this, ConstantsUrl.IC_AVATAR, ivAvatar);*//*

    }

  *//*  @OnClick({R.id.ll_title_menu, R.id.ll_nav_scan_download,R.id.iv_title_gank, R.id.iv_title_one, R.id.iv_title_dou, R.id.iv_avatar, R.id.ll_nav_homepage,
            R.id.ll_nav_deedback, R.id.ll_nav_about, R.id.ll_nav_login,R.id.ll_nav_exit,R.id.day_night_switch,R.id.ll_header_bg,R.id.ll_nav_night})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ll_title_menu:
                break;
            case R.id.iv_title_gank:
                break;
            case R.id.iv_title_one:
                break;
            case R.id.iv_title_dou:
                break;

            case R.id.iv_avatar: //点击用户头像
                break;

            case R.id.ll_nav_exit: //离开应用
                break;


            case R.id.ll_nav_homepage: //首页
                break;

            case R.id.ll_nav_scan_download://扫码下载
                break;

            case R.id.ll_nav_about: //关于
                break;
            case R.id.ll_nav_login: //登录
                break;

            case R.id.ll_nav_deedback: //问题反馈
                break;

            case R.id.day_night_switch://选择器
                break;
            case R.id.ll_header_bg://背景图片选择
            break;

            case R.id.ll_nav_night://夜间模式
                break;
        }
    }*//*


}*/
