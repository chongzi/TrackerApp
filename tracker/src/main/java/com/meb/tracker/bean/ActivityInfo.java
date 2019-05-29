package com.meb.tracker.bean;

public class ActivityInfo {

    private String fromPageId;
    private String fromPath;
    private String pageId;
    private String path;
    private long time;

    public ActivityInfo(String fromPageId, String fromPath, String pageId, String path, long time) {
        this.fromPageId = fromPageId;
        this.fromPath = fromPath;
        this.pageId = pageId;
        this.path = path;
        this.time = time;
    }

    public String getFromPageId() {
        return fromPageId;
    }

    public void setFromPageId(String fromPageId) {
        this.fromPageId = fromPageId;
    }

    public String getFromPath() {
        return fromPath;
    }

    public void setFromPath(String fromPath) {
        this.fromPath = fromPath;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
