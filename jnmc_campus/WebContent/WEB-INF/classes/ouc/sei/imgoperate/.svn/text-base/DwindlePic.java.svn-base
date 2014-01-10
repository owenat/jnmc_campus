package ouc.sei.imgoperate;

import java.io.*;
import com.sun.image.codec.jpeg.*;
import com.sun.xml.internal.ws.wsdl.writer.document.http.Address;
import java.awt.image.*;
import java.awt.*;
import java.applet.*;

import ouc.sei.common.Constant;

/**
 * 类作用对图片进行缩放，存储在classes/pic文件夹中。
 * 
 * @author qsw-Myonlystar
 * 
 */
public class DwindlePic {

	String InputDir; // 输入图路径
	String OutputDir; // 输出图路径
	String InputFileName; // 输入图文件名
	String OutputFileName; // 输出图文件名
	int OutputWidth = 80; // 默认输出图片宽
	int OutputHeight = 80; // 默认输出图片高
	int rate = 1;// 缩放比例
	boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)
	private String defaultpath = Constant.picSaveUrl;// 文件默认的存储路径

	private int clientId;// 临时加上的，为了请求图片，尤其是请求验证码时使用。 杨志龙

	public DwindlePic() {
		// 初始化变量
		InputDir = "";
		OutputDir = "";
		InputFileName = "";
		OutputFileName = "";
		// OutputWidth = "";
		OutputHeight = 226;
		rate = 1;
	}

	/**
	 * 实现图片的缩放
	 * 
	 * @return
	 */
	public boolean s_pic() {
		// 建立输出文件对象
		Image img = null;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Applet app = new Applet();
		MediaTracker mt = new MediaTracker(app);
		try {
			img = tk.getImage(InputDir + InputFileName);
			mt.addImage(img, 0);
			mt.waitForID(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (img.getWidth(null) == -1) {
			System.out
					.println("======In DwinlePic can't read the download Image ,Place check it======");
			return false;
		} else {

			int new_w;
			int new_h;
			if (this.proportion == true && this.rate == 1) { // 判断是否是等比缩放.
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = ((double) img.getWidth(null))
						/ (double) OutputWidth + 0.1;
				double rate2 = ((double) img.getHeight(null))
						/ (double) OutputHeight + 0.1;
				double rate = rate1 > rate2 ? rate1 : rate2;
				if (rate < 1) {
					// 如果要求缩放的宽度大于图片的原有宽度，则图片按照原尺寸显示，简言之就是只实现图片的缩放，不实现图片的放大

					new_w = img.getWidth(null);
					new_h = img.getHeight(null);
					System.out.println("in DwindlePic new_w=" + new_w);
				} else {
					new_w = (int) (((double) img.getWidth(null)) / rate);
					new_h = (int) (((double) img.getHeight(null)) / rate);
				}
			} else if (this.proportion == true && this.rate != 1) {
				// 按图片的原尺寸等比例进行比例缩放
				new_w = (int) (((double) img.getWidth(null)) / rate);
				new_h = (int) (((double) img.getHeight(null)) / rate);
			} else {
				// 否则就按照规定的尺寸进行非等比例的缩放
				new_w = OutputWidth; // 输出的图片宽度
				new_h = OutputHeight; // 输出的图片高度
			}
			BufferedImage buffImg = new BufferedImage(new_w, new_h,
					BufferedImage.TYPE_INT_RGB);

			Graphics g = buffImg.createGraphics();

			g.setColor(Color.white);
			g.fillRect(0, 0, new_w, new_h);
			g.drawImage(img, 0, 0, new_w, new_h, null);
			g.dispose();
			File file = new File(OutputDir + OutputFileName);
			FileOutputStream tempout = null;
			try {
				tempout = new FileOutputStream(file);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(tempout);
			try {
				encoder.encode(buffImg);
				tempout.close();
			} catch (IOException ex) {
				System.out.println(ex.toString());
			}
		}
		return true;
	}

	public void setInputDir(String InputDir) {
		this.InputDir = InputDir;
	}

	public void setOutputDir(String OutputDir) {
		this.OutputDir = OutputDir;
	}

	public void setInputFileName(String InputFileName) {
		this.InputFileName = InputFileName;
	}

	public void setOutputFileName(String OutputFileName) {
		this.OutputFileName = OutputFileName;
	}

	public void setOutputWidth(int OutputWidth) {
		this.OutputWidth = OutputWidth;
	}

	public void setOutputHeight(int OutputHeight) {
		this.OutputHeight = OutputHeight;
	}

	public void setW_H(int width, int height) {
		this.OutputWidth = width;
		this.OutputHeight = height;
	}

	/**
	 * 
	 * @param InputFileName
	 * @param OutputFileName
	 * @param width
	 * @param height
	 * @param gp
	 * @return
	 */
	public boolean s_pic(String InputFileName, String OutputFileName,
			int width, int height, boolean gp) {
		System.out.println(defaultpath);
		// 输入图路径
		this.InputDir = defaultpath;
		// 输出图路径
		this.OutputDir = defaultpath;
		// 输入图文件名
		this.InputFileName = InputFileName;
		// 输出图文件名
		this.OutputFileName = OutputFileName;
		// 设置图片长宽
		setW_H(width, height);
		// 是否是等比缩放 标记
		this.proportion = gp;
		return s_pic();
	}

	/**
	 * 通过提供weburl进行图片的获取和缩放
	 * 
	 * @param webUrl
	 *            //图片的网络地址
	 * @param width
	 * @param height
	 * @param gp
	 * @return 返回文件名称
	 */
	public String s_pic(String webUrl, int width, int height, boolean gp) {
		GetPic getPic = new GetPic(webUrl);
		getPic.saveToFile();
		String imageName = getPic.getImageName();
		// 输入图路径
		this.InputDir = defaultpath;
		// 输出图路径
		this.OutputDir = defaultpath;
		// 输入图文件名
		this.InputFileName = imageName;
		// 输出图文件名
		this.OutputFileName = imageName;
		// 设置图片长宽
		setW_H(width, height);
		// 是否是等比缩放 标记
		this.proportion = gp;
		boolean s = s_pic();
		System.out.println("图片缩放是否成功:" + s);
		return imageName;
	}

	/**
	 * 按rate规定的比例等比缩放图片
	 * 
	 * @param webUrl
	 * @param rate
	 * @return 文件名称
	 */
	public String s_pic(String webUrl, int width) {
		GetPic getPic = new GetPic(webUrl);
		// getPic.saveToFile();
		getPic.saveToFile2(clientId);
		String imageName = getPic.getImageName();

		String dName = "";
		if (imageName.contains("%")) {
			dName = imageName.replaceAll("%", "");
		} else {
			dName = imageName;
		}
		System.out.println("======In s_pic dName is ======" + dName);
		// 输入图路径
		this.InputDir = defaultpath;
		// 输出图路径
		this.OutputDir = defaultpath;
		// 输入图文件名
		this.InputFileName = imageName;
		// 输出图文件名
		this.OutputFileName = dName;
		// 设置图片长宽
		setW_H(width, 1000);
		// this.OutputWidth=width;
		// 是否是等比缩放 标记
		this.proportion = true;
		boolean s = s_pic();
		System.out.println("图片缩放是否成功:" + s);
		return dName;
	}

	/**
	 * 图片按照宽度为226等比例缩放，宽度不足226的不进行处理
	 * 
	 * @param webUrl
	 * @return
	 */

	public String s_pic(String webUrl) {
		GetPic getPic = new GetPic(webUrl);
		getPic.saveToFile();
		String imageName = getPic.getImageName();
		// 输入图路径
		this.InputDir = defaultpath;
		System.out.println("in s-pic defaultPath=" + defaultpath);
		// 输出图路径
		this.OutputDir = defaultpath;
		// 输入图文件名
		this.InputFileName = imageName;
		// 输出图文件名
		this.OutputFileName = imageName;
		// 设置图片长宽
		setW_H(226, 1000);
		this.proportion = true;
		boolean s = s_pic();
		System.out.println("图片缩放是否成功:" + s);
		return imageName;
	}

	// 用来获得到WEB-INF的路径
	public String getAddress() {
		Class theClass = Address.class;
		java.net.URL u = theClass.getResource("/");
		// str会得到这个函数所在类的路径
		String str = u.toString();
		// 截去一些前面6个无用的字符
		str = str.substring(6, str.length());
		// 将%20换成空格（如果文件夹的名称带有空格的话，会在取得的字符串上变成%20）
		str = str.replaceAll("%20", " ");
		// 查找“WEB-INF”在该字符串的位置
		// int num = str.indexOf("WEB-INF");
		// 截取即可
		// str=str.substring(0, num+"WEB-INF".length());
		return str;
	}

	/**
	 * 测试
	 * 
	 * @param a
	 */
	public static void main(String[] args) {
		// s_pic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度)
		// String path=this.getClass().getResource("/").getPath()+"/pic/";
		// String
		// webUrl="http://www.sailuntyre.com.cn/manage/FCKeditor/UserImages/dc032e52-574a-4fb5-9128-5da37c02942e.JPG";
		// GetPic gp=new GetPic(webUrl);
		// gp.saveToFile();
		// String imageName=gp.getImageName(webUrl);
		// System.out.println(imageName);
		DwindlePic mypic = new DwindlePic();
		// String
		// path=(mypic.getClass().getResource("/").getPath()+"pic/").substring(1);
		// System.out.println(path);
		// boolean s=mypic.s_pic(path, path, imageName, imageName, 30,20, true);
		// 图片按照规定的路径和文件名称和比例进行缩放
		// String s=mypic.s_pic(webUrl, 30,20, true);//图片按照规定的长宽进行缩放,返回文件名称

		// String s=mypic.s_pic(webUrl,10);//实现图片的等比例缩放，返回文件名称
		// String s=mypic.s_pic(webUrl);
		// System.out.println("文件名称："+s);
		// String path=mypic.getAddress();
		// System.out.println(path);
		boolean s = mypic.s_pic("20090916204929614803.jpg",
				"20090916204929614803.jpg", 50, 50, true);
		System.out.println(s);

	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
}