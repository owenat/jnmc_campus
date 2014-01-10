/**
 *@author:LF 杨志龙 Version 2
 *2010-7-5
 *说明：过滤掉页面html字符串中不能识别的标签
 */
package ouc.sei.regexOperate;
import java.util.Iterator;

import ouc.sei.beans.HtmlFilterBean;
import ouc.sei.common.Constant;

public class HtmlFilter {

	public HtmlFilter() {

	}

	public String htmlFilter(String htmlStr) {
		// 将要过滤的字符串写到配置文件当中，如果要过滤直接在配置文件当中添加要过滤或者替代的字符转即可
		if (Constant.htmlFilter != null) {
			for (Iterator<HtmlFilterBean> it = Constant.htmlFilter.iterator(); it
					.hasNext();) {
				HtmlFilterBean htmlFilter = it.next();
				htmlStr = htmlStr.replace(htmlFilter.getOldStr(),
						htmlFilter.getNewStr());
			}
		}
		return htmlStr;
	}

}
