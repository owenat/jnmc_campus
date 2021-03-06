package ouc.sei.imgoperate;

import java.net.*;
import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import ouc.sei.common.Constant;

/**
 * 类的作用是下载网页图片，并缩放保存到本地classes/pic文件夹里。
 * 
 * @author qsw-Myonlystar
 * 
 */
public class GetPic {
	private String destUrl;
	private String picUrl = Constant.picSaveUrl;

	public GetPic(String webUrl) {
		this.destUrl = webUrl;
	}

	/**
	 * 根据URL返回图片的名称
	 * 
	 * @param webUrl
	 * @return
	 */
	public String getImageName() {
		String imageName = null;
		String stara[] = destUrl.split("/");
		int i = stara.length;
		imageName = stara[i - 1];

		if (imageName.contains("%")) {
			imageName = imageName.replace("%", "");
		}
		return imageName;
	}

	public String newImageName(int clientId) {
		String imageName = getImageName();
		String newImageName = imageName;
		if (imageName.contains("authImg")) {
			System.out.println("======In GetPic image is valid image!======");
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
	 * 根据目标地址读取原图片，并存储到本地classes/pic文件夹里
	 * 
	 * @param destUrl
	 * @deprecated
	 */
	public void saveToFile() {
		String imageName = getImageName();
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream(picUrl + imageName);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.flush();
		} catch (IOException e) {
		} catch (ClassCastException e) {
		} finally {
			try {
				fos.close();
				bis.close();
				httpUrl.disconnect();
			} catch (IOException e) {
			} catch (NullPointerException e) {
			}
		}
	}

	/**
	 * @author Yang zhilong
	 * @function 使用httpclient请求，这样获得图片以保持同步。、
	 * 
	 */

	public void saveToFile2(int clientId) {
		String imageName = this.newImageName(clientId);
		System.out.println("======In GetPic clientId======" + clientId);
		System.out.println("======In GetPic picUrl + imageName======" + picUrl
				+ imageName);
		DefaultHttpClient client = null;
		client = (DefaultHttpClient) Constant.clientList.get(clientId).getClient();
		System.out.println("======In saveToFile2 destUrl is======" + destUrl);

		// 存在中文的情况下，注意Jtidy将含有中文的属性转码为utf-16be，本方法就是将这个编码转换过来
		String tempDestUrl = destUrl;
		if (tempDestUrl.matches(".*%\\w{4}%.*")) {
			StringBuilder sb = new StringBuilder(tempDestUrl);
			for (int k = 0; k < sb.length(); k++) {
				if (sb.charAt(k) == '%') {
					k = k + 3;
					sb.insert(k, "%");
				}
			}
			try {
				tempDestUrl = URLDecoder.decode(sb.toString(), "utf-16be");
				String tempPath = tempDestUrl.substring(0,
						tempDestUrl.lastIndexOf("/") + 1);
				String tempImageName = tempDestUrl.substring(tempDestUrl
						.lastIndexOf("/") + 1);
				tempImageName = URLEncoder.encode(tempImageName, "utf-8");
				tempDestUrl = tempPath + tempImageName;

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("=====In GetPic destUrl=====" + tempDestUrl);
		} else {
			System.out.println("=====In GetPic destUrl=====" + tempDestUrl);
		}

		HttpGet get = new HttpGet(tempDestUrl);
		FileOutputStream fos = null;
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity.isStreaming()) {
				byte[] codes = EntityUtils.toByteArray(entity);
				fos = new FileOutputStream(picUrl + imageName);
				fos.write(codes);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			get.abort();
		}

	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GetPic getPic1 = new GetPic(
				"http://oa.coscoqmc.com.cn:7001/issue/img/201310162761.jpg");
		System.out.println(getPic1.newImageName(1));
	}

}