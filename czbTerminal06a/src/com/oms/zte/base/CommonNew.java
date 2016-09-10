package com.oms.zte.base;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.oms.zte.MyURL;
import com.oms.zte.Params;
import com.oms.zte.R;
import com.oms.zte.updatesoft.UpdateSoftActivity;


public class CommonNew extends BaseAdapterActivity
{
	protected Dialog pbarDialog;
	private boolean connectError =false;
	protected WebView webview = null;
	AlertDialog menuDialog;
	ListView listView;
	GridView menuGrid;
	GridView toolbarGrid;
	View menuView;
	private ImageButton btnLast;
	private ImageButton btnNext;
	protected ImageButton topnextpage = null;
	protected ImageButton toplastpage = null;
	protected TextView baseText;
	Intent intent = null;
	Intent intentFile = null;
	Intent intentSensing = null ;
	private int page_All = 0;
	private int page_my = 0;//还有多少页码
	private boolean flag_return =true ;// 返回状态
	private boolean flag_error =false ;
	private int flag_menet =0 ;
	protected String path =null;
	private static final String STR_PART = "&item="+MyURL.ITEM_COUNT ;
	int menu_image_array[] = {
		R.drawable.menu_filemanager, R.drawable.menu_downmanager,R.drawable.icon_sensing,R.drawable.menu_quit
	};
	String menu_name_array[] = {
			"文件管理", "下载管理","重力感应", "退出系统"
		};
	private android.view.View.OnClickListener updateListener;
	private android.view.View.OnClickListener lastListener;
	private android.view.View.OnClickListener nextListener;
	private android.view.View.OnClickListener mainListener;
	private android.view.View.OnClickListener exitListener;
	private android.view.View.OnClickListener MenuListener;
	private android.view.View.OnClickListener nextpageListener;
	private android.view.View.OnClickListener returnListener;

	private final static String STR_D=".";
	private final static String STR_DJSP=".jsp";
	private final static String STR_DCN=".cn/";
	private final static String STR_DCOM=".com";
	private final static String STR_DNET=".net";
	private static final String rtspType = "rtsp://";
	public CommonNew()
	{
		webview = null;
		flag_error =false ;
		flag_menet =0 ;
		nextpageListener = new android.view.View.OnClickListener() {
			public void onClick(View v)
			{
				--page_my ;
				flag_return =false ;
				toplastpage.setVisibility(View.VISIBLE);
				toplastpage.setImageResource(R.drawable.state_lastpage);
				int start =(page_All -page_my)*MyURL.ITEM_COUNT ;
				String str =path+start+STR_PART;
				webview.loadUrl(str);
				if(page_my<=1)
				{
					CommonNew.this.topnextpage.setVisibility(View.INVISIBLE);
				}
			}
		};
		lastListener = new android.view.View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				if(flag_return)
				{
					if(flag_error)
					{
						CommonNew.this.finish();
						return ;
					}
					if(flag_menet==1)
			    	{
			    		webview.loadUrl("http://whotel.czton.cn/account_main.jsp");
			    		 flag_menet =2;
					     return ;
			    	}
			    	if(flag_menet==2)
			    	{
			    		 webview.loadUrl("http://whotel.czton.cn/main.jsp");
			    		 flag_menet =3;
					     return ;
			    	}
			    	if(flag_menet==3)
			    	{
			    		webview.loadUrl("http://wap.czton.cn?m=1019"+MyURL.UESER+MyURL.SYSTEM);
			    		 flag_menet =0;
					     return ;
			    	}
					if(webview!=null&&webview.canGoBack())
					{
						webview.goBack();
					}else
					{
						CommonNew.this.finish();
					}
				}else
				{
					CommonNew.this.topnextpage.setVisibility(View.VISIBLE);
					++page_my ;
					int start =(page_All -page_my)*MyURL.ITEM_COUNT ;
					String str =path+start+STR_PART;
					System.out.println(str);
					webview.loadUrl(str);
					if(page_my>=page_All)
					{
						toplastpage.setImageResource(R.drawable.state_return);
						flag_return=true ;
					}
				}
			}
			
		};
		returnListener  = new android.view.View.OnClickListener() {
			public void onClick(View v)
			{
				
				if(flag_return)
				{
					CommonNew.this.finish();
				}else
				{
					CommonNew.this.topnextpage.setVisibility(View.VISIBLE);
					++page_my ;
					int start =(page_All -page_my)*10 ;
					String str =path+start+STR_PART;
					System.out.println(str);
					webview.loadUrl(str);
					if(page_my>=page_All)
					{
						toplastpage.setImageResource(R.drawable.state_return);
						flag_return=true ;
					}
				}
			}

		};
		updateListener = new android.view.View.OnClickListener() {

			public void onClick(View v)
			{
				if(null!=webview)
				webview.reload();
			}
		};
		nextListener = new android.view.View.OnClickListener() {

			public void onClick(View v)
			{
				if(null!=webview){
					if (webview.canGoForward())
						webview.goForward();
				}
			}
			
		};
		mainListener = new android.view.View.OnClickListener() {
			public void onClick(View v)
			{
				Params.IsExitApp = false;
				Params.SetMainView = true;
				onKeyDown(4, new KeyEvent(0, 4));
			}
		};
		exitListener = new android.view.View.OnClickListener() {

			public void onClick(View v)
			{
				showDialog(3);
			}
		};
		MenuListener = new android.view.View.OnClickListener() {

			public void onClick(View v)
			{
				menuDialog.show();
			}
		};
	}

	public void init_Button(ImageButton page_return,ImageButton btnMain,ImageButton btnUpdate,ImageButton btnLast,ImageButton btnNext,ImageButton btnMore,ImageButton btnExit)
	{
		menuView = View.inflate(this,R.layout.gridview_menu,null);
		menuDialog = (new android.app.AlertDialog.Builder(this)).create();
		menuDialog.setView(menuView);
		menuDialog.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
			{
				if (keyCode == 82)
					dialog.dismiss();
				return false;
			}
		});
		menuGrid = (GridView)menuView.findViewById(R.id.gridview);
		menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
		intent = new Intent();
		intent.setClass(this, com.oms.zte.base.DownManager.class);
		intentFile = new Intent();
		intentFile.setClass(this, com.oms.zte.FileManage.class);
		intentSensing = new Intent();
		intentSensing.setClass(this, com.oms.zte.SetActivity.class);
		menuGrid.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				
				switch (arg2)
				{
				case 0:
					startActivity(intentFile);
					menuDialog.dismiss();
					break;
				case 1: 
					startActivity(intent);
					menuDialog.dismiss();
					break;
				case 2: 
					startActivity(intentSensing);
					menuDialog.dismiss();
					break;
				case 3: 
					showDialog(3);
					menuDialog.dismiss();
					break;
				}
			}
		});
		flag_return =true ;
		page_return.setVisibility(View.VISIBLE);
		page_return.setImageResource(R.drawable.state_return);
		page_return.setOnClickListener(returnListener);
		this.btnLast = btnLast;
		this.btnNext = btnNext;
		btnMain.setOnClickListener(mainListener);
		btnUpdate.setOnClickListener(updateListener);
		this.btnLast.setOnClickListener(lastListener);
		this.btnNext.setOnClickListener(nextListener);
		btnMore.setOnClickListener(MenuListener);
		btnExit.setOnClickListener(exitListener);
	}
	protected void init_Dialog()
	{
		pbarDialog = super.onCreateDialog(1);
		 this.pbarDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
		    {
		      public void onCancel(DialogInterface arg0)
		      {
		    	CommonNew.this.dissPrssDialog();
		        if (CommonNew.this.webview.canGoBack())
		        {
		        	CommonNew.this.webview.goBack();
		        }
		        else
		        {
		        	CommonNew.this.onKeyDown(4, new KeyEvent(0, 4));
		        }
		      }
		    });
	}
	public void news(String newpath)
	{
		flag_return =true ;
		init_Dialog();
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setPluginsEnabled(true);
		webSettings.setDefaultFixedFontSize(40);
		webview.clearCache(true);
		webview.setVerticalScrollBarEnabled(false);
		webview.setWebViewClient(new WebClinet());
		webview.setWebChromeClient(new ChromeClient());
		webview.loadUrl(newpath);
		this.path =newpath ;
		
	}
	public void news(String newpath,final WebView webview)
	{
		this.webview = webview;
		flag_return =true ;
		init_Dialog();
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setPluginsEnabled(true);
		webSettings.setDefaultFixedFontSize(40);
		webview.clearCache(true);
		webview.setVerticalScrollBarEnabled(false);
		webview.setWebViewClient(new WebClinet());
		webview.setWebChromeClient(new ChromeClient());
		webview.loadUrl(newpath);
		this.path =newpath ;
		
	}

	public void initTopPage()
	{
		toplastpage = (ImageButton)findViewById(R.id.lastpageid);
		topnextpage = (ImageButton)findViewById(R.id.nextpageid);
		toplastpage.setOnClickListener(returnListener);
		topnextpage.setOnClickListener(nextpageListener);
		toplastpage.setVisibility(View.VISIBLE);
		toplastpage.setImageResource(R.drawable.state_return);
	}

	public void initPage(int number)
	{
		initTopPage();
		int sub=path.lastIndexOf("&");
		--sub ; 
		path =path.substring(0,sub) ;
		int page_number = MyURL.pageTotals[number];
		int yu = page_number%MyURL.ITEM_COUNT;
		if(yu>0)
		{
			
			this.page_my=page_number/MyURL.ITEM_COUNT+1 ;
			this.page_All =this.page_my ;
		}
		if(this.page_All>1)
		{
			topnextpage.setVisibility(View.VISIBLE);
		}else
		{
			topnextpage.setVisibility(View.INVISIBLE);
			
		}
	}

	public void onConfigurationChanged(Configuration config)
	{
		super.onConfigurationChanged(config);
	}

	private SimpleAdapter getMenuAdapter(String menuNameArray[], int imageResourceArray[])
	{
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", Integer.valueOf(imageResourceArray[i]));
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data, R.layout.item_menu, 
				new String[] {"itemImage", "itemText"}, 
				new int[]  {R.id.item_image, R.id.item_text});
		return simperAdapter;
	}

	public void dissPrssDialog()
	{
		try{
			if (pbarDialog != null)
			{
				pbarDialog.dismiss();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	 public boolean onKeyDown(int keyCode, KeyEvent event)
	  {
	    if ((keyCode == 4) &&(this.webview!=null)&& (this.webview.canGoBack()) && (!Params.IsExitApp))
	    {
	    	if(flag_error)
	    	{
	    		CommonNew.this.finish();
	    		flag_error =false ;
		        return true;
	    	}
	    	if(flag_menet==1)
	    	{
	    		 this.webview.loadUrl("http://whotel.czton.cn/account_main.jsp");
	    		 flag_menet =2;
			     return true;
	    	}
	    	if(flag_menet==2)
	    	{
	    		 this.webview.loadUrl("http://whotel.czton.cn/main.jsp");
	    		 flag_menet =3;
			     return true;
	    	}
	    	if(flag_menet==3)
	    	{
	    		 this.webview.loadUrl("http://wap.czton.cn?m=1019"+MyURL.UESER+MyURL.SYSTEM);
	    		 flag_menet =0;
			     return true;
	    	}
		      if (this.connectError)
		      {
		        this.connectError = false;
		      } 
		      else if (!Params.SetMainView)
		      {
		        this.webview.goBack();
		        return true;
		      }
	    }
	    return super.onKeyDown(keyCode, event);
	  }
	 private void setNextInVisiable()
	 {
		 if(null!=topnextpage&&null!=toplastpage)
		 {
			 topnextpage.setVisibility(View.INVISIBLE);
			 toplastpage.setImageResource(R.drawable.state_return);
			 flag_return=true ;
		 }
	 }
		class WebClinet extends WebViewClient
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				String end =url.substring(url.length()-4);
				if (url.toLowerCase().startsWith(rtspType)|| url.toLowerCase().endsWith(".3gp")|| url.toLowerCase().endsWith(".mp4")) 
				{
					
					Uri rtspUri = Uri.parse(url);
					Intent viewIntent = new Intent(Intent.ACTION_VIEW, rtspUri);
					startActivity(viewIntent);
				} 
				else if(end.startsWith(STR_D)&&(!end.equals(STR_DJSP))&&(!end.equals(STR_DCN))&&(!end.equals(STR_DCOM))&&(!end.equals(STR_DNET)))
				{
					dissPrssDialog();
					Intent downIntent = new Intent();
					downIntent.putExtra("downUrl", url);
					downIntent.setClass(CommonNew.this, UpdateSoftActivity.class);
					startActivity(downIntent);
				}
				else
				{
					  setNextInVisiable();
					  view.loadUrl(url);
		        } 
				return true;
			}
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
			{
				
				if (pbarDialog!=null&&pbarDialog.isShowing())
				{
					CommonNew.this.dissPrssDialog();
					if(webview!=null&&webview.canGoBack())
					{
						webview.goBack();
					}
				}
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (url.equals("http://wap.czton.cn/?code=0"))
				{
					flag_error =true;
				}
				if(url.equals("http://wticket.czton.cn/of.jsp"))
				{
					flag_menet =1 ;
				}
				if (pbarDialog!=null&&!pbarDialog.isShowing())
				{
					try{
						pbarDialog.show();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				super.onPageStarted(view, url, favicon);
			}
			@Override
			public void onPageFinished(WebView view, String url) 
			{
				super.onPageFinished(view, url);
				if (pbarDialog!=null&&pbarDialog.isShowing())
				{
					CommonNew.this.dissPrssDialog();
				}
			}
		};
		class ChromeClient extends  WebChromeClient{
			@Override
			// 处理javascript中的alert
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				System.out.println("onJsAlert");
				// 构建一个Builder来显示网页中的对话框`+
				Builder builder = new Builder(CommonNew .this);
				builder.setTitle("提示对话框");
				builder.setMessage(message);
				builder.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// 点击确定按钮之后,继续执行网页中的操作
								result.confirm();
							}
						});
				builder.setCancelable(false);
				builder.create();
				builder.show();
				return true;
			};

			@Override
			// 处理javascript中的confirm
			public boolean onJsConfirm(WebView view, String url,
					String message, final JsResult result) {
				System.out.println("onJsConfirm");
				Builder builder = new Builder(CommonNew .this);
				builder.setTitle("带选择的对话框");
				builder.setMessage(message);
				builder.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.confirm();
							}
						});
				builder.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.cancel();
							}
						});
				builder.setCancelable(false);
				builder.create();
				builder.show();
				return true;
			};

			@Override
			// 处理javascript中的prompt
			// message为网页中对话框的提示内容
			// defaultValue在没有输入时，默认显示的内容
			public boolean onJsPrompt(WebView view, String url, String message,
					String defaultValue, final JsPromptResult result) {
				System.out.println("onJsPrompt");
				final LayoutInflater factory = LayoutInflater
						.from(CommonNew .this);
				final View dialogview = factory.inflate(R.layout.prom_dialog,
						null);
				((TextView) dialogview.findViewById(R.id.TextView_PROM))
						.setText(message);
				((EditText) dialogview.findViewById(R.id.EditText_PROM))
						.setText(defaultValue);
				Builder builder = new Builder(CommonNew .this);
				builder.setTitle("带输入的对话框");
				builder.setView(dialogview);
				builder.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								String value = ((EditText) dialogview
										.findViewById(R.id.EditText_PROM))
										.getText().toString();
								System.out.println("value==" + value);
								result.confirm(value);
							}
						});
				builder.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.cancel();
							}
						});
				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						result.cancel();
					}
				});
				builder.show();
				return true;
			};

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				CommonNew .this.getWindow().setFeatureInt(
						Window.FEATURE_PROGRESS, newProgress * 100);
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				CommonNew .this.setTitle(title);
				super.onReceivedTitle(view, title);
			}
		};
}
