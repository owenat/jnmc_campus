package ouc.sei.beans;

public class HtmlFilterBean {
	String oldStr = null;
	String newStr = null;
	
	public HtmlFilterBean(String oldStr, String newStr) {
		super();
		this.oldStr = oldStr;
		this.newStr = newStr;
	}
	public String getOldStr() {
		return oldStr;
	}
	public void setOldStr(String oldStr) {
		this.oldStr = oldStr;
	}
	public String getNewStr() {
		return newStr;
	}
	public void setNewStr(String newStr) {
		this.newStr = newStr;
	}
}
