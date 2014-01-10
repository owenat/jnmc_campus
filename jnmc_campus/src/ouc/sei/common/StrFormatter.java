package ouc.sei.common;

import java.io.UnsupportedEncodingException;

public class StrFormatter {
	/**
	 * 对form表单数据进行编码，utf-8到unicode的转换
	 * 
	 * @param data
	 * @return
	 */
	public static String encodeUnicode(final String data) {
		char[] utfBytes = data.toCharArray();
		String unicodeBytes = "";
		for (char utfByte : utfBytes) {
			String hexByte = Integer.toHexString(utfByte);
			if (hexByte.length() <= 2) {
				hexByte = "00" + hexByte;
			}
			unicodeBytes += "%u" + hexByte;
		}
		return unicodeBytes;
	}

	/**
	 * unicode编码转化为UTF-8
	 * 
	 * @param s
	 * @return
	 */
	public static String fixString(String s) {
		s = s.replaceAll("%", "\\\\");
		while (true) {
			int index = s.indexOf("\\u");
			if (index != -1) {
				String s1 = s.substring(index, index + 6);
				if (s1.matches("\\\\u[0-9A-Fa-f]{4}")) {
					char c = (char) Integer.parseInt(s1.replace("\\u", ""), 16);
					s = s.substring(0, index) + c + s.substring(index + 6);
				}
			} else {
				break;
			}
		}
		return s;
	}

	/**
	 * unicode到gb2312的转变
	 * 
	 * @param dataStr
	 *            输入格式：%91CD%5927
	 * @return
	 */
	public static String Unicode2GBK(String dataStr) {
		int index = 0;
		dataStr = dataStr.replaceAll("%", "\\\\u");
		StringBuffer buffer = new StringBuffer();
		while (index < dataStr.length()) {
			if (!"\\u".equals(dataStr.substring(index, index + 2))) {
				buffer.append(dataStr.charAt(index));
				index++;
				continue;
			}
			String charStr = "";
			charStr = dataStr.substring(index + 2, index + 6);
			char letter = (char) Integer.parseInt(charStr, 16);
			buffer.append(letter);
			index += 6;
		}
		return buffer.toString();
	}

	/**
	 * 打印字节流
	 * 
	 * @param bt
	 */
	private static void printbyte(byte[] bt) {
		for (int i = 0; i < bt.length; i++) {
			int hex = (int) bt[i] & 0xff;
			System.out.print(Integer.toHexString(hex) + " ");
		}
		System.out.println("length = " + bt.length);
	}

	/**
	 * 将整个url中的中文字符由Unicode编码转换为Gb2312编码
	 * 
	 * @param dataStr
	 *            输入格式为：http://www.sdtele.com/news_list.asp?inclass=%91CD%5927%65
	 *            B0%95FB&amp;page=1"));;
	 * @return
	 */
	public static String URIU2G(String dataStr) {
		String str = "";
		int beginIndex = 0, endIndex = 0;
		StringBuffer s = new StringBuffer(dataStr);
		beginIndex = dataStr.indexOf('%');
		endIndex = dataStr.lastIndexOf('%') + 5;
		String subStr = s.substring(beginIndex, endIndex);
		System.out.println(subStr);
		subStr = Unicode2GBK(subStr);
		try {
			byte[] bt = subStr.getBytes("GB2312");
			for (int i = 0; i < bt.length; i++) {
				int hex = (int) bt[i] & 0xff;
				str = str + '%' + Integer.toHexString(hex);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.replace(beginIndex, endIndex, str);
		//System.out.println(str);
		return s.toString();
	}

	public static void main(String[] args) {
		System.out
				.println(fixString("http://www.sdtele.com/news_list.asp?inclass=%91CD%5927%65B0%95FB&amp;page=1"));
		;
	}

}
