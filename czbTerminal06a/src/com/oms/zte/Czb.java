package com.oms.zte;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Window;

public class Czb extends Activity
{
	private NotificationManager mNotificationManager;

	private Handler mHandler = new Handler();

	private int phoneHight ;
	int alpha = 255;
	int b = 0;
	private final String Path = "com.oms.zte" ;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除标题
		setContentView(R.layout.welcome);

		Display display = getWindowManager().getDefaultDisplay(); 
		phoneHight = display.getHeight();
		Params.ViewWith = display.getWidth();

		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		if(uninstall_Application(Path))
		{
    		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:"+Path));      
			startActivityForResult(uninstallIntent,888);
		}
		else
		{
			this.viewIconMood();
			new Thread(new Runnable()
			{
				public void run()
				{
					initApp(); // 初始化程序

					try
					{

						Thread.sleep(2000);
						updateApp();
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}).start();
		}
		mHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				Params.orientationControl = initGravityChange();
				Intent in = new Intent(Czb.this,LoginActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
				finish();
			}
		};

	}

	 private boolean uninstall_Application(String packName)
	    {
	    	Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
	        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	        List<ResolveInfo>  application_infor=  this.getPackageManager().queryIntentActivities(mainIntent, 0);
	        Iterator<ResolveInfo> application_iterator = application_infor.iterator();
	        ResolveInfo application = null;
	        while(application_iterator.hasNext())
	        {
	        	application = application_iterator.next();
	        	String pkg = application.activityInfo.packageName; 
	        	if(pkg.equals(packName))
	        	{
					return true;
	        	}
	        }
	        return false; 
	    }
	 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode == 888) 
			{
				if(uninstall_Application(Path))
				{
					this.finish();
				}
				else
				{
					this.viewIconMood();
					new Thread(new Runnable() {
						public void run() {
							initApp(); //初始化程序

							try {
								
								Thread.sleep(2000);
								updateApp();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			}
		}
	public void updateApp()
	{

		try
		{
			Rect frame = new Rect();
			getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
			int statusBarHeight = frame.top;
			Params.ViewHight = phoneHight - statusBarHeight;
			mHandler.sendMessage(mHandler.obtainMessage());

		} catch (Exception e)
		{
			Log.v("updateApp", e.getMessage());
		}

	}

	public void initApp()
	{

	}

	private void viewIconMood()
	{

		Intent minIntent = new Intent(this, MinwinActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, minIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		CharSequence text = getText(R.string.app_name);
		final Notification notification = new Notification(R.drawable.stat_min,text, 
				System.currentTimeMillis()); 
		notification.setLatestEventInfo(this,getText(R.string.app_name),text, contentIntent); 
		notification.icon = R.drawable.stat_min;
		mNotificationManager.notify(R.layout.welcome, notification); 
	}

	private int initGravityChange()
	{
		SharedPreferences   datas = getSharedPreferences("czb_Cfg",0);
		int onCfgChanges = datas.getInt("cfgGravity", 1);

		return onCfgChanges;
	}


}
