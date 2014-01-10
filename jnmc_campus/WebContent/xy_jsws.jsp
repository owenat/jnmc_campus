<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="ouc.sei.common.*;"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width; initial-scale=1.0;  minimum-scale=1.0; 
maximum-scale=2.0" />
<meta name="MobileOptimized" content="240" />
<jsp:include page="includes/header.jsp" />
<title>精神卫生学院</title>
<link style="text/css" rel="stylesheet" href="css/show.css" />
<link style="text/css" rel="stylesheet" href="css/header.css" />
</head>
<body>
<%
		String addUrl = "http://210.44.16.40/";
		String baseUrl = Constant.baseUrl;
%>
	
<div class="zt">
<div class="hangju">
	<br />
	<div style="margin-left: 30px">
		<div style="color: blue; font-size: 15px;">
			<strong><a href="<%=baseUrl%><%=addUrl%>a/xueyuangaikuang/xueyuanjianjie/20101210/386.html">学院概况</a></strong>
		</div>
		<br />
		<div style="color: blue; font-size: 15px;">
			<strong><a href="<%=baseUrl%><%=addUrl%>a/xinwendongtai/">新闻动态</a></strong>
		</div>
		<br />
		<div style="color: blue; font-size: 15px;">
			<strong><a href="<%=baseUrl%><%=addUrl%>a/jiaoyujiaoxue/">教育教学</a></strong>
		</div>
		<br />
	</div>
	</div>
	</div>
	<jsp:include page="includes/footer.jsp" />
</body>
</html>