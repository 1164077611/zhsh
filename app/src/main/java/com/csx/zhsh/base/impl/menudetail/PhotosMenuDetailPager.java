package com.csx.zhsh.base.impl.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.csx.zhsh.R;
import com.csx.zhsh.base.BaseMenuDetailPager;
import com.csx.zhsh.domain.PhotoBean;
import com.csx.zhsh.global.Constants;
import com.csx.zhsh.utils.CacheUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * 菜单详情页-组图
 * 
 * @author csx
 */
public class PhotosMenuDetailPager extends BaseMenuDetailPager implements
		View.OnClickListener {

	@ViewInject(R.id.lv_list)
	private ListView lvList;
	@ViewInject(R.id.gv_list)
	private GridView gvList;

	private ArrayList<PhotoBean.PhotoNewsData> mPhotoList;

	private boolean isList = true;// 当前界面状态

	private ImageButton btnDisplay;

	public PhotosMenuDetailPager(Activity activity, ImageButton btnDisplay) {
		super(activity);
		this.btnDisplay = btnDisplay;
		btnDisplay.setOnClickListener(this);
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.pager_menu_detail_photo,
				null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		String cache = CacheUtils.getCache(Constants.PHOTOS_URL, mActivity);
		if (!TextUtils.isEmpty(cache)) {
			processResult(cache);
		}

		getDataFromServer();
	}

	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpRequest.HttpMethod.GET, Constants.PHOTOS_URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						processResult(responseInfo.result);

						CacheUtils.setCache(Constants.PHOTOS_URL,
								responseInfo.result, mActivity);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						error.printStackTrace();
						Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	protected void processResult(String result) {
		Gson gson = new Gson();
		PhotoBean photoBean = gson.fromJson(result, PhotoBean.class);
		mPhotoList = photoBean.data.news;

		lvList.setAdapter(new PhotoAdapter());
		gvList.setAdapter(new PhotoAdapter());
	}

	class PhotoAdapter extends BaseAdapter {

		private BitmapUtils mBitmapUtils;

		public PhotoAdapter() {
			mBitmapUtils = new BitmapUtils(mActivity);
			mBitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
		}

		@Override
		public int getCount() {
			return mPhotoList.size();
		}

		@Override
		public PhotoBean.PhotoNewsData getItem(int position) {
			return mPhotoList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.list_item_photo,
						null);
				holder = new ViewHolder();
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.ivIcon = (ImageView) convertView
						.findViewById(R.id.iv_icon);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			PhotoBean.PhotoNewsData item = getItem(position);

			holder.tvTitle.setText(item.title);
			mBitmapUtils.display(holder.ivIcon, item.listimage);

			return convertView;
		}

	}

	static class ViewHolder {
		public TextView tvTitle;
		public ImageView ivIcon;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_display:
				// 如果当前是列表, 要切换成grid, 如果是grid,就切换成列表
				if (isList) {
					isList = false;

					lvList.setVisibility(View.GONE);
					gvList.setVisibility(View.VISIBLE);

					btnDisplay.setImageResource(R.drawable.icon_pic_list_type);
				} else {
					isList = true;

					lvList.setVisibility(View.VISIBLE);
					gvList.setVisibility(View.GONE);

					btnDisplay.setImageResource(R.drawable.icon_pic_grid_type);
				}

				break;

			default:
				break;
		}
	}

}
