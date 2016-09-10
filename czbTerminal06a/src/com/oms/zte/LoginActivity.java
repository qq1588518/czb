package com.oms.zte;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity
{

	private static final String STOREPATH = "//sdcard//userInfo.dat";

	private final String USERDATAPATH = "//sdcard//CZB";
	private boolean loginView = false;
	private int mFlag = -1; // 0登录成功 1登录中 2失败 3vpdn失败

	private ProgressDialog pbarDialog;
	private Handler mProgressHandler = new Handler();
	private boolean isSetVpdn = false;

	private UserData udata;

	private final int DIALOG_PROGRESS = 0;
	private final int DIALOG_ERRINFO = 1;
	private final int DIALOG_ERRINPUT = 2;
	private final int DIALOG_ERRLOGIN = 3;
	private final int DIALOG_TIMEOUT = 4;
	private static String dialogInfo = new String();

	private EditText userName;
	private EditText passWord;
	private boolean flag_Loading = false ;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(Params.orientationControl);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除标题栏
		mProgressHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);

				pbarDialog_dismiss();

				if (mFlag == 0)
				{
					isSetVpdn = false;
					Intent intent = new Intent(LoginActivity.this,
							MenuGrid.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				} else if (mFlag == 3)
				{
					Log.e("Login", "Apn is not readly");
					showDialog(DIALOG_TIMEOUT);
				} else
				{
					if (udata == null)
					{

						showDialog(DIALOG_TIMEOUT);
					} else if (udata.code.equals("0003"))
					{

						showDialog(DIALOG_ERRINFO);
					} else
					{
						Log.e("EEE", "EEEEEE3==>" + udata.code);
						if (udata.equals("0008"))
						{
							File file = new File(STOREPATH);
							if (file.exists())
							{
								file.delete();
							}
						}
						showDialog(DIALOG_ERRLOGIN);
					}
				}
			}
		};

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		MyURL.phoneNum = tm.getLine1Number();   

		UserData data = getAcountInfo();
		if (data == null || data.code == null)
		{

			initLoginView();
		} else
		{
			setContentView(R.layout.welcome);
			showDialog(DIALOG_PROGRESS);
			oldInfoLogin();
		}

		this.haveFile();
	}

	private void pbarDialog_dismiss()
	{
		if(null!=pbarDialog)
		{
			pbarDialog.dismiss();
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{

			if(flag_Loading)
			{
				return  flag_Loading ;
			}else
			{
				exitThisSys();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initLoginView()
	{

		loginView = true;
		setContentView(R.layout.login_layout);
		userName = (EditText) this.findViewById(R.id.username);
		passWord = (EditText) this.findViewById(R.id.password);
		TextView lgpass = (TextView) this.findViewById(R.id.loginpass);
		lgpass.setText("密        码");
//		userName .setText("00003");
//		passWord .setText("123456");
		Button loginBtn = (Button) this.findViewById(R.id.login);
		loginBtn.setOnClickListener(loginListener);
		Button cancelBtn = (Button) this.findViewById(R.id.cancel);
		cancelBtn.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			
			{
				exitThisSys();
			}

		});

	}

	private void exitThisSys()
	{
		finish();
		NotificationManager notifnm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notifnm.cancel(R.layout.welcome);
		System.exit(0);
	}

	private OnClickListener loginListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			String user = userName.getText().toString();
			String pwd = passWord.getText().toString();
			if (user == null || user.equals("") || pwd == null
					|| pwd.equals(""))
			{

				showDialog(DIALOG_ERRINPUT);
			} else
			{

				InputMethodManager imm = (InputMethodManager)LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
				if(imm.isActive())
				{
					 imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);
					 imm.hideSoftInputFromWindow(passWord.getWindowToken(), 0);
				}
				setContentView(R.layout.welcome);
				showDialog(DIALOG_PROGRESS);
				inputInfoLogin();
				flag_Loading =true ;
			}

		}
	};
