package br.com.firstsoft.historicodenotificacoes.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by root on 01/06/17.
 */

public class CPackage implements Serializable{

    private String packageName;
    private Drawable packageIcon;
    private long childCount;
    private String appName;

    public CPackage(String packageName, Drawable packageIcon, int childCount) {
        this.packageName = packageName;
        this.packageIcon = packageIcon;
        this.childCount = childCount;
    }

    public CPackage() {
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getPackageIcon() {
        return packageIcon;
    }

    public void setPackageIcon(Drawable packageIcon) {
        this.packageIcon = packageIcon;
    }

    public long getChildCount() {
        return childCount;
    }

    public void setChildCount(long childCount) {
        this.childCount = childCount;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
