package ouc.sei.beans;
public class Rule{
	public String urlRegex = null;   //url正则表达式匹配规则
	public String xmlTem = null;     //规则对应的xml模板
	public String showTem = null;    //规则对应的显示模板
	
	/**
	 * default constructor
	 */
	public Rule(){
		
	}
	
	/**
	 * 含餐构造函数
	 * @param urlRegex  正则表达式匹配规则
	 * @param xmlTem    xml模板
	 * @param showTem   显示模板
	 */
	public Rule(String urlRegex, String xmlTem, String showTem){
		this.urlRegex = urlRegex;
		this.xmlTem = xmlTem;
		this.showTem = showTem;
	}
	
	public String getUrlRegex() {
		return urlRegex;
	}
	public void setUrlRegex(String urlRegex) {
		this.urlRegex = urlRegex;
	}
	public String getXmlTem() {
		return xmlTem;
	}
	public void setXmlTem(String xmlTem) {
		this.xmlTem = xmlTem;
	}
	public String getShowTem() {
		return showTem;
	}
	public void setShowTem(String showTem) {
		this.showTem = showTem;
	}
}