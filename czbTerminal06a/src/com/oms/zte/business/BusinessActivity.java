package com.oms.zte.business;

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
import com.oms.zte.shop.MemberActivity;
import com.oms.zte.shop.ShoppingActivity;

public class BusinessActivity extends CommonNew {

	private final int UERINPUT = 0; // 会员登入
	private final int STOREINPUT = 1; // 店铺登入
	private SimpleAdapter listItemAdapter = null;
	private TextView text;
	private ListView list;
	private ArrayList<HashMap<String, Object>> listItem = null;

	public void onCreate(Bundle savedInstanceState) {
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
		list = (ListView) this.findViewById(R.id.lists);
		text.setText("业 务 管 理");
		listItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.img_v_input);// 图像资源的ID
		map.put("ItemTitle", "会员专区");
		map.put("ItemImag2", R.drawable.go);
		listItem.add(map);

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ItemImage", R.drawable.img_v_store);// 图像资源的ID
		map1.put("ItemTitle", "工作室专区");
		map1.put("ItemImag2", R.drawable.go);
		listItem.add(map1);


		listItemAdapter = new SimpleAdapter(BusinessActivity.this, listItem,
				R.layout.shoplistview,
				new String[] { "ItemImage", "ItemTitle", "ItemImag2" }, new int[] { R.id.image,
				R.id.listtext1, R.id.image2 });
		list.setAdapter(listItemAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intents = new Intent();
				try {
					switch (arg2) {
					case UERINPUT: 
					{
						intents.setClass(BusinessActivity.this,MemberActivity.class);
						startActivity(intents);
						break;
					}
					case STOREINPUT:
					{ 
						intents.setClass(BusinessActivity.this,ShoppingActivity.class);
						startActivity(intents);
						break;
					}
					default:
						break;
					}
				} catch (Exception e) {
					Log.v("viewToolerr", e.getMessage());
				}

			}
		});

	}
	@Override
	protected void onResume() {
		setRequestedOrientation(Params.orientationControl);
		super.onResume();
	}
}
