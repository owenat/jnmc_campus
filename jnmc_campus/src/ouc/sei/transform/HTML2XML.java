package ouc.sei.transform;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

import ouc.sei.common.Constant;

public class HTML2XML {
	private Templates template;
	// private String baseUrl = CommonList.baseUrl;//URL基地址
	private String picSaveUrl = Constant.picSaveUrl;// 图片的默认存储路径
	// 图片保存的路径
	private String xmlStr = null;
	private String webUrl = null;
	private String xmlTem=null;
	private Document dom;
	private int clientId;//临时加上的，为了请求图片，尤其是请求验证码时使用。 杨志龙
	
	public HTML2XML(){}
	public String getXmlStr() {
		return this.xmlStr;
	}
	
	/**
	 * @param htmlStr
	 * @param xmlTemplate
	 * @param webUrl
	 * @param flag
	 *  
	 */
	public HTML2XML(Document dom, String xmlTemplate, String webUrl ,Integer clientId) {
		this.clientId = clientId;
		this.webUrl = webUrl;
		this.xmlTem=xmlTemplate;
		setXSLT(xmlTemplate);
		if (this.template != null) {	
			this.xmlStr = TransformerStr(dom);
		}
	}
	/**
	 * 把String类型转化为InputStream类型
	 * @param str 字符串
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

	/**
	 * 解析转换的样式表，保存为模板
	 * @param xsltFileName
	 *            样式表文件名
	 * @return 样式表模板对象
	 */
	public Templates setXSLT(String xsltFileName) {
		Logger log = Logger.getLogger("setXSLT");
		String xslPath = this.getClass().getResource("/").getPath()
				+xsltFileName;
		File xsltFile = new File(xslPath);
		System.out.println("xslPath="+xslPath);
		StreamSource xsltSource = new StreamSource(xslPath); // 使用 JAXP
		// 标准方法建立样式表的模板对象
		TransformerFactory tff = TransformerFactory.newInstance(); // 可以重复利用这个模板
		Templates template = null;
		try {
			template = tff.newTemplates(xsltSource);
		} catch (TransformerConfigurationException e) {
			log.severe(e.getMessage());
		}
		this.template = template;
		return template;
	}

	/**
	 * 结合前面setXSLT所保存的模版，对htmlStr进行第一次提取，返回xmlstr
	 * 
	 * @param doc
	 * @return
	 */
	private String transformToXml(Document doc) {
		String xmlstr = "";
		Logger log = Logger.getLogger("transformToXml");
		try {
			Source source = new DOMSource(doc);
			ByteArrayOutputStream ns = new ByteArrayOutputStream();// 创建一个新的字节流数组
			Result result = new StreamResult(ns);
			Transformer transformer = template.newTransformer(); // 使用保存的样式表模板对象
			transformer.transform(source, result); // 生成转换器，转换文档对象
			byte[] buff = ns.toByteArray();
			xmlstr = ns.toString("UTF-8");
			xmlstr = new String(xmlstr.getBytes("UTF-8"), "UTF-8");
			ns.close();
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return xmlstr;
	}

	/**
	 * 函数作用是返回处理好的xml字符串
	 * 
	 * @param doc
	 * @return
	 */
	private String TransformerStr(Document doc) {
		String nxmlStr = "";
		String xmlstr = transformToXml(doc);// 
		
		StringBuffer sb = new StringBuffer();
		String line;
		List<String> list;
		Pattern pattern = Pattern.compile("^.*arrUrl\\[.*jpg.*$");
		BufferedReader br = new BufferedReader(new StringReader(xmlstr));
		sb.append("<result>");
		try {
			while ((line = br.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				if (matcher.matches()) {
					list = new ArrayList<String>();
					list.add(line);
					for (int i = 0; i < list.size(); i++) {
						int n = list.get(i).length() - 2;
						int m = list.get(i).indexOf("'");
						line = "<img src=\""
								+list.get(i).toString().substring(m + 1, n)
								+ "\"/>";
						sb.append(line);
					}
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DomOperater domo = new DomOperater();
		System.out.println("ttttttttttttttttttweb"+webUrl);
		if (webUrl.contains("Photo/ShowPhoto")) {
			sb.append("</result>");
			 dom = domo.stringToDom(sb.toString());// 将字符串转化为dom对象
			 System.out.println("tttttttttttttttttttttttttttt"+sb.toString());
//			 this.xmlStr = sb.toString();
//			 return  sb.toString();
		} else {
			 dom = domo.stringToDom(xmlstr);// 将字符串转化为dom对象
		}
		try {
			System.out.println("In HTML2XML clientId is "+ clientId);
			DomFilters dfs = new DomFilters(dom,picSaveUrl,webUrl,clientId);
			dom=dfs.getDom();
			nxmlStr = domo.domToString(dom);
			this.xmlStr = nxmlStr;
			return nxmlStr;
		} catch (Exception e) {
		}
		
		
		return nxmlStr;
	}
	
	
}