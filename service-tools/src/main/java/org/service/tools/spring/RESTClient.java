package org.service.tools.spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class RESTClient {
	static ExecutorService pool = Executors.newCachedThreadPool();
	private static Logger log = LoggerFactory.getLogger(RESTClient.class);
	private static HttpClient httpClient = HttpClientBuilder.create().build();
	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(25000).setConnectTimeout(5000)
			.build();// 设置请求和传输超时时间

	public static void post(String url, String method, Map<String, Object> param) {

		pool.execute(new Runnable() {
			@Override
			public void run() {
				HttpPost post = new HttpPost(url + method);
				post.setConfig(requestConfig);
				// 设置需要提交的参数
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				for (Entry<String, Object> entry : param.entrySet()) {
					Object objValue = entry.getValue();
					list.add(new BasicNameValuePair(entry.getKey(), objValue == null ? "" : objValue.toString()));
				}
				try {
					post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
					long startTime = System.currentTimeMillis();
					HttpResponse response = httpClient.execute(post);
					String resposeText = EntityUtils.toString(response.getEntity());
					if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
						log.info("send  to [{}] success \n times [{}] \n response [{}] \n param:[{}]", url + method,
								System.currentTimeMillis() - startTime, resposeText, param);
					} else {
						log.error("send  to [{}] fail \n times [{}] \n response [{}] \n param:[{}]", url + method,
								System.currentTimeMillis() - startTime, resposeText, param);
					}
				} catch (IOException e) {
					log.error("send  to [{}] error message:{}", url + method, e);
					e.printStackTrace();
				}
			}
		});
	}

	public static String postForSynchronize(String url, String method, Map<String, Object> param) {
		HttpPost post = new HttpPost(url + method);
		post.setConfig(requestConfig);
		// 设置需要提交的参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Entry<String, Object> entry : param.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);
			String resposeText = EntityUtils.toString(response.getEntity());
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				log.info("send  to [{}] success \n times [{}] \n response [{}] \n param:[{}]", url + method,
						System.currentTimeMillis() - startTime, resposeText, param);
				return resposeText;
			} else {
				log.error("send  to [{}] fail \n times [{}] \n response [{}] \n param:[{}]", url + method,
						System.currentTimeMillis() - startTime, resposeText, param);
			}
		} catch (IOException e) {
			log.error("send  to [{}] error message:{}", url + method, e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String getForSynchronize(String url, String method, Map<String, Object> headersParam, Map<String, Object> urlParam) {
		String paramStr = "";
		if(urlParam != null && urlParam.size() > 0){
			StringBuilder sb = new StringBuilder("?");
			for(Map.Entry<String, Object> entry : urlParam.entrySet()){
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			paramStr = sb.toString().substring(0, sb.length()-1);
		}
		HttpGet get = new HttpGet(url + method + paramStr);
		get.setConfig(requestConfig);
		// 设置需要提交的参数
		for (Entry<String, Object> entry : headersParam.entrySet()) {
			get.setHeader(entry.getKey(), entry.getValue().toString());
		}
		try {
			HttpResponse response = httpClient.execute(get);
			String resposeText = EntityUtils.toString(response.getEntity());
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				return resposeText;
			} else {
			}
		} catch (IOException e) {
			log.error("send  to [{}] error message:{}", url + method + paramStr, e);
			e.printStackTrace();
		}
		return null;
	}
}
