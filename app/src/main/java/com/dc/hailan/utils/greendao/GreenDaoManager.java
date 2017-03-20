package com.dc.hailan.utils.greendao;

import com.dc.hailan.BaseApplication;
import com.dc.hailan.utils.greendao.demo.DaoMaster;
import com.dc.hailan.utils.greendao.demo.DaoSession;

/**
 * Created by dc on 2017/3/14.   数据库 管理类
 *
 *
 */

public class GreenDaoManager {

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private static  GreenDaoManager mInstance = null;

    private GreenDaoManager() {
            DaoMaster.DevOpenHelper devOpenHelper = new
                    DaoMaster.DevOpenHelper(BaseApplication.getInstance(), "hailan-db");
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                mInstance = new GreenDaoManager();
            }
            }
        }
        return mInstance;
    }


    public DaoMaster getMaster() {
        return mDaoMaster;
    }


    /**
     * 使用 这个
     * @return
     */
    public DaoSession getSession() {
        return mDaoSession;
    }


    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }







}
