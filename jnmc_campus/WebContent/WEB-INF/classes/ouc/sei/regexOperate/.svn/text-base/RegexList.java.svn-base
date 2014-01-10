/**
 * 2011-10-11
 *@author zhangjing 
 */
package ouc.sei.regexOperate;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import ouc.sei.common.Constant;


/**
 * 
 *
 */
public class RegexList {

	public static Vector<RegexRule> RegexRuleList = new Vector<RegexRule>();//存储正则匹配的链表
	public static RegexList instance;
	
	
	private RegexList (){
		
		String  xslPath = Constant.classPath;
		String configPath=xslPath+ "regexConfig.xml";//返回服务区绝对路径
		System.out.println("configPath is===="+configPath);
		
		
		try {
			configPath=java.net.URLDecoder.decode(configPath,"UTF_8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try{
			SAXReader reader = new SAXReader();
			File file = new File(configPath);
			Document document = reader.read(file);//获得constant.xml的document对象
			Element rootElm = document.getRootElement();//获得跟节点
			List nodes = rootElm.elements("rule");//获得所有rule节点的集合
			for (Iterator it = nodes.iterator(); it.hasNext();){
				Element elm = (Element)it.next();
				String urlRegex=elm.element("urlRegex").getText();
				String regexTem = elm.element("regexFile").getText();
				this.RegexRuleList.add(new RegexRule(urlRegex,regexTem));
				
			}
		}catch(Exception e){
			System.out.println(e);
		}
		
		/**
		 * 打印一下
		 */
		for(Iterator it=this.RegexRuleList.iterator();it.hasNext();){
			RegexRule rule = (RegexRule)it.next();
			System.out.println("urlRegex=="+rule.getUrlRegex()+"\n rexgexTem==="+rule.getRegexTem());	
		}
	}
	
	
	public static RegexList newInstance(){
		if(instance == null){
			instance = new RegexList();
		}
		return instance;
	}
	
	
}
