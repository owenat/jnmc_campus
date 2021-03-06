package ouc.sei.operate;

import java.io.File;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import ouc.sei.common.Constant;
import java.io.UnsupportedEncodingException;
/**
 * 类作用是读取配置文件config.xml，初始化constant中各参数
 * @author qsw-Myonlystar
 *
 */
public class BaseUrlOper {
	
	private static BaseUrlOper instance;
	private BaseUrlOper(){
		String classPath = this.getClass().getResource("/").getPath();//返回服务区绝对路径
		String xslPath =null;//返回服务区绝对路径
		try {
			classPath=java.net.URLDecoder.decode(classPath,"UTF-8");//返回服务区绝对路径;
			Constant.classPath = classPath;
			xslPath=classPath+"constant.xml";
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("=="+xslPath);
		System.out.println(System.getProperty("java.class.path")); 
		
		try {
			SAXReader reader = new SAXReader();
			File file = new File(xslPath);
			Document document = reader.read(file);//获得constant.xml的document对象
			Element rootElm = document.getRootElement();//获得跟节点
			Constant.defaultWebUrl=rootElm.element("defaultWebUrl").getText();
			Constant.baseUrl=rootElm.element("baseUrl").getText();
			Constant.baseUrl2=rootElm.element("baseUrl2").getText();
			Constant.hrefUrl=rootElm.element("hrefUrl").getText();
			Constant.pageEncode=rootElm.element("pageEncode").getText();
			Constant.formPostEncode=rootElm.element("formPostEncode").getText();
			String path=this.getClass().getResource("/").getPath();
			System.out.println("in Constant.java.path="+path);
			path=path.substring(0, path.lastIndexOf("/"));
			path=path.substring(0, path.lastIndexOf("/"));
			path=path.substring(0, path.lastIndexOf("/")+1);
			//在tomcat下运行的时候可以直接通过下面的方式得到图片的存储路径。
			Constant.htmlSaveUrl=path+rootElm.element("htmlSaveUrl").getText();	
			Constant.picSaveUrl=path+rootElm.element("picSaveUrl").getText();	
			Constant.htmlUrl=rootElm.element("htmlUrl").getText();
			Constant.picUrl=rootElm.element("picUrl").getText();
//			Constant.documentsSavePath = path
//					+ rootElm.element("documentSavePath").getText();
			//Constant.pageClassName=rootElm.element("pageClassName").getText();
			Constant.C_SCHEDULE_HOUR=Integer.parseInt(rootElm.element("C_SCHEDULE_HOUR").getText());
			Constant.indexflash=Integer.parseInt(rootElm.element("indexflash").getText());
		} catch (Exception e) {
			System.out.println(e);
		}	
	}

	public static BaseUrlOper newInstance(){
		if(instance == null){
			instance = new BaseUrlOper();
		}
		return instance;
	}
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String args[]){
//		System.out.println(c);
	}
}
