package ouc.sei.browser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * 类封装了一些对httpclient抓取页面内容的一些基本操作
 * @author qsw-Myonlystar
 * 
 */
public class ClientOperate {
	/**
	 * 处理一个实体
	 * @param entity
	 */
	
	
	public StringBuffer processEntity(HttpEntity entity,String encodType) {
		StringBuffer sb = new StringBuffer();
		InputStreamReader iReader = null;
		try {
			// 从消息实体中获取输入流对象
			InputStream inputStream = entity.getContent();
			iReader = new InputStreamReader(inputStream,encodType);
			BufferedReader reader = new BufferedReader(iReader);
			String line = null;
			// 用reader.ready()是不行的，这是用来判断此流是否已准备好被读取
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\r\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				iReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb;

	}

	/**
	 * 判断服务器页面是否重定向
	 * 
	 * @param statuscode
	 *            response中的状态码
	 * @return
	 */
	public static boolean ifRedirect(int statusCode) {
		boolean flag = false;
		if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
				|| (statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
				|| (statusCode == HttpStatus.SC_SEE_OTHER)
				|| (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
			flag = true;
		}

		return flag;
	}
	/**
	 * 打印服务器返回的状态
	 * @param response
	 */
	public void printResponseStatus(HttpResponse response){
		System.out.println("-----------服务器返回状态------------------------");
		System.out.println(response.getStatusLine()); // 服务器返回状态
		System.out.println("-----------服务器返回状态------------------------");
	}
	/**
	 * 打印返回的头部信息
	 * @param response
	 */
	public void printAllHeaders(HttpResponse response){
		
		System.out.println("-----------返回的HTTP头信息------------------------");
		Header[] headers = response.getAllHeaders(); // 返回的HTTP头信息
		for (int i = 0; i < headers.length; i++) {
			System.out.println(headers[i]);

		}
		System.out.println("-------------返回的HTTP头信息----------------");	
	}
	/**
	 * 打印cookie信息
	 * @param httpclient
	 */
	public void printCookie(DefaultHttpClient httpclient){
		 System.out.println("Initial set of cookies:");  
	        List<Cookie> cookies = httpclient.getCookieStore().getCookies();  
	        if (cookies.isEmpty()) {  
	            System.out.println("None");  
	        } else {  
	            for (int i = 0; i < cookies.size(); i++) {  
	                System.out.println("- " + cookies.get(i).toString());  
	            }  
	        } 
		
	}
	
}
