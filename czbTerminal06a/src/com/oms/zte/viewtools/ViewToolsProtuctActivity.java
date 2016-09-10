package com.oms.zte.viewtools;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oms.zte.MyURL;
import com.oms.zte.Params;
import com.oms.zte.R;
import com.oms.zte.base.CommonNew;

public class ViewToolsProtuctActivity extends CommonNew
{

	public static final String path = MyURL.IP + "/ssczb/distributionurl.htm?action=goodFood&start=0&item="+ MyURL.ITEM_COUNT+MyURL.UESER+MyURL.SYSTEM;
	public static final String selfProtect = MyURL.IP + "/ssczb/distributionurl.htm?action=selfProtect&start=0&item="+ MyURL.ITEM_COUNT+MyURL.UESER + MyURL.SYSTEM;
	public static final String dressProduct = MyURL.IP + "/ssczb/distributionurl.htm?action=dressProduct&start=0&item="+ MyURL.ITEM_COUNT+MyURL.UESER+MyURL.SYSTEM;
	public static final String otherProduct = MyURL.IP + "/ssczb/distributionurl.htm?action=otherProduct&start=0&item="+ MyURL.ITEM_COUNT+MyURL.UESER+MyURL.SYSTEM;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(Params.orientationControl);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除标题
		setContentView(R.layout.view_tools_protuct);
		ImageButton page_return = (ImageButton) findViewById(R.id.lastpageid);
		ImageButton btnMain = (ImageButton)findViewById(R.id.page_main);
		ImageButton btnUpdate = (ImageButton)findViewById(R.id.webUpdate);
		ImageButton btnLast = (ImageButton)findViewById(R.id.page_last);
		ImageButton btnNext = (ImageButton)findViewById(R.id.page_next);
		ImageButton btnMore = (ImageButton)findViewById(R.id.page_more);
		ImageButton btnExit = (ImageButton)findViewById(R.id.page_exit);
		init_Button(page_return,btnMain,btnUpdate,btnLast,btnNext,btnMore,btnExit);
		baseText = (TextView) this.findViewById(R.id.titleView);
		baseText.setText("产 品 介 绍");
		webview = (WebView) this.findViewById(R.id.news_web);
		news(path,webview);
		initPage(4);
		ImageButton toolfood = (ImageButton) this.findViewById(R.id.page_food);

		toolfood.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				news(path,webview);
				initPage(4);
			}
		});

		ImageButton toolself = (ImageButton) this.findViewById(R.id.page_self);

		toolself.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				news(selfProtect,webview);
				initPage(5);
			}
		});

		ImageButton tooldress = (ImageButton) this
		.findViewById(R.id.page_dress);

		tooldress.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				news(dressProduct,webview);
				initPage(6);
			}
		});

		ImageButton toolother = (ImageButton) this.findViewById(R.id.page_other);

		toolother.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				news(otherProduct,webview);
				initPage(7);
			}
		});


	}
	@Override
	protected void onResume() {
		setRequestedOrientation(Params.orientationControl);
		super.onResume();
	}
}