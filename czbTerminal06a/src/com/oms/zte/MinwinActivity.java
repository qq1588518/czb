package com.oms.zte;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

public class MinwinActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
		onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(0, 4));
	}

}
