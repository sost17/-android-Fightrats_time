package com.col.commo.fightrats_time;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity_test extends Activity implements OnClickListener{
	private RadioButton jd,yb,kn;
	private ListView listview;
	private List<Map<String, Object>> listitem;
	private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        listview=(ListView) findViewById(R.id.listView1);
        jd=(RadioButton) findViewById(R.id.btn_jiandan);
        yb=(RadioButton) findViewById(R.id.btn_yiban);
        kn=(RadioButton) findViewById(R.id.btn_kunnan);
        jd.setOnClickListener(this);
        yb.setOnClickListener(this);
        kn.setOnClickListener(this);
        listitem=getpaihang(2);//初始化 listitem 为简单的积分
        adapter=new SimpleAdapter(MainActivity_test.this, listitem, R.layout.listitem
        		, new String[] {"image","name","paiming"}
        		, new int[] {R.id.imageView1,R.id.textView1,R.id.textView2});
        listview.setAdapter(adapter);        
    }

    public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_jiandan:
			jd.setTextColor(Color.parseColor("#FF0000"));
			yb.setTextColor(Color.parseColor("#000000"));
			yb.setChecked(false);
			kn.setTextColor(Color.parseColor("#000000"));
			yb.setChecked(false);
			listitem.clear();
			listitem.addAll(getpaihang(2));//这里必须这样做 不然 会不更新
			//因为使用了listitem=getpaihang(2)，这里的listitem和adapter里的就不是一个了，所以无法更新
			adapter.notifyDataSetChanged(); //这里不能使用该函数，不知道为啥，使用了wuxiao
			Toast.makeText(MainActivity_test.this, "简单", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_yiban:			
			//viewPager.setCurrentItem(TAB_contact);
			jd.setTextColor(Color.parseColor("#000000"));
			jd.setChecked(false);
			yb.setTextColor(Color.parseColor("#FF0000"));
			kn.setTextColor(Color.parseColor("#000000"));
			kn.setChecked(false);
			listitem.clear();
			listitem.addAll(getpaihang(4));///这里必须这样做 不然 会不更新
			//因为使用了listitem=getpaihang(2)，这里的listitem和adapter里的就不是一个了，所以无法更新
			adapter.notifyDataSetChanged();  //这里不能使用该函数，不知道为啥，使用了无效
			Toast.makeText(MainActivity_test.this, "一般", Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_kunnan:
			//viewPager.setCurrentItem(TAB_plugin);
			jd.setTextColor(Color.parseColor("#000000"));
			jd.setChecked(false);
			yb.setTextColor(Color.parseColor("#000000"));
			yb.setChecked(false);
			kn.setTextColor(Color.parseColor("#FF0000"));
			listitem.clear();
			listitem.addAll(getpaihang(6));//这里必须这样做 不然 会不更新
			//因为使用了listitem=getpaihang(2)，这里的listitem和adapter里的就不是一个了，所以无法更新
			adapter.notifyDataSetChanged(); //这里不能使用该函数，不知道为啥，使用了无效

			Toast.makeText(MainActivity_test.this, "困难", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}		
	}
    private List<Map<String, Object>> getpaihang(int count) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> newlistItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < count; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image",R.drawable.ic_launcher);
			map.put("name","名字"+i);
			map.put("paiming", i);
			listItems.add(map);
		}
		Collections.sort(listItems,new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> map0,
					Map<String, Object> map1) {
				// TODO Auto-generated method stub
				int data1=(Integer) map0.get("paiming");
				int data2=(Integer) map1.get("paiming");
				if(data1<data2){
					return 1;
				}
					return -1;
			}
		});
		for (int i = 0; i < count; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image",listItems.get(i).get("image"));
			map.put("name",listItems.get(i).get("name"));
			map.put("paiming", listItems.get(i).get("paiming")+"分");
			newlistItems.add(map);
		}
		return newlistItems;
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
