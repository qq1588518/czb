
package com.oms.zte.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;

import com.oms.zte.Apn;
import com.oms.zte.Params;
import com.oms.zte.R;


public class BaseAdapterActivity extends Activity
{
	protected final int BASE_DIALOG_PROGRESS = 1;
	protected final int BASE_DIALOG_INFO = 2;
	protected final int SYSTEM_EXIT = 3;
	protected final int MAIN_EXIT = 4;
	private ProgressDialog pbarDialog;
	private static DialogButton d_p_button = new DialogButton();
	private static DialogButton d_n_button = new DialogButton();
	private static DialogTitle d_title = new DialogTitle();
	
	public String dialogMesg ="页面加载中..";

	protected void onRestart()
	{
		super.onRestart();
		if (Params.SetMainView || Params.IsExitApp)
			finish();
	}

	protected void onMenuGridRestart()
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

	private Dialog initDialog(DialogTitle d_title, DialogButton d_button)
	{
		return (new android.app.AlertDialog.Builder(this)).setIcon(d_title.icon).setTitle(d_title.title).setNegativeButton(d_button.id, d_button.listener).create();
	}

	private Dialog initDialog(DialogTitle d_title, String message, DialogButton d_p_button, DialogButton d_n_button)
	{
		return (new android.app.AlertDialog.Builder(this)).setIcon(d_title.icon).setTitle(d_title.title).setPositiveButton(d_p_button.id, d_p_button.listener).setNegativeButton(d_n_button.id, d_n_button.listener).create();
	}

	protected Dialog onCreateDialog(int id) 
	{
		switch (id)
		{
		case 0: 
			d_title.set(R.drawable.alert_dialog_icon, getString(R.string.dialog_title));
			d_n_button.set(R.string.dialog_cancel, new NullButtonListener());
			return initDialog(d_title, d_n_button);

		case 2: 
			d_title.set(R.drawable.alert_dialog_icon , dialogMesg);
			d_n_button.set(R.string.dialog_cancel, new NullButtonListener());
			return initDialog(d_title, d_n_button);

		case 1: 
			pbarDialog = new ProgressDialog(this);
			pbarDialog.setProgress(0);
			pbarDialog.setMessage(dialogMesg);
			pbarDialog.setCancelable(true);
			pbarDialog.setProgressStyle(0);
			return pbarDialog;

		case 3:
			d_title.set(R.drawable.alert_dialog_icon, getString(R.string.dialog_cancel));
			d_p_button.set(R.string.btn_ok, new android.content.DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int whichButton)
				{
					Params.IsExitApp = true;
					Params.SetMainView = false;
					Apn.setApn(Apn.CloseAPN, getContentResolver());
					onKeyDown(4, new KeyEvent(0, 4));
				}
			
			});
			d_n_button.set(R.string.btn_cancel, new NullButtonListener());
			return initDialog(d_title, getString(R.string.dialog_sysexit), d_p_button, d_n_button);

		case 4: 
			d_title.set(R.drawable.alert_dialog_icon, getString(R.string.dialog_cancel));
			d_p_button.set(R.string.btn_ok,new android.content.DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int whichButton)
				{
					finish();
					NotificationManager notifnm = (NotificationManager)getSystemService("notification");
					notifnm.cancel(R.layout.welcome);
					Apn.setApn(Apn.CloseAPN, getContentResolver());
					System.exit(0);
				}
			});
			d_n_button.set(R.string.btn_cancel, new NullButtonListener());
			return initDialog(d_title, getString(R.string.dialog_sysexit), d_p_button, d_n_button);

		case 5: 
			d_title.set(R.drawable.alert_dialog_icon, getString(R.string.dialog_error));
			d_n_button.set(R.string.dialog_cancel, new NullButtonListener());
			return initDialog(d_title, d_n_button);
		}
		return null;
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

	public void startActivity(Intent intent, String url)
	{
		intent.putExtra("StrPath", url);
		intent.setClass(this, com.oms.zte.viewtools.ViewToolsOnLineServiceActivity.class);
		startActivity(intent);
	}
	
	private static class DialogButton
	{
		int id;
		DialogInterface.OnClickListener listener;
		public void set(int id, android.content.DialogInterface.OnClickListener listener)
		{
			this.id = id;
			this.listener = listener;
		}

	}
	private static class DialogTitle
	{

		int icon;
		String title;
		public void set(int icon, String title)
		{
			this.icon = icon;
			this.title = title;
		}
	}

	private class NullButtonListener implements android.content.DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface dialoginterface, int i)
		{
		}
	}


}
