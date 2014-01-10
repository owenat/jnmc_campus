package ouc.sei.transform;

/**
 * author：LF
 * 定义DOM结构的各个过滤器，实现各个部分的过滤操作，对过滤操作进行集中化管理
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

import ouc.sei.common.Constant;
import ouc.sei.imgoperate.PicNameToHash;

public class DomFilters {
	private Document dom = null;// 页面源码的DOM结构
	private String picSaveUrl = null;// 图片保存路径
	private String webUrl = null;// 页面url，补全相对路径的时候会用到
	private int clientId;

	public DomFilters(Document dom, String picSaveurl, String webUrl,
			Integer clientId) {
		this.clientId = clientId;
		this.picSaveUrl = picSaveurl;
		this.webUrl = webUrl;
		this.dom = dom;
		this.formFilter();
		this.aFilter();
		this.imgFilter();
		this.styleFilter();
	}

	private void styleFilter() {
		// TODO Auto-generated method stub
		DocumentTraversal traversal = (DocumentTraversal) dom;
		NodeIterator iterator = traversal.createNodeIterator(
				dom.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null, true);
		for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
			Element att = (Element) n;
			if (att.hasAttribute("cellspacing")) {
				att.removeAttribute("cellspacing");
			}
			if (att.hasAttribute("xmlns")) {
				att.removeAttribute("xmlns");
			}
			if (att.hasAttribute("background")) {
				att.removeAttribute("background");
			}
			if (att.hasAttribute("bgcolor")) {
				att.removeAttribute("bgcolor");
			}
			if (att.hasAttribute("nowrap")) {
				att.removeAttribute("nowrap");
			}
			if (att.hasAttribute("class")) {
				att.removeAttribute("class");
			}
			if (att.hasAttribute("height")) {
				att.removeAttribute("height");
			}
			if (att.hasAttribute("x:num")) {
				att.removeAttribute("x:num");
			}
			if (att.hasAttribute("x:str")) {
				att.removeAttribute("x:str");
			}
			if (att.hasAttribute("script")) {
				att.removeAttribute("script");
			}
		}

	}

	public DomFilters() {

	}

	public Document getDom() {
		return this.dom;
	}

	/**
	 * 过滤FORM表单的提交按钮
	 */
	private void formFilter() {
		NodeList list = dom.getElementsByTagName("form"); // 将相对URL地址转换为绝对地址
		for (int i = 0; i < list.getLength(); i++) { // 处理a标签
			Element link = (Element) list.item(i);
			if (link.hasAttribute("action")) {// 如果存在链接属性。这个是在内容页面中出现的问题，有时候会存在没有链接属性的<a>标签
				String newValue = link.getAttributeNode("action")
						.getNodeValue().toString();
				String addStr = getAddStr(newValue, "form");// 含有匹配规则
				newValue = addStr + newValue;
				link.setAttribute("action", newValue);
			}

		}
	}
	/**
	 * 对dom对象中的img标签进行处理
	 * 
	 * @param dom
	 */
	private void imgFilter() {
		NodeList list = dom.getElementsByTagName("img");
		for (int i = 0; i < list.getLength(); i++) { // 处理img标签
			Element link = (Element) list.item(i);
			if(link.hasAttribute("width"))
			{
				link.removeAttribute("width");
			}
			if(link.hasAttribute("height"))
			{
				link.removeAttribute("height");
			}
			if(link.hasAttribute("style"))
			{
				link.removeAttribute("style");
			}
			if (link.hasAttribute("src")) {
				String newValue = link.getAttributeNode("src").getNodeValue()
						.toString();
				if (newValue.startsWith("..")) {
					newValue = newValue.substring(2);
				}
				if ((newValue.startsWith("/"))) {
					newValue = newValue.substring(0);
				}
				if ((newValue.startsWith("/FileDown"))) {
					link.removeAttribute("src");
					break;
				}
				if (newValue.contains("baidu")) {
					link.removeAttribute("src");
					break;
				}
				else{
					String picUrl = null;
					// 使用配置文件替换
					String addStr = getAddStr(this.webUrl,newValue, "src");
					System.out.println("addStr"+addStr);
					
					picUrl = addStr + newValue;
					System.out.println("newValue"+newValue);
					System.out.println("picUrl===="+picUrl);
					 PicNameToHash pnth = new PicNameToHash(picSaveUrl);
					 System.out.println("In domFilter clientId = " +
					 clientId);
					 pnth.setClientId(clientId);
					
					 String picName = pnth.picJudge(picUrl, 226);//判断图片是不是在本地，如果不在本地则对图片
					 // 下载并缩小。如果在则直接使用
					 picName = newImageName(picName, clientId);
					 link.setAttribute("src", Constant.picUrl + picName);
//					 link.setAttribute("src", picUrl);
					// link.setAttribute("width", "226");
				}
			}
		}
	}

	/**
	 * @author Yangzhilong
	 * @function 用于对含有验证码的图片进行重新命名，对于不同的用户，不同的验证码
	 * @param picName
	 *            图片的原始名字
	 * @param clientId
	 *            用户的ID
	 * @return 返回新的图片名字
	 */
	public String newImageName(String picName, int clientId) {
		String imageName = picName;
		String newImageName = picName;
		if (picName.contains("authImg")) {// 这个参数在不同系统中验证码的名字不同，因此要更改。
			System.out
					.println("======In DomFilters Image is valid image======");
			if (picName.contains("/")) {
				String stara[] = picName.split("/");
				int i = stara.length;
				imageName = stara[i - 1];
			}

			if (imageName.contains(".")) {
				String tempImageName = imageName.substring(0,
						imageName.lastIndexOf("."));
				String tempImageType = imageName.substring(imageName
						.lastIndexOf("."));
				newImageName = tempImageName + clientId + tempImageType;
			} else {
				newImageName = imageName + clientId;
			}
		}
		return newImageName;

	}

	/**
	 * 对dom的a标签进行处理
	 * 
	 * @param dom
	 */
	private void aFilter() {
		NodeList list = dom.getElementsByTagName("a"); // 将相对URL地址转换为绝对地址
		for (int i = 0; i < list.getLength(); i++) { // 处理a标签
			Element link = (Element) list.item(i);
			String aValue = link.getTextContent();// 获取的是网页上显示的文本内容
//			if (aValue.length() >= 16) {// 为了页面的美观，链接内容统一长度。比如<a>实验</a>截取的是<a></a>之间的内容
//				aValue = aValue.substring(0, 14);
//				aValue = aValue + "...";
//			}
//			link.setTextContent(aValue);
			if (link.hasAttribute("href")) {// 如果存在链接属性。这个是在内容页面中出现的问题，有时候会存在没有链接属性的<a>标签
				String newValue = link.getAttributeNode("href").getNodeValue().toString();
				if (newValue.contains("..")) {
					newValue = newValue.replace("..", "");
				}
				if (newValue.startsWith("./")) {
					newValue = newValue.substring(2);
				}
				if (newValue.contains("baidu")) {
				      break;
				}
				if(!newValue.startsWith("http://"))
				{
				String addStr = getAddStr(this.webUrl,newValue, "a");// 含有匹配规则
				newValue = addStr + newValue;
				}
//				System.out.println("baseUrl======================="+Constant.baseUrl+ newValue);
				if (newValue.endsWith(".doc") || newValue.endsWith(".xls")
						|| newValue.endsWith(".pdf")
						|| newValue.endsWith(".rar")
						|| newValue.endsWith(".swf")) {
					link.setAttribute("href", newValue);
				} else {
					link.setAttribute("href", Constant.baseUrl + newValue);
				}
			}

		}
	}
	/**
	 * 根据网页url得到要采用的补全超链接字符串 根据图片src属性得到要采用的补全字符串
	 * 
	 * @param url
	 * @return
	 */
	public String getAddStr(String rootUrl, String url, String filterType) {
		int length = 0;
		String addStr = "defaultRule";
		if (filterType.equals("a")) 
		{
			length = Constant.afilterList.size();
			for (int i = 0; i < length; i++) 
			{
				if ("".equals(Constant.afilterList.elementAt(i)
						.getRootUrlRegex())) {
					if (Constant.afilterList.elementAt(i).getRegex() != null
							&& regexStr(url, Constant.afilterList.elementAt(i)
									.getRegex())) {
						addStr = Constant.afilterList.elementAt(i).getAddStr();
						break;
					}
				} 
				else 
				{
					if (Constant.afilterList.elementAt(i).getRegex() != null
							&& regexStr(url, Constant.afilterList.elementAt(i)
									.getRegex())
							&& regexStr(rootUrl, Constant.afilterList
									.elementAt(i).getRootUrlRegex())) {
						addStr = Constant.afilterList.elementAt(i).getAddStr();
						break;
					}
				}
			}
			if (addStr.equals("defaultRule")) {
				addStr = Constant.afilterList.elementAt(length - 1).getAddStr();
			}
		} 
		else 
			if (filterType.equals("src")) {
				length = Constant.imgfilterList.size();
				System.out.println("length=="+length);
				System.out.println("rootUrl=="+rootUrl);
				for (int i = 0; i < length; i++) {
					if ("".equals(Constant.imgfilterList.elementAt(i)
							.getRootUrlRegex())) {
						if (Constant.imgfilterList.elementAt(i).getRegex() != null
								&& regexStr(url, Constant.imgfilterList.elementAt(i)
										.getRegex())) {
							addStr = Constant.imgfilterList.elementAt(i).getAddStr();
							System.out.println("honw=="+addStr);
							break;
						}
					} else {
						if (Constant.imgfilterList.elementAt(i).getRegex() != null
								&& regexStr(url, Constant.imgfilterList.elementAt(i)
										.getRegex())
								&& regexStr(rootUrl, Constant.imgfilterList
										.elementAt(i).getRootUrlRegex())) {
							addStr = Constant.imgfilterList.elementAt(i).getAddStr();
							System.out.println("honw=="+addStr);
							System.out.println("url=="+addStr);
							System.out.println("rootUrl=="+addStr);
							break;
						}
					}
				}
				if (addStr.equals("defaultRule")) {
					addStr = Constant.imgfilterList.elementAt(length-1).getAddStr();
				}
		} else 
			if (filterType.equals("form")) {
			length = Constant.formConfigList.size();

			for (int i = 0; i < length; i++) {
				if (Constant.formConfigList.elementAt(i).getRegex() != null
						&& regexStr(url, Constant.formConfigList.elementAt(i)
								.getRegex())) {
					addStr = Constant.formConfigList.elementAt(i).getAddStr();
					System.out.println("---match--" + i);
					break;
				}
			}
			if (addStr.equals("")) {
				addStr = Constant.formConfigList.elementAt(length - 1)
						.getAddStr();
			}
		}
		return addStr;
	}

	/**
	 * 匹配两个字符串是否相等
	 * 
	 * @param inputStr
	 *            待匹配的str
	 * @param regex
	 *            匹配规则
	 * @return 匹配成功与否
	 */
	private boolean regexStr(String inputStr, String regex) {
		Pattern p = Pattern.compile(regex);// 匹配content
		Matcher m = p.matcher(inputStr);
		return m.matches();
	}

	/**
	 * 根据网页url得到要采用的补全超链接字符串 根据图片src属性得到要采用的补全字符串
	 * 
	 * @param url
	 * @return
	 */
	public String getAddStr(String url, String filterType) {
		int length = 0;
		String addStr = "";
		if (filterType.equals("a")) {
			length = Constant.afilterList.size();
			for (int i = 0; i < length; i++) {
				if (Constant.afilterList.elementAt(i).getRegex() != null
						&& regexStr(url, Constant.afilterList.elementAt(i)
								.getRegex())) {
					addStr = Constant.afilterList.elementAt(i).getAddStr();
					break;
				}
			}
			if (addStr.equals("")) {
				addStr = Constant.afilterList.elementAt(length - 1).getAddStr();
			}
		} else if (filterType.equals("img")) {
			length = Constant.imgfilterList.size();

			for (int i = 0; i < length; i++) {
				if (Constant.imgfilterList.elementAt(i).getRegex() != null
						&& regexStr(url, Constant.imgfilterList.elementAt(i)
								.getRegex())) {
					addStr = Constant.imgfilterList.elementAt(i).getAddStr();
					System.out.println("---match--" + i);
					break;
				}
			}
			if (addStr.equals("")) {
				addStr = Constant.imgfilterList.elementAt(length - 1)
						.getAddStr();
			}

		} else if (filterType.equals("form")) {
			length = Constant.formConfigList.size();

			for (int i = 0; i < length; i++) {
				if (Constant.formConfigList.elementAt(i).getRegex() != null
						&& regexStr(url, Constant.formConfigList.elementAt(i)
								.getRegex())) {
					addStr = Constant.formConfigList.elementAt(i).getAddStr();
					System.out.println("---match--" + i);
					break;
				}
			}
			if (addStr.equals("")) {
				addStr = Constant.formConfigList.elementAt(length - 1)
						.getAddStr();
			}
		}
		return addStr;
	}

	/**
	 * 对dom对象的b（页码）标签进行处理
	 * 
	 * @param dom
	 */
	private void bFilter() {
		NodeList list1 = dom.getElementsByTagName("b"); // 将下一页ajax代码进行处理
		String pageNos = "";
		if (list1.getLength() > 0) {

			for (int i = 0; i < list1.getLength(); i++) { // 处理b标签
				Element link = (Element) list1.item(i);
				if (link.hasAttributes()) {
					String page = link.getTextContent().toString();// 获得链接页数的字符串“转到第X页”
					char[] s = page.toCharArray();
					char p = s[3];// 获得具体页数
					char p1 = s[4];
					double p1int = -1;
					try {
						p1int = Double.parseDouble(String.valueOf(p1));
					} catch (NumberFormatException e) {

					}
					String p2 = "";
					if (p1int >= 0 && p1int <= 9) {
						p2 = String.valueOf(p) + String.valueOf(p1);
					} else {
						p2 = String.valueOf(p);
					}
					link.setAttribute("href", webUrl + "@page=" + p2);// 给页数赋值
					link.setTextContent(p2);// 页面显示的页数
					pageNos = pageNos + p2 + "@";

				} else {
					// System.out.println("sorry,but I don't have href.");
				}
			}
			String[] pagess = pageNos.split("@");
			int pl = pagess.length;
			double firstPage = Double.parseDouble(pagess[0]);
			// (int)pagess[0];
			double secondPage = Double.parseDouble(pagess[1]);
			double lastPage = Double.parseDouble(pagess[pl - 1]);
			double secondLast = Double.parseDouble(pagess[pl - 2]);
			if ((secondPage - firstPage) == 1) {
				int ll = list1.getLength();
				Element link = (Element) list1.item(ll - 1);
				link.setTextContent(" 下一页");

			} else if (lastPage - secondLast == 1) {
				int ll = list1.getLength();
				Element link = (Element) list1.item(0);
				link.setTextContent("前一页 ");
			} else {
				int ll = list1.getLength();
				Element link = (Element) list1.item(0);
				Element link2 = (Element) list1.item(ll - 1);
				link.setTextContent("前一页 ");
				link2.setTextContent(" 下一页");
			}
		}
	}

}
