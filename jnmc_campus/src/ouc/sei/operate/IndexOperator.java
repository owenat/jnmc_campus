package ouc.sei.operate;
/**
 *@author LF
 *@version 2010-10-15下午04:09:27
 *处理首页字符串获取
 **/
import java.io.IOException;
import ouc.sei.browser.*;
public class IndexOperator {
	public static String iframeurl1=null;
	public static String iframeurl2=null;
	
	
	public String getHtmlStr(){
		String htmlStr1="";
		String htmlStr2="";
		try {
		Browser browser=new Browser();
		browser.setWebUrl(iframeurl1);
		htmlStr1=browser.getSource(0);//可以得到htmlSr
		System.out.println("iframeurl1:"+IndexOperator.iframeurl1);
		browser.setWebUrl(iframeurl2);
		htmlStr2=browser.getSource(0);	
//		System.out.println("iframeurl2:"+IndexOperator.iframeurl2);
		} catch (IOException e) {
			System.out.println("ClassNotFoundException4");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!htmlStr1.equals("")){
			htmlStr1=htmlStr1.replace("document.write(\"", "");
			htmlStr1=htmlStr1.replace("\");", "");
			htmlStr1=htmlStr1.replace("<br>", "");
			htmlStr1=htmlStr1.replace("&nbsp;", "");
			htmlStr1=htmlStr1.replace("/news/Article", "http://news.ouc.edu.cn/news/Article");
			//htmlStr1="<a href=\"http://news.ouc.edu.cn/news/Article/class3/\">海大要闻</a>"+htmlStr1;
		}
		if(!htmlStr2.equals("")){
			htmlStr2 = htmlStr2.replace("document.write (\"", "");
			//htmlStr2=htmlStr2.replaceAll("document.write(\"", "");
			htmlStr2 = htmlStr2.replace("&nbsp;", "");
			htmlStr2 = htmlStr2.replace("<br>\");", "");
			
			//htmlStr2="<a href=\"http://www2.ouc.edu.cn/yaowen/article_js.asp?ClassID=2&amp;IncludeChild=true&amp;SpecialID=&amp;ArticleNum=&amp;ShowType=1&amp;ShowCols=1&amp;ShowProperty=false&amp;ShowClassName=false&amp;ShowIncludePic=false&amp;ShowTitle=true&amp;ShowUpdateTime=true&amp;ShowHits=true&amp;ShowAuthor=false&amp;ShowHot=false&amp;ShowMore=false&amp;TitleMaxLen=&amp;ContentMaxLen=2000&amp;Hot=false&amp;Elite=false&amp;DateNum=&amp;OrderField=ArticleID&amp;OrderType=desc\">海大公告</a>"+htmlStr2;
		}
		htmlStr1="<result>"+htmlStr1+htmlStr2+"</result>";
//		htmlStr1 = htmlStr1.replaceAll("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">", " ");
		
//		System.out.println("====44=="+htmlStr1); 
//		System.out.println(htmlStr1.contains("?")); 
		String t = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
		int tl = t.length();
		String t1= htmlStr1.substring(0, htmlStr1.indexOf(t)-1)+
		htmlStr1.substring(htmlStr1.indexOf(t)+tl);
		System.out.println("---"+t1);
		return htmlStr1;
	} 
	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s="sdfsd?sdfsd?Fsd?Fsd?FsD?FsD?FsDF?sdf";
		System.out.println(s.replaceAll("\\?", ""));
		

	}

}
