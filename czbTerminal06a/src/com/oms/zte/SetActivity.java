package com.oms.zte;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SetActivity extends Activity
{

	private RadioButton p_b, l_b, u_b;
	private RadioGroup rg;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(Params.orientationControl);
		setContentView(R.layout.set);
		rg = (RadioGroup) findViewById(R.id.radioGroupSet);
		p_b = (RadioButton) findViewById(R.id.radio_P);//竖
		l_b = (RadioButton) findViewById(R.id.radio_L);//横
		u_b = (RadioButton) findViewById(R.id.radio_U);//自应
		rg.setOnCheckedChangeListener(new MyListener());

		switch(Params.orientationControl)
		{
			case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
			{
				p_b.setChecked(true);
				break; 
			}
			case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
			{
				l_b.setChecked(true);
				break;
			}
			case ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED:
			{
				u_b.setChecked(true);
				break;
			}
		}
	}
	
	@Override
	protected void onResume() {
		setRequestedOrientation(Params.orientationControl);
		super.onResume();
	}

	private class MyListener implements RadioGroup.OnCheckedChangeListener
	{
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId)
		{
			if (checkedId == p_b.getId())
			{
				WriteSharedPreferences(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				Params.orientationControl = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
			}
			else if (checkedId == l_b.getId())
			{
				WriteSharedPreferences(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				Params.orientationControl = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
			}
			else if (checkedId == u_b.getId())
			{
				WriteSharedPreferences(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
				Params.orientationControl = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
			}
			setRequestedOrientation(Params.orientationControl);
		}

	}


	private void  WriteSharedPreferences(int isGravity)
	{

		SharedPreferences  datas = getSharedPreferences("czb_Cfg",0);
		datas.edit().putInt("cfgGravity", isGravity).commit();
		
	}
}
