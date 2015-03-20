
package com.amida.easydb.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class ManifestUtils {

    public static <T>  T getMetaData(Context context, String metaName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo appInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (appInfo != null && appInfo.metaData != null) {
                    return (T)appInfo.metaData.get(metaName);
                }
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
