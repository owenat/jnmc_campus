<%@page import="ouc.sei.docoperator.HtmlToHsh"%>
<%@page import="ouc.sei.regexOperate.HtmlFilter"%>
<%@page import="ouc.sei.contentfilesave.GetFile"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="ouc.sei.browser.*,ouc.sei.xmltojsp.*,ouc.sei.transform.*,java.io.*,ouc.sei.common.*,org.w3c.dom.Document"
	import="java.util.*,java.lang.StringBuilder,ouc.sei.regexOperate.*,ouc.sei.operate.*"
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width; initial-scale=1.0;  minimum-scale=1.0; maximum-scale=1.5"/>
	<meta name="MobileOptimized" content="240"/>
	<link type="text/css" rel="stylesheet" href="css/show.css" />
	<link type="text/css" rel="stylesheet" href="css/header.css" />	 
</head>
<body>
	<jsp:include page="includes/header.jsp"/>
	<%
	String webUrl=session.getAttribute("webUrl").toString();
	String xmlTem=session.getAttribute("xmlTem").toString();
	if(xmlTem.equals("doc/indexXml.xsl"))
		{%>
	<jsp:include page="includes/NewFile.jsp"/>
	<%} %>
	<div class="wrap">
		<%
			String name = (String) session.getAttribute("name");
			String showTem = (String) session.getAttribute("showTem");
			System.out.println("webUrl=" + webUrl + "\n" + "xmlTem=" + xmlTem
					+ "\n showTem=" + showTem);
			String htmlString = "";
			Browser browser = new Browser();
			
			/*处理post形式和伪装post形式的表单请求  */
		/* 	String method = request.getMethod();
			if ("post".equalsIgnoreCase(method)){
				Map<String, String[]> map = request.getParameterMap();
				String formMethod = request.getParameter("formMethod");
				PostMethodOper postMethod = new PostMethodOper(map,  webUrl,
						browser, formMethod);
				postMethod.postMehtodOper();
				webUrl = postMethod.getWebUrl();
			} */
			
			browser.setWebUrl(webUrl);			
			String htmlStr = null;
			//System.out.println("####################################");
			String clientid = (String) session.getAttribute("clientid");
			//System.out.println("clientid-------------" + clientid);
			int clientidint = 0;
			//System.out.println("in show.jsp,clientid=" + clientid);
			//System.out.println("####################################");
			 if (null == clientid || "".equals(clientid)) {
				Constant.i = Constant.i + 1;
				clientidint = Constant.i;
				session.setAttribute("clientid", String.valueOf(clientidint));
			} else {
				clientidint = Integer.parseInt(clientid);
			} 
			htmlStr = browser.getSource(clientidint);//可以得到htmlSr
			HtmlFilter htmlFilter=new HtmlFilter();
			htmlStr = htmlFilter.htmlFilter(htmlStr);//过滤不能识别的标签
			//System.out.println("====== In show.jsp htmlStr ======"+htmlStr);
			
			Document dom = browser.HTMLToXML(htmlStr);			
			
			DomOperater dop = new DomOperater();
			String xhtml = dop.domToString(dom);
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^in show.jsp,dom to string:===" + xhtml);
			HTML2XML html2xml = new HTML2XML(dom, xmlTem, webUrl,clientidint);
			System.out.println("in show.jsp,html2xml clientid=" + clientidint);
			String xmlstr = html2xml.getXmlStr();
			System.out.println("======in show.jsp,xmlstr======"+xmlstr);
			
		/* 	RuleListOperater c = new RuleListOperater();
			if (c.isSecondHandle(webUrl) == true) {
				System.out.println("======the second operate!======");
				SecondHandle bsm = new SecondHandle(xmlstr, "", clientidint);
				xmlstr = bsm.dealWithMore(webUrl);
				System.out.println("in show.jsp,第二次抓取后代码======" + xmlstr);
			} */


			/***********正则匹配处理*********/
			/* RegexOperater operater = new  RegexOperater();
			String regexFileName = operater.getRegexTem(webUrl);
			if(regexFileName!=null){
				//正则匹配处理的url
			    xmlstr=operater.regexExtract(xmlstr);
				System.out.println("======in show.jsp,正则处理之后,xmlstr======="+xmlstr);
			} */
			/************************/
			XmlToJsp xmlToJsp = new XmlToJsp();
			htmlString = xmlToJsp.transform(xmlstr, showTem);
			if(!xmlTem.equals("doc/indexXml.xsl")&&!xmlTem.contains("List")){
				System.out.print(Constant.htmlSaveUrl+name+".jsp");
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Constant.htmlSaveUrl+name+".jsp"),"UTF-8"));
				bw.write(htmlString);
				bw.flush();
				bw.close();
				System.out.println("html保存完毕");
				HtmlToHsh hth =new HtmlToHsh(Constant.htmlSaveUrl);
				hth.process();
			}
			System.out.println("======in show.jsp,htmlStr======" + htmlString);
			if (htmlString.equals("")) {
				out.println("<html><body style=\"width:'228px'\">页面内容涉及表格，视频等格式不适合手机观看，如有需要请查看电脑网页版本，谢谢！</body></html>");
			} else {
				if (showTem.contains("formJsp")) {
					htmlString = htmlString.replaceAll("textarea", "input");
					htmlString = htmlString.replaceAll("table", "div");
					htmlString = htmlString.replaceAll("tr", "div");
					htmlString = htmlString.replaceAll("td", "div");
					htmlString = htmlString.replaceAll("size=\"85\"", "");
				}
			out.println(htmlString);
			}//如果页面格式出现读取错误，返回空的字符串，则会运行出错界面。
		%>
</div>
<%
	if(xmlTem.equals("doc/indexXml.xsl"))
		{%>
	<jsp:include page="includes/xiazai.jsp" />
	<%} %>
	
	<jsp:include page="includes/footer.jsp" />
</body>
</html>