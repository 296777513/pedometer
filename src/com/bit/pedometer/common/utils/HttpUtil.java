package com.bit.pedometer.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

	/**
	 * 网络请求属于耗时操作，所以必须开启另外一个线程，如果不这样，就会导致主线程被阻塞
	 * 这里为什么要使用java的回调机制，因为我们这里开启了一个线程，来发送HTTP请求
	 * ，因为HTTP请求属于耗时操作，这就导致sendHttpRequest 方法还没来得及相应时候就执行结束了，当然也就无法返回显影的数据了
	 * @param address
	 * @param listener
	 */
	public static void sendHttpRequest(final String address,
			final HttpCallbackListener listener) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					/* 这是另一种实现的方法
					 * HttpClient httpClient = new DefaultHttpClient(); HttpGet
					 * httpGet = new HttpGet(address); HttpResponse httpResponse =
					 * httpClient.execute(httpGet); String response = null; if
					 * (httpResponse.getStatusLine().getStatusCode() == 200) {
					 * HttpEntity entity = httpResponse.getEntity(); response =
					 * EntityUtils.toString(entity);
					 * 
					 * }else {
					 * 
					 * } Gson gson = new Gson(); List<Weather> weathers =
					 * gson.fromJson(response, new TypeToken<List<Weather>>() {
					 * }.getType()); for (Weather weather1 : weathers) {
					 * weather.setPtime(weather1.getPtime());
					 * weather.setTemp1(weather1.getTemp1());
					 * weather.setTemp2(weather1.getTemp2());
					 * weather.setWeather(weather1.getWeather()); }
					 */
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					if (listener != null) {
						// 回调onFinish()方法
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if (listener != null) {
						// 回调onError()方法
						listener.onError(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}

}