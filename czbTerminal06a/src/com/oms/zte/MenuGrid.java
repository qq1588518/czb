package com.oms.zte;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.oms.zte.base.BaseAdapterActivity;
import com.oms.zte.business.BusinessActivity;
import com.oms.zte.news.NewsInfoActivity;
import com.oms.zte.plays.PlaysActivity;
import com.oms.zte.shop.ShopActivity;
import com.oms.zte.train.MobilTrainActivity;
import com.oms.zte.updatesoft.UpdateSoftActivity;
import com.oms.zte.viewtools.ViewToolsActivity;

public class MenuGrid extends BaseAdapterActivity
{
	private Handler mProgressHandler = new Handler();
	private static int result = -2;// 0 是最新版 -1网络错误
	private Thread loginTd = null;
	private final int LASTEST_VERSION = 0;
	private final int NET_ERROR = -1;
	private final int REMIND_UPDATE = -3;

	private GridView gridview;

	public String version = "";
	public String newVersion = "";
	public String oldVersion = "";

	private static boolean check = false;

	private static final String TAG ="MenuGrid" ;
	@Override
	protected void onResume() {
		setRequestedOrientation(Params.orientationControl);
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.grid_layer);
		setRequestedOrientation(Params.orientationControl);
		Button searchBtn = (Button) this.findViewById(R.id.search);
		searchBtn.setOnClickListener(searchListener);
		Button modeBtn = (Button) this.findViewById(R.id.change);
		modeBtn.setOnClickListener(modeListener);
		Button exitBtn = (Button) this.findViewById(R.id.exit);
		exitBtn.setOnClickListener(exitListener);
		gridview = (GridView) findViewById(R.id.menuGrid);
		initLayer();
		
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 9; i++)
		{

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", mThumbIds[i]);
			map.put("ItemText", ntextDatas[i]);
			lstImageItem.add(map);
		}

		gridview.setAdapter(new SimpleAdapter(this, lstImageItem, R.layout.grid_item, 
				new String[] {"ItemImage", "ItemText" }, 
				new int[] { R.id.grid_img, R.id.grid_ntext })
		);
		gridview.setOnItemClickListener(this.getItemlistener(this));
		mProgressHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				switch (result)
				{
				case LASTEST_VERSION:
				{
					showInfoDialog(BASE_DIALOG_INFO, "已经是最新版本");
					break;
				}
				case NET_ERROR:
				{
					showInfoDialog(BASE_DIALOG_INFO, "版本检查失败");
					break;
				}
				case REMIND_UPDATE:
				{
					showInfoDialog(BASE_DIALOG_INFO, "有最新版本,可以更新");
					break;
				}
				}
				result = -2;
			}
		};

		checkVersion();

	}

	protected void onRestart()
	{

		onMenuGridRestart();

	};

	private OnClickListener modeListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			Intent intent = new Intent();
			intent.setClass(MenuGrid.this, MenuList.class);
			startActivity(intent);
			finish();
		}
	};

	private OnClickListener searchListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			Intent intent = new Intent();
			intent.setClass(MenuGrid.this, SearchActivity.class);
			startActivity(intent);
		}
	};

	private OnClickListener exitListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			showDialog(MAIN_EXIT);
		}
	};

	private OnItemClickListener getItemlistener(final Context context)
	{

		OnItemClickListener onclick = new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{

				@SuppressWarnings("unchecked")
				HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
				Intent intent = new Intent();
				try
				{

					switch ((Integer) item.get("ItemImage"))
					{
					case R.drawable.img_shopping:
					{
						intent.setClass(context, ShopActivity.class);
						startActivity(intent);
						break;
					}

					case R.drawable.img_news:
						intent.setClass(context, NewsInfoActivity.class);
						startActivity(intent);
						break;
					case R.drawable.img_viewtool:
						intent.setClass(context, ViewToolsActivity.class);
						startActivity(intent);
						break;
					case R.drawable.img_train:
						if (!Apn.isVpdnUser)
						{
							showInfoDialog(BASE_DIALOG_INFO , getString(R.string.dialog_title));
						} else
						{
							intent.setClass(context, MobilTrainActivity.class);
							startActivity(intent);
						}
						break;
					case R.drawable.img_business:
						intent.setClass(context, BusinessActivity.class);
						startActivity(intent);
						break;
					case R.drawable.img_email:
					{
						intent.setClass(context, EmailActivity.class);
						startActivity(intent);
						break;
					}
					case R.drawable.img_account:
					{
						showInfoDialog(BASE_DIALOG_INFO , "此功能尚未开通！");
						break;
					}
					case R.drawable.img_play:

						intent.setClass(context, PlaysActivity.class);
						startActivity(intent);

						break;
					case R.drawable.img_softup:
						showInfoDialog(BASE_DIALOG_PROGRESS, "请稍后");
						new Thread(new Runnable()
						{
							public void run()
							{
								newVersion = MyURL.webServerConnect(MyURL.UPDATA_WEB_URL, "getVersionNO",
										new String[0]).toString();
								Log.i(TAG, "newVersion: "+newVersion);
								if (MyURL.err == 1)
								{
									dissPrssDialog();
									result = -1;
									MyURL.err = 0;
									mProgressHandler.sendMessage(mProgressHandler
											.obtainMessage());

								} 
								else if ((!"".equals(oldVersion.trim()))&&(!"".equals(newVersion.trim()))&&Integer.valueOf(oldVersion) < Integer.valueOf(newVersion))
									{
									MyURL.updataVersion = newVersion;
									Log.i(TAG, "newVersion: "+newVersion);
									dissPrssDialog();
									Intent downIntent = new Intent();
									downIntent.setClass(context, UpdateSoftActivity.class);
									startActivity(downIntent);
									} 
									else
									{
										dissPrssDialog();
										result = 0;
										mProgressHandler.sendMessage(mProgressHandler.obtainMessage());
									}
							}

						}).start();

						break;
					default:
						showDialog(0);
						break;
					}
				} catch (Exception e)
				{
					System.out.println("log==>" + e.getMessage());
				}
			}
		};
		return onclick;
	}

	public void checkVersion()
	{
		if (check)
		{
			return;
		}
		check = true;
		showInfoDialog(BASE_DIALOG_PROGRESS, "系统初始化中");

		oldVersion = MyURL.getVersion();
		Log.i(TAG, "oldVersion : " +oldVersion );
		loginTd = new Thread(new Runnable()
		{
			public void run()
			{
				newVersion = MyURL.webServerConnect(MyURL.UPDATA_WEB_URL, "getVersionNO",
						new String[0]).toString();
				dissPrssDialog();
				if (MyURL.err == 1)
				{
					result = NET_ERROR;
					MyURL.err = 0;
					mProgressHandler.sendMessage(mProgressHandler.obtainMessage());

				} else if (Integer.valueOf(oldVersion) < Integer.valueOf(newVersion))
				{
					result = REMIND_UPDATE;
					mProgressHandler.sendMessage(mProgressHandler.obtainMessage());

				}
			}

		});
		loginTd.start();
	}

	public void onConfigurationChanged(Configuration config)
	{
		initLayer();
		super.onConfigurationChanged(config);
	}
	
	private void initLayer(){
		
		int top = 0;
		LinearLayout.LayoutParams returnParams = (LinearLayout.LayoutParams)gridview.getLayoutParams();
		LayoutParams linearParams = gridview.getLayoutParams();
		linearParams.width = Params.ViewWith;
		
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) 
		{
			top = (Params.ViewWith - 420)/3;
		} 
		else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 
		{
			top = (Params.ViewHight - 420)/3;
		}
		if(top < 0)
		{
			returnParams.topMargin = 10;
		}
		else
		{
			returnParams.topMargin = top;
		}
		gridview.setLayoutParams(returnParams);

	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			showDialog(MAIN_EXIT);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void dialogBackDismiss(){

		if(loginTd != null )
		{
			dissPrssDialog();
			loginTd.interrupt();
		}
	};

	private final Integer[] mThumbIds = { R.drawable.img_news, R.drawable.img_shopping,
			R.drawable.img_viewtool, R.drawable.img_train, R.drawable.img_business,
			R.drawable.img_account, R.drawable.img_email, R.drawable.img_play,
			R.drawable.img_softup };

	private String[] ntextDatas = 
	{ 
			"移动资讯", "移动购物", "展业工具", "移动培训", "业务管理", "账户管理", "手机邮箱", "娱乐中心","软件升级" 
	};
}
