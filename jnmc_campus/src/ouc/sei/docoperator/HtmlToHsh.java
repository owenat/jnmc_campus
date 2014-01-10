/**
 * @author wangqi
 *2013-8-27 下午10:20:28
 */
package ouc.sei.docoperator;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import ouc.sei.common.Constant;
import ouc.sei.transform.MD5;

/**
 * TODO
 * 下午10:20:28
 */
public class HtmlToHsh {
private String path=null;//网页保存的路径
	public HtmlToHsh(String path){
		this.path=path;
	}
	/**
	 * 把已有的网页读出来，存入picList
	 */
	public void process(){
		File f=new File(path);
		File[] lists=f.listFiles();
		for(int i=0;i<lists.length;i++){
			if(lists[i].isFile()){
				String htmlName=lists[i].getName();
				if(htmlName.contains("%")){
					htmlName=htmlName.replaceAll("%", "");
					lists[i].renameTo(new File(path+htmlName));
				}
//				System.out.println("save"+htmlName);
				Constant.fileList.put(htmlName,htmlName);
			}
		}
	}
	/**
	 * 判断指定路径的网页是否已经存在，如果存在直接进行读取，否则把网页下载到本地并存进picList
	 * @param webUrl
	 * @throws ServletException 
	 * @throws IOException 
	 */
	public boolean htmlJudge(String webUrl,String xmlTem,String showTem,ServletRequest request) throws IOException, ServletException{
		System.out.println("In htmlToHash htmlJudge webUrl ="+webUrl);
		MD5 md5 = new MD5();
		String htmlName=md5.md5s(webUrl)+".jsp";
		String dName="";
		if(htmlName.contains("%"))
		{
			dName=htmlName.replaceAll("%", "");
		}
		else{
			dName=htmlName;
		}
		boolean s=Constant.fileList.containsKey(dName);
		System.out.println(s+"filelistsize="+Constant.fileList.size());
		if(s){
			return true;
		}else
		return false;
	}
}


