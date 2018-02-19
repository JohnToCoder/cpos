package com.cpos.classes;

/**
 * Created by john on 2018/1/25.
 */
public class UpdateVersion {
    public String getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(String versionCode) {
        VersionCode = versionCode;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String versionName) {
        VersionName = versionName;
    }

    public String getAppmessages() {
        return appmessages;
    }

    public void setAppmessages(String appmessages) {
        this.appmessages = appmessages;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }

    public String getGmtCreat() {
        return gmtCreat;
    }

    public void setGmtCreat(String gmtCreat) {
        this.gmtCreat = gmtCreat;
    }

    private String VersionCode;
    private String VersionName;
    private String appmessages;
    private String appurl;
    private String gmtCreat;
}
