<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="ouc.sei.common.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>政务公开</title>
	<link rel="stylesheet" href="../css/common.css" />
	<link rel="stylesheet" href="../css/otherstyle.css"/>
</head>
<body>
<%
String preUrl=Constant.baseUrl+"/show.jsp?webUrl=";
%>
<div class="wrap">
<jsp:include page="../includes/header.jsp"/>
	<div class="biglist" style="margin-top:-3px;">
		<label style="margin-left:6px;"><a href="#down2" id="down1" accesskey="h"><img src="../images/003.gif" alt=">>"/></a></label>
		<label style="margin-left:1px;"><font color="#0073ae" size="2.5"><strong><b>政务信息公开</b></strong></font></label><br>

	</div>
	<div class="square">
		<div style="white-space:nowrap;">	
		<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n243/n283/index.html">政务信息</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n243/n281/n12104/index.html&temtype=1">领导专页</a>
		</div>
		
		<div style="white-space:nowrap;">	
		<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n243/n286/index.html">政府公告</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n243/n290/n10211631/index.html">公文法规</a>
		</div>
		
		<div style="white-space:nowrap;">	
		<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n243/n689059/index.html">应急管理</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n243/n289/index.html">人事任免</a>
		</div>
		
		<div style="white-space:nowrap;">	
		<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n243/n285/index.html">热点专题</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n243/n10209247/index.html">政策解读</a>
		</div>
		 
		<div style="white-space:nowrap;">	
		<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n243/n282/qlindex.html&temtype=5">组织机构</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	</div>
	
	<jsp:include page="../includes/footer.jsp"/>
	
	
</div>

</body>
</html>