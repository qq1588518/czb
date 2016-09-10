package com.oms.zte;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FileManage extends ListActivity   {//implements OnItemLongClickListener

	private List<String> items=null;
	private List<String> paths=null;
	private String rootPath="/sdcard";
	private TextView mPath;
	private AlertDialog menuDialog;
	private File file ;
	private MyAdapter adapter = null;

	protected void onCreate(Bundle icicle) {

		super.onCreate(icicle);
		setRequestedOrientation(Params.orientationControl);
		setContentView(R.layout.file_main);
		ImageButton returns = (ImageButton) this.findViewById(R.id.returns);
		returns.setOnClickListener(goBack);
		mPath=(TextView)findViewById(R.id.mPath);
		String sd =Environment.getExternalStorageState();
        if(sd.equals(Environment.MEDIA_MOUNTED))
        {
        	getFileDir(rootPath);
        	init_Dialog();
        	
        }else
        {
        	Toast.makeText(this, "SD卡不存在", Toast.LENGTH_SHORT).show();
        	onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(0, 4));
        }
	}

	private void getFileDir(String filePath)
	{
		mPath.setText(filePath);
		items=new ArrayList<String>();
		paths=new ArrayList<String>();
		File f=new File(filePath);  
		File[] files=f.listFiles();

		if(!filePath.equals(rootPath))
		{
			items.add("b1");
			paths.add(rootPath);
			items.add("b2");
			paths.add(f.getParent());
		}
		for(int i=0;i<files.length;i++)
		{
			File file=files[i];
			items.add(file.getName());
			paths.add(file.getPath());
		}
		adapter = new MyAdapter(this,items,paths);
		setListAdapter(adapter);
	}
	protected void onListItemClick(ListView l,View v,int position,
			long id)
	{
		file=new File(paths.get(position));
		if (file.isDirectory())
		{
			getFileDir(paths.get(position));
		}
		else
		{
			menuDialog.show();
		}
	}

	private void init_Dialog()
	{
		int menu_image_array[] = 
		{
				R.drawable.menu_filemanager, R.drawable.menu_downmanager,R.drawable.menu_quit
		};
		String menu_name_array[] =
		{
				 "打开文件","重新命名", "删除文件"
		};
		View menuView = View.inflate(this,R.layout.gridview_menu,null);
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
		GridView  menuGrid = (GridView)menuView.findViewById(R.id.gridview);
		menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
		menuGrid.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?>  arg0, View arg1, int arg2, long arg3)
			{
				
				switch (arg2)
				{
				case 0:
					openFile(file);
					menuDialog.dismiss();
					break;
				case 2: 
					deleteFile(file);
					menuDialog.dismiss();
					break;
				case 1: 
					String str =file.toString();
					int sub=str.lastIndexOf("/");
					final String path =str.substring(0,sub+1);
					String name =str.substring(sub + 1);
					android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(FileManage.this);
					LayoutInflater inflater = (LayoutInflater)getSystemService("layout_inflater");
					LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.rename, null);
					dialog.setTitle("请重新输入文件名");
					dialog.setView(layout);
					final EditText edit = (EditText)layout.findViewById(R.id.filename);
					edit.setText(name);
					dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which)
						{
							String newFileNames =edit.getText().toString();
							
							if (newFileNames == null || "".equalsIgnoreCase(newFileNames))
							{
								(new android.app.AlertDialog.Builder(FileManage.this)).setTitle("提示：").setMessage("文件名不能为空，请重新输入！").setPositiveButton("确定", new android.content.DialogInterface.OnClickListener()  {
									public void onClick(DialogInterface dialoginterface1, int j)
									{
									}
						
								}).show();
								return;
							}
							
							for (int i = 0; i < items.size(); i++)
							{
								if (newFileNames.equalsIgnoreCase(items.get(i)))
								{
									(new android.app.AlertDialog.Builder(FileManage.this)).setTitle("提示：").setMessage("文件名已存在，请重新命名！").setPositiveButton("确定", new android.content.DialogInterface.OnClickListener()  {

										public void onClick(DialogInterface dialoginterface1, int j)
										{
											
										}
									}).show();
									return;
								}
							}
							File file_new  = new File(path+newFileNames);
							int location_a =items.indexOf(file.getName());
							int location_b =paths.indexOf(file.getPath());
							if(file.renameTo(file_new))
							{
								items.set(location_a, file_new.getName());
								paths.set(location_b, file_new.getPath());
								adapter.notifyDataSetChanged();
							}
						}

					});
					
					dialog.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {


						public void onClick(DialogInterface dialoginterface, int j)
						{
						}
					});
					dialog.show();
					menuDialog.dismiss();
					break;
				}
			}
		});
	}
	
	private SimpleAdapter getMenuAdapter(String menuNameArray[], int imageResourceArray[])
	{
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++)
		{
			HashMap<String,Object> map = new HashMap<String,Object> ();
			map.put("itemImage", Integer.valueOf(imageResourceArray[i]));
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data, R.layout.item_menu, 
				new String[] {"itemImage", "itemText"}, 
				new int[] {R.id.item_image, R.id.item_text});
		return simperAdapter;
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
	private void deleteFile(File f)
	{
		if(f.exists())
		{
			f.delete();
			items.remove(file.getName());
			paths.remove(file.getPath());
			adapter.notifyDataSetChanged();
		}
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


	private OnClickListener goBack = new OnClickListener()

	{
		public void onClick(View v)
		{		
			onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(0, 4));
		}
	};



}