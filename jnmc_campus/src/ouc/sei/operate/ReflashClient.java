package ouc.sei.operate;

import java.io.File;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import ouc.sei.beans.ClientBean;
import ouc.sei.common.Constant;

public class ReflashClient extends Thread
{
	private String url = Constant.defaultWebUrl;//获取首页url
	private int reflashtime=Constant.indexflash;//刷新首页间隔时间 30
	public ReflashClient() 
	{
		System.out.println("In operate the ReflashClient.java is selected");
		this.setDaemon(true);
		start();
	}
	private void cleanfiles(String url)
	{
		System.out.println("In operate the ReflashClient.java is selected");
		File f=new File(url);
		File[] lists=f.listFiles();
		if(lists.length!=0)
		{
		for(int i=0;i<lists.length;i++)
		{
				if(lists[i].isFile())
				{
					lists[i].delete();
				}
		}
		}
	}
	public void run() 
	{
		System.out.println("In operate the ReflashClient.java is selected");
		try 
		{
			sleep(1000*60);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) 
		{
			System.out.println("in reflashClient!!Constant.clientList.isEmpty()?"+Constant.clientList.isEmpty());
			if(!Constant.clientList.isEmpty())
			{
				Iterator it = Constant.clientList.keySet().iterator();
				while(it.hasNext())
				{
			        int key =Integer.valueOf(String.valueOf(it.next()));
			        	//it.next().hashCode();
			        int time=Constant.clientList.get(key).getTime();
			        System.out.println("key:"+key+"\ntime:"+time);
			        if(time>1)
			        {
			        	Constant.clientList.get(key).setTime(time-1);
			        }
			        else
			        {
			        	Constant.clientList.get(key).getClient().getConnectionManager().shutdown();
			        	Constant.clientList.remove(key);
			        	it = Constant.clientList.keySet().iterator();
			        }
			       
				}
			}
			Calendar cal = Calendar.getInstance();
				System.out.println("线程获得的时间"+cal.get(Calendar.HOUR_OF_DAY));
				if (Constant.C_SCHEDULE_HOUR == cal.get(Calendar.HOUR_OF_DAY)) 
				{
					Constant.i=1;
					Constant.clientList.clear();
					Constant.htmlStrList.clear();
	//				this.cleanfiles(Constant.picdownUrl);
	//				this.cleanfiles(Constant.yanzhengmaSaveUrl);
				} 
//				if(cal.get(Calendar.HOUR_OF_DAY)==0||cal.get(Calendar.HOUR_OF_DAY)==8||cal.get(Calendar.HOUR_OF_DAY)==10||cal.get(Calendar.HOUR_OF_DAY)==12){
//					Constant.htmlStrList.clear(); 
//				}
			try
			{
				sleep(1000*60);
			} catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
