package com.oms.zte;


import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oms.zte.base.CommonNew;


public class EmailActivity extends CommonNew {
//	private String path = "http://czbnew.czton.cn:8000/ssczb/index.htm?m=1012"+MyURL.UESER+MyURL.SYSTEM;  
	private String path = "http://wapmail.yofoto.cn?m=1012"+MyURL.UESER+MyURL.SYSTEM;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(Params.orientationControl);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除标题
		setContentView(R.layout.news_layer);
		baseText = (TextView) this.findViewById(R.id.titleView);
		webview= (WebView) this.findViewById(R.id.news_web);
		baseText.setText("手 机 邮 箱"); 
		ImageButton page_return = (ImageButton) findViewById(R.id.lastpageid);
		ImageButton btnMain = (ImageButton)findViewById(R.id.page_main);
		ImageButton btnUpdate = (ImageButton)findViewById(R.id.webUpdate);
		ImageButton btnLast = (ImageButton)findViewById(R.id.page_last);
		ImageButton btnNext = (ImageButton)findViewById(R.id.page_next);
		ImageButton btnMore = (ImageButton)findViewById(R.id.page_more);
		ImageButton btnExit = (ImageButton)findViewById(R.id.page_exit);
		init_Button(page_return,btnMain,btnUpdate,btnLast,btnNext,btnMore,btnExit);
		news(path,webview );
	}
}
