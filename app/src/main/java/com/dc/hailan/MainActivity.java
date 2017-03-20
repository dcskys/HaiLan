package com.dc.hailan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dc.hailan.data.WPfCommon;
import com.dc.hailan.data.global.CoConfig;
import com.dc.hailan.data.global.GlobalConstants;
import com.dc.hailan.data.global.Hks;
import com.dc.hailan.data.net.loginreg.LoginUserData;
import com.dc.hailan.modules.SplashActivity;
import com.dc.hailan.modules.WelcomeActivity;
import com.dc.hailan.modules.assist.GuideViewPager;
import com.dc.hailan.utils.DeviceUtil;
import com.dc.hailan.utils.StrUtil;
import com.dc.hailan.utils.greendao.demo.NoteActivity;
import com.dc.hailan.utils.logger.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main)
        //没有对应视图
        //todo  启动监听 来电显示的的服务  Service

       // 判断启动方式是不是从点击OPEN键启动的.  还是home 启动  防止多次启动
        //判断点击图标的意图，如果是原来的应用不存在，点击应用ICON进入应用
//如果应用已经存在用户点击HOME键之后重新点击应用的ICON 重新进入app保证进入的是按home键时的Activity
//而不是重新进入SplashActivity---> MainActivity
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            L.e("程序退出了");
            this.finish();
            return;
        }

        initGuide();

    }


    /**
     * 引导页
     */
    private void initGuide() {

        if (!CoConfig.want_guide) {  // 根据一个string资源  是否为1，默认0 ，返回true
            initData();
            L.e("这里默认不执行的！");
            return;
        }

        // 获取当前版本信息     比较版本号,新版本显示引导页
        int versionCode = WPfCommon.getInstance().get(Hks.app_version_code, Integer.class, 0);

        //当前程序的版本  大于 储存 （升级操作等）
        if (DeviceUtil.getVersionCode(this) != null
                && DeviceUtil.getVersionCode(this) > versionCode) { //当前软件版本大于储存的版本

            // 初始化wq列表例如:邀请等

            if (versionCode == 0) {
                // 首次安装,需要下载联系人
              //  WeqiaApplication.getInstance().setFirstInstall(true);
            }

              /*启动引导页*/
            Intent intent = new Intent(MainActivity.this, GuideViewPager.class);
            intent.putExtra(GlobalConstants.KEY_IS_FROM_ABOUT, false);

            // 记录最新版本号,下次进入不再显示引导页
            WPfCommon.getInstance().put(Hks.app_version_code, DeviceUtil.getVersionCode(this));
            startActivity(intent);
            finish();

        } else {      // 不升级版本  一般直接执行这个
            initData();
        }
    }

    /**
     * 主要执行这个
     */
    private void initData() {

        if (!CoConfig.want_log) {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class); //主界面
            startActivity(intent);
            finish();
            return;
        }

        getLoginInfo();
        if (BaseApplication.getInstance().getLoginUser() != null) {
            //已登录   可以从 数据库或网络 获取  设置到全局中
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class); //主界面
            startActivity(intent);
            finish();
        }
    }

    private void getLoginInfo() {

        LoginUserData  loginUser = BaseApplication.getInstance().getLoginUser();
        if (loginUser!=null){
            //todo 不为空 操作  如下载 用户 资料
        }else {
            // 缓存用户账号,直接登录
            if (StrUtil.notEmptyOrNull(WPfCommon.getInstance().get(Hks.user_account, String.class))) {
                Intent intent = new Intent(this, SplashActivity.class);
                intent.putExtra("toClass", 1); // 1 to LoginActivity 2 to RegLoginActivity
                startActivity(intent);
                finish();
            } else {
                // 不含任何信息,进入注册
                Intent intent = new Intent(this, SplashActivity.class);
                intent.putExtra("toClass", 2); // 1 to LoginActivity 2 to RegLoginActivity
                startActivity(intent);
                finish();
            }
        }
    }

}
