package com.oms.zte;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class Apn
{

	private static Uri createApnUri = Uri.parse("content://telephony/carriers");
	private static Uri preferapnUri = Uri.parse("content://telephony/carriers/preferapn");

	public static boolean isVpdnUser = false;
	private static String VPDN = "nbss.zjapn";
	private static String DefaultVpdn = "3gnet";//"中国联通3g网络";"中国移动GPRS";

	public static String name = "nbss.zjapn";
	public static String apn = "nbss.zjapn";
	public static String proxy = "";
	public static String port = "";
	public static String user = "";
	public static String password = "";
	public static String server = "";
	public static String mmsc = "";
	public static String mmsproxy = "";
	public static String mmsport = "";
	public static String mcc = "460";
	public static String mnc = "01";
	public static String numeric = "46001";//移动是46000 联通是46001
	public static String authenticationType = "";
	public static String apnType = "default,supl";

	public static String OpenAPN = "1";
	public static String CloseAPN = "0";

	public static boolean isExist(ContentResolver contentResolver)
	{
		Cursor c = contentResolver.query(createApnUri, null, null,
				null, null);

		if (c != null)
		{
			int rowCount = c.getCount();
			int colCount = c.getColumnCount();
			c.moveToFirst();
			for (int i = 0; i < rowCount; i++)
			{
				for (int j = 0; j < colCount; j++)
				{
					//		    Log.e("apn", c.getColumnName(j) + "|||" + c.getString(j));
					if (c.getColumnName(j).equals("name")
							&& c.getString(j).equals(VPDN))
					{
						c.close();
						return true;
					}
				}
				c.moveToNext();
			}
			c.close();
		}
		return false;
	}

	public static int getApnId(ContentResolver contentResolver,String vpdnName)
	{

		int id = -1;
		Cursor c = contentResolver.query(createApnUri, null, null, null, null);

		if (c != null)
		{
			int rowCount = c.getCount();
			int colCount = c.getColumnCount();
			c.moveToFirst();
			for (int i = 0; i < rowCount; i++)
			{
				for (int j = 0; j < colCount; j++)
				{
					//		    Log.e("apn", c.getColumnName(j) + "|||" + c.getString(j));
					if(c.getColumnName(j).equals("apn")&&c.getString(j).equals(vpdnName))
					{
						//			Log.e("CZB id:", "" + c.getShort(c.getColumnIndex("_id")));
						return c.getShort(c.getColumnIndex("_id"));
					}
				}
				c.moveToNext();
			}
			c.close();
		}
		return id;
	}

	public static void createApn(ContentResolver contentResolver)
	{
		ContentValues values = new ContentValues();

		values.put("name", VPDN);
		values.put("apn", "nbss.zjapn");
		//	values.put("proxy", "");
		//	values.put("port", "");


		values.put("mcc", "460");
		values.put("mnc", "01");
		values.put("numeric", "46001");
		values.put("type", "default,supl");
		//	values.put("authenticationType", authenticationType);
		//	values.put("type", apnType);


		/****
		 * 　　　　　 * 在真机上使用 　　　　　
		 ****/
		// values.put("mcc", "460");
		// values.put("mnc", "01");
		// values.put("numeric", "46001");
		// values.put("type", "default,supl");
		// type|||default,supl
		/****
		 * 模拟器上使用
		 */
		//	values.put("mcc", "310");
		//	values.put("mnc", "260");
		//	values.put("numeric", "310260");

		Uri iuri = contentResolver.insert(createApnUri, values);
	}

	public static void setDefaultApn(ContentResolver contentResolver, int id)
	{
		ContentResolver cr = contentResolver;
		ContentValues cv = new ContentValues();
		cv.put("apn_id", id);

		try
		{
			// cr.delete(preferapnUri, cv., selectionArgs);
			int i = cr.update(preferapnUri, cv, null, null);


		} catch (Exception e)
		{
			Log.e("error", e.toString());
		}
	}

	public static void setApn(String isOn,ContentResolver contentResolver)
	{


		if(isOn.equals(OpenAPN))
		{

			Log.e("OpenAPN", "create APN");
			isVpdnUser = true;
			if(!isExist(contentResolver))
			{
				Log.e("OpenAPN", "open APN");

				createApn(contentResolver);
				setDefaultApn(contentResolver,getApnId(contentResolver,VPDN));
			}
			else
			{
				setDefaultApn(contentResolver,getApnId(contentResolver,VPDN));
				Log.e("APN has exist", "***********");
			}
		}
		else if(isOn.equals(CloseAPN))
		{
			isVpdnUser = false;
			Log.e("CloseAPN", "close apn");
			setDefaultApn(contentResolver,getApnId(contentResolver,DefaultVpdn));
		}
	}


}
