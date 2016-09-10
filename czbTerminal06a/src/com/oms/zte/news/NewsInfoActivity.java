package com.oms.zte.news;

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

public class NewsInfoActivity extends CommonNew
{

	public static final String path = MyURL.IP + "/ssczb/distributionurl.htm?action=getYdzx&start=0&item="+ MyURL.ITEM_COUNT;
	public static final String corpPath = MyURL.IP + "/ssczb/distributionurl.htm?action=getNews&start=0&item="+ MyURL.ITEM_COUNT;
	public static final String hotPath = MyURL.IP + "/ssczb/distributionurl.htm?action=getHotSale&start=0&item="+ MyURL.ITEM_COUNT;
	public static final String productPath = MyURL.IP+ "/ssczb/distributionurl.htm?action=getProductNews&start=0&item="+ MyURL.ITEM_COUNT;
	public static final String rencentPath = MyURL.IP+ "/ssczb/distributionurl.htm?action=getRencentNews&start=0&item="+ MyURL.ITEM_COUNT;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(Params.orientationControl);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news);
		ImageButton page_return = (ImageButton) findViewById(R.id.lastpageid);
		ImageButton btnMain = (ImageButton)findViewById(R.id.page_main);
		ImageButton btnUpdate = (ImageButton)findViewById(R.id.webUpdate);
		ImageButton btnLast = (ImageButton)findViewById(R.id.page_last);
		ImageButton btnNext = (ImageButton)findViewById(R.id.page_next);
		ImageButton btnMore = (ImageButton)findViewById(R.id.page_more);
		ImageButton btnExit = (ImageButton)findViewById(R.id.page_exit);
		init_Button(page_return,btnMain,btnUpdate,btnLast,btnNext,btnMore,btnExit);
		baseText = (TextView) this.findViewById(R.id.titleView);
		webview = (WebView) this.findViewById(R.id.news_web);
		news(path,webview);
		final ImageButton compnew = (ImageButton) this.findViewById(R.id.page_compnew);
		compnew.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				news(corpPath);
				initPage(0);
			}
		});

		final ImageButton lastnew = (ImageButton) this.findViewById(R.id.page_lastnew);
		lastnew.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				news(rencentPath);
				initPage(2);
			}
		});

		final ImageButton prodnew = (ImageButton) this.findViewById(R.id.page_prodnew);

		prodnew.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				news(productPath);
				initPage(1);
			}
		});
		
		final ImageButton distnew = (ImageButton) this.findViewById(R.id.page_distnew);

		distnew.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				news(hotPath);
				initPage(3);
			}
		});

	}
	@Override
	protected void onResume() {
		setRequestedOrientation(Params.orientationControl);
		super.onResume();
	}
}