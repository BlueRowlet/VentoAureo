package com.figer.game.System;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;

public class Permissions{
    //@TargetApi(23)
    public static void verifyLocationPermissions(Activity activity) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
			int permissionCheck = activity.checkSelfPermission("Manifest.p	ermission.ACCESS_FINE_LOCATION");
			permissionCheck += activity.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
			if (permissionCheck != 0) {
				activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
			}
		}else{
			System.out.println("fuck this shit im out");
		}
    }
}