package ouc.sei.browser;

import java.io.IOException;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;

import ouc.sei.common.Constant;

/**
 * @author LF
 * @version 创建时间：2011-1-4 下午08:25:47
 */
public class MyClient {

	public String doGet(HttpClient httpclient, String getUrl) {
		String a = "";
		System.out.println("In MyClient.doGet,url=" + getUrl);

		getUrl = getUrl.replace(" ", "");
		HttpGet httpget = new HttpGet(getUrl);
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				System.out.println("MyClient网页编码"+Constant.pageEncode);
				a = new String(EntityUtils.toByteArray(response.getEntity()),
						Constant.pageEncode);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpget.abort();
		}
		return a;
	}

	
	public String doPost(HttpClient client,String posturl, Map<String,String[]> paras) {
		HttpClient httpclient = client;
		HttpPost httpost = new HttpPost(posturl);
		HttpClientParams.setCookiePolicy(httpclient.getParams(),
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpost.setHeader("Content-Type",
				"application/x-www-form-urlencoded;charset="
						+ Constant.pageEncode);
		// 设置参数的集合
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// 设置第一个参数，格式是name:value
		Set<String> set = paras.keySet();
		Iterator<String> key = set.iterator();
		while (key.hasNext()) {
			String onekey = key.next();
			String[] onevalue = (String[]) paras.get(onekey);
			String thevalue = "";

			thevalue = onevalue[0];// java.net.URLEncoder.encode(onevalue[0],"gb2312");
			nvps.add(new BasicNameValuePair(onekey.toString().trim(), thevalue));

			System.out.println("key:" + onekey.toString() + "  value:"
					+ onevalue[0]);
		}
		UrlEncodedFormEntity urlEntity = null;

		try {
			urlEntity = new UrlEncodedFormEntity(nvps, "utf-8");
			urlEntity.setContentEncoding(Constant.pageEncode);
			urlEntity
					.setContentType("application/x-www-form-urlencoded;charset="
							+ Constant.pageEncode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 设置实体
		httpost.setEntity(urlEntity);
		HttpResponse res = null;
		try {
			res = httpclient.execute(httpost);
			String str2 = EntityUtils.toString(res.getEntity(),
					Constant.pageEncode);
			if (res.getStatusLine().getStatusCode() == 303
					|| res.getStatusLine().getStatusCode() == 302
					|| res.getStatusLine().getStatusCode() == 307) {
				String secondPath = res.getFirstHeader("Location").getValue();
				str2 = doGet(httpclient,secondPath);
			}
			return str2;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpost.abort();
		}
		return null;

	}


	public static void main(String[] args) {
	}
}
