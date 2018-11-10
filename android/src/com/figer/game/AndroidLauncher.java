package com.figer.game;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.figer.game.GameMain;

public class AndroidLauncher extends AndroidApplication{
	@Override
	protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new GameMain(this.getContext()), config);
    }

    public void checkBTPermissions() {
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
			int permissionCheck = checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
			permissionCheck += checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
			if (permissionCheck != 0) {
				requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
			}
		}else{
			System.out.println("fuck this shit im out");
		}
	}
}
