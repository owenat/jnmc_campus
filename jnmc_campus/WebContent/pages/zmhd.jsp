<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="ouc.sei.common.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>互动交流</title>
	<link rel="stylesheet" href="../css/common.css" />
	<link rel="stylesheet" href="../css/otherstyle.css"/>
</head>
<body>
<%
String preUrl=Constant.baseUrl;
%>
<div class="wrap">
<jsp:include page="../includes/header.jsp"/>
	<div class="biglist" style="margin-top:-3px;">
		<label style="margin-left:6px;"><a href="#down2" id="down1" accesskey="h"><img src="../images/003.gif" alt=">>"/></a></label>
		<label style="margin-left:1px;"><font color="#0073ae" size="2.5"><strong><b>互动交流</b></strong></font></label><br>

	</div>
	<div style="padding-left:15px;font-size:12px"> 
		<div class="">	
			在线访谈>>
			<br/>
			&nbsp;&nbsp;<a href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/n10213787/n10213802/index.html">访谈预告</a>
			<br/>
			&nbsp;&nbsp;<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/n10213787/n10213817/index.html">访谈现场</a>
			<br/>
			&nbsp;&nbsp;<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/n10213787/n10243169/index.html">后期处理</a>
		</div>
		<br/>
		<div class="">	
			投诉咨询>>
			<br/>
			&nbsp;&nbsp;<a href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/n10649083/n10649104/index.html">区长信箱</a>
			<br/>
			&nbsp;&nbsp;<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/n10649083/n10649188/index.html">来信反馈</a>
			<br/>
			&nbsp;&nbsp;<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/jjxfindex.html&temtype=9">纪检信访</a>
			<br/>
			&nbsp;&nbsp;<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/xntsindex.html&temtype=9">行政效能投诉</a>
			<br/>
			&nbsp;&nbsp;<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/syhljbindex.html&temtype=9">商业贿赂举报</a>
			<br/>
			&nbsp;&nbsp;<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/xzfyindex.html&temtype=9">行政复议</a>
		</div>
		<br/>
		<div class="">	
			民意征集>> 
			<br/>
			&nbsp;&nbsp;<a href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/n10648506/n10648935/index.html">民意征集</a>
			<br/>
			&nbsp;&nbsp;<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/n10648506/n10648999/index.html">人大概表建议</a>
			<br/>
			&nbsp;&nbsp;<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/n10648506/n10649046/index.html">政协提案</a>
		</div>
		<br/> 
		<div class="">	
			<a	href="<%=preUrl%>http://www.qdsf.gov.cn/n241/n10213388/n10213478/index.html">部门通讯录</a>
			
		</div>
	</div>
	
	<jsp:include page="../includes/footer.jsp"/>
	
	
</div>

</body>
</html>