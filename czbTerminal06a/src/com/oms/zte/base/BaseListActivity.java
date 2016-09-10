package com.oms.zte.base;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oms.zte.Apn;
import com.oms.zte.Params;
import com.oms.zte.R;


public class BaseListActivity extends ListActivity
{
	private Integer imgDatas[];
	private Integer ntextDatas[];
	private Integer stextDatas[];
	private Integer backImg;
	protected final int BASE_DIALOG_PROGRESS = 1;
	protected final int BASE_DIALOG_INFO = 2;
	protected final int SYSTEMEXIT = 3;
	protected final int MAINEXIT = 4;
	private static ProgressDialog pbarDialog;
	
	private String dialogMesg="页面加载中..";
	protected Dialog onCreateDialog(int id)
	{
		switch (id)
		{
		case 0: 
			return 
			(new android.app.AlertDialog.Builder(this)).setIcon(R.drawable.alert_dialog_icon).setTitle(R.string.dialog_title).setNegativeButton(R.string.dialog_cancel, new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialoginterface, int i)
				{
				}
			}).create();

		case 1:
			pbarDialog = new ProgressDialog(this);
			pbarDialog.setMessage(dialogMesg);
			pbarDialog.setCancelable(false);
			pbarDialog.setProgressStyle(0);
			return pbarDialog;

		case 2: 
			return 
			(new android.app.AlertDialog.Builder(this)).setIcon(R.drawable.alert_dialog_icon ).setTitle(dialogMesg).setNegativeButton(R.string.dialog_cancel, new android.content.DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialoginterface, int i)
				{
				}
			
			}).create();

		case 3: 
			return 
			(new android.app.AlertDialog.Builder(this)).setIcon(R.drawable.alert_dialog_icon).setTitle(R.string.dialog_cancel).setMessage(R.string.dialog_sysexit).setPositiveButton(R.string.btn_ok, new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton)
				{
					Params.IsExitApp = true;
					Params.SetMainView = false;
					Apn.setApn(Apn.CloseAPN, getContentResolver());
					onKeyDown(4, new KeyEvent(0, 4));
				}
			
			}).setNegativeButton(R.string.btn_cancel, new android.content.DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialoginterface, int i)
				{
				}
			}).create();

		case 4: 
			return (new android.app.AlertDialog.Builder(this)).setIcon(R.drawable.alert_dialog_icon).setTitle(R.string.dialog_cancel).setMessage(R.string.dialog_sysexit).setPositiveButton(R.string.btn_ok, new android.content.DialogInterface.OnClickListener() {


				public void onClick(DialogInterface dialog, int whichButton)
				{
					finish();
					NotificationManager notifnm = (NotificationManager)getSystemService("notification");
					notifnm.cancel(R.layout.welcome);
					Apn.setApn(Apn.CloseAPN, getContentResolver());
					System.exit(0);
				}
			
			}).setNegativeButton(R.string.btn_cancel, new android.content.DialogInterface.OnClickListener() {


				public void onClick(DialogInterface dialoginterface, int i)
				{
				}
			
			}).create();

		case 5: 
			return (new android.app.AlertDialog.Builder(this)).setIcon(R.drawable.alert_dialog_icon).setTitle(R.string.dialog_error).setNegativeButton(R.string.btn_cancel, new android.content.DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialoginterface, int i)
				{
				}
			}).create();
		}
		return null;
	}

	protected void onRestart()
	{
		super.onRestart();
		if (Params.SetMainView || Params.IsExitApp)
			finish();
	}

	protected void onMenuListRestart()
	{
		super.onRestart();
		if (Params.IsExitApp)
		{
			finish();
			NotificationManager notifnm = (NotificationManager)getSystemService("notification");
			notifnm.cancel(R.layout.welcome);
			System.exit(0);
		} else
		{
			Params.SetMainView = false;
		}
	}

	public void set_DialogMesg(String info)
	{
		this.dialogMesg = info;
	}
	public void showInfoDialog(int id, String info)
	{
		try
		{
			this.removeDialog(id);
			dialogMesg = info;
			showDialog(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void dissPrssDialog()
	{
		try
		{
			if (pbarDialog != null)
			{
				pbarDialog.dismiss();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected EfficientAdapter getAdapterClass(Integer backImg,Context context)
	{
		this.backImg = backImg;
		EfficientAdapter aper = new EfficientAdapter(context);
		return aper;
	}

	public Integer[] getImgDatas()
	{
		return imgDatas;
	}

	public void setImgDatas(Integer imgDatas[])
	{
		this.imgDatas = imgDatas;
	}

	public Integer[] getNtextDatas()
	{
		return ntextDatas;
	}

	public void setNtextDatas(Integer ntextDatas[])
	{
		this.ntextDatas = ntextDatas;
	}

	public Integer[] getStextDatas()
	{
		return stextDatas;
	}

	public void setStextDatas(Integer stextDatas[])
	{
		this.stextDatas = stextDatas;
	}

	public void imgBackListDo(int i)
	{
	}

	public void startActivity(Intent intent, String url)
	{
		intent.putExtra("StrPath", url);
		intent.setClass(this, com.oms.zte.news.NewsInfoActivity.class);
		startActivity(intent);
	}
	
	class ViewHolder
	{
		ImageView imgFront;
		TextView northText;
		TextView southText;
		ImageView imgBack;

	}
	private class EfficientAdapter extends BaseAdapter
	{
		private LayoutInflater mInflater;
		private Bitmap backIcon;
		private ViewHolder holder;
		
		public EfficientAdapter(Context context)
		{
			mInflater = LayoutInflater.from(context);
			if (backImg != null && backImg.intValue() != 0)
				backIcon = BitmapFactory.decodeResource(context.getResources(), backImg.intValue());
		}

		public int getCount()
		{
			return imgDatas.length;
		}

		public Object getItem(int position)
		{
			return Integer.valueOf(position);
		}

		public long getItemId(int position)
		{
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			
			this.holder = null;
			if (convertView == null)
			{
				convertView = mInflater.inflate(R.layout.list_item_icon_text, null);
				holder = new ViewHolder();
				holder.imgFront = (ImageView)convertView.findViewById(R.id.item_front);
				holder.northText = (TextView)convertView.findViewById(R.id.item_text);
				holder.southText = (TextView)convertView.findViewById(R.id.item_text_2);
				holder.imgBack = (ImageView)convertView.findViewById(R.id.item_back);
				convertView.setTag(holder);
			} 
			else
			{
				holder = (ViewHolder)convertView.getTag();
			}
			holder.northText.setText(ntextDatas[position].intValue());
			holder.southText.setText(stextDatas[position].intValue());
			holder.imgFront.setImageResource(imgDatas[position].intValue());
			if (backIcon != null)
			{
				holder.imgBack.setImageBitmap(backIcon);
			}
			return convertView;
		}

	}


}
