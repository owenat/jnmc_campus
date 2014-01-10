package ouc.sei.Servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ouc.sei.beans.Rule;
import ouc.sei.common.Constant;
import ouc.sei.common.StrFormatter;
import ouc.sei.operate.RuleListOperater;

/**
 * Servlet implementation class FormQuery
 */
public class FormQuery extends HttpServlet {
	private Rule rule=null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormQuery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String key=request.getParameter("thekey");
		System.out.println("查询关键字为："+key);
		key=StrFormatter.encodeUnicode(key);//调用公共函数进行编码转换
		if(!"".equals(key)){
			String queryUrl="http://www.qepb.gov.cn/News/NewsList.aspx?lid="+key+"&id=key";
			response.sendRedirect("http://localhost:8080/qepb/show.jsp?webUrl="+queryUrl);
		}
		else{
			response.sendRedirect("http://localhost:8080/qepb/show.jsp");//如果查询条件为空，则跳转到首页
		}
	}

}
