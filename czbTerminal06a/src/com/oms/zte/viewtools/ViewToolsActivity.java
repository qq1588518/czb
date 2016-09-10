package com.oms.zte.viewtools;
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

public class ViewToolsActivity extends  CommonNew {

	private final int CULTURE = 0; // 企业文化
	private final int PRODUCTS = 1; // 产品介绍
	private final int INTRUDCTION = 2; // 事业介绍
	private final int DOWNLOAD = 3; // 课件下载
	private final int SERVICE = 4; // 在线客服
	private final int QUESTION = 5; // 事业百问
	
	SimpleAdapter listItemAdapter = null;

	
	// 生成动态数组，加入数据
	ArrayList<HashMap<String, Object>> listItem = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(Params.orientationControl);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除标题栏
		setContentView(R.layout.list_second_layer);
		ImageButton page_return = (ImageButton) findViewById(R.id.page_return);
		ImageButton btnMain = (ImageButton)findViewById(R.id.page_main);
		ImageButton btnUpdate = (ImageButton)findViewById(R.id.webUpdate);
		ImageButton btnLast = (ImageButton)findViewById(R.id.page_last);
		ImageButton btnNext = (ImageButton)findViewById(R.id.page_next);
		ImageButton btnMore = (ImageButton)findViewById(R.id.page_more);
		ImageButton btnExit = (ImageButton)findViewById(R.id.page_exit);
		init_Button(page_return,btnMain,btnUpdate,btnLast,btnNext,btnMore,btnExit);
		TextView text = (TextView) this.findViewById(R.id.titleView);
		ListView list = (ListView) this.findViewById(R.id.list);
		text.setText("展 业 工 具");
		listItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.img_v_cul);// 图像资源的ID
		map.put("ItemTitle", "企业文化");
		map.put("ItemText", "全面了解企业实力、文化、历史");
		map.put("ItemImag2", R.drawable.go);
		listItem.add(map);
		
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ItemImage", R.drawable.img_v_prod);// 图像资源的ID
		map1.put("ItemTitle", "产品介绍");
		map1.put("ItemText", "全方位展示产品卓越品质");
		map1.put("ItemImag2", R.drawable.go);
		listItem.add(map1);

		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("ItemImage", R.drawable.img_v_int);// 图像资源的ID
		map2.put("ItemTitle", "事业介绍");
		map2.put("ItemText", "感受三生事业平台魅力");
		map2.put("ItemImag2", R.drawable.go);
		listItem.add(map2);

		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("ItemImage", R.drawable.img_v_load);// 图像资源的ID
		map3.put("ItemTitle", "课件下载");
		map3.put("ItemText", "事件、培训辅助课件");
		map3.put("ItemImag2", R.drawable.go);
		listItem.add(map3);

		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("ItemImage", R.drawable.img_v_serv);// 图像资源的ID
		map4.put("ItemTitle", "在线客服");
		map4.put("ItemText", "客服在线解答事业疑问");
		map4.put("ItemImag2", R.drawable.go);
		listItem.add(map4);

		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("ItemImage", R.drawable.img_v_qust);// 图像资源的ID
		map5.put("ItemTitle", "事业百问");
		map5.put("ItemText", "三生事业百问解答");
		map5.put("ItemImag2", R.drawable.go);
		listItem.add(map5);


		SimpleAdapter listItemAdapter = new SimpleAdapter(ViewToolsActivity.this,
				listItem,// 数据源
				R.layout.listview,// ListItem的XML实现
				new String[] { "ItemImage", "ItemTitle", "ItemText",
						"ItemImag2" }, new int[] { R.id.image, R.id.listtext1,
						R.id.listtext2, R.id.image2 });

		list.setAdapter(listItemAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {			
				Intent intent = new Intent();
				try {
					switch (arg2) {
					case CULTURE: {
						intent.setClass(ViewToolsActivity.this, ViewToolsCultureActivity.class);
						startActivity(intent);
						break;
					}

					case PRODUCTS: {
						intent.setClass(ViewToolsActivity.this, ViewToolsProtuctActivity.class);
						startActivity(intent);
						break;
					}

					case INTRUDCTION: {
						intent.setClass(ViewToolsActivity.this, ViewToolsWorkIntrudceActivity.class);
						startActivity(intent);
						break;
					}

					case DOWNLOAD:// 课件下载
					{
						intent.setClass(ViewToolsActivity.this, ViewToolsLessonDownActivity.class);
						startActivity(intent);
						break;
					}
					case SERVICE: {
						intent.setClass(ViewToolsActivity.this, ViewToolsOnLineServiceActivity.class);
						startActivity(intent);
						break;
					}
					case QUESTION: {// 事业百问
						intent.setClass(ViewToolsActivity.this, ViewToolsWorkQuestActivity.class);
						startActivity(intent);
						break;
					}
					default:
						break;

					}
				} catch (Exception e) {
					// TODO: handle exception
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
