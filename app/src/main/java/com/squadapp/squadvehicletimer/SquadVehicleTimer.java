package com.squadapp.squadvehicletimer;

import android.app.Application;

public class SquadVehicleTimer extends Application {
    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    /*public static Context getContext() {
        return getApplication().getApplicationContext();
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
