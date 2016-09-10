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

public class ViewToolsWorkQuestActivity extends CommonNew
{
	
	public static final String path = MyURL.IP + "/ssczb/distributionurl.htm?action=workQuest&start=0&item="+ MyURL.ITEM_COUNT;
	public static final String healthPath = MyURL.IP + "/ssczb/distributionurl.htm?action=healthQuest&start=0&item="+ MyURL.ITEM_COUNT;
	public static final String questPath = MyURL.IP + "/ssczb/distributionurl.htm?action=productQuestion&start=0&item="+ MyURL.ITEM_COUNT;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(Params.orientationControl);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.view_tools_question);
		baseText = (TextView) this.findViewById(R.id.titleView);
		webview = (WebView) this.findViewById(R.id.news_web);
		ImageButton page_return = (ImageButton) findViewById(R.id.lastpageid);
		ImageButton btnMain = (ImageButton)findViewById(R.id.page_main);
		ImageButton btnUpdate = (ImageButton)findViewById(R.id.webUpdate);
		ImageButton btnLast = (ImageButton)findViewById(R.id.page_last);
		ImageButton btnNext = (ImageButton)findViewById(R.id.page_next);
		ImageButton btnMore = (ImageButton)findViewById(R.id.page_more);
		ImageButton btnExit = (ImageButton)findViewById(R.id.page_exit);
		init_Button(page_return,btnMain,btnUpdate,btnLast,btnNext,btnMore,btnExit);
		news(path,webview);
		initPage(10);
		ImageButton toolhealthquest = (ImageButton) this.findViewById(R.id.page_toolhealthquest);
		toolhealthquest.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				news(healthPath,webview);
				initPage(10);
			}
		});

		ImageButton toolworkquest = (ImageButton) this.findViewById(R.id.page_toolworkquest);
		toolworkquest.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				news(path,webview);
				initPage(11);
			}
		});

		ImageButton toolprodquestion = (ImageButton) this.findViewById(R.id.page_toolprodquestion);
		toolprodquestion.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				news(questPath,webview);
				initPage(12);
			}
		});
	}
	@Override
	protected void onResume() {
		setRequestedOrientation(Params.orientationControl);
		super.onResume();
	}
}