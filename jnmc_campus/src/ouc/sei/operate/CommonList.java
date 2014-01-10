package ouc.sei.operate;

/**
 * 读取constant.xml配置文件，初始化规则链表rulelist
 * @author qsw-Myonlystar
 */
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import ouc.sei.beans.FilterBean;
import ouc.sei.beans.HtmlFilterBean;
import ouc.sei.beans.RedirectBean;
import ouc.sei.beans.Rule;
import ouc.sei.common.Constant;
import ouc.sei.docoperator.DocNametoHash;
import ouc.sei.imgoperate.PicNameToHash;

public class CommonList {

	// private Rule rule = new Rule();
	private static CommonList instance;

	private CommonList() {
		String xslPath = this.getClass().getResource("/").getPath();
		String configpath = xslPath + "config.xml";// 返回服务区绝对路径
//		String regexfilepath = xslPath + "redirectpage.xml";
		String afilterPath = xslPath + "afilter.xml";
		String imgfilterPath = xslPath + "imgfilter.xml";
//		String formconfigPath = xslPath + "formconfig.xml";
//		String secondHandleFliter = xslPath + "secondHandle.xml";
		String htmlFilter = xslPath + "htmlFilter.xml";

		System.out.println("=====path=" + xslPath);
		String picPath = Constant.picSaveUrl;// 返回图片文件夹的绝对路径

		try {
			configpath = java.net.URLDecoder.decode(configpath, "UTF-8");
//			regexfilepath = java.net.URLDecoder.decode(regexfilepath, "UTF-8");
			//picPath = java.net.URLDecoder.decode(picPath, "UTF_8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	/*	String filePath = Constant.documentsSavePath;
		System.out.println("======In CommonList documentSavePath======"
				+ Constant.documentsSavePath);
		DocNametoHash dnth = new DocNametoHash(filePath);
		dnth.existentFiletoHash();*/

		PicNameToHash pnth = new PicNameToHash(picPath);
		pnth.process();// 把已有的图片文件读到hash表里面
		try {
			SAXReader reader = new SAXReader();
			File file = new File(configpath);
			Document document = reader.read(file);// 获得constant.xml的document对象
			Element rootElm = document.getRootElement();// 获得跟节点
			List nodes = rootElm.elements("rule");// 获得所有rule节点的集合
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				String urlRegex = elm.element("urlRegex").getText();
				String xmlTem = elm.element("xmlTem").getText();
				String showTem = elm.element("showTem").getText();
				Constant.ruleList
						.addElement(new Rule(urlRegex, xmlTem, showTem));
			}
			// 取得最后缺省子节点
			Element defaultElm = rootElm.element("defaultRule");
			String defaultxmlTem = defaultElm.element("xmlTem").getText();
			String defaultshowTem = defaultElm.element("showTem").getText();
			Constant.ruleList.addElement(new Rule(null, defaultxmlTem,
					defaultshowTem));
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("picPath is:" + picPath);
		// 读取页面跳转信息
		/*try {
			SAXReader reader = new SAXReader();
			File file = new File(regexfilepath);
			Document document = reader.read(file);// 获得constant.xml的document对象
			Element rootElm = document.getRootElement();// 获得跟节点
			List nodes = rootElm.elements("rule");// 获得所有rule节点的集合
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				String urlRegex = elm.element("regex").getText();
				String url = elm.elementText("url");// .element("url").getText();
				Constant.resirectList
						.addElement(new RedirectBean(urlRegex, url));
			}
			// 取得最后缺省子节点
		} catch (Exception e) {
			System.out.println(e);
		}*/

		// 读取超链接补全信息
		try {
			SAXReader reader = new SAXReader();
			File file = new File(afilterPath);
			Document document = reader.read(file);// 获得constant.xml的document对象
			Element rootElm = document.getRootElement();// 获得跟节点
			List nodes = rootElm.elements("rule");// 获得所有rule节点的集合
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				String urlRegex = elm.element("regex").getText();
				String rootUrl = elm.element("rootUrlRegex").getText();
				String addStr = elm.elementText("addStr");
				Constant.afilterList.addElement(new FilterBean(rootUrl,urlRegex, addStr));
			}
			// 取得最后缺省子节点
			Element defaultElm = rootElm.element("defaultRule");
			String defAddStr = defaultElm.elementText("addStr");
			Constant.afilterList.addElement(new FilterBean(null,null, defAddStr));
			System.out.println("afilterList.lenght()=="+Constant.afilterList.size());
		} catch (Exception e) {
			System.out.println(e);
		}
		 

		// 读取图片补全信息
		try 
		{
			SAXReader reader = new SAXReader();
			File file = new File(imgfilterPath);//imgfilterPath=imgfilter.xml
			Document document = reader.read(file);
			Element rootElm = document.getRootElement();// 获得跟节点
			List nodes = rootElm.elements("rule");// 获得所有rule节点的集合
			for (Iterator it = nodes.iterator(); it.hasNext();) 
			{
				Element elm = (Element) it.next();
				String rootUrlRegex = elm.element("rootUrlRegex").getText();
				String urlRegex = elm.element("regex").getText();
				String addStr = elm.elementText("addStr");
				Constant.imgfilterList.addElement(new FilterBean(rootUrlRegex,urlRegex,addStr));
			}
			// 取得最后缺省子节点
			Element defaultElm = rootElm.element("defaultRule");
			String defAddStr = defaultElm.elementText("addStr");
			Constant.imgfilterList.addElement(new FilterBean(null,null,defAddStr));
			System.out.println("commonlist="+Constant.imgfilterList.size()+"defAddStr="+defAddStr);
		} catch (Exception e) 
		{
			System.out.println(e);
		}

		/**
		 * 读取需要第二次处理的列表：
		 */
		/*try {
			SAXReader reader = new SAXReader();
			File file = new File(secondHandleFliter);
			Document document = reader.read(file);// 获得constant.xml的document对象
			Element rootElm = document.getRootElement();// 获得跟节点
			List nodes = rootElm.elements("rule");// 获得所有rule节点的集合

			for (Iterator it = nodes.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				String urlRegex = elm.element("regex").getText();
				Constant.secondHandleFliter.addElement(urlRegex);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
*/
/*		// 读取form表单action的url配置信息
		try {
			SAXReader reader = new SAXReader();
			File file = new File(formconfigPath);
			Document document = reader.read(file);
			Element rootElm = document.getRootElement();// 获得跟节点
			List nodes = rootElm.elements("rule");// 获得所有rule节点的集合
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				String urlRegex = elm.element("regex").getText();
				String addStr = elm.elementText("addStr");
				Constant.formConfigList.addElement(new FilterBean(urlRegex,
						addStr));
			}
			// 取得最后缺省子节点
			Element defaultElm = rootElm.element("defaultRule");
			String defAddStr = defaultElm.elementText("addStr");
			Constant.formConfigList.addElement(new FilterBean(null, defAddStr));
		} catch (Exception e) {
			System.out.println(e);
		}*/
		// 对第一次请求来的html源码中的字符进行过滤，要过滤的关键字
		try {
			SAXReader reader = new SAXReader();
			File file = new File(htmlFilter);
			Document document = reader.read(file);
			Element rootElm = document.getRootElement();
			List nodes = rootElm.elements("item");
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				String oldStr = elm.element("oldStr").getText();
				String newStr = elm.elementText("newStr");
				Constant.htmlFilter.add(new HtmlFilterBean(oldStr, newStr));
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static CommonList newInstance() {
		if (instance == null) {
			instance = new CommonList();
		}
		return instance;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		CommonList c = CommonList.newInstance();
		for (Iterator it = Constant.imgfilterList.iterator(); it.hasNext();) {
			Rule rule = (Rule) it.next();
			System.out.println(rule.getUrlRegex() + "   " + "   "
					+ rule.getXmlTem() + "  " + rule.getShowTem());
		}
	}
}