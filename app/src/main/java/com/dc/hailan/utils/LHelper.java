package com.dc.hailan.utils;

import android.text.TextUtils;

import com.dc.hailan.utils.logger.L;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by dc on 2017/2/20.
 *
 * 打印工具类  帮助打印json
 */

public class LHelper {

    public LHelper() {
    }

    public void json(String json) {

        if(TextUtils.isEmpty(json)) {
            L.e("Empty/Null json content");
        } else {

            try {
                if(!json.startsWith("{") && !json.startsWith("[")) {
                    L.e("not json show real:" + json);

                } else {

                    String e;
                    if(json.startsWith("{")) {

                        JSONObject e2 = new JSONObject(json);
                        e = e2.toString(4);
                        this.log(e, new Object[0]);
                        return;
                    }

                    if(json.startsWith("[")) {
                        JSONArray e1 = new JSONArray(json);
                        e = e1.toString(4);
                        this.log(e, new Object[0]);
                    }
                }
            } catch (Exception var4) {
                L.e("not json show real:" + json);
            }
        }

    }


    private synchronized void log(String msg, Object... args) {

        //判断长度  获取 msg
        String message = this.createMessage(msg, args);

        this.log1();

        byte[] bytes = message.getBytes();
        int length = bytes.length;

        if(length <= 4000) {
            this.log2(message);
            this.log3();
        } else {

            for(int i = 0; i < length; i += 4000) {
                int count = Math.min(length - i, 4000);
                this.log2(new String(bytes, i, count));
            }

            this.log3();
        }

    }

    private void log1() {
        L.i("╔════════════════════════════════════════════════════════════════════════════════════════");
    }

    private void log3() {
        L.i("╚════════════════════════════════════════════════════════════════════════════════════════");
    }

    private void log2(String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));

        String[] arr$ = lines;
        int len$ = lines.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String line = arr$[i$];
            L.i("║ " + line);
        }

    }

    private String createMessage(String message, Object... args) {
        return args.length == 0?message:String.format(message, args);
    }








}
