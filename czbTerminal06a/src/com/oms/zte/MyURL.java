package com.oms.zte;

import java.io.File;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class MyURL {
	 public static final String IP= "http://czbnew.czton.cn:8000";
//	public static final String IP= "http://mob.nbu.edu.cn:8080";
	
	public static final int ITEM_COUNT = 11 ;
	
//	public static final String UPDATA_URL = "http://mob.nbu.edu.cn:8080/managessWebservice/";
	public static final String UPDATA_URL = "http://czbnew.czton.cn:8000/managessWebservice/";
	
	 public static final String LOGION_WEB_URL = "http://czbnew.czton.cn:8000/managessWebservice/service/managess.ws";
//	public static final String LOGION_WEB_URL = "http://mob.nbu.edu.cn:8080/managessWebservice/service/managess.ws";
	
	 public static final String UPDATA_WEB_URL = "http://czbnew.czton.cn:8000/managessWebservice/service/getversion.ws?ws";
//	public static final String UPDATA_WEB_URL = "http://mob.nbu.edu.cn:8080/managessWebservice/service/getversion.ws?ws";
	
	public static final String STOREPATH = "//sdcard//userInfo.dat";
	
	public static final String savePath = "//sdcard/MyCollections/Others/";
	
	public static final int UPDATA_ACTION = 1;
	
	public static final int PHONE_ACTION = 1017;
	
	public static final int pageItem = 10;
	
	public static String phoneNum;// 电话号码
	
	public static String usercd;// 8位流水号码
	
	public static String UESER;// 用户帐号
	
	public static String SYSTEM;// 系统验证码
	
	public static String version = "20111015";
	
	public static String updataVersion = "";
	
	public static int err = 0;
	
	/*
	 * getNews:公司新闻 0,getProductNews:产品资讯 1, getRencentNews :最近公告 2, getHotSale:热点促销 3,NewsInfoActivity
	 * 
	 * goodFood:营养保健品 4,selfProtect:个人护理 5,dressProduct:化妆品 6, otherProduct:辅助用品 7, ViewToolsProtuctActivity
	 * 
	 * coursewareDown:课件下载 8,productQuest::软件下载 9, ViewToolsLessonDownActivity
	 * 
	 * healthQuest:健康问答 10,workQuest:事业问答 11, productQuestion:产品问答 12,ViewToolsWorkQuestActivity
	 * 
	 * workIntroduce:事业介绍 13,ViewToolsWorkIntrudceActivity companyCulture:企业文化 14,ViewToolsCultureActivity gameDown:游戏下载 15, GameDownActivity
	 */
	public static int pageTotals[] = new int[16];
	
	public static String getVersion() {
		return version;
	}
	
	public static Object webServerConnect(String url, String method, Object[] params) {
		Object result = null;
		String wsdlUrl = url;
		Log.e("wsdlUrl", wsdlUrl);
		try {
			err = 0;
			
			SoapObject rpc = new SoapObject("http://webservice.web.proj.oa.ztenc.com", method);
			int size = params.length;
			for (int i = 0; i < size; i++) {
				
				rpc.addProperty("in" + i, params[i]);
			}
			
			HttpTransportSE ht = new HttpTransportSE(wsdlUrl);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			
			envelope.bodyOut = rpc;
			ht.debug = true;
			ht.call(null, envelope);
			result = envelope.getResponse();
			
		} catch (Exception e) {
			err = 1;
			result = "";
			// TODO: handle exception
			Log.e("version", e.toString());
		}
		
		return result;
	}
	
	public static String url(int action) {
		switch (action) {
			case UPDATA_ACTION: {
				Log.e("url:", UPDATA_URL + "SSCZBN" + updataVersion + ".apk");
				return UPDATA_URL + "SSCZBN" + updataVersion + ".apk";
			}
			default:
				break;
		}
		return "";
	}
	
	// 打开APK程序代码
	
	public static Intent openFile(File file) {
		Log.e("OpenFile", file.getName());
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		return intent;
	}
	
}
