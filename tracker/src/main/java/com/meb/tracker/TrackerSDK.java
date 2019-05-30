package com.meb.tracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.meb.tracker.aop.ActivityTrace;
import com.meb.tracker.bean.ActivityInfo;
import com.meb.tracker.net.CollectBody;
import com.meb.tracker.utils.AppManager;
import com.meb.tracker.utils.DeviceUtils;
import com.meb.tracker.utils.SharedPreferencesUtils;

import java.util.UUID;

public class TrackerSDK {

    public static TrackerSDK trackerSDK;

    private Context context;

    private CollectBody configuration;

    private long startTime;

    public int count = 0;//判断前后台用

    private Handler handler;
    private Runnable runnable;
    private boolean isSendExit;


    public static synchronized TrackerSDK getInstance() {
        if (trackerSDK == null) {
            trackerSDK = new TrackerSDK();
        }
        return trackerSDK;
    }

    public void init(@NonNull Application application, @NonNull String appId) {
        context = application;
        startTime = System.currentTimeMillis();
        SharedPreferencesUtils.init(application);
        initCommonInfo(application, appId);
        initPageChange(application);
        TrackerManager.getInstance().init();
        initHandler();
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //结束任务
                ActivityInfo lastActivityInfo = AppManager.getAppManager().getLastActivityInfo();
                if (lastActivityInfo != null) {
                    Log.i("Tracker:", "页面id：" + lastActivityInfo.getPageId() + "  path:" + lastActivityInfo.getPath());
                    Log.i("Tracker:", "上一个页面id：" + lastActivityInfo.getFromPageId() + "  path:" + lastActivityInfo.getFromPath());

                    CollectBody collectBody = creatCommonCollect();
                    collectBody.setPageId(lastActivityInfo.getPageId());
                    collectBody.setPageUrl(lastActivityInfo.getPath());
                    collectBody.setFrom(lastActivityInfo.getFromPageId());
                    collectBody.setIsExitApp(lastActivityInfo.getPageId() == null);
                    collectBody.setEventTime(String.valueOf(System.currentTimeMillis()));
                    collectBody.setUserDuration(System.currentTimeMillis() - startTime);
                    collectBody.setPageDuration(System.currentTimeMillis() - lastActivityInfo.getTime());
                    collectBody.setIsExitApp(true);
                    TrackerManager.getInstance().addCollect(collectBody);
                    Log.i("Tracker:添加页面跳转事件", collectBody.toString());

                }

                Activity currentActivity = AppManager.getAppManager().currentActivity();
                AppManager.getAppManager().setLastActivityInfo(new ActivityInfo(
                        null,
                        null,
                        currentActivity == null ? null : currentActivity.getClass().getAnnotation(ActivityTrace.class).pageId(),
                        currentActivity == null ? null : currentActivity.getClass().getName(),
                        System.currentTimeMillis()));

                isSendExit = true;
            }
        };
    }

    private void initPageChange(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activityCreated(activity);
                if (activity instanceof AppCompatActivity) {
                    ((AppCompatActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (count == 0) {
                    handler.removeCallbacks(runnable);
                    if (isSendExit) {
                        startTime = System.currentTimeMillis();
                    }
                }
                count++;

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                count--;
                if (count == 0) {
                    startEndEvent();
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityDestroyed(activity);
                if (activity instanceof AppCompatActivity) {
                    ((AppCompatActivity) activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks);
                }
            }
        });


    }

    private void startEndEvent() {
        runnable = new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                message.what = 1;
                handler.handleMessage(message);
            }
        };
        handler.postDelayed(runnable, 30000);
    }


    FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
        @Override
        public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
            super.onFragmentPreAttached(fm, f, context);
        }

        @Override
        public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
            super.onFragmentAttached(fm, f, context);
        }

        @Override
        public void onFragmentPreCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
            super.onFragmentPreCreated(fm, f, savedInstanceState);
        }

        @Override
        public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
            super.onFragmentCreated(fm, f, savedInstanceState);
        }

        @Override
        public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
            super.onFragmentActivityCreated(fm, f, savedInstanceState);
        }

        @Override
        public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        }

        @Override
        public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentStarted(fm, f);
        }

        @Override
        public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment fragment) {
            super.onFragmentResumed(fm, fragment);
            System.out.println("------------>  Resumed" + fragment.getClass().getName());
        }

        @Override
        public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentPaused(fm, f);
        }

        @Override
        public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment fragment) {
            super.onFragmentStopped(fm, fragment);
            System.out.println("------------>  Stopped" + fragment.getClass().getName());
        }

        @Override
        public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
            super.onFragmentSaveInstanceState(fm, f, outState);
        }

        @Override
        public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentViewDestroyed(fm, f);
        }

        @Override
        public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentDestroyed(fm, f);
        }

        @Override
        public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
            super.onFragmentDetached(fm, f);
        }
    };

    private void activityCreated(Activity activity) {
        ActivityInfo lastActivityInfo = AppManager.getAppManager().getLastActivityInfo();
        if (lastActivityInfo != null) {
            Log.i("Tracker:", "页面id：" + lastActivityInfo.getPageId() + "  path:" + lastActivityInfo.getPath());
            Log.i("Tracker:", "上一个页面id：" + lastActivityInfo.getFromPageId() + "  path:" + lastActivityInfo.getFromPath());

            CollectBody collectBody = creatCommonCollect();
            collectBody.setPageId(lastActivityInfo.getPageId());
            collectBody.setPageUrl(lastActivityInfo.getPath());
            collectBody.setFrom(lastActivityInfo.getFromPageId());
            collectBody.setIsEntrancePage(lastActivityInfo.getFromPageId() == null);
            collectBody.setEventTime(String.valueOf(System.currentTimeMillis()));
            collectBody.setUserDuration(System.currentTimeMillis() - startTime);
            collectBody.setPageDuration(System.currentTimeMillis() - lastActivityInfo.getTime());

            TrackerManager.getInstance().addCollect(collectBody);
            Log.i("Tracker:添加页面跳转事件", collectBody.toString());

        }

        AppManager.getAppManager().setLastActivityInfo(new ActivityInfo(lastActivityInfo == null ? null : lastActivityInfo.getPageId(),
                lastActivityInfo == null ? null : lastActivityInfo.getPath(),
                activity.getClass().getAnnotation(ActivityTrace.class).pageId(),
                activity.getClass().getName(),
                System.currentTimeMillis()));

        AppManager.getAppManager().addActivity(activity);


    }

    private void activityDestroyed(Activity activity) {
        ActivityInfo lastActivityInfo = AppManager.getAppManager().getLastActivityInfo();
        if (lastActivityInfo != null) {
            Log.i("Tracker:", "页面id：" + lastActivityInfo.getPageId() + "  path:" + lastActivityInfo.getPath());
            Log.i("Tracker:", "上一个页面id：" + lastActivityInfo.getFromPageId() + "  path:" + lastActivityInfo.getFromPath());

            CollectBody collectBody = creatCommonCollect();
            collectBody.setPageId(lastActivityInfo.getPageId());
            collectBody.setPageUrl(lastActivityInfo.getPath());
            collectBody.setFrom(lastActivityInfo.getFromPageId());
            collectBody.setIsExitApp(lastActivityInfo.getPageId() == null);
            collectBody.setEventTime(String.valueOf(System.currentTimeMillis()));
            collectBody.setUserDuration(System.currentTimeMillis() - startTime);
            collectBody.setPageDuration(System.currentTimeMillis() - lastActivityInfo.getTime());
            TrackerManager.getInstance().addCollect(collectBody);
            Log.i("Tracker:添加页面跳转事件", collectBody.toString());

        }
        AppManager.getAppManager().finishActivity(activity);

        Activity currentActivity = AppManager.getAppManager().currentActivity();
        AppManager.getAppManager().setLastActivityInfo(new ActivityInfo(
                activity.getClass().getAnnotation(ActivityTrace.class).pageId(),
                activity.getClass().getName(),
                currentActivity == null ? null : currentActivity.getClass().getAnnotation(ActivityTrace.class).pageId(),
                currentActivity == null ? null : currentActivity.getClass().getName(),
                System.currentTimeMillis()));

    }


    private void initCommonInfo(@NonNull Application application, @NonNull String appId) {
        configuration = new CollectBody();
        configuration.setAppId(appId);
        //访客id
        String visitorId = SharedPreferencesUtils.getString(SharedPreferencesUtils.Key.SP_KEY_VISITORID, "");
        if (TextUtils.isEmpty(visitorId)) {
            visitorId = UUID.randomUUID().toString();
            SharedPreferencesUtils.setString(SharedPreferencesUtils.Key.SP_KEY_VISITORID, visitorId);
        }
        configuration.setVisitorId(visitorId);

        //设备唯一id
        String deviceId = Settings.System.getString(application.getContentResolver(), Settings.Secure.ANDROID_ID);
        configuration.setDeviceId(deviceId);

        //设备平台
        int devicePlatform = 1;
        configuration.setDevicePlatform(devicePlatform);

        //设备品牌
        String deviceBrand = DeviceUtils.getPhoneBrand();
        configuration.setDeviceBrand(deviceBrand);

        //设备型号
        String deviceModel = DeviceUtils.getPhoneModel();
        configuration.setDeviceModel(deviceModel);

        //系统
        String os = "Android";
        configuration.setOs(os);

        //系统版本
        String osVersion = DeviceUtils.getBuildVersion();
        configuration.setOsVersion(osVersion);

        //系统默认语言
        String language = DeviceUtils.getDeviceDefaultLanguage();
        configuration.setLanguage(language);

        //屏幕宽高
        String resolutionRatio = DeviceUtils.deviceWidth(application) + "*" + DeviceUtils.deviceHeight(application);
        configuration.setResolutionRatio(resolutionRatio);

        //应用version
        String appVersion = DeviceUtils.getVersionName(application);
        configuration.setAppVersion(appVersion);

        //ip
//        String ip = DeviceUtils.getIPAddress(application);

        //运营商
        String telecomOperators = DeviceUtils.getPhoneOperators(application);
        configuration.setTelecomOperators(telecomOperators);

    }


    /**
     * 获取网络类型名称
     */
    private String getNetTypeName() {
        return DeviceUtils.getNetworkType(context);
//        int apnType = DeviceUtils.getAPNType(context);
//        switch (apnType) {
//
//            case 1:
//                return "WIFI";
//            case 2:
//                return "没有获取到网络";
//            case 3:
//                return "没有获取到网络";
//            case 0:
//            default:
//                return "没有获取到网络";
//        }
    }

    /**
     * 获取页面横竖屏
     *
     * @return
     */
    private int getPageDirection() {
        Activity activity = AppManager.getAppManager().currentActivity();
        if (activity != null) {
            Configuration mConfiguration = activity.getResources().getConfiguration(); //获取设置的配置信息
            int ori = mConfiguration.orientation; //获取屏幕方向
            if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
                //横
                return 1;
            } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
                //竖
                return 0;
            }
        }
        return 0;
    }


    public CollectBody creatCommonCollect() {
//        CollectBody collectBody = new CollectBody();
//        collectBody.setAppId(configuration.getAppId());
//        collectBody.setVisitorId(configuration.getVisitorId());
//        collectBody.setDeviceId(configuration.getDeviceId());
//        collectBody.setDevicePlatform(configuration.getDevicePlatform());
//        collectBody.setDeviceBrand(configuration.getDeviceBrand());
//        collectBody.setDeviceModel(configuration.getDeviceModel());
//        collectBody.setOs(configuration.getOs());
//        collectBody.setOsVersion(configuration.getOsVersion());
//        collectBody.setLanguage(configuration.getLanguage());
//        collectBody.setResolutionRatio(configuration.getResolutionRatio());
//        collectBody.setAppVersion(configuration.getAppVersion());
//        collectBody.setAppVersion(configuration.getAppVersion());
//        collectBody.setTelecomOperators(configuration.getTelecomOperators());
        CollectBody collectBody = (CollectBody) configuration.clone();
        collectBody.setDeviceDirection(getPageDirection());
        collectBody.setNetTypeName(DeviceUtils.getNetworkType(context));
        collectBody.setIp(DeviceUtils.getLocalIpAddress());
        collectBody.setUserDuration(System.currentTimeMillis() - startTime);
        return collectBody;
    }


    public void addClickEvent(@NonNull String eventId) {
        CollectBody collectBody = creatCommonCollect();
        collectBody.setEventId(eventId);
        collectBody.setEventTime(String.valueOf(System.currentTimeMillis()));
        TrackerManager.getInstance().addCollect(collectBody);
        Log.i("Tracker:添加点击事件", collectBody.toString());
    }

    /**
     * 获取配置信息
     *
     * @return
     */
    public CollectBody getConfiguration() {
        return configuration;
    }

    /**
     * 设置配置信息
     *
     * @param configuration
     */
    public void setConfiguration(CollectBody configuration) {
        this.configuration = configuration;
    }


    /**
     * 设置登录用户id
     *
     * @param userId
     */
    public void setUserId(String userId) {
        configuration.setUserId(userId);
    }

    /**
     * 添加自定义埋点
     * 可以先通过creatCommonCollect()创建对象
     *
     * @param collectBody
     */
    public void addCustomTracker(CollectBody collectBody) {
        TrackerManager.getInstance().addCollect(collectBody);
        Log.i("Tracker:添加自定义事件", collectBody.toString());
    }

}
