/**
 * 能够很方便的打印出当前类名，方法名，行数。 
 * 并且当调用Log.d的时候，如果长度超过MAX_LOG_LENGTH,
 * 会自动分多次打印，解决一些长message打印不全的问题。
 */

package com.amida.easylog;

import android.util.Log;

import com.amida.easydb.BuildConfig;

public class EasyLog {
    private static int MAX_LOG_LENGTH = 1500;

    private EasyLog() {
    }

    //@formatter:off
    /**
     * 消息级别 v<d<i<w<e
     * 消息颜色
     * v:黑色 任何
     * d:蓝色 调试消息
     * i:绿色 提示性消息
     * w:橙色 警告
     * e:红色 error错误
     * @param message
     */
    //@formatter:on
    public static void v(String message) {
        if (!isDebuggable()) {
            return;
        }

        StackInfo stackInfo = StackUtil.getStackInfo();
        Log.v(stackInfo.getClassName(), generateMessage(message, stackInfo));
    }

    //@formatter:off
    /**
     * 当长度超过MAX_LOG_LENGTH的时候，自动分几次打印，解决一些长message打印不全的问题。
     * 消息级别 v<d<i<w<e
     * 消息颜色
     * v:黑色 任何
     * d:蓝色 调试消息
     * i:绿色 提示性消息
     * w:橙色 警告
     * e:红色 error错误
     * @param message
     */
    //@formatter:on
    public static void d(String message) {
        if (!isDebuggable()) {
            return;
        }

        StackInfo stackInfo = StackUtil.getStackInfo();

        if (message != null && message.length() > MAX_LOG_LENGTH) {
            for (int i = 0; i <= message.length() / MAX_LOG_LENGTH; i++) {
                int start = i * MAX_LOG_LENGTH;
                int end = (i + 1) * MAX_LOG_LENGTH;
                end = end > message.length() ? message.length() : end;
                Log.e(stackInfo.getClassName(), generateMessage(message.substring(start, end), stackInfo));
            }
        } else {
            Log.e(stackInfo.getClassName(), generateMessage(message, stackInfo));
        }

    }

    //@formatter:off
    /**
     * 消息级别 v<d<i<w<e
     * 消息颜色
     * v:黑色 任何
     * d:蓝色 调试消息
     * i:绿色 提示性消息
     * w:橙色 警告
     * e:红色 error错误
     * @param message
     */
    //@formatter:on
    public static void i(String message) {
        if (!isDebuggable()) {
            return;
        }

        StackInfo stackInfo = StackUtil.getStackInfo();
        Log.i(stackInfo.getClassName(), generateMessage(message, stackInfo));
    }

    //@formatter:off
    /**
     * 消息级别 v<d<i<w<e
     * 消息颜色
     * v:黑色 任何
     * d:蓝色 调试消息
     * i:绿色 提示性消息
     * w:橙色 警告
     * e:红色 error错误
     * @param message
     */
    //@formatter:on
    public static void w(String message) {
        if (!isDebuggable()) {
            return;
        }

        StackInfo stackInfo = StackUtil.getStackInfo();
        Log.w(stackInfo.getClassName(), generateMessage(message, stackInfo));
    }

    //@formatter:off
    /**
     * 消息级别 v<d<i<w<e
     * 消息颜色
     * v:黑色 任何
     * d:蓝色 调试消息
     * i:绿色 提示性消息
     * w:橙色 警告
     * e:红色 error错误
     * @param message
     */
    //@formatter:on
    public static void e(String message) {
        StackInfo stackInfo = StackUtil.getStackInfo();
        Log.e(stackInfo.getClassName(), generateMessage(message, stackInfo));
    }

    /**
     * 致命错误的消息
     * 
     * @param message
     */
    public static void wtf(String message) {
        if (!isDebuggable()) {
            return;
        }

        StackInfo stackInfo = StackUtil.getStackInfo();
        Log.wtf(stackInfo.getClassName(), generateMessage(message, stackInfo));
    }

    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String generateMessage(String message, StackInfo stackInfo) {
        StringBuffer logMsg = new StringBuffer();
        logMsg.append("[")
                .append(stackInfo.getMethodName())
                .append(":")
                .append(stackInfo.getLineNumber())
                .append("]")
                .append(message);
        return logMsg.toString();
    }

}
