package com.mrqyoung.rpc.target;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class Target {

	private Target() {
		System.out.println("new Target");
	}

	private static class Holder {
		private static final Target target = new Target();
	}

	/**
	 * SDK 通常会采用单例模式，返回唯一对象
	 */
	public static Target getInstance() {
		return Holder.target;
	}

	/**
	 * SDK 中的基本方法例子1－加法
	 * @param i1
	 * @param i2
	 * @return
	 */
	public int add(int i1, int i2) {
		return i1 + i2;
	}

	/**
	 * SDK 中的基本方法例子1－减法
	 * @param i1
	 * @param i2
	 * @return
	 */
	public int subtract(int i1, int i2) {
		return i1 - i2;
	}
	
	/**
	 * 在内部进行网络请求并返回内容的例子
	 * 本例使用 ipip.net 的IP查询接口
	 * @param String ipAddr: 需要传入IP地址
	 * @return String
	 * @throws Exception 
	 */
	public String httpGet(String ipAddr) throws Exception {
		String url = "http://freeapi.ipip.net/" + ipAddr; 
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
		if (httpResponse == null) return "<Null>";
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode != 200) return "<Http Error: " + statusCode +">";
		String result = EntityUtils.toString(httpResponse.getEntity());
		if (result.isEmpty()) return "<Empty>";
		return result;
	}
	
	/**
	 * 读取 Android 设备的一些简单信息
	 * @return
	 * @throws JSONException
	 */
	public String getDeviceInfo() throws Exception {
		JSONObject json = new JSONObject();
		json.put("deviceName", android.os.Build.MODEL);
		json.put("apiLevel", android.os.Build.VERSION.SDK_INT);
		json.put("androidVersion", android.os.Build.VERSION.RELEASE);
		json.put("manufactuer", android.os.Build.MANUFACTURER);
		json.put("product", android.os.Build.PRODUCT);
		return json.toString();
	}

}
