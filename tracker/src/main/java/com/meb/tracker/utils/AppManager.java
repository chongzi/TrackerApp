package com.meb.tracker.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.meb.tracker.aop.ActivityTrace;
import com.meb.tracker.bean.ActivityInfo;

import java.util.Stack;

public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private ActivityInfo lastActivityInfo;

    public ActivityInfo getLastActivityInfo() {
        return lastActivityInfo;
    }

    public void setLastActivityInfo(ActivityInfo lastActivityInfo) {
        this.lastActivityInfo = lastActivityInfo;
    }

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack == null) return null;
        if (activityStack.empty()) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


    public void finishAllOtherActivity(Activity activity) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i) && activityStack.get(i) != activity) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void recreateAllOtherActivity(Activity activity) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            activityStack.get(i).recreate();
//            if (null != activityStack.get(i) && activityStack.get(i) != activity) {
//                activityStack.get(i).recreate();
//            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
