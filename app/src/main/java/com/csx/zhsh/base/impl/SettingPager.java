package com.csx.zhsh.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.csx.zhsh.base.BasePager;
import com.csx.zhsh.utils.LogUtils;


/**
 * 设置
 * 
 * @author csx
 */
public class SettingPager extends BasePager {

	public SettingPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		LogUtils.log("csx->设置初始化");
		tvTitle.setText("设置");

		btnMenu.setVisibility(View.GONE);

		TextView view = new TextView(mActivity);
		view.setText("设置");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);

		flContent.addView(view);
	}

}
