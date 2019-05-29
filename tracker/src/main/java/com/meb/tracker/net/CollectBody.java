package com.meb.tracker.net;

public class CollectBody implements Cloneable {


    /**
     * userId (string, optional): [选填] 用户编号， 登录后必填 ,
     * pageId (string, optional): [选填] 页面编号，页面上报时必填，事件信息上报不传该值。 ,
     * eventId (string, optional),
     * appId (string, optional): [必填] appId 编号 ,
     * visitorId (string, optional): [必填] 访客ID ,
     * deviceId (string, optional): [必填] 设备编号 ,
     * pageDuration (string, optional): [必填] 页面停留时长 ,
     * userDuration (string, optional): [必填] 用户会话停留时长 ,
     * pageUrl (string, optional): [必填] 页面路径 ,
     * from (string, optional): [必填] 来源，也包括上级页面 ,
     * isExitApp (boolean, optional),
     * devicePlatform (integer, optional): [必填] 设备平台 = ['1', '2', '3', '4', '5'],
     * deviceBrand (string, optional): [选填] 品牌 ,
     * deviceModel (string, optional): [选填] 设备型号 ,
     * os (string, optional): [必填] 系统 ,
     * osVersion (string, optional): [必填] 系统版本号 ,
     * language (string, optional): [必填] 系统语言 ,
     * deviceDirection (integer, optional): [选填] 设备方向,默认竖向 = ['0', '1'],
     * resolutionRatio (string, optional): [必填] 屏幕分辨率，格式：width*hight ,
     * browser (string, optional): [选填] 浏览器，网页浏览为必填 ,
     * browserVersion (string, optional): [选填] 浏览器版本，网页浏览为必填 ,
     * appVersion (string, optional): [选填] 应用版本 ,
     * appChannel (string, optional): [选填] 推广渠道 ,
     * adChannel (string, optional): [选填] 广告渠道 ,
     * geo (string, optional): [选填] 位置坐标，经纬度，格式：{经度},{纬度} ,
     * ip (string, optional): 不用传值 ,
     * domain (string, optional): [选填] 域名，网页浏览为必填 ,
     * isEntrancePage (boolean, optional): [必填] 是否是入口页面 ,
     * urlType (integer, optional): [必填] 路径类型，默认物理路径（Physical_Path） = ['0', '1'],
     * netTypeName (string, optional): [必填] 网络名称 ,
     * telecomOperators (string, optional): [必填] 运营商 ,
     * isDebug (boolean, optional): [选填] 调试模式的，默认true。调试模式时，上报的数据均不记录到用于分析的日志，而是记录到调试库中 ,
     * eventTime (string, optional): [必填] 事件实际发生时间 ,
     * parameters (string, optional): [选填] 页面参数（json 格式）
     */

    private String userId;
    private String pageId;
    private String eventId;
    private String appId;
    private String visitorId;
    private String deviceId;
    private long pageDuration;
    private long userDuration;
    private String pageUrl;
    private String from;
    private boolean isExitApp;
    private int devicePlatform;
    private String deviceBrand;
    private String deviceModel;
    private String os;
    private String osVersion;
    private String language;
    private int deviceDirection;
    private String resolutionRatio;
    private String browser;
    private String browserVersion;
    private String appVersion;
    private String appChannel;
    private String adChannel;
    private String geo;
    private String ip;
    private String domain;
    private boolean isEntrancePage;
    private int urlType;
    private String netTypeName;
    private String telecomOperators;
    private boolean isDebug;
    private String eventTime;
    private String parameters;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getPageDuration() {
        return pageDuration;
    }

    public void setPageDuration(long pageDuration) {
        this.pageDuration = pageDuration;
    }

    public long getUserDuration() {
        return userDuration;
    }

    public void setUserDuration(long userDuration) {
        this.userDuration = userDuration;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isIsExitApp() {
        return isExitApp;
    }

    public void setIsExitApp(boolean isExitApp) {
        this.isExitApp = isExitApp;
    }

    public int getDevicePlatform() {
        return devicePlatform;
    }

    public void setDevicePlatform(int devicePlatform) {
        this.devicePlatform = devicePlatform;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getDeviceDirection() {
        return deviceDirection;
    }

    public void setDeviceDirection(int deviceDirection) {
        this.deviceDirection = deviceDirection;
    }

    public String getResolutionRatio() {
        return resolutionRatio;
    }

    public void setResolutionRatio(String resolutionRatio) {
        this.resolutionRatio = resolutionRatio;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppChannel() {
        return appChannel;
    }

    public void setAppChannel(String appChannel) {
        this.appChannel = appChannel;
    }

    public String getAdChannel() {
        return adChannel;
    }

    public void setAdChannel(String adChannel) {
        this.adChannel = adChannel;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isIsEntrancePage() {
        return isEntrancePage;
    }

    public void setIsEntrancePage(boolean isEntrancePage) {
        this.isEntrancePage = isEntrancePage;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }

    public String getNetTypeName() {
        return netTypeName;
    }

    public void setNetTypeName(String netTypeName) {
        this.netTypeName = netTypeName;
    }

    public String getTelecomOperators() {
        return telecomOperators;
    }

    public void setTelecomOperators(String telecomOperators) {
        this.telecomOperators = telecomOperators;
    }

    public boolean isIsDebug() {
        return isDebug;
    }

    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "CollectBody{" +
                "userId='" + userId + '\'' +
                ", pageId='" + pageId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", appId='" + appId + '\'' +
                ", visitorId='" + visitorId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", pageDuration='" + pageDuration + '\'' +
                ", userDuration='" + userDuration + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", from='" + from + '\'' +
                ", isExitApp=" + isExitApp +
                ", devicePlatform=" + devicePlatform +
                ", deviceBrand='" + deviceBrand + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", os='" + os + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", language='" + language + '\'' +
                ", deviceDirection=" + deviceDirection +
                ", resolutionRatio='" + resolutionRatio + '\'' +
                ", browser='" + browser + '\'' +
                ", browserVersion='" + browserVersion + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", appChannel='" + appChannel + '\'' +
                ", adChannel='" + adChannel + '\'' +
                ", geo='" + geo + '\'' +
                ", ip='" + ip + '\'' +
                ", domain='" + domain + '\'' +
                ", isEntrancePage=" + isEntrancePage +
                ", urlType=" + urlType +
                ", netTypeName='" + netTypeName + '\'' +
                ", telecomOperators='" + telecomOperators + '\'' +
                ", isDebug=" + isDebug +
                ", eventTime='" + eventTime + '\'' +
                ", parameters='" + parameters + '\'' +
                '}';
    }

    @Override
    public Object clone() {
        CollectBody c = null;
        try {
            c = (CollectBody) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return c;
    }
}
