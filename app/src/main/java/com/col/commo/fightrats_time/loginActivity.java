package com.col.commo.fightrats_time;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class loginActivity extends Activity {

	private DatabaseHelper dbHelper;
	private Button button1;
	private Button button2;
	private AutoCompleteTextView user;
	private EditText passwd;
	private String user1;
	private String passwd1;
	private ImageView pwdClean1;
	private ImageView pwdClean2;
	private int count = 0;
	private int time = 11;
	// private DatabaseHelper dbHelper;
	// String[] contents = new String[]{"mzy"};
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);

		dbHelper = new DatabaseHelper(this, "Mydenglu.db", null, 2);

		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		user = (AutoCompleteTextView) findViewById(R.id.editText1);
		passwd = (EditText) findViewById(R.id.editText2);
		pwdClean1 = (ImageView) findViewById(R.id.iv_pwd_clear1);
		pwdClean2 = (ImageView) findViewById(R.id.iv_pwd_clear2);
		// box=(CheckBox) findViewById(R.id.checkBox1);

		// String namestring=sp.getString("username", "");
		// String passwdstring=sp.getString("passwd", "");
		// user.setText(namestring);
		// passwd.setText(passwdstring);

//		String sql1 = "select * from dict where username like ? ";
//		Cursor cursor1 = dbHelper.getReadableDatabase().rawQuery(sql1,
//				new String[] { "%" });
//		ArrayList<String> contents = new ArrayList<String>();
//		while (cursor1.moveToNext()) {
//			contents.add(cursor1.getString(1));
//		}
//
//		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
//				android.R.layout.simple_dropdown_item_1line, contents);
//		user.setAdapter(adapter1);
//		user.setTextColor(Color.BLACK);
//		user.setThreshold(2);// 输入两个字符提示

		pwdClean1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				user.setText(null);
			}
		});

		pwdClean2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				passwd.setText(null);
			}
		});

		user.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					pwdClean1.setVisibility(View.VISIBLE);
				} else {
					pwdClean1.setVisibility(View.INVISIBLE);
				}
			}
		});

		passwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					pwdClean2.setVisibility(View.VISIBLE);
				} else {
					pwdClean2.setVisibility(View.INVISIBLE);
				}
			}
		});

		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String sqluser = null;
				String sqlpass = null;

				String us = user.getText().toString();
				// String pass=passwd.getText().toString();
				String sql = "select * from dict where username like ? ";
				Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql,
						new String[] { us });
				while (cursor.moveToNext()) {
					sqluser = cursor.getString(1);
					sqlpass = cursor.getString(2);
				}
				if (user.getText().toString().equals(sqluser)
						&& passwd.getText().toString().equals(sqlpass)) {
					String sql1 = "select * from dict where username like ? ";
					Cursor cursor1 = dbHelper.getReadableDatabase().rawQuery(
							sql1, new String[] { "%" });
					ArrayList<Map<String, String>> results = new ArrayList<Map<String, String>>();
					while (cursor1.moveToNext()) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("id", cursor1.getInt(0) + "");
						map.put("user", cursor1.getString(1));
						map.put("passwd", cursor1.getString(2));
						map.put("phone", cursor1.getString(3));
						results.add(map);
					}

					Intent intent = new Intent();
					Bundle data = new Bundle();
					data.putSerializable("data", results);
					intent.putExtras(data);
					intent.setClass(loginActivity.this, MainActivity.class);
					// dbHelper.close();
					startActivity(intent);
				} else if (user.getText().toString().equals("")
						|| passwd.getText().toString().equals("")) {
					Toast.makeText(loginActivity.this, "用户或密码不能为空",
							Toast.LENGTH_SHORT).show();
				} else if (count >= 4) {
					final MyDialog userDialog = new MyDialog(loginActivity.this);
					// 设置对话框的图标
					userDialog.setIcon(R.drawable.ic_launcher);
					userDialog.setCancelable(false);
					// 设置标题
					userDialog.setTitle("密码错误");
					userDialog.show();
					final TextView timetext = (TextView) userDialog
							.findViewById(R.id.textView2);
					final Timer timer = new Timer();
					TimerTask task = new TimerTask() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									time--;
									timetext.setText("" + time + "秒");
									if (time < 0) {
										timer.cancel();
										timetext.setVisibility(View.GONE);
										time = 11;
										userDialog.dismiss();
									}
								}
							});
						}
					};
					timer.schedule(task, 1000, 1000);

				} else {
					Toast.makeText(loginActivity.this, "用户或密码错误",
							Toast.LENGTH_SHORT).show();
					count++;
					user.setText("");
					passwd.setText("");
				}
			}
		});
		button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(loginActivity.this, regActivity.class);
				startActivityForResult(intent, 10086);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		String sql1 = "select * from dict where username like ? ";
		Cursor cursor1 = dbHelper.getReadableDatabase().rawQuery(sql1,
				new String[] { "%" });
		ArrayList<String> contents = new ArrayList<String>();
		while (cursor1.moveToNext()) {
			contents.add(cursor1.getString(1));
		}

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, contents);
		user.setAdapter(adapter1);
		user.setTextColor(Color.BLACK);
		user.setThreshold(2);// 输入两个字符提示

		if (data != null) {
			if (resultCode == 100) {
				user1 = data.getStringExtra("user");
				passwd1 = data.getStringExtra("passwd");
				user.setText(user1);
				passwd.setText(passwd1);
			}
		}

	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		String sql1 = "select * from dict where username like ? ";
		Cursor cursor1 = dbHelper.getReadableDatabase().rawQuery(sql1,
				new String[] { "%" });
		ArrayList<String> contents = new ArrayList<String>();
		while (cursor1.moveToNext()) {
			contents.add(cursor1.getString(1));
		}

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, contents);
		user.setAdapter(adapter1);
		user.setTextColor(Color.BLACK);
		user.setThreshold(2);// 输入两个字符提示
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(loginActivity.this)
					.setIcon(R.drawable.ic_launcher)
					.setTitle("警告")
					.setMessage("你确定要退出登录？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub
									finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub

								}
							}).create().show();
		}

		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
