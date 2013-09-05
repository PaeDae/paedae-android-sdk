package com.paedae.android.sampleapp;

import java.util.HashMap;

import com.paedae.android.sdk.AdvertisementActivity;
import com.paedae.android.sdk.PaeDae;
import com.paedae.android.testapp.R;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DefaultActivity extends Activity {
    private final String TAG = "SampleApp:DefaultActivity";
    private TextView statusView = null;
    private EditText milestoneUniqueIdView = null;
    private Button cacheAdButtonView = null;
    private Button showAdButtonView = null;
    
    private static final int AD_ACTIVITY = 0;
    
    PaeDae.AdInterface adInterface = new PaeDae.AdInterface() {
		@Override
		public void onAdUnavailable() {
			Log.d(TAG, "ad unavailable");
			statusView.setText("Ad Unavailable");
			enableControls();
		}

		@Override
		public void onAdCached(String milestoneUniqueId) {
			Log.d(TAG, "ad cached");
			statusView.setText("Ad Cached for: " + milestoneUniqueId);
			enableControls();
		}

		@Override
		public void onAdReady(Intent intent) {
			Log.d(TAG, "ad is ready to be shown");
			statusView.setText("Ad Was Loaded");
			enableControls();
			startActivityForResult(intent, AD_ACTIVITY);
		}
	};
    
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
         
        statusView = (TextView) findViewById(R.id.sdk_status); 
        milestoneUniqueIdView = (EditText) findViewById(R.id.milestone_unique_id); 
        
        statusView.setText("PaeDaeSDK v" + com.paedae.android.sdk.Consts.VERSION);
        
        Log.d(TAG, "Started DefaultActivity");
        
        cacheAdButtonView = (Button) findViewById(R.id.load_ad_button); 
        cacheAdButtonView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				disableControls();
				
				HashMap<String, Object> options = new HashMap<String, Object>();
				options.put("milestone_unique_id", milestoneUniqueIdView.getText().toString());
				
				PaeDae.getInstance().loadAd(DefaultActivity.this, options);
			}
		});
        
        showAdButtonView = (Button) findViewById(R.id.show_ad_button); 
        showAdButtonView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				disableControls();
				
				HashMap<String, Object> options = new HashMap<String, Object>();
				options.put("milestone_unique_id", milestoneUniqueIdView.getText().toString());
				
				PaeDae.getInstance().showAd(options);
			}
		});
    }
	
	protected void onStart() {
		super.onStart();
		PaeDae.getInstance().setAdInterface(adInterface);
	}
	
	protected void onStop() {
		super.onStop();
		PaeDae.getInstance().setAdInterface(null);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == AD_ACTIVITY) {
	        if (resultCode == AdvertisementActivity.RESULT_AD_DISMISSED) {
	        	statusView.setText("Ad Was Dismissed");
	        } else if (resultCode == AdvertisementActivity.RESULT_AD_ACTION_TAKEN) {
	        	statusView.setText("Ad Action Was Taken");
		    }
	    }
	}
	
	private void disableControls() {
		milestoneUniqueIdView.setEnabled(false);
		showAdButtonView.setEnabled(false);
		cacheAdButtonView.setEnabled(false);
	}
	
	private void enableControls() {
		milestoneUniqueIdView.setEnabled(true);
		showAdButtonView.setEnabled(true);
		cacheAdButtonView.setEnabled(true);
	}
}