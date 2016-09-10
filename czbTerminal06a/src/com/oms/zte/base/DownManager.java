package com.oms.zte.base;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.oms.zte.R;


public class DownManager extends BaseAdapterActivity implements android.view.View.OnClickListener
{

	private ListView list = null;
	private	TextView fileCount =null;
	private	GridView menuGrid;
	private	View menuView;
	static ImageButton deleteFile = null;
	private ImageButton returns = null;
	private	EditText EditText;
	
	private	File files;
	private	ArrayList<HashMap<String,Object>> listItem;
	private AlertDialog menuDialog;
	
	private	int item;
	private	String fileName;
	private	String newFileName;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		String sd =Environment.getExternalStorageState();
		if(!sd.equals(Environment.MEDIA_MOUNTED))
        {
        	Toast.makeText(this, "SD卡不存在", Toast.LENGTH_SHORT).show();
        	onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(0, 4));
        	return;
        }
		setContentView(R.layout.down_manager_layer);
		deleteFile = (ImageButton)findViewById(R.id.delete);
		returns= (ImageButton)findViewById(R.id.returns);
		returns.setOnClickListener(this);
		int menu_image_array[] = {
				R.drawable.menu_edit, R.drawable.menu_delete, R.drawable.menu_downmanager
		};
		String menu_name_array[] = {
				"重新命名", "删除文件", "打开文件"
			};
		list = (ListView)findViewById(R.id.ListView);
		listItem = new ArrayList<HashMap<String,Object>>();
		String Filename[] = getFileName();
		if(null==Filename)
		{
			Toast.makeText(this, "没有下载文件", Toast.LENGTH_SHORT).show();
			deleteFile.setVisibility(View.INVISIBLE);
			return;
		}
		deleteFile.setOnClickListener(this);
		for (int i = 0; i < Filename.length; i++)
		{
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("ItemImage", Integer.valueOf(R.drawable.file));
			map.put("ItemfileName", Filename[i]);
			listItem.add(map);
		}

		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem, R.layout.down_list_layer, 
				new String[] {"ItemImage", "ItemfileName"}, 
				new int[] {R.id.fileimage, R.id.filename
		});
		list.setAdapter(listItemAdapter);
		list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				item = arg2;
				menuDialog.show();
			}
		});
		menuView = View.inflate(this, R.layout.gridview_menu, null);
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
		
		menuGrid.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				
				switch (arg2)
				{
				default:
					break;

				case 0: 
					fileName = filNameMatch();
					android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(DownManager.this);
					LayoutInflater inflater = (LayoutInflater)getSystemService("layout_inflater");
					LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.rename, null);
					dialog.setTitle("请重新输入文件名");
					dialog.setView(layout);
					DownManager.this.EditText = (EditText)layout.findViewById(R.id.filename);
					DownManager.this.EditText.setText(fileName);
					dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which)
						{
							String newFileNames = DownManager.this.EditText.getText().toString();
							System.out.println((new StringBuilder("user  ")).append(newFileNames).toString());
							newFileName = newFileNames;
							System.out.println((new StringBuilder("newFileName  ")).append(newFileName).toString());
							if (newFileName == null || "".equalsIgnoreCase(newFileName))
							{
								(new android.app.AlertDialog.Builder(DownManager.this)).setTitle("提示：").setMessage("文件名不能为空，请重新输入！").setPositiveButton("确定", new android.content.DialogInterface.OnClickListener()  {

									public void onClick(DialogInterface dialoginterface1, int j)
									{
									}
						
								}).show();
								return;
							}
							String Filename[] = getFileName();
							for (int i = 0; i < Filename.length; i++)
							{
								if (newFileName.equalsIgnoreCase(Filename[i]))
								{
									(new android.app.AlertDialog.Builder(DownManager.this)).setTitle("提示：").setMessage("文件名已存在，请重新命名！").setPositiveButton("确定", new android.content.DialogInterface.OnClickListener()  {

										public void onClick(DialogInterface dialoginterface1, int j)
										{
										}
						
									}).show();
									return;
								}
								File file = new File((new StringBuilder("/sdcard/MyCollections/Others/")).append(fileName).toString());
								File file2 = new File((new StringBuilder("/sdcard/MyCollections/Others/")).append(newFileName).toString());
								boolean success = file.renameTo(file2);
								if (!success)
									System.out.println("重命名失败  +11111111111111");
								else
									System.out.println("重命名成功");
							}

							listItem.clear();
							Filename = getFileName();
							for (int i = 0; i < Filename.length; i++)
							{
								HashMap<String ,Object> map = new HashMap<String ,Object>();
								map.put("ItemImage", Integer.valueOf(R.drawable.file));
								map.put("ItemfileName", Filename[i]);
								System.out.println((new StringBuilder("Filename[i] ")).append(Filename[i]).toString());
								listItem.add(map);
							}

							SimpleAdapter listItemAdapter = new SimpleAdapter(DownManager.this, listItem, R.layout.down_list_layer, new String[] {
								"ItemImage", "ItemfileName"
							}, new int[] {
									R.id.fileimage, R.id.filename
							});
							list.setAdapter(listItemAdapter);
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

				case 1: 
					fileName = filNameMatch();
					files = new File((new StringBuilder("/sdcard/MyCollections/Others/")).append(fileName).toString());
					if (files.exists())
						(new android.app.AlertDialog.Builder(DownManager.this)).setTitle("提示：").setMessage("是否要删除文件？").setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int whichButton)
							{
								files.delete();
								listItem.clear();
								String Filename[] = getFileName();
								for (int i = 0; i < Filename.length; i++)
								{
									HashMap<String ,Object> map = new HashMap<String ,Object>();
									map.put("ItemImage", Integer.valueOf(R.drawable.file));
									map.put("ItemfileName", Filename[i]);
									System.out.println((new StringBuilder("Filename[i] ")).append(Filename[i]).toString());
									listItem.add(map);
								}

								SimpleAdapter listItemAdapter = new SimpleAdapter(DownManager.this, listItem, R.layout.down_list_layer, new String[] {
									"ItemImage", "ItemfileName"
								}, new int[] {
										R.id.fileimage, R.id.filename
								});
								list.setAdapter(listItemAdapter);
								set_fileCount();
							}

					
						}).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {


							public void onClick(DialogInterface dialoginterface, int j)
							{
							}
					
						}).show();
					menuDialog.dismiss();
					break;

				case 2: 
					fileName = filNameMatch();
					String fileLast4Name = fileName.substring(fileName.length() - 4);
					if (fileName.length() < 5 && fileName.length() > 0)
					{
						(new android.app.AlertDialog.Builder(DownManager.this)).setTitle("提示：").setMessage("文件名称格式错误，请重新命名！     格式为 名称.apk ").setPositiveButton("确定", new android.content.DialogInterface.OnClickListener()  {

							public void onClick(DialogInterface dialoginterface1, int j)
							{
							}

						}).show();
						return;
					}
					if (!".apk".equals(fileLast4Name))
					{
						
						openFile(new File((new StringBuilder("file:///sdcard/MyCollections/Others/")).append(fileName).toString()));
						menuDialog.dismiss();
						return;
					}
					Intent intent = new Intent();
					intent = new Intent("android.intent.action.VIEW");
					intent.setDataAndType(Uri.parse((new StringBuilder("file:///sdcard/MyCollections/Others/")).append(fileName).toString()), "application/vnd.android.package-archive");
					startActivity(intent);
					menuDialog.dismiss();
					break;
				}
			}
			
		});
		fileCount = (TextView)findViewById(R.id.allpage);
		set_fileCount();
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
	private void set_fileCount()
	{
		int fileCounts = getFileCount();
		fileCount.setText(fileCounts+")");
		if(fileCounts==0)
		{
			deleteFile.setVisibility(View.GONE);
		}else
		{
			deleteFile.setVisibility(View.VISIBLE);
		}
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.returns:
			KeyEvent k = new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_BACK);
			this.onKeyDown(KeyEvent.KEYCODE_BACK, k);
			break;
		case R.id.delete:
			new android.app.AlertDialog.Builder(DownManager.this).setTitle("提示：").setMessage("是否要清空全部文件？").setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() 
			{

			public void onClick(DialogInterface dialog, int whichButton)
			{
				DownManager.delAllFile("/sdcard/MyCollections/Others/");
				DownManager.this.list.setAdapter(null);
				fileCount.setText(0+")");
				deleteFile.setVisibility(View.INVISIBLE);
			}
			}).setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialoginterface, int i)
				{
				}
			
			}).show();
		}
		
	}
	public void onConfigurationChanged(Configuration config)
	{
		super.onConfigurationChanged(config);
	}

	public int getFileCount()
	{
		String path = "/sdcard/MyCollections/Others/";
		int fileCount = 0;
		int folderCount = 0;
		File d = new File(path);
		File list[] = d.listFiles();
		for (int i = 0; i < list.length; i++)
			if (list[i].isFile())
				fileCount++;
			else
				folderCount++;

		return fileCount;
	}

	public String[] getFileName()
	{
		String myList[] = {
			"no"
		};
		try
		{
			File path = new File("/sdcard/MyCollections/Others/");
			myList = path.list();
			for (int i = 0; i < myList.length; i++)
				System.out.println(myList[i]);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return myList;
	}

	public String filNameMatch()
	{
		String filename = "";
		String Filename[] = getFileName();
		HashMap<String ,Object> map = new HashMap<String ,Object>();
		for (int i = 0; i < Filename.length; i++)
			map.put(String.valueOf(i), Filename[i]);

		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();)
		{
			Object key = iter.next();
			if (item == Integer.parseInt(key.toString()))
			{
				filename = (String)map.get(key);
				System.out.println((new StringBuilder("map key ")).append(key.toString()).append(" value=---").append((String)map.get(key)).toString());
			}
		}

		return filename;
	}

	private SimpleAdapter getMenuAdapter(String menuNameArray[], int imageResourceArray[])
	{
		ArrayList<HashMap<String ,Object>> data = new ArrayList<HashMap<String ,Object>>();
		for (int i = 0; i < menuNameArray.length; i++)
		{
			HashMap<String ,Object> map = new HashMap<String ,Object>();
			map.put("itemImage", Integer.valueOf(imageResourceArray[i]));
			map.put("itemText", menuNameArray[i]);
			data.add(map);
		}

		SimpleAdapter simperAdapter = new SimpleAdapter(this, data, R.layout.down_list_layer, new String[] {
			"itemImage", "itemText"
		}, new int[] {
				R.id.fileimage, R.id.filename
		});
		return simperAdapter;
	}

	public static void delFolder(String folderPath)
	{
		try
		{
			boolean flags = false;
			flags = delAllFile(folderPath);
			System.out.println((new StringBuilder("文件夹 is ")).append(flags).toString());
		}
		catch (Exception exception) { }
	}

	public static boolean delAllFile(String path)
	{
		boolean flag = false;
		File file = new File(path);
		if (!file.exists())
		{
			deleteFile.setEnabled(false);
			return flag;
		}
		if (!file.isDirectory())
		{
			deleteFile.setEnabled(false);
			return flag;
		}
		String tempList[] = file.list();
		if (tempList.length == 0)
			deleteFile.setEnabled(false);
		File temp = null;
		for (int i = 0; i < tempList.length; i++)
		{
			if (path.endsWith(File.separator))
				temp = new File((new StringBuilder(String.valueOf(path))).append(tempList[i]).toString());
			else
				temp = new File((new StringBuilder(String.valueOf(path))).append(File.separator).append(tempList[i]).toString());
			if (temp.isFile())
				temp.delete();
			if (temp.isDirectory())
			{
				delAllFile((new StringBuilder(String.valueOf(path))).append("/").append(tempList[i]).toString());
				flag = true;
			}
		}

		return flag;
	}

	public String Rename(String fileName)
	{
		android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
		LayoutInflater inflater = (LayoutInflater)getSystemService("layout_inflater");
		LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.set, null);
		dialog.setTitle("请重新输入文件名");
		dialog.setView(layout);
		DownManager.this.EditText = (EditText)findViewById(R.layout.rename);
		EditText.setText(fileName);
		dialog.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which)
			{
				newFileName = DownManager.this.EditText.getText().toString();
				System.out.println((new StringBuilder("user")).append(newFileName).toString());
			}
			
		});
		dialog.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {


			public void onClick(DialogInterface dialoginterface, int i)
			{
			}
			
		});
		dialog.show();
		return newFileName;
	}

}
