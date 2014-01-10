package ouc.sei.filters;

/**
 * @author qsw-Myonlystar
 * 
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ouc.sei.beans.Rule;
import ouc.sei.common.Constant;
import ouc.sei.docoperator.HtmlToHsh;
import ouc.sei.docoperator.MD5;
import ouc.sei.operate.RuleListOperater;

/**
 * Servlet Filter implementation class Controller
 */
public class Controller implements Filter {
	private String webUrl = "";
	private Rule rule = null;
	/**
	 * Default constructor.
	 */
	public Controller() {
		// TODO Auto-generated constructor stub
		System.out.println("filter's constructor!");
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("filter is destroy!");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		javax.servlet.http.HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		String xmlTem = "";
		String jspTem = "";
		String realUrl = "";
        System.out.println("++++++++++++++++++++++++++");
		String param = req.getQueryString();
		System.out.println("in controller param=" + param);
		if (param != null) {
			try {
				webUrl = param.replaceAll("webUrl=", "");
				System.out.println("In controller,webUrl=" + webUrl);
			} catch (UnknownError e) {
				e.printStackTrace();
			}
		} else {
			webUrl = Constant.defaultWebUrl;
		}
		
		/**
		 * url中通过最后的参数temtype判断页面的模板，但是抓取的时候不能用带有temtype参数的url抓取参数
		 */
		if (webUrl.contains("temtype")) {
			realUrl = webUrl.substring(0, webUrl.indexOf("temtype") - 1);
		} else {
			realUrl = webUrl;
		}
		if (webUrl != null) {
				rule = RuleListOperater.newInstance().getTemplate(webUrl);
				xmlTem = rule.getXmlTem();
				jspTem = rule.getShowTem();
				session.setAttribute("xmlTem", xmlTem);
				session.setAttribute("showTem", jspTem);
				session.setAttribute("webUrl", realUrl);
				System.out.println("controllerwebUrl=" + webUrl + "\n" + "controllerxmlTem=" + xmlTem
						+ "\n controllershowTem=" + jspTem);
				String str=MD5.md5s(realUrl);
				HtmlToHsh pnth = new HtmlToHsh(Constant.htmlSaveUrl);
				if(pnth.htmlJudge(webUrl, xmlTem, jspTem, request)==true){
					System.out.println("重定向中>>"+Constant.htmlUrl+str+".jsp");
					resp.sendRedirect(Constant.htmlUrl+str+".jsp");
				}else{
				session.setAttribute("name", str);
				this.webUrl = null;
				rule = null;
				chain.doFilter(request, response);
			    }
			}else {
			this.webUrl = null;
			rule = null;
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		/*
		 * this.home_page=fConfig.getInitParameter("HOME_URI");
		 * System.out.println("filter is init!");
		 */

	}
}
