package ouc.sei.loginTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;

import ouc.sei.browser.Browser;
import ouc.sei.common.Constant;
import ouc.sei.operate.HtmlFilter;
import ouc.sei.operate.IndexOperator;
import ouc.sei.operate.PostMethodOper;
import ouc.sei.transform.HTML2XML;
import ouc.sei.xmltojsp.XmlToJsp;

/**
 * Servlet implementation class LoginTest
 */
public class LoginTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginTest() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			
			PrintWriter out = response.getWriter();
			HttpSession session = request.getSession();

			String webUrl = session.getAttribute("webUrl").toString();
			String xmlTem = session.getAttribute("xmlTem").toString();
			String showTem = session.getAttribute("showTem").toString();
			System.out.println("webUrl=" + webUrl + "\n" + "xmlTem=" + xmlTem
					+ "\n" + "showTem=" + showTem);
			String htmlString = "";
			String htmlStr = "";
			String xmlstr = "";
			int clientidint = 0;
			Browser browser = new Browser();

			/* 处理post形式和伪装post形式的表单请求 */
			String method = request.getMethod();
			if ("post".equalsIgnoreCase(method)) {
				Map<String, String[]> map = request.getParameterMap();
				String formMethod = request.getParameter("formMethod");
				PostMethodOper postMethod = new PostMethodOper(map, webUrl,
						browser, formMethod);
				postMethod.postMehtodOper();
				webUrl = postMethod.getWebUrl();
			}

			// ------------首页的iframe1和iframe2-----------------------------------------------------------------------------------------
			if (xmlTem.equals("doc/indexXml.xsl")) {
				IndexOperator indexOp = new IndexOperator();
				htmlStr = indexOp.getHtmlStr();

			} else if (xmlTem.equals("doc/newscontentXml.xsl")
					&& !webUrl.contains("Class")
					&& !webUrl.contains("Article/English")) {

				browser.setWebUrl(webUrl);
				htmlStr = browser.getSource(0);// 可以得到htmlSr
				webUrl = htmlStr.substring(htmlStr.indexOf("http"),
						htmlStr.lastIndexOf("'"));// 得到新的url地址
				browser.setWebUrl(webUrl);
				htmlStr = browser.getSource(0);// 可以得到htmlSr
				HtmlFilter htmlFilter = new HtmlFilter();
				htmlStr = htmlFilter.htmlFilter(htmlStr); // 过滤不能识别的标签

			} else {
				browser.setWebUrl(webUrl);
				String clientid = (String) session.getAttribute("clientid");

				if (null == clientid || "".equals(clientid)) {
					System.out.println("没有找到对应的clientID");
					Constant.i = Constant.i + 1;
					clientidint = Constant.i;
					session.setAttribute("clientid",
							String.valueOf(clientidint));
				} else {
					System.out.println("找到对应的clientID is " + clientid);
					clientidint = Integer.parseInt(clientid);
				}
				htmlStr = browser.getSource(clientidint);// 可以得到htmlSr
				HtmlFilter htmlFilter = new HtmlFilter();
				htmlStr = htmlFilter.htmlFilter(htmlStr); // 过滤不能识别的标签
			}
			System.out.println("htmlStr is "+htmlStr);
			/*
			 * 校园信息门户登陆窗口 重新请求一遍，临时处理方案。框架不具有js解析功能，因此只能人工解析。
			 * js代码为：http://urp.ouc.edu.cn/script/portal-login.js
			 * 登陆成功后调用函数为：function
			 * handleLoginSuccessed(){location.href=Login["forwardUrl"
			 * ]?Login["forwardUrl"]:"index.portal"}
			 */
			if (htmlStr.contains("handleLoginSuccessed")) {
				browser.setWebUrl("http://urp.ouc.edu.cn/index.portal");
				htmlStr = browser.getSource(clientidint);// 可以得到htmlSr
				HtmlFilter htmlFilter = new HtmlFilter();
				htmlStr = htmlFilter.htmlFilter(htmlStr);// 过滤不能识别的标签
				/*
				 * System.out.println("====== In show.jsp htmlStr ======" +
				 * htmlStr);
				 */
			} else if (htmlStr.contains("handleLoginFailure")) {
				// TODO
				// 在这里提示登陆出错的信息
				out.print("用户不存在或密码错误，请重新输入");
				return;
			}
			/*
			 * 校园信息门户登陆处理结束
			 */

			Document dom = browser.HTMLToXML(htmlStr);
			HTML2XML html2xml = new HTML2XML(dom, xmlTem, webUrl, clientidint);
			xmlstr = html2xml.getXmlStr();
			xmlstr = xmlstr.replace("xmlns=\"http://www.w3.org/1999/xhtml\"",
					"");

			//XmlToJsp xmlToJsp = new XmlToJsp();
			//htmlString = xmlToJsp.transform(xmlstr, showTem);
			htmlString = xmlstr;
			//System.out.println("htmlString is" + htmlString);
			if (htmlString.equals("")) {
				out
						.println("<html><body style=\"width:'228px'\">页面格式出现错误，无法显示！</body></html>");
			} else {
				out.println(htmlString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
