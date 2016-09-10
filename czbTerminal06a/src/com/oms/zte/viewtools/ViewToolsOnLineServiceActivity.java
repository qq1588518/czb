package com.oms.zte.viewtools;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.oms.zte.MyURL;
import com.oms.zte.Params;
import com.oms.zte.R;
import com.oms.zte.base.CommonNew;

public class ViewToolsOnLineServiceActivity extends CommonNew {
   
	private static final String Str_Main=MyURL.IP+"/ssczb/index.htm?m=1004"+MyURL.UESER+MyURL.SYSTEM;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setRequestedOrientation(Params.orientationControl);
        init_Activity();
    }
    private void init_Activity()
    {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.wwmain);
    	webview  = (WebView)findViewById(R.id.webview);
    	ImageButton page_return = (ImageButton) findViewById(R.id.lastpageid);
		ImageButton btnMain = (ImageButton)findViewById(R.id.page_main);
		ImageButton btnUpdate = (ImageButton)findViewById(R.id.webUpdate);
		ImageButton btnLast = (ImageButton)findViewById(R.id.page_last);
		ImageButton btnNext = (ImageButton)findViewById(R.id.page_next);
		ImageButton btnMore = (ImageButton)findViewById(R.id.page_more);
		ImageButton btnExit = (ImageButton)findViewById(R.id.page_exit);
		init_Button(page_return,btnMain,btnUpdate,btnLast,btnNext,btnMore,btnExit);
		news(Str_Main,webview );
    }
    @Override
	protected void onResume() {
		setRequestedOrientation(Params.orientationControl);
		super.onResume();
	}
}