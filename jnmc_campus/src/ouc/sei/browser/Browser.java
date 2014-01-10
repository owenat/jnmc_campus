package ouc.sei.browser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import ouc.sei.browser.WebEncoding;
import ouc.sei.beans.ClientBean;
import ouc.sei.common.Constant;
import ouc.sei.transform.DomOperater;

/**
 * 类作用：抽象类，封装了所有关于虚拟浏览器的基本操作。 子类继承，实现抓取asp.net分页的防范
 * 
 * @author qsw-myonlystar
 */

public  class Browser 
{
	protected String webUrl;// web网站页面地址
	protected String htmlStr;// web页面源代码
	protected String xmlTemplate;// 对应抽取xml的模板的名字
	protected String jspTemplate;// 对应的美化生成jsp模板的名字
	protected String viewstate = null;
	protected String userId = null;
	protected Boolean postOrGet = false;// 用于判断这个请求是post还是get请求
	protected Map<String, String[]> map = null;// 当请求为post请求时把请求参数发送过来

	public void setPostOrGet(Boolean postOrGet) {
		this.postOrGet = postOrGet;
	}

	public void setMap(Map<String, String[]> map) {
		this.map = map;
	}

	/**
	 * 构造函数
	 * 
	 * @param wapUrl
	 * @param webUrl
	 */
	public Browser(String webUrl) {
		this.webUrl = webUrl;
		// System.out.println("this is in father");
	}

	/**
	 * 缺省构造函数
	 */
	public Browser() {
		// System.out.println("this is in father");
	}

	/**
	 * 2011-9-7 @author zhangjing
	 * 
	 * @param i
	 * @return
	 * @throws IOException
	 */
	public String getSource(int i) throws IOException 
	{
		URL url = new URL(this.webUrl);
		URLConnection conn = url.openConnection();
		String pagetype = null;
		String pageinfo = conn.getContentType();
		WebEncoding web=new WebEncoding();
		String pagecode = web.getCharset(webUrl);
		System.out.println("==========In Browser.getSurce,pageinfo========="+pageinfo);
		System.out.println("==========In Browser.getSurce,pagecode========="+pagecode);
		if (pageinfo != null && pagecode != null) 
		{
//			String[] types = pageinfo.split(";");
//			pagetype = types[0].replaceAll(" ", "");
//			System.out.println("In if pagetype::" + pagetype);
//			String encode = types[1];
//			String[] encodes = encode.split("=");
//			//encodetype = encodes[1].replaceAll(" ", "");
			System.out.println("======In Browser.getSurce======" + Constant.pageEncode);
			if(pagecode.length()<8)
				Constant.pageEncode = pagecode;
			else 
				Constant.pageEncode = "utf-8";
		} else 
		{	
			if (pageinfo != null) 
			{
				pagecode = "GBK";
				Constant.pageEncode = pagecode;
			}
		}
		String source = "";
		if (Constant.clientList.containsKey(i)) {
			MyClient myClient = new MyClient();
			HttpClient httpclient = Constant.clientList.get(i).getClient();
			Constant.clientList.get(i).setTime(30);//如果这个用户继续请求，那么将其time设置为30分钟
			System.out.println("======In Browser clientId ======" + i);
			if (postOrGet) {
				source = myClient.doPost(httpclient, webUrl, map);
			} else {
				source = myClient.doGet(httpclient, webUrl);
			}

		} else {
			System.out.println("======In Browser New Creat httpClient ID is ======" + i);
			MyClient myClient = new MyClient();
			HttpClient httpClient = new DefaultHttpClient();
			Constant.clientList.put(i, new ClientBean(30,httpClient));//新建用户设置为30分钟
			if (postOrGet) {
				source = myClient.doPost(httpClient, webUrl, map);
			} else {
				source = myClient.doGet(httpClient, webUrl);
			}
		}
		return StrUTF(source);
	}


	/**
	 * HTML转化为标准的XML的DOM对象
	 * 
	 * @param htmlStr
	 *            html流
	 * @return
	 */
	public Document HTMLToXML(String htmlStr) {
		// TODO Auto-generated method stub
		Document doc = null;
		try {
			InputStream in = String2InputStream(htmlStr);
			// 设置 tidy ，准备转换
			Tidy tidy = new Tidy();
			tidy.setXmlOut(true); // 输出格式 xml
			//tidy.setDropFontTags(true); // 删除字体节点
			tidy.setDropEmptyParas(true); // 删除空段落
			tidy.setFixComments(true); // 修复注释
			tidy.setFixBackslash(true); // 修复反斜杆
			tidy.setMakeClean(true); // 删除混乱的表示
			tidy.setPrintBodyOnly(true);
			tidy.setQuoteNbsp(false); // 将空格输出为 &nbsp;
			tidy.setQuoteMarks(false); // 将双引号输出为 &quot;
			tidy.setQuoteAmpersand(true); // 将 &amp; 输出为 &
			tidy.setShowWarnings(false); // 不显示警告信息
			tidy.setInputEncoding("UTF-8"); // 输入的流的编码为utf-8
			tidy.setOutputEncoding("UTF-8"); // 输出流的编码为ｕｔｆ－８

			doc = tidy.parseDOM(in, null); // 通过 JTidy 将 HTML 网页解析为
			in.close(); // W3C 的 Document 对象

			NodeList list = doc.getChildNodes(); // 页面中 DOCTYPE 中可能问题
			for (int i = 0; i < list.getLength(); i++) // 删除 DOCTYPE 元素
			{
				Node node = list.item(i);
				if (node.getNodeType() == Node.DOCUMENT_TYPE_NODE) // 查找类型定义节点
				{
					node.getParentNode().removeChild(node);
					/*
					 * if (log.isLoggable(Level.INFO)) {
					 * log.info("已经将文档定义节点删除！"); }
					 */
				}
			}
			 list = doc.getElementsByTagName("script"); // 脚本中的注释有时有问题
			 for (int i = 0; i < list.getLength(); i++) 
			 { // 清理 script 元素
			 Element script = (Element) list.item(i);
			 script.getParentNode().removeChild(script);
			 if (script.getFirstChild() != null) { 
				 script.removeChild(script.getFirstChild());
				 }
			 }

			 
			 list = doc.getElementsByTagName("iframe");
				for (int i = 0; i < list.getLength(); i++) {
					// list.item(i).getParentNode().removeChild(list.item(i));
					Element script = (Element) list.item(i);
					script.getParentNode().removeChild(script);

					// if (script.getFirstChild() != null) {
					// /*
					// * if (log.isLoggable(Level.FINEST)) { log.finest("删除脚本元素: "
					// * + script.getFirstChild().getNodeValue()); }
					// */
					// script.removeChild(script.getFirstChild());
					//
					// }
				}
//				list = doc.getElementsByTagName("style"); // 脚本中的注释有时有问题
//				for (int i = 0; i < list.getLength(); i++) { // 清理 script 元素
//					// list.item(i).getParentNode().removeChild(list.item(i));
//					Element script = (Element) list.item(i);
//					if (script.getFirstChild() != null) {
//						/*
//						 * if (log.isLoggable(Level.FINEST)) { log.finest("删除脚本元素: "
//						 * + script.getFirstChild().getNodeValue()); }
//						 */
//						script.removeChild(script.getFirstChild());
//
//					}
//				}
				
			list = doc.getElementsByTagName("head"); // 脚本中的注释有时有问题
			// System.out.println("第一个head标签："+list.item(0).toString()+"haha");
			for (int i = 0; i < list.getLength(); i++) { // 清理 head标签

				list.item(i).getParentNode().removeChild(list.item(i));
			}

			list = doc.getElementsByTagName("object"); // 脚本中的注释有时有问题
			for (int i = 0; i < list.getLength(); i++) { // 清理 head标签

				list.item(i).getParentNode().removeChild(list.item(i));
			}
		} catch (Exception e) {
			// log.severe(e.getMessage());
			e.printStackTrace();
		} finally {

		}

		/**
		 * 2011-10-14 @author zhangjing 将转换得到的源码打印到文件
		 */
		DomOperater operater = new DomOperater();
		operater.dom2xmlFile(doc);
		return doc;
	}

	
	public String domtostring(Document dom)
	{
		
		String xhtml="";
		return xhtml;
	}
	
	/**
	 * 把String类型转化为InputStream类型
	 * 
	 * @param str
	 *            字符串
	 * @return InputStream
	 */
	public InputStream String2InputStream(String str) {
		// 用新的字符编码生成字符串
		ByteArrayInputStream stream = null;
		try {
			stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stream;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getHtmlStr() {
		try {
			htmlStr = new String(htmlStr.getBytes("UTF-8"), "UTF-8");
		} catch (Exception e) {// UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return htmlStr;
	}

	/**
	 * 对传入的字符串进行utf-8编码
	 * 
	 * @param str
	 * @return
	 */
	private String StrUTF(String str) {
		try {
			str = new String(str.getBytes("UTF-8"), "UTF-8");
		} catch (Exception e) {// UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public void setHtmlStr(String htmlStr) {
		this.htmlStr = htmlStr;
	}

	public String getXmlTemplate() {
		return xmlTemplate;
	}

	public void setXmlTemplate(String xmlTemplate) {
		this.xmlTemplate = xmlTemplate;
	}

	public String getJspTemplate() {
		return jspTemplate;
	}

	public void setJspTemplate(String jspTemplate) {
		this.jspTemplate = jspTemplate;
	}

}
