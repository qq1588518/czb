package com.oms.zte.shop;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.oms.zte.Params;
import com.oms.zte.R;
import com.oms.zte.base.CommonNew;

public class ShopActivity extends  CommonNew
{

	private final int UERINPUT = 0; // 会员登入
	private final int STOREINPUT = 1; // 店铺登入
	private final int SSCZBNT = 2; // 财智通
	SimpleAdapter listItemAdapter = null;
	ListView listView = null;

	ArrayList<HashMap<String, Object>> listItem = null;
	HashMap<String, Object> map = new HashMap<String, Object>();
	HashMap<String, Object> map1 = new HashMap<String, Object>();
	HashMap<String, Object> map2 = new HashMap<String, Object>();

	TextView tt = null;
	TextView text = null;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(Params.orientationControl);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除标题栏
		setContentView(R.layout.list_shop_layer);
		ImageButton page_return = (ImageButton) findViewById(R.id.page_return);
		ImageButton btnMain = (ImageButton)findViewById(R.id.page_main);
		ImageButton btnUpdate = (ImageButton)findViewById(R.id.webUpdate);
		ImageButton btnLast = (ImageButton)findViewById(R.id.page_last);
		ImageButton btnNext = (ImageButton)findViewById(R.id.page_next);
		ImageButton btnMore = (ImageButton)findViewById(R.id.page_more);
		ImageButton btnExit = (ImageButton)findViewById(R.id.page_exit);
		init_Button(page_return,btnMain,btnUpdate,btnLast,btnNext,btnMore,btnExit);
		text = (TextView) this.findViewById(R.id.titleView);
		listView = (ListView) this.findViewById(R.id.lists);
		text.setText("移动购物");
		listItem = new ArrayList<HashMap<String, Object>>();
		map.put("ItemImage", R.drawable.img_v_input);// 图像资源的ID
		map.put("ItemTitle", "会员专区");
		map.put("ItemImag2", R.drawable.go);
		listItem.add(map);

		map1.put("ItemImage", R.drawable.img_v_store);// 图像资源的ID
		map1.put("ItemTitle", "工作室专区");
		map1.put("ItemImag2", R.drawable.go);
		listItem.add(map1);

		map2.put("ItemImage", R.drawable.img_v_money);// 图像资源的ID
		map2.put("ItemTitle", "网上商城");
		map2.put("ItemImag2", R.drawable.go);
		listItem.add(map2);

		listItemAdapter = new SimpleAdapter(ShopActivity.this, listItem,R.layout.shoplistview,
				new String[] { "ItemImage", "ItemTitle", "ItemImag2" }, 
				new int[] { R.id.image,R.id.listtext1, R.id.image2 });
		listView.setAdapter(listItemAdapter);
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{

				Intent intents = new Intent();
				try
				{
					switch (arg2)
					{
					case UERINPUT:
					{// 会员登入
						intents.setClass(ShopActivity.this, MemberActivity.class);
						startActivity(intents);
						break;
					}
					case STOREINPUT:
					{ // 店铺登入
						intents.setClass(ShopActivity.this, ShoppingActivity.class);
						startActivity(intents);
						break;
					}
					case SSCZBNT:
					{ // 财智通:
						intents.setClass(ShopActivity.this, CZTActivity.class);
						startActivity(intents);
						break;
					}
					default:
						break;
					}
				} catch (Exception e)
				{
					Log.v("viewToolerr", e.getMessage());
				}

			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		setRequestedOrientation(Params.orientationControl);
	}
}
