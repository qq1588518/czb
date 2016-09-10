package com.oms.zte.shop;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oms.zte.MyURL;
import com.oms.zte.Params;
import com.oms.zte.R;
import com.oms.zte.base.CommonNew;

public class ShoppingActivity extends CommonNew {
    private String path = "http://wapsystem.yofoto.com.cn/index2.aspx?m=1015"+MyURL.UESER+MyURL.SYSTEM;
	 public void onCreate(Bundle savedInstanceState)
	    {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(Params.orientationControl);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_layer);
		baseText = (TextView) this.findViewById(R.id.titleView);
		webview = (WebView) this.findViewById(R.id.news_web);
		baseText.setText("工作室专区");
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
	    @Override
		protected void onResume() {
			super.onResume();
			setRequestedOrientation(Params.orientationControl);
		}
}
		
			
		