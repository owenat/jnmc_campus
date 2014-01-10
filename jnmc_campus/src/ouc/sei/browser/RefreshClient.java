/**
 * @author Yang zhilong
 * @date 2011-12-29
 * @function 本类主要有两个功能，一个是对于用户没有任何操作超过三十分钟的client进行shoudown操作，
 * 			另外一个功能每天两点时间，对整个ClientList进行清空。并且让标识用户Id的i置为0
 */
package ouc.sei.browser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import ouc.sei.common.Constant;

public class RefreshClient {

	private RefreshClient() {
	}

	private static RefreshClient refreshClient = new RefreshClient();

	public synchronized static RefreshClient getInstance() {
		return refreshClient;
	}

	public synchronized void refreshTime() {
		Timer timer = new Timer();
		refreshTimeClass rtc = new refreshTimeClass();
		/** Tomcat启动之后每一分钟执行一次 **/
		timer.schedule(rtc, 0, 1000 * 60);
	}

	public synchronized void refreshClientId() {
		Calendar calendar = Calendar.getInstance();

		/*** 定制每日2:00执行方法 ***/
		calendar.set(Calendar.HOUR_OF_DAY, 2);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Date date = calendar.getTime(); // 第一次执行定时任务的时间

		// 如果第一次执行定时任务的时间 小于 当前的时间
		// 此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		if (date.before(new Date())) {
			date = this.addDay(date, 1);
		}

		Timer timer = new Timer();
		refreshTimeClass rtc = new refreshTimeClass();
		/*** 定制每日2:00执行 ***/
		timer.schedule(rtc, date, 1000 * 60 * 60 * 24);

	}

	// 增加或减少天数
	public Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}

}

class refreshTimeClass extends TimerTask {
	@Override
	public void run() {
		System.out.println("======refreshTimeClass Run======");

		ArrayList<Integer> noRestTimeId = new ArrayList<Integer>();
		// TODO Auto-generated method stub
		if (!Constant.clientList.isEmpty()) {
			Set<Integer> keys = Constant.clientList.keySet();
			for (Iterator<Integer> it = keys.iterator(); it.hasNext();) {
				Integer i = it.next();
				if (Constant.clientList.get(i).getTime() > 1) {
					Constant.clientList.get(i).setTime(
							Constant.clientList.get(i).getTime() - 1);
					System.out.println("======ClientId " + i
							+ " time - 1 , And the rest time is "+Constant.clientList.get(i).getTime()+" minutes======"
							 );
				} else {
					
					//对于剩余时间为0的Client，进行统计
					noRestTimeId.add(i);
				}
			}

			//对于时间为0的client进行删除操作
			if (!noRestTimeId.isEmpty()) {
				for (Iterator<Integer> it = noRestTimeId.iterator(); it
						.hasNext();) {
					Integer i = it.next();
					Constant.clientList.get(i).getClient()
							.getConnectionManager().shutdown();
					Constant.clientList.remove(i);
					System.out.println("======Remove Client, it's ID is "+ i +"======");

				}
			}
		} else {
			System.out.println("======Constant.clientList is Empty======");
		}

	}

}

class refreshClientIdClass extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (!Constant.clientList.isEmpty()) {
			//对整个列表清除
			Constant.clientList.clear();
			//设置初始标识client的值为0
			Constant.i = 0;
		}

	}

}
