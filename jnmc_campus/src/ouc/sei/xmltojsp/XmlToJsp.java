/**
 *@author:LF
 *2010-3-29
 *说明：装载XML和XSL文件组装html流
 */
package ouc.sei.xmltojsp;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
public class XmlToJsp {
	/**
	 * 无参构造函数
	 */
	public XmlToJsp() {

	}

	/**
	 * 装载xml和xsl，输出html流
	 * @param xmlstr
	 * @param xslFileName
	 * @return htmlStr
	 * @throws Exception
	 */
	public String transform(String xmlstr, String xslFileName) throws Exception {
		System.out.println("sdfsdfsdfsdfds"+this.getClass().getResource("/").getPath());
		String xslPath = this.getClass().getResource("/").getPath()+xslFileName;// 获取文件的绝对路径
		StringWriter writer = new StringWriter();
		StreamResult streamResult = new StreamResult(writer);
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tFactory.newTransformer(new StreamSource(xslPath));
		} catch (TransformerConfigurationException _ex) {
			System.out.println(_ex);
		}
		try {
			StringReader sRead = new StringReader(xmlstr);
			transformer.transform(new StreamSource(sRead), streamResult);
		} catch (TransformerException _ex) {
			System.out.println(_ex);
		}
		String htmlStr=((StringWriter) streamResult.getWriter()).toString();
		htmlStr=new String(htmlStr.getBytes("UTF-8"),"UTF-8");
		//System.out.println(htmlStr);
		return htmlStr;
	}
}
