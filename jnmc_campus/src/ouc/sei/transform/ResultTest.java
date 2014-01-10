package ouc.sei.transform;
/**
 * 项目进行中的测试类
 */
import java.io.IOException;

import org.w3c.dom.Document;

import ouc.sei.browser.AspString;
import ouc.sei.browser.Browser;

public class ResultTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Browser bro = new AspString(
				"http://www.qdsf.gov.cn/n241/n10213388/syhljbindex.html");
		String htmlStr = null;
		try {
			htmlStr = bro.getSource(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 可以得到htmlSr
		System.out.println(htmlStr);
		HTML2XML h2x = new HTML2XML();
		// hx.parser(htmlStr, "doc/test.xml");
		Document dom = bro.HTMLToXML(htmlStr);
		// //下面三行代码是测试将源代码初步规范化以后将XML保存到本地的XML文件
		// DomOperater domo = new DomOperater();
		// domo.dom2xmlFile(dom);
		// System.out.println("dom对象已经生成");
		//
		// //下面三行代码是按照抽取模板对源码就行信息抽取
		// h2x.setXSLT("formXml.xsl");
		// String xml = h2x.TransformerStr(dom);
		// System.out.println("===========" + xml);
		//	
		// //下面三行代码是测试按照渲染模板对抽取内容进行格式调整
		// XmlToJsp x2j = new XmlToJsp();
		// String str= x2j.transform(xml,"zuzhijigouJsp.xsl");
		// System.out.println("--------str--"+str);

	}

}
