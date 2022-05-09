package com.application.developer;

public class AppList
{
    private String appName;
    private String appPackage;
    private String appIcon;
    private String appPath;
    public AppList()
    {
    }

    public AppList(String appName, String appPackage, String appIcon, String appPath)
    {
        this.appName = appName;
        this.appPackage = appPackage;
        this.appIcon = appIcon;
        this.appPath = appPath;
    }

    public String getappName()
    {
        return appName;
    }

    public String getappPackage()
    {
        return appPackage;
    }

    public String getappIcon()
    {
        return appIcon;
    }
    
    public String getappPath()
    {
        return appPath;
    }

    public void setappName(String appPackage)
    {
        this.appPackage = appPackage;
    }

    public void setappPackage(String appPackage)
    {
        this.appPackage = appPackage;
    }

    public void setappIcon(String appIcon)
    {
        this.appIcon = appIcon;
    }
    
    public void setappPath(String appPath)
    {
        this.appPath = appPath;
    }
}
