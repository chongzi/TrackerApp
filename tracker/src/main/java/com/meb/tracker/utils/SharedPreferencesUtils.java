package com.meb.tracker.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Desc: SharedPreferences 统一工具类
 */
public class SharedPreferencesUtils {
    public interface Key {
        String SP_KEY_VISITORID = "sp_key_visitorid";
        String SP_KEY_HISTROY_LOG = "sp_key_histroy_log";
    }

    private static Context mApplication;
    private static SharedPreferences sharedPreferences = null;

    public static <T extends Application> SharedPreferences init(T mContext) {
        mApplication = mContext;
        if (null == sharedPreferences) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mApplication);
        }
        return sharedPreferences;
    }

    synchronized public static String getString(String key, String defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, defValue);
        }
        return defValue;
    }

    synchronized public static boolean setString(String key, String value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            return editor.commit();
        }
        return false;
    }

    synchronized public static boolean setBoolean(String key, boolean value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }
        return false;
    }

    synchronized public static boolean getBoolean(String key, boolean defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, defValue);
        }
        return defValue;
    }

    synchronized public static boolean setInt(String key, int value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            return editor.commit();
        }
        return false;
    }

    synchronized public static int getInt(String key, int defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, defValue);
        }
        return defValue;
    }

    synchronized public static boolean setLong(String key, long value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(key, value);
            return editor.commit();
        }
        return false;
    }

    synchronized public static long getLong(String key, long defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(key, defValue);
        }
        return defValue;
    }

    synchronized public static boolean setFloat(String key, float value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat(key, value);
            return editor.commit();
        }
        return false;
    }

    synchronized public static float getFloat(String key, float defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(key, defValue);
        }
        return defValue;
    }

    synchronized public static boolean deleteKey(String key) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key);
            return editor.commit();
        }
        return false;
    }
}
