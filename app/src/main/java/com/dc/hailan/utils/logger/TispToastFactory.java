package com.dc.hailan.utils.logger;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dc on 2017/2/17.
 *
 *
 * 提示工具类
 *
 */

public class TispToastFactory {

    private static Context context = null;
    private static Toast toast = null;

    public TispToastFactory() {
    }

    /**
     ShowToast
     */
    public static Toast getToast(Context context, String hint, int duration) {

        if(context == context) {
            if(toast != null) {
                toast.setText(hint);
            }
            return toast;
        } else {

            context = context;
            toast = Toast.makeText(context, hint, duration);
            return toast;
        }
    }


}
