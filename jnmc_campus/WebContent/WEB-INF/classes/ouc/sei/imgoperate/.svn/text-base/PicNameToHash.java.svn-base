/**
*@author:LF
*2010-6-21
*说明：封装了对picLIst哈希表的一些操作
*/
package ouc.sei.imgoperate;

import java.io.File;

import ouc.sei.common.Constant;

public class PicNameToHash {
	private String path=null;//图片保存的路径
	
	private int clientId;//临时加上的，为了请求图片，尤其是请求验证码时使用。 杨志龙
	public PicNameToHash(String path){
		this.path=path;
	}
	/**
	 * 把已有的图片读出来，存入picList
	 */
	public void process(){
		File f=new File(path);	
		File[] lists=f.listFiles();
		for(int i=0;i<lists.length;i++){
			if(lists[i].isFile()){
				String picName=lists[i].getName();
				if(picName.contains("%")){
					picName=picName.replaceAll("%", "");
					lists[i].renameTo(new File(path+picName));
				}
				if(!picName.contains("authImg")){
				Constant.picList.put(picName,picName);
				}
			}
		}
	}
	/**
	 * 判断指定路径的图片是否已经存在，如果存在直接进行读取，否则把图片下载到本地并存进picList
	 * @param webUrl
	 */
	public String picJudge(String webUrl,int width){
		System.out.println("In PicNameToHash picJudge picUrl ="+webUrl);
		String []paths=webUrl.split("/");
		int i=paths.length;
		String picName=paths[i-1];
		String dName="";
		if(picName.contains("%"))
		{
			dName=picName.replaceAll("%", "");
		}
		else{
			dName=picName;
		}
		boolean s=Constant.picList.containsKey(dName);
		if(s){
			
		}else{
			System.out.println("Downloading picture......");
			DwindlePic newPic=new DwindlePic();
			newPic.setClientId(clientId);
			newPic.s_pic(webUrl,width);
			process();//重新把图片读进哈希表
		}
		return dName;
	}
	
	
	
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
}
