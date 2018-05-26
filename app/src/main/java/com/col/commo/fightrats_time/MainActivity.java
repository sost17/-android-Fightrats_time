package com.col.commo.fightrats_time;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
	
    private ListView listview;
	private List<Map<String, String>> listItem;
	private DatabaseHelper dbHelper;
	private SimpleAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this, "Mydenglu.db", null, 2);
        initView();
        //dbHelper.close();
    }
    @SuppressWarnings("unchecked")
	private void initView() {
		
    	listview=(ListView) findViewById(R.id.listView1);
    	Intent intent = getIntent();
		Bundle data = intent.getExtras();
		listItem = (List<Map<String, String>>) data.getSerializable("data");
		adapter=new SimpleAdapter(MainActivity.this,listItem,R.layout.list_item
				,new String[]{"id","user","passwd","phone"}
		        ,new int[]{R.id.textView1,R.id.textView3,R.id.textView5,R.id.textView7});
		listview.setAdapter(adapter);
		listview.setSelection(0);   
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final int positions = position;
				View v = getLayoutInflater().inflate(R.layout.dialong_view, null);
				final EditText user = (EditText) v.findViewById(R.id.editText1);
				final EditText passwd = (EditText) v.findViewById(R.id.editText2);
				final EditText phone = (EditText) v.findViewById(R.id.editText3);
				user.setText(listItem.get(positions).get("user"));
				passwd.setText(listItem.get(positions).get("passwd"));
				phone.setText(listItem.get(positions).get("phone"));
				
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("单词信息")
				.setView(v)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						String sqluser = user.getText().toString();
						String sqlpasswd = passwd.getText().toString();
						String sqlphone = phone.getText().toString();
						
						listItem.get(positions).put("user", sqluser);
						listItem.get(positions).put("passwd", sqlpasswd);
						listItem.get(positions).put("phone", sqlphone);
						adapter.notifyDataSetChanged();
						String sql = "update dict set username=?,passwd=?,phone=? where _id=?";
						dbHelper.getReadableDatabase().execSQL(sql, new String[]{sqluser,sqlpasswd,sqlphone,listItem.get(positions).get("id")});
						Toast.makeText(MainActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
						
					}
				})
				.setNegativeButton("取消", null)
				.create().show();
			}
		});
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final int positions = position;
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("警告")
				.setMessage("你打算删除该用户吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {						
						String sql = "delete from dict where _id = ?";
						dbHelper.getReadableDatabase().execSQL(sql, new String[]{listItem.get(positions).get("id")});
						listItem.remove(positions);
						adapter.notifyDataSetChanged();				
					}
				})
				.setNegativeButton("取消", null)
				.create().show();
				return false;
			}
		});
	}
}
