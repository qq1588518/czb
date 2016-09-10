package com.oms.zte.updatesoft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.oms.zte.MyURL;
import com.oms.zte.Params;
import com.oms.zte.R;
public class UpdateSoftActivity extends Activity {

	ProgressBar pb;
	TextView tv;
	int   fileSize;
	int   downLoadFileSize;
	String fileEx,fileNa,filename, downUrl=null;
	
	boolean isRunning = true;
	boolean flag_APK =false ;
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (!Thread.currentThread().isInterrupted())
			{
				switch (msg.what)
				{
				case 0:
					pb.setMax(fileSize);
				case 1:
					pb.setProgress(downLoadFileSize);
					int result = downLoadFileSize * 100 / fileSize;
					tv.setText(result + "%");
					break;
				case 2:
					Toast.makeText(UpdateSoftActivity.this, "文件下载完成", 1).show();
					
					File file = new File(MyURL.savePath+filename);
					
					if(file.isFile()){
						setupSoft(file);
					}
					break;
				case 3:
					UpdateSoftActivity.this.finish();
					break;
				case -1:
					String error = msg.getData().getString("error");
					Toast.makeText(UpdateSoftActivity.this, error, 1).show();
					break;
				}
			}
			super.handleMessage(msg);
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String sd =Environment.getExternalStorageState();
		if(!sd.equals(Environment.MEDIA_MOUNTED))
        {
        	Toast.makeText(this, "SD卡不存在，无法下载 ", Toast.LENGTH_SHORT).show();
        	onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(0, 4));
        	return;
        }
		setRequestedOrientation(Params.orientationControl);
		setContentView(R.layout.update_soft);
		pb = (ProgressBar)findViewById(R.id.down_pb);
		tv = (TextView)findViewById(R.id.down_tv);
		downUrl = this.getIntent().getStringExtra("downUrl");
		if(downUrl==null||downUrl.endsWith(".apk"))
		{
			flag_APK =true ;
		}
		new Thread(){
			public void run(){
				try {
					if(downUrl == null || downUrl.equals(""))
					{
						downFile(MyURL.url(MyURL.UPDATA_ACTION), MyURL.savePath);
					}
					else
					{
						downFile(downUrl, MyURL.savePath);
					}
				} 
				catch (ClientProtocolException e) 
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
	public void downFile(String url,String path) throws IOException{
		
			File tmpFile = new File(path);
			if (!tmpFile.exists()) {
				tmpFile.mkdirs();
			}
			filename = url.substring(url.lastIndexOf("/") + 1);
			File f  = new File(path+filename);
			if(!f.exists())
			{
				f.createNewFile();
			}
			URL myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			this.fileSize = conn.getContentLength();//根据响应获取文件大小
			if (this.fileSize <= 0) throw new RuntimeException("无法获知文件大小 ");
			if (is == null) throw new RuntimeException("stream is null");
			FileOutputStream fos = new FileOutputStream(path+filename);
			byte buf[] = new byte[1024];
			downLoadFileSize = 0;
			sendMsg(0);
			do
			{
				int numread = is.read(buf);
				if (numread == -1)
				{
					break;
				}
				fos.write(buf, 0, numread);
				downLoadFileSize += numread;
				sendMsg(1);
			} while (isRunning);
			
			if(isRunning)
			{
				sendMsg(2);
			}else
			{
				sendMsg(3);
			}
			try
			{
				is.close();
			}
			catch (Exception ex)
			{
				Log.e("tag", "error: " + ex.getMessage(), ex);
			}

	}
	private void sendMsg(int flag)
	{
		Message msg = new Message();
		msg.what = flag;
		handler.sendMessage(msg);
	}	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		super.onConfigurationChanged(newConfig);
	}
	private void setupSoft(File file){
		
		if(flag_APK)
		{
			startActivity(MyURL.openFile(file));
		}else
		{
			openFile(file) ;
		}
		this.finish();
	}
	private void openFile(File f) 
	{
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		String type = getMIMEType(f);
		intent.setDataAndType(Uri.fromFile(f),type);
		startActivity(intent); 
	}
	private String getMIMEType(File f)
	{
		String type="";
		String fName=f.getName();
		String end=fName.substring(fName.lastIndexOf(".")+1,
				fName.length()).toLowerCase(); 

		if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")
				||end.equals("xmf")||end.equals("ogg")||end.equals("wav"))
		{
			type = "audio"; 
		}
		else if(end.equals("3gp")||end.equals("mp4"))
		{
			type = "video";
		}
		else if(end.equals("jpg")||end.equals("gif")||end.equals("png")
				||end.equals("jpeg")||end.equals("bmp"))
		{
			type = "image";
		}
		else
		{
			type="*";
		}
		type += "/*"; 
		return type; 
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			isRunning = false;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onResume() {
		super.onResume();
		setRequestedOrientation(Params.orientationControl);
	}
}
