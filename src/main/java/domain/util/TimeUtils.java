package domain.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>版权所有: 版权所有 (C) 2014-2024</p >
 * <p>公    司: 北京科蓝软件系统股份有限公司</p >
 *
 * TimeUtils.java
 *
 * 创建时间: 2018年5月8日 下午5:21:45
 *
 * 作者: 魏军宏
 *
 * 功能描述：日期工具类
 *
 * 版本：1.0.0
 */
public class TimeUtils {
	public static final String data_formate = "yyyyMMdd";
	public static final String data_formate_ = "yyyy-MM-dd";
	
	/**
	 * 获取当前系统时间 格式为:yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getSystemTime() {
		Date day = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(day);
	}
	
	/**
	 * 获取系统前一日时间 格式为:yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getLastSysDate() {
		Calendar ca = Calendar.getInstance(); // 得到一个Calendar的实例
		ca.setTime(new Date()); 	// 设置时间为当前时间
		ca.add(Calendar.DATE, -1);  // 减1
		Date lastDate = ca.getTime();
		SimpleDateFormat DATE_FORMATE = new SimpleDateFormat("yyyy-MM-dd");
		return DATE_FORMATE.format(lastDate);
	}
	
	/**
	 * 获取系统当前日期 格式为: yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getSysDate(){
		SimpleDateFormat DATE_FORMATE =  new SimpleDateFormat("yyyy-MM-dd");
		return DATE_FORMATE.format(new Date());
	}
	
	/**
	 * 获取系统前一日时间 
	 *    格式为:yyyyMMdd
	 * @return String
	 */
	public static String getLastSysDate(String dataFormate) {
		Calendar ca = Calendar.getInstance(); // 得到一个Calendar的实例
		ca.setTime(new Date()); 	// 设置时间为当前时间
		ca.add(Calendar.DATE, -1);  // 减1
		Date lastDate = ca.getTime();
		SimpleDateFormat DATE_FORMATE = new SimpleDateFormat(dataFormate);
		return DATE_FORMATE.format(lastDate);
	}
}
