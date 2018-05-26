package com.col.commo.fightrats_time;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class MyDialog extends AlertDialog {

	private Context context;

	/**
	 * @param context
	 */
	protected MyDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 通过LayoutInflater来获取布局文件对象
		LayoutInflater inflater = LayoutInflater.from(context);
		View userDialog = inflater.inflate(R.layout.overtime_main, null);
		setView(userDialog);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

}
