package com.services.tct;


import com.services.tct.Utilities.FontsOverride;

/**
 * Created by Ali on 3/27/2016.
 */
public final class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "MONOSPACE", "IRANSansMobile(FaNum).ttf");

//        AnalyticsTrackers.initialize(this);
//        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
    }

}