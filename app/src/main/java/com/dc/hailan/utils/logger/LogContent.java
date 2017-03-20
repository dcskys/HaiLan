package com.dc.hailan.utils.logger;

import android.util.Log;

import com.dc.hailan.utils.StrUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * Created by dc on 2017/2/17.
 */

public class LogContent {

    /**
     * 枚举
     */
    static enum Type {
        Error,
        Warning,
        Information,
        Debug,
        Verbose;

        private Type() {
        }
    }


    public static String EMPTY = "";
    public static String NULL = "null";
    private Throwable tr = null;

    private String message;
    private String tag;
    private LogContent.Type type;



    public LogContent() {
    }

    public LogContent(LogContent.Type type, String msg) {
        this.type = type;
        this.message = msg;
    }

    public LogContent(LogContent.Type type, String msg, Throwable tr) {
        this.type = type;
        this.message = msg;
        this.tr = tr;
    }



    public void flush() {
        try {
            //得到方法的调用栈 信息 自动打印MainActivity.onCreate(line:37)这种类名.方法名(行数)
            StackTraceElement e = getStackTraceElement(Thread.currentThread().getStackTrace());

            //方法名
            this.tag = getTag(e);

            this.tag = "HaiLan:" + this.tag;

            if(this.message == null) {
                this.message = NULL;
            }

            if(this.tr != null && !(this.tr instanceof UnknownHostException)) {

                this.message = this.message + " " + getThrowableString(this.tr);
            } else {
                this.message = this.message + " " + getMsgEnd(e);
            }
        } catch (Exception var2) {
            this.tag = EMPTY;
            this.message = NULL;
        } catch (OutOfMemoryError var3) {
            this.tag = EMPTY;
            this.message = NULL;
        }



        switch(this.type.ordinal()) {
            case 0:
                Log.e(this.tag, this.message);
                break;
            case 1:
                Log.w(this.tag, this.message);
                break;
            case 2:
                Log.i(this.tag, this.message);
                break;
            case 3:
                Log.d(this.tag, this.message);
                break;
            case 4:
                Log.v(this.tag, this.message);
        }

    }




    /**
     * @param tr Throwable 转String
     * @return
     */
    public static String getThrowableString(Throwable tr) {
        if(tr == null) {
            return "";
        } else {
            for(Throwable t = tr; t != null; t = t.getCause()) {
                ;
            }

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            tr.printStackTrace(pw);
            return sw.toString();
        }
    }


    public static String getMsgEnd(StackTraceElement stackTraceElement) {

        StringBuilder strBuilder = new StringBuilder();
        //重复次数
        strBuilder.append(StrUtil.makeLongRepeatString(" ", 60));
        strBuilder.append("at ");
        strBuilder.append(stackTraceElement.getClassName());
        strBuilder.append(".");
        strBuilder.append(stackTraceElement.getMethodName());
        strBuilder.append("(");
        strBuilder.append(stackTraceElement.getFileName());
        strBuilder.append(":");
        strBuilder.append(stackTraceElement.getLineNumber());
        strBuilder.append(")");
        return strBuilder.toString();
    }





    /**
     * @param stackTraceElement   得到 方法名
     * @return
     */
    public static String getTag(StackTraceElement stackTraceElement) {
        return StrUtil.getShortClassName(stackTraceElement.getClassName()) + "." + stackTraceElement.getMethodName();
    }



    public static StackTraceElement getStackTraceElement(StackTraceElement[] stackTraceElements) {
        boolean doNext = false;

        for(int i = 0; i < stackTraceElements.length; ++i) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            if(stackTraceElement == null) {
                return null;
            }

            String methodName = stackTraceElement.getMethodName();
            if(doNext) {
                return stackTraceElement;
            }

            doNext = methodName.equals("i") || methodName.equals("e") || methodName.equals("d") || methodName.equals("v") || methodName.equals("w") || methodName.equals("handleException");
        }

        return null;
    }






}
