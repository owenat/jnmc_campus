package ouc.sei.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ouc.sei.browser.RefreshClient;
import ouc.sei.common.Constant;
import ouc.sei.docoperator.DocNametoHash;
import ouc.sei.imgoperate.PicNameToHash;
import ouc.sei.operate.BaseUrlOper;
import ouc.sei.operate.CommonList;

/**
 * Servlet implementation class StartUp
 */
public class StartUp extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartUp() {
        super();
        BaseUrlOper.newInstance();//在tomcat启动时为CommonList里面的baseUrl赋值
        CommonList.newInstance();//在tomcat启动时初始化规则链表
  
        RefreshClient.getInstance().refreshTime();
        RefreshClient.getInstance().refreshClientId();

        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
