package com.csx.zhsh.domain;

import java.util.ArrayList;

/**
 * 组图对象封装
 * 
 * @author csx
 */
public class PhotoBean {
	public int retcode;
	public PhotoData data;
	public String a="你好";

	public class PhotoData {
		public ArrayList<PhotoNewsData> news;
	}

	public class PhotoNewsData {
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String url;
	}
}
