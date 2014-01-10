package ouc.sei.operate;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import ouc.sei.beans.ClientBean;
import ouc.sei.common.Constant;
/**
 * 有关于client的一些公共操作
 * @author liu
 *
 */


public class CommonFunctions 
{
	/**
	 * 根据clientid 获取对应的client
	 * @param clientId
	 * @return
	 */
	public static HttpClient getClient(int clientId)
	{
		HttpClient httpclient=null;
		if(Constant.clientList.containsKey(clientId))
		{
			httpclient = Constant.clientList.get(clientId).getClient();
			//Constant.clientList.get(clientId).setTime(Constant.sessiontime);
		}
		else
		{
			httpclient = new DefaultHttpClient();
			//Constant.clientList.put(clientId, new ClientBean(Constant.sessiontime,httpclient));
		}
		System.out.println("In operate ,CommonFunctions.java is selected!!!");
		return httpclient;
	}
	/**
	 * 组织页面之间的参数，以字符串形式返回
	 * @param paras
	 * @return
	 */
	public static String getTheParams(Map paras)//show.jsp里引用方法
	{
		StringBuilder sb = new StringBuilder();
		Set set = paras.keySet();
		Iterator key = set.iterator();
		sb.append("<div>");
		while (key.hasNext()) 
		{
			String onekey = (String) key.next();
			if(!onekey.equals("webUrl")&&!onekey.equals("temtype")&&!onekey.equals("middleparams"))
			{
				String[] onevalue = (String[]) paras.get(onekey);
				String thevalue="";
				thevalue=onevalue[0];
				System.out.println("key:" + onekey.toString() + "  value:"+ thevalue);
				sb.append("<input type=\"hidden\" value=\"");
				sb.append(thevalue);
				sb.append("\" name=\"");
				sb.append(onekey);
				sb.append("\"/>");
			}
			
		}
		sb.append("</div>");
		System.out.println("In operate ,CommonFunctions.java is selected!!!");
		return sb.toString();
	}
	public static void main(String []args)
	{
		String s="sd";
		if(!s.equals("webUrl")&&!s.equals("temtype")&&!s.equals("middleparams")&&!s.equals("actionUrl")){
			System.out.println("==");
		}else{
			System.out.println("sdfsdf=");
		}
		
	}

}
