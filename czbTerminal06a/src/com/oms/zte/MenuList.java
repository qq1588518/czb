package com.oms.zte;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.oms.zte.base.BaseListActivity;
import com.oms.zte.business.BusinessActivity;
import com.oms.zte.news.NewsInfoActivity;
import com.oms.zte.plays.PlaysActivity;
import com.oms.zte.shop.ShopActivity;
import com.oms.zte.train.MobilTrainActivity;
import com.oms.zte.updatesoft.UpdateSoftActivity;
import com.oms.zte.viewtools.ViewToolsActivity;

public class MenuList extends BaseListActivity {

	private final int NEWS = 0; // 移动资讯
	private final int SHOPPING = 1; // 移动购物
	private final int VIEWTOOL = 2; // 展业工具
	private final int TRAIN = 3; // 移动培训
	private final int BUSINESS = 4; // 业务管理
	private final int ACCOUNT = 5; // 账户管理
	private final int EMAIL = 6; // 手机邮箱
	private final int PLAY = 7; // 娱乐中心
	private final int SOFTUPDATE = 8; // 软件升级
	
	private Thread loginTd = null;
	public String newVersion = "";
	public String oldVersion = "";
	private static final String STOREVERSION = "//sdcard//versionInfo.dat";

	int alpha = 255;
	int b = 0;
	static int loading = 0;// 正在读取数据
	private Handler mProgressHandler = new Handler();

