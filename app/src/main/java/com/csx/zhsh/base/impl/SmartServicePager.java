package com.csx.zhsh.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.csx.zhsh.base.BasePager;
import com.csx.zhsh.utils.LogUtils;


/**
 * 智慧服务
 * 
 * @author csx
 */
public class SmartServicePager extends BasePager {

	public SmartServicePager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		LogUtils.log("csx->智慧服务初始化");
		tvTitle.setText("智慧服务");

		TextView view = new TextView(mActivity);
		view.setText("智慧服务");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);

		flContent.addView(view);
	}

}
