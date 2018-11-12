package com.figer.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.figer.game.System.Permissions;

public class AndroidLauncher extends AndroidApplication{
	@Override
	protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        Permissions.verifyLocationPermissions(this);
        initialize(new GameMain(this.getContext()), config);
    }
}
