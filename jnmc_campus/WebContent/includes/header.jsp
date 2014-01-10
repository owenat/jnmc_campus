<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="ouc.sei.common.*;"%>
<link rel="stylesheet" href="css/show.css" />
<link rel="stylesheet" href="css/header.css" />
<% 	
	String paths=Constant.baseUrl;
	String base=Constant.baseUrl2;
%>
 <style type="text/css">
	.headcss{font-size:16px}
</style>
<div align="center" id="Logo"><img src="images/topview.jpg"/></div> 

<!--  <div class="header" data-toolbar="fixed">
<div class="logo"><div style="margin:-10px auto;"><img width="100%"  src="images/topview.png"></div></div>		
	</div>  -->
<div class="navside">
	<ul>
		<li  class="li1"><span><A href="<%=paths%>http://www.jnmc.edu.cn/">网站首页</A></span></li>
		<li class="li2" ><span><A href="<%=paths%>http://www.jnmc.edu.cn/zjjy/xxgk/200905/6636.html">学校概况</A></span></li>
		<li class="li4"><span><A href="<%=paths%>http://www.jnmc.edu.cn/xsxx/xsxx/">学术信息</A></span></li>
		<li class="li3"><span><A href="<%=base%>/depts.jsp">院系设置</A></span></li>
	</ul>
</div>