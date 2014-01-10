/**
 * 2011-10-11
 * @author zhangjing
 */
package ouc.sei.regexOperate;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import ouc.sei.transform.DomOperater;

import java.util.List;

import java.util.Iterator;


public class RegexOperater {
	private String regexTem="";
	private Vector<Regex> regexList = new Vector<Regex>();//存储regexTem中的正则表达式
	
	/**
	 * 获得匹配规则文件
	 * @param webUrl
	 * @return 正则表达式文件名
	 */
	public String getRegexTem(String webUrl){
		
		this.regexTem=null;
		int length=RegexList.RegexRuleList.size();
		int i=0;
		boolean isOk=false;
		while(i<length&&isOk==false){
			String urlRegex = RegexList.RegexRuleList.elementAt(i).getUrlRegex();
			if(regexStr(webUrl,urlRegex)){
				this.regexTem = RegexList.RegexRuleList.elementAt(i).getRegexTem();
				isOk=true;
			}
			i++;
		}
		return this.regexTem;
	}
	
	/**
	 * 字符串匹配
	 * @param inputStr
	 * @param regex
	 * @return
	 */
	public boolean regexStr(String inputStr,String regex){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(inputStr);
		return m.matches();
	}
	
	/**
	 * 将输入字符串进行正则抽取，将抽取得到的内容以新的节点添加到如数字符串中
	 * @param inputStr
	 * @return
	 */
	public String regexExtract(String inputStr){
		getRegexList();
		int size = this.regexList.size();
		for(int i = 0;i<size;i++){
			Regex reg = this.regexList.elementAt(i);
			Regex resReg=regexMatch(inputStr,reg);
			
			inputStr=addresStr(inputStr,resReg);
			
		}
		
		return inputStr;
	}

	
	/**
	 * 将匹配结果添加为新字符
	 * @param inputStr
	 * @param reg
	 */
	private String addresStr(String inputStr, Regex reg) {
		// TODO Auto-generated method stub
		DomOperater operater = new DomOperater();
		org.w3c.dom.Document doc =  operater.stringToDom(inputStr);
		org.w3c.dom.Element root =  doc.getDocumentElement();//获得根节点
		System.out.println("要添加的节点是："+reg.getRegexName());
		org.w3c.dom.Element newElm = doc.createElement(reg.getRegexName().toString());
		newElm.setTextContent(reg.getResStr());
		root.appendChild(newElm);
		
//		ele.addElement(reg.getRegexName().toString());
////		ele.addElement(reg.getRegexName());
//		ele.element(reg.getRegexName()).setText(reg.getResStr());
		
		inputStr = operater.domToString(doc);
		System.out.println("添加节点之后的字符串"+inputStr);
		
		return inputStr;
	}

	/**
	 * 正则匹配
	 * @param inputStr
	 * @param reg
	 * @return
	 */
	private Regex  regexMatch(String inputStr, Regex reg) {
		// TODO Auto-generated method stub
		System.out.println("in regexMatch inputStr="+inputStr);
		String name= reg.getRegexName();
		String regex = reg.getRegex();
		System.out.println("in regexMatch regex=="+regex);
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(inputStr);
		StringBuffer res = new StringBuffer("");
		while(m.find())
		{
			res.append(m.group());
			
		}
		
		String resStr=res.toString();
		System.out.println("抽取结果："+resStr);
		reg.setResStr(resStr);
		
		
		return reg;
	}

	/**
	 * 得到regexTem文件中的所有正则表达式
	 * @param regexTem
	 */
	private boolean getRegexList() {
		// TODO Auto-generated method stub
		String regexPath = this.getClass().getResource("/").getPath()+"regex/"+this.regexTem;
		System.out.println("in getRegexList,regex文件Path是："+regexPath);
		
		try {
			regexPath=java.net.URLDecoder.decode(regexPath,"UTF_8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SAXReader reader = new SAXReader();
		File regexFile=new File(regexPath);
		try {
			Document document = reader.read(regexFile);
			Element root = document.getRootElement();
			List nodes = root.elements();//获得所有子节点
			for(Iterator it =nodes.iterator(); it.hasNext();){
				Element elm = (Element)it.next();
				String regStr = elm.getText();
				String regName = elm.getName();
				this.regexList.add(new Regex(regName,regStr,""));
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/***
		 * d打印下
		 */
		for(Iterator it =this.regexList.iterator(); it.hasNext();){
			Regex reg = (Regex)it.next();
			String regex = reg.getRegex();
			String name= reg.getRegexName();
			System.out.println("RegexName=="+name+"\n regex=="+regex);
		}
			return true;
	}

	
	
	public static void main (String [] args){
		String inputStr="<rusult><p>12334456chncvbnmcvbn太阳，出来看花旗，12148，hellocvbnm,dfj,</p></rusult>";
		String regName = "regex1";
		String regex="[\u4E00-\u9FA5]";
		Regex  regTest = new Regex(regName,regex,"");
		RegexOperater operator = new RegexOperater();
//		String resStr = operator.regexExtract(inputStr,regTest);
		
		Regex resReg=operator.regexMatch(inputStr,regTest);
		
		String resStr=operator.addresStr(inputStr,resReg);
		System.out.println("result=="+resStr);
	}
	
}
