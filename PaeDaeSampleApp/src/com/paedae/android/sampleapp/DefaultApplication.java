package com.paedae.android.sampleapp;

import java.util.HashMap;

import com.paedae.android.sdk.PaeDae;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class DefaultApplication extends Application {
	private final static String TAG = "DefaultApplication";

	PaeDae.SessionInterface sessionInterface = new PaeDae.SessionInterface() {
		@Override
		public void onSessionStarted() {
			Log.d(TAG, "PaeDae session started");
		}
		
		@Override
		public void onSessionFailed() {
			Log.d(TAG, "PaeDae session failed");
		}
	};
	
    @Override
    public void onCreate() {
    	PaeDae.getInstance().setSessionInterface(sessionInterface);
    	PaeDae.getInstance().startSession(this, "b00015e0-5cf7-012f-c818-12313f04f84c");
    }
}