//验证第一步
	private void inputInfoLogin()
	{

		new Thread(new Runnable()
		{
			public void run()
			{
				initApp(); // 初始化程序

				try
				{

					String user = userName.getText().toString().trim();
					String pwd = passWord.getText().toString().trim();
					udata = dealLogin(user, pwd);
					Thread.sleep(20000);
					if (mFlag == 0)
					{
						String result = MyURL.webServerConnect(MyURL.LOGION_WEB_URL, "TestNet", 
								new Integer[]{1, 1}).toString();
						Log.e("TestNet", "result =" + result);
						if (MyURL.err == 1)
						{
							mFlag = 3;
						}
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}

				mProgressHandler.sendMessage(mProgressHandler.obtainMessage());
			}
		}).start();
	}

	private void oldInfoLogin()
	{

		new Thread(new Runnable()
		{
			public void run()
			{
				initApp(); // 初始化程序
				try
				{

					udata = dealLoginHasCode();
					Thread.sleep(20000);

				} catch (Exception e)
				{
					// TODO: handle exception
					e.printStackTrace();
				}
				if (mFlag == 0)
				{
					String result = MyURL.webServerConnect(MyURL.LOGION_WEB_URL, "TestNet", 
							new Integer[]{1, 1}).toString();
					Log.e("TestNet", "result =" + result);
					if (MyURL.err == 1)
					{
						mFlag = 3;
					}
				}
				mProgressHandler.sendMessage(mProgressHandler.obtainMessage());
			}
		}).start();
	}

	private void haveFile()
	{

		File file;
		file = new File(USERDATAPATH);
		try
		{

			if (!file.exists())
			{
				file.createNewFile();
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void initApp()
	{

	}

	/**
	 * 对话框与进度条的设置
	 */
	@Override
	protected Dialog onCreateDialog(int id)
	{

		switch (id)
		{

		case DIALOG_PROGRESS:
			pbarDialog = new ProgressDialog(this);
			pbarDialog.setMessage("登录中...");
			pbarDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			return pbarDialog;

		case DIALOG_ERRINFO:

			dialogInfo = "用户已绑定手机卡\n24小时内不可修改！";

			return createInfoDialog(DIALOG_ERRINFO);

		case DIALOG_ERRINPUT:

			dialogInfo = "用户名密码不能为空！";

			return createInfoDialog(DIALOG_ERRINPUT);

		case DIALOG_ERRLOGIN:

			dialogInfo = "用户认证失败！";

			return createInfoDialog(DIALOG_ERRLOGIN);
		case DIALOG_TIMEOUT:

			dialogInfo = "网络超时，链接失败！";

			return createInfoDialog(DIALOG_TIMEOUT);

		default:
			break;
		}

		return null;

	}

	private Dialog createInfoDialog(final int type)
	{

		return new AlertDialog.Builder(LoginActivity.this).setIcon(
				R.drawable.alert_dialog_icon).setMessage(dialogInfo)
				.setPositiveButton(R.string.btn_ok,
						new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog,
							int whichButton)
					{
						if (loginView)
						{
							initLoginView();
						} else if (type == DIALOG_TIMEOUT)
						{
							showDialog(DIALOG_PROGRESS);
							oldInfoLogin();
						} else
						{
							initLoginView();
						}
					}
				}).setNegativeButton(R.string.dialog_cancel,
						new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog,
							int whichButton)
					{

						NotificationManager notifnm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
						notifnm.cancel(R.layout.welcome);
						System.exit(0);

					}
				}).create();
	}

	// 登录先调用getAcountInfo 如果为空则没保存账号和密码，需要输入
	/*
	 * 手机用户登录 //测试账号hyh 密码123456 imei 123456789012345
	 * 
	 * @param userNo 用户帐号
	 * 
	 * @param pwd 密码
	 * 
	 * @return 根据 UserData.code 的值判断 1.账号存在返回:ok.8位流水号.16位校验码
	 * 2.如果流水号有误返回：err.8位流水号.0001 3.如果imei号有误返回：err.8位流水号.0002
	 * 4.其它错误：err.8位流水号.0004 5.密码错误：err.8位流水号.0005 6.未购买财智宝：err.8位流水号.0006
	 */
	//验证第二步
	public UserData dealLogin(String userNo, String pswd)
	{
		String serialNum;
		String imei;
		String logInStr;
		UserData data = new UserData();
		serialNum = getSerialNum();
		imei = getIMEI();
		data.pswd = pswd;
		data.userNo = userNo;
		data.serialNum = serialNum;
		mFlag = 1;
		logInStr = MyURL.webServerConnect(MyURL.LOGION_WEB_URL, "MobileUserLogIn",
				new String[]{serialNum, imei, userNo, pswd}).toString();
		Log.e("login===>>", "" + logInStr);
		mFlag = 2;
		
		if(MyURL.err == 1){
			mFlag = 3;
		}
		
		if (logInStr == null || logInStr.trim().equals(""))
		{
			data.code = "0004";
			return data;
		}
		String splitStr[] = logInStr.split("\\.");
		int strLen = splitStr.length;
		if (strLen >= 3)
		{
			if (splitStr[0].equals("ok"))
			{
				if ((strLen-1) == 3)
				{
					data.code = "00000";
				} else if (splitStr[1].equals(serialNum)) // 8位流水号
				{
					// 16位验证码
					if (splitStr[2].length() == 16)
					{
						data.code = splitStr[2].trim();
						storeAcountInfo(data);
						storePageInfo(splitStr[strLen -1]);
						
						mFlag = 0;// ******如果不存在的情况。
						Log.e("==autocode==", data.code);
						MyURL.UESER = "&telno=" + userName.getText();
						MyURL.SYSTEM = "&ssczbo=" + data.code;
						MyURL.usercd = userName.getText().toString();
						Log.e("Login", "OK");

					} else if (splitStr[2].length() == 1)
					{
						data.code = splitStr[2].trim();
					} else
					{
						data.code = "0004";
					}
				} else
				{
					data.code = "0004";
				}

				if(!isSetVpdn)
				{
					Apn.setApn(splitStr[3], getContentResolver());
					isSetVpdn = true;
				}
			} else if (splitStr[0].equals("err"))
			{
				if (splitStr[1].equals(serialNum)) // 8位流水号
				{
					data.code = splitStr[2].trim();
				} else
				{
					data.code = "0004";
				}
			} else
			{
				data.code = "0004";
			}
		}
		Log.e("aaa", "aaaaa");
		return data;
	}

	/*public String TestNet()
	{
		String result = null;
		String wsdlUrl = MyURL.LOGION_WEB_URL;

		try
		{

			SoapObject rpc = new SoapObject(
					"http://webservice.web.proj.oa.ztenc.com", "TestNet");
			rpc.addProperty("in0", 1);
			rpc.addProperty("in1", 1);

			HttpTransportSE ht = new HttpTransportSE(wsdlUrl);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.bodyOut = rpc;
			ht.debug = true;
			ht.call(null, envelope);

			result = envelope.getResponse().toString();

		} catch (Exception e)
		{
			// TODO: handle exception

			e.printStackTrace();
			return "0";
		}

		return result;
	}

	public String MobileUserLogIn(String serialNum, String IMEI, String userNo,
			String pswd)
	{
		String result = null;
		String wsdlUrl = MyURL.LOGION_WEB_URL;

		try
		{

			SoapObject rpc = new SoapObject(
					"http://webservice.web.proj.oa.ztenc.com",
			"MobileUserLogIn");
			rpc.addProperty("in0", serialNum);
			rpc.addProperty("in1", IMEI);
			rpc.addProperty("in2", userNo);
			rpc.addProperty("in3", pswd);

			Log.e("serialNum", serialNum);
			Log.e("IMEI", IMEI);
			Log.e("userNo", userNo);
			Log.e("pswd", pswd);

			HttpTransportSE ht = new HttpTransportSE(wsdlUrl);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.bodyOut = rpc;
			ht.debug = true;
			ht.call(null, envelope);

			result = envelope.getResponse().toString();

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;

	}*/

	public boolean storeAcountInfo(UserData data)
	{
		File file;
		FileOutputStream out;
		BufferedOutputStream bout;
		DataOutputStream dout;

		try
		{
			file = new File(STOREPATH);
			// if(!file.exists())
			{
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			bout = new BufferedOutputStream(out);
			dout = new DataOutputStream(out);

			dout.writeUTF(new String(Base64.encode(data.userNo.trim().getBytes())));
			dout.writeUTF(new String(Base64.encode(data.pswd.trim().getBytes())));
			dout.writeUTF(new String(Base64.encode(data.code.trim().getBytes())));

			dout.close();
			bout.close();
			out.close();

			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	private void storePageInfo(String pageInfo){
		
		String pagetype[] = pageInfo.split(",");
		
		int i=0;
		String ptypename[];
		String pagetal;
		int pagetotal;
		for(String ptype :  pagetype){
			ptypename = ptype.split(":");
			pagetal = ptypename[1];
			try {
				pagetotal = Integer.valueOf(pagetal);
			} catch (NumberFormatException e) {
				// TODO: handle exception
				pagetotal = 1;
			}
			
			MyURL.pageTotals[i] = pagetotal;
			++i;
		}
	}

	public UserData getAcountInfo()
	{

		File file;
		FileInputStream in;
		BufferedInputStream bin;
		DataInputStream din;
		UserData data = new UserData();
		try
		{

			Log.v("logtag", "getAcountInfo");
			file = new File(STOREPATH);
			if (!file.exists())
			{
				return null;
			} else
			{
				// 打开文件file的InputStream
				in = new FileInputStream(file);
				bin = new BufferedInputStream(in);
				din = new DataInputStream(bin);

				data.userNo = new String(Base64.decode(din.readUTF()
						.toCharArray()));
				data.pswd = new String(Base64.decode(din.readUTF()
						.toCharArray()));
				data.code = new String(Base64.decode(din.readUTF()
						.toCharArray()));

				bin.close();
				din.close();
				in.close();

			}
		} catch (IOException e)
		{
			// 将出错信息打印到Logcat
			e.printStackTrace();
		}
		return data;
	}
	public String getIMEI()
	{
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imis = tm.getSubscriberId();
		if(imis.length() == 6)
		{
			return tm.getDeviceId();
		}
		return imis;

	}

	// 获取8位随即流水号
	public String getSerialNum()
	{
		String serialNum = new String();
		Random ra = new Random();
		for (int i = 0; i < 8; i++)
		{
			serialNum += ra.nextInt(10);
		}
		return serialNum;
	}

	/*public String hasCodeLogin(String serialNum, String IMEI, String userNo,
			String code)
	{
		String result = null;
		String wsdlUrl = MyURL.LOGION_WEB_URL;
		try
		{

			SoapObject rpc = new SoapObject(
					"http://webservice.web.proj.oa.ztenc.com", "hasCodeLogin");
			rpc.addProperty("in0", serialNum);
			rpc.addProperty("in1", IMEI);
			rpc.addProperty("in2", userNo);
			rpc.addProperty("in3", code);

			Log.e("serialNum", serialNum);
			Log.e("IMEI", IMEI);
			Log.e("userNo", userNo);
			Log.e("code", code);

			HttpTransportSE ht = new HttpTransportSE(wsdlUrl);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.bodyOut = rpc;
			ht.debug = true;
			ht.call(null, envelope);

			result = envelope.getResponse().toString();

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;

	}*/

	/*
	 * 手机用户本地有校验码情况登录
	 * 
	 * @return 根据 UserData.code 的值判断 1.账号不存在返回：0 2.本地存有此账号并与IMEI号匹配：allow
	 * 3.绑定时间超过24小时,提示用户输入认证信息：putin 4.如果流水号有误返回：0001 5.如果imei号有误返回：0002
	 * 6.如果24小时内换卡重新绑定账号：0003 7.其他未知错误:0004
	 */
	public UserData dealLoginHasCode()
	{
		String serialNum;
		String imei;
		String logInStr;
		mFlag = 1;
		UserData data = getAcountInfo();
		serialNum = getSerialNum();

		imei = getIMEI();// "123456789012345";

//		logInStr = hasCodeLogin(serialNum, imei, data.userNo, data.code);
		logInStr = MyURL.webServerConnect(MyURL.LOGION_WEB_URL, "hasCodeLogin",
				new String[]{serialNum, imei, data.userNo, data.code}).toString();
		
		Log.e("HasCode==logInStr:", "" + logInStr);
		mFlag = 2;
		
		if (MyURL.err == 1)
		{
			mFlag = 3;
		}
		
		if (logInStr == null || logInStr.trim().equals(""))
		{
			return null;
		}
		String splitStr[] = logInStr.split("\\.");
		int strLen = splitStr.length;
		if (strLen >= 3)
		{
			if (splitStr[0].equals("ok"))
			{
				if ((strLen-1) == 3)
				{
					data.code = "0000";
				} else if (splitStr[1].equals(serialNum)) // 8位流水号
				{
					data.code = splitStr[2].trim();
					Log.e("==autocode==", data.code);
					if (data.code.equals("allow"))
					{
						mFlag = 0; // 0登录成功 1登录中 2失败***********本地存有用户名和验证码
						
						storePageInfo(splitStr[strLen-1]);
						MyURL.UESER = "&telno=" + getAcountInfo().userNo;
						MyURL.SYSTEM = "&ssczbo=" + getAcountInfo().code;
						MyURL.usercd = getAcountInfo().userNo;
					}

				} else
				{
					data.code = "0004";
				}
				if(!isSetVpdn)
				{
					Apn.setApn(splitStr[3], getContentResolver());
					isSetVpdn = true;
				}
			} else if (splitStr[0].equals("err"))
			{
				if (splitStr[1].equals(serialNum)) // 8位流水号
				{
					data.code = splitStr[2].trim();
				} else
				{
					data.code = "0004";
				}
			} else
			{
				data.code = "0004";
			}
		}
		return data;
	}
}

class UserData
{
	// 用户名
	public String userNo;
	// 密码
	public String pswd;

	// 登录成功:16位验证码 ;无手机号码:0;流水号有误:0001;
	// imei有误0002;24小时内不能换卡0003;其他错误0004
	public String code;
	// 流水号
	public String serialNum;
}
