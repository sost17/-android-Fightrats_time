package com.col.commo.fightrats_time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class regActivity extends Activity {

	private DatabaseHelper dbHelper;
	private EditText user;
	private EditText passwd1;
	private EditText passwd2;
	private EditText phone;
	private Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_main);
		
		dbHelper = new DatabaseHelper(this, "Mydenglu.db", null, 2);
		
		user=(EditText) findViewById(R.id.editText1);
		passwd1=(EditText) findViewById(R.id.editText2);
		passwd2=(EditText) findViewById(R.id.editText3);
		phone=(EditText) findViewById(R.id.editText4);
		button=(Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String user1=user.getText().toString();
				String passwd01=passwd1.getText().toString();
				String passwd02=passwd2.getText().toString();
				String phone01=phone.getText().toString();
				String admin="false";
				if(passwd01.equals("")||passwd02.equals("")||user1.equals("")){
					Toast.makeText(regActivity.this, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
				}
				else if(phone01.equals("")){
					Toast.makeText(regActivity.this, "联系号码不能为空！", Toast.LENGTH_SHORT).show();
				}
				else if(passwd01.equals(passwd02)){
					Intent intent=new Intent();
					intent.putExtra("user", user1);
					intent.putExtra("passwd", passwd01);
					setResult(100, intent);
					dbHelper.getReadableDatabase().execSQL(
							"insert into dict values(null,?,?,?,?)", 
							new String[]{user1,passwd01,phone01,admin});
					Toast.makeText(regActivity.this, 
							"添加成功",
							Toast.LENGTH_SHORT).show();
					//dbHelper.close();
					finish();
				}
				else{
					Toast.makeText(regActivity.this, "两次输入的密码不一样！", Toast.LENGTH_SHORT).show();
					user.setText("");
					passwd1.setText("");
					passwd2.setText("");
				}
				
			}
		});
		
	}
	

}
