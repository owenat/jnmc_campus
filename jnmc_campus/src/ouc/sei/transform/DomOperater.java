package ouc.sei.transform;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ouc.sei.common.Constant;

public class DomOperater {
	/**
	 * 将字符串转化为dom对象
	 * 
	 * @param xmlStr
	 * @return
	 */
	public Document stringToDom(String xmlStr) {
		StringReader sr = new StringReader(xmlStr);
		InputSource is = new InputSource(sr);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document dom = null;
		try {
			builder = factory.newDocumentBuilder();
			dom = builder.parse(is);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dom;
	}

	public String domToString(Document dom) {
		String xmlStr = "";
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = null;
		try {
			t = tf.newTransformer();
			t.setOutputProperty("encoding", "UTF-8");// 解决中文问题，试过用GBK不行
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			t.transform(new DOMSource(dom), new StreamResult(bos));
			xmlStr = bos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlStr;
	}
	/**
	 * 把Document转化为xml文件
	 * 
	 * @param dom
	 *            void
	 */
	public void dom2xmlFile(Document dom) {
		// 获得将DOM文档转化为XML文件的转换器，在jdk1.4中，有类TransformerFactory
		// 来实现，类Transformer实现转化API。
		TransformerFactory tfactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tfactory.newTransformer();
			// 将DOM对象转化为DOMSource类对象，该对象表现为转化成别的表达形式的信息容器。
			DOMSource source = new DOMSource(dom);
			// 获得一个StreamResult类对象，该对象是DOM文档转化成的其他形式的文档的容器，可以是XML文件，文本文件，HTML文件。这里为一个XML文件。
			StreamResult result = new StreamResult(new File("/output.xml"));
			// 调用API，将DOM文档转化成XML文件。
			try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void document2xml(Document dom) {
		TransformerFactory tfac = TransformerFactory.newInstance();
		StringWriter strWtr = new StringWriter();
		StreamResult strResult = new StreamResult(strWtr);

		try {
			Transformer trans = tfac.newTransformer();
			trans.transform(new DOMSource(dom.getDocumentElement()), strResult);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(strResult.getWriter().toString());
	}
}
