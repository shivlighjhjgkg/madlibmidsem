package com.example.myapplication;

import android.graphics.drawable.Drawable;
import java.io.Serializable;
import java.util.List;

public class AppInfo implements Serializable {
    private String appName;
    private String packageName;
    private transient Drawable icon;
    private boolean isSystemApp;
    private String version;
    private long size;
    private List<String> permissions;

    public AppInfo(String appName, String packageName, Drawable icon, boolean isSystemApp, String version, long size, List<String> permissions) {
        this.appName = appName;
        this.packageName = packageName;
        this.icon = icon;
        this.isSystemApp = isSystemApp;
        this.version = version;
        this.size = size;
        this.permissions = permissions;
    }

    public String getAppName() { return appName; }
    public String getPackageName() { return packageName; }
    public Drawable getIcon() { return icon; }
    public boolean isSystemApp() { return isSystemApp; }
    public String getVersion() { return version; }
    public long getSize() { return size; }
    public List<String> getPermissions() { return permissions; }
}