package com.oms.zte.plays;

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

import com.oms.zte.Apn;
import com.oms.zte.Params;
import com.oms.zte.R;
import com.oms.zte.base.CommonNew;

public class PlaysActivity extends CommonNew {

	private final int PLAY_MOVIES = 0; // 移动影院
	private final int PLAY_GAME = 1;//游戏下载

	SimpleAdapter listItemAdapter = null;
	ListView listView = null;
	TextView text = null;

	ArrayList<HashMap<String, Object>> listItem = null;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(Params.orientationControl);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
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
		text.setText("娱 乐 中 心");

		listItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ItemImage", R.drawable.img_freemovie);// 图像资源的ID
		map1.put("ItemTitle", "移动影院");
		map1.put("ItemImag2", R.drawable.go);
		listItem.add(map1);

		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("ItemImage", R.drawable.img_game);// 图像资源的ID
		map2.put("ItemTitle", "游戏下载");
		map2.put("ItemImag2", R.drawable.go);
		listItem.add(map2);

		SimpleAdapter listItemAdapter = new SimpleAdapter(PlaysActivity.this,listItem,R.layout.shoplistview,
				new String[] { "ItemImage", "ItemTitle", "ItemImag2" },
				new int[] { R.id.image, R.id.listtext1, R.id.image2 });

		listView.setAdapter(listItemAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				try {
					switch (arg2) {
					case PLAY_MOVIES:
						if (!Apn.isVpdnUser) 
						{
							showInfoDialog(BASE_DIALOG_INFO, getString(R.string.dialog_title));
						} 
						else 
						{
							intent.setClass(PlaysActivity.this, MoviesActivity.class);
							startActivity(intent);
						}
						break;
					case PLAY_GAME:
						intent.setClass(PlaysActivity.this, GameDownActivity.class);
						startActivity(intent);
						break;

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
		super.onResume();
		setRequestedOrientation(Params.orientationControl);
	}
}