	private static int result = -2;// 0 是最新版本 -1网络错误
	@Override
	protected void onResume() {
		setRequestedOrientation(Params.orientationControl);
		super.onResume();
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除标题栏
		setContentView(R.layout.menu_list_layer);
		setRequestedOrientation(Params.orientationControl);
		this.setImgDatas(new Integer[] { R.drawable.img_news,
				R.drawable.img_shopping, R.drawable.img_viewtool,
				R.drawable.img_train, R.drawable.img_business,
				R.drawable.img_account, R.drawable.img_email,
				R.drawable.img_play, R.drawable.img_softup });
		
		this.setNtextDatas(new Integer[] { R.string.str_news,
				R.string.str_shopping, R.string.str_viewtool,
				R.string.str_train, R.string.mng_business,
				R.string.mng_account, R.string.mobile_email, R.string.str_play,
				R.string.str_softup });
		this.setStextDatas(new Integer[] { R.string.str_news_s,
				R.string.str_shopping_s, R.string.str_viewtool_s,
				R.string.str_train_s, R.string.mng_business_s,
				R.string.mng_account_s, R.string.mobile_email_s,
				R.string.str_play_s, R.string.str_soft_s });

		Button searchBtn = (Button) this.findViewById(R.id.search);
		searchBtn.setOnClickListener(searchListener);

		Button modeBtn = (Button) this.findViewById(R.id.change);
		modeBtn.setOnClickListener(mGoListener);

		Button exitBtn = (Button) this.findViewById(R.id.exit);
		exitBtn.setOnClickListener(exitListener);

		setListAdapter(getAdapterClass(R.drawable.img_jt,this));
		mProgressHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (result == 0) {
					showInfoDialog(2, "已经是最新版本 ");
				} else if (result == -1) {
					showInfoDialog(2, "网络错误");
				}
				result = -2;
			}
		};

	}

	protected void onRestart() {
		onMenuListRestart();

	};

	private OnClickListener mGoListener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MenuList.this, MenuGrid.class);
			startActivity(intent);
			finish();
		}
	};

	private OnClickListener searchListener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MenuList.this, SearchActivity.class);
			startActivity(intent);
		}
	};

	private OnClickListener exitListener = new OnClickListener() {
		public void onClick(View v) {
			showDialog(MAINEXIT);
		}
	};

	@Override
	protected void onListItemClick(ListView list, View view, int position,long id) {
		Intent intent = new Intent();
		try {

			switch (position) {
			case SHOPPING: {
				intent.setClass(this, ShopActivity.class);
				startActivity(intent);
				break;
			}
			case NEWS:{
				intent.setClass(this, NewsInfoActivity.class);
				startActivity(intent);
				break;
			}
			case VIEWTOOL:{
				intent.setClass(this, ViewToolsActivity.class);
				startActivity(intent);
				break;
			}
			case TRAIN:{
				
				if (!Apn.isVpdnUser) 
				{
					showInfoDialog(BASE_DIALOG_INFO, getString(R.string.dialog_title));
				} else 
				{
					intent.setClass(this, MobilTrainActivity.class);
					startActivity(intent);
				}
				break;
			}
			case BUSINESS:{
				intent.setClass(this, BusinessActivity.class);
				startActivity(intent);
				break;
			}
			case EMAIL: {
				intent.setClass(this,EmailActivity.class);
				startActivity(intent);
				break;
			}
			case ACCOUNT: {
				showInfoDialog(BASE_DIALOG_INFO , "此功能尚未开通！");
				break;
			}
			case PLAY:{
				intent.setClass(this, PlaysActivity.class);
				startActivity(intent);
				break;

			}
			case SOFTUPDATE:
				showInfoDialog(1, "请稍后");
				haveFile();
//				if (getVersionInfo().equals("wrong")) {
//					storeOldVersionInfo();
//				}
//				oldVersion = getVersionInfo();
				oldVersion = MyURL.version;
				loginTd = new Thread(new Runnable() {
					public void run() {

						newVersion = MyURL.webServerConnect(MyURL.UPDATA_WEB_URL, "getVersionNO",
								new String[0]).toString();
						Log.e("new version", newVersion);
						
						if (MyURL.err == 1) {
							dissPrssDialog();
							result = -1;
							MyURL.err = 0;
							mProgressHandler.sendMessage(mProgressHandler
									.obtainMessage());

						} else if (Integer.valueOf(oldVersion) < Integer
								.valueOf(newVersion)) {
//							storeNewVersionInfo();
							MyURL.updataVersion = newVersion;
							//更新版本
							showUpdateActivity();
							
						} else {
							dissPrssDialog();
							result = 0;
							mProgressHandler.sendMessage(mProgressHandler
									.obtainMessage());

						}
					}

				});
				loginTd.start();
				break;
			default:
				showInfoDialog(BASE_DIALOG_INFO, "选择信息错误");
				break;
			}
		} catch (Exception e) {
			System.out.println("log==>" + e.getMessage());
		}
	}

	private void haveFile() {

		File file;
		file = new File(STOREVERSION);
		try {

			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/*public boolean storeOldVersionInfo() {
		File file;
		FileOutputStream out;
		BufferedOutputStream bout;
		DataOutputStream dout;
		String oldVersion = "1";
		try {
			file = new File(STOREVERSION);

			out = new FileOutputStream(file);
			bout = new BufferedOutputStream(out);
			dout = new DataOutputStream(out);
			dout.writeUTF(new String(Base64.encode(oldVersion.getBytes())));
			dout.close();
			bout.close();
			out.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
*/
//	public boolean storeNewVersionInfo() {
//		File file;
//		FileOutputStream out;
//		BufferedOutputStream bout;
//		DataOutputStream dout;
//		try {
//			file = new File(STOREVERSION);
//			out = new FileOutputStream(file);
//			bout = new BufferedOutputStream(out);
//			dout = new DataOutputStream(out);
//
//			dout.writeUTF(new String(Base64.encode(newVersion.getBytes())));
//
//			dout.close();
//			bout.close();
//			out.close();
//
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}

//	public String getVersionInfo() {
//
//		File file;
//		FileInputStream in;
//		BufferedInputStream bin;
//		DataInputStream din;
//		try {
//
//			Log.v("logtag", "getAcountInfo");
//			file = new File(STOREVERSION);
//			if (!file.exists()) {
//				return "ok";
//			} else {
//				// 打开文件file的InputStream
//				in = new FileInputStream(file);
//				bin = new BufferedInputStream(in);
//				din = new DataInputStream(bin);
//
//				version = new String(Base64.decode(din.readUTF().toCharArray()));
//
//				bin.close();
//				din.close();
//				in.close();
//
//			}
//		} catch (IOException e) {
//			version = "wrong";
//
//		}
//		return version;
//	}

//	public void sendSMS() {
//		SmsManager sms = SmsManager.getDefault();
//		try {
//			PendingIntent pi = PendingIntent.getBroadcast(MenuList.this, 0,
//					new Intent(), 0);
//			sms.sendTextMessage("13668136283", null, "test", pi, null);
//			Toast.makeText(this, "成功", Toast.LENGTH_LONG).show();
//
//		} catch (Exception e) {
//			Toast.makeText(this, "失败", Toast.LENGTH_LONG).show();
//			e.printStackTrace();
//		}
//	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			showDialog(MAINEXIT);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showUpdateActivity(){
		
		Intent downIntent = new Intent();
		downIntent.setClass(this, UpdateSoftActivity.class);
		startActivity(downIntent);
	}
	
	protected void dialogBackDismiss(){

		if(loginTd != null)
		{
			dissPrssDialog();
			loginTd.interrupt();
		}
	};
}
