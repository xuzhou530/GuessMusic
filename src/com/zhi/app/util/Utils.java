package com.zhi.app.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import com.zhi.app.inter.IDialogBtnClickListener;
import com.zhi.app.ui.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Utils {

	private static Dialog mAlertDialog = null;
	
	/**
	 * 获取View
	 * 
	 * @param context
	 * @param layoutId
	 * @return
	 */
	public static View getView(Context context, int layoutId) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View layout = inflater.inflate(layoutId, null);

		return layout;
	}

	/**
	 * 随机生成汉字
	 * 
	 * @return
	 */
	public static String generateRandomWord() {
		String word = "";
		int hightPos = 0, lowPos = 0;
		Random random = new Random();

		// 获取高位值，高位字节的范围0xB0 - 0xF7，而0xB0就是176
		// 之所以设置在0-39之间取值是因为这个范围得出的汉字比较正常，不会出现生僻字
		hightPos = 176 + Math.abs(random.nextInt(39));
		// 获取低位值,低位字节的范围是0xA1 - 0xFE，而0xA1就是161
		lowPos = (161 + Math.abs(random.nextInt(93)));

		// 定义一个byte数据，用于存放汉字。PS：汉字占两个字节
		byte[] bytes = new byte[2];

		bytes[0] = Integer.valueOf(hightPos).byteValue();
		bytes[1] = Integer.valueOf(lowPos).byteValue();

		try {
			word = new String(bytes, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return word;
	}

	/**
	 * 获取res/values目录下的integer值
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public static int getIntegerValues(Context context, int id) {
		return context.getResources().getInteger(id);
	}

	/**
	 * 启动一个新的Activity
	 * 
	 * @param context
	 * @param destClass
	 */
	public static void startActivity(Context context, Class destClass) {
		Intent intent = new Intent();
		intent.setClass(context, destClass);
		context.startActivity(intent);

		((Activity) context).finish();
	}

	/**
	 * 显示对话框
	 * 
	 * @param context
	 * @param message
	 * @param listener
	 */
	public static void showDialog(Context context, String message, final IDialogBtnClickListener listener) {
		
		// 设置dialog的主题样式
		AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AlertDialog);
		View view = getView(context, R.layout.layout_dialog);

		// 设置提示文本
		TextView tv_dialog_message = (TextView) view.findViewById(R.id.tv_dialog_message);
		tv_dialog_message.setText(message);

		ImageButton btn_dialog_btn_ok = (ImageButton) view.findViewById(R.id.btn_dialog_btn_ok);
		ImageButton btn_dialog_btn_cancel = (ImageButton) view.findViewById(R.id.btn_dialog_btn_cancel);
		
		builder.setView(view);
		mAlertDialog = builder.create();

		btn_dialog_btn_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 关闭对话框
				if(mAlertDialog != null) {
					mAlertDialog.cancel();
				}
				
				// 点击“确定”按钮时回调click方法，执行对应的点击逻辑
				if(listener != null) {
					listener.onClick();
				}
			}
		});

		btn_dialog_btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mAlertDialog != null) {
					mAlertDialog.cancel();
				}
			}
		});

		// 显示对话框
		mAlertDialog.show();
	}
}
