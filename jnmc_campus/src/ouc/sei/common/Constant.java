/**
*@author:LF
*2010-6-21
*说明：
*/
package ouc.sei.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;


import ouc.sei.beans.ASPPageParams;
import ouc.sei.beans.ClientBean;
import ouc.sei.beans.FilterBean;
import ouc.sei.beans.HtmlFilterBean;
import ouc.sei.beans.RedirectBean;
import ouc.sei.beans.Rule;


public class Constant {

	public static Hashtable<String,String> picList=new Hashtable<String,String>();//图片缓存队列
	public static Hashtable<String,String> fileList=new Hashtable<String,String>();//图片缓存队列
	public static String baseUrl = null;// 项目根路径
	public static String baseUrl2 = null;// 项目根路径
	public static String hrefUrl = null;// html2xml类中用到的补充超链接地址
	public static String picSaveUrl=null;//图片的保存路径
	public static String htmlSaveUrl=null;//网址的保存路径
	public static String htmlUrl=null;//网址路径
	public static String picUrl=null;//图片路径
	public static String pageEncode="gb2312";//网页的编码格式，缺省的为UTF-8
	public static String formPostEncode=null;
	//public static String pageClassName=null;//根据此参数，调用不同的分页类名称
	public static int i=0;//对不同的客户访问，分配不同的号码，以代表这个客户
	public static String defaultWebUrl=null;//缺省项目映射的路径
	public static Hashtable<String,String>htmlStrList=new Hashtable<String,String>();//html缓存信息
	public static int C_SCHEDULE_HOUR=0;
	public static int indexflash=30;
	public static HashMap<Integer ,ClientBean> clientList = new HashMap<Integer,ClientBean>();
	public static Hashtable<Integer,ASPPageParams>pageCookiesList=new Hashtable<Integer,ASPPageParams>();
	
	public static Vector<Rule> ruleList = new Vector<Rule>();//存储判断规则的链表
	//public static Vector<RedirectBean> resirectList = new Vector<RedirectBean>();//保存页面跳转的验证信息
    public static Vector<FilterBean> afilterList=new Vector<FilterBean>();//保存页面中超链接的路径补全信息
    public static Vector<FilterBean> imgfilterList=new Vector<FilterBean>();
    public static Vector<FilterBean> formConfigList = new Vector<FilterBean>();
    
   // public static HashMap<String,String> documentList = new HashMap<String,String>();//对已经下载并转换的文档进行保存
    //public static String documentsSavePath = null;//文档保存路径
    public static List<HtmlFilterBean> htmlFilter = new ArrayList<HtmlFilterBean>();//对第一提取的源码进行过滤，然后才可以进行jtidy的整理
    public static String classPath=null;//项目的classes的路径
    
   // public static Vector<String> secondHandleFliter = new Vector<String>();

}
