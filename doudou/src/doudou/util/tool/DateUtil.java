package doudou.util.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	private DateFormat sdf ;
	private DateFormat detail;
	private DateFormat fullDateFormat;
	private DateFormat yearWithMoth;
	private DateFormat dateFormat;
	private DateFormat announceFormat;
	
	private static DateUtil instance = new DateUtil();
	
	private DateUtil() {
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		detail = new SimpleDateFormat("yyyyMMddHHmmss");
		fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		yearWithMoth = new SimpleDateFormat("yyyy-MM");
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		announceFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a",Locale.ENGLISH);
	}
	
	public static DateUtil getInstance() {
		return instance;
	}
	
	public String getDetailStringFromDate(Date date) {
		return detail.format(date);
	}
	
	public String getStringFromDate(Date date) {
		return sdf.format(date);
	}
	
	public Date fromString(String dateString) {
		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Date fromFullString(String dateString) {
		try {
			return fullDateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String toFullString(Date date) {
		return fullDateFormat.format(date);
	}
	
	public String toYearMonthString(Date date) {
		return yearWithMoth.format(date);
	}
	
	public String getBeginTimeOfDate(Date date) {
		return dateFormat.format(date) + " 00:00:00";
	}
	
	public String getEndTimeOfDate(Date date) {
		return dateFormat.format(date) + " 23:59:59";
	}
	
	public Date getDateFromString(String dateString) {
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getDateString(Date date) {
		return dateFormat.format(date);
	}
	
	public String toAnnounceString(Date date) {
		return announceFormat.format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getInstance().toAnnounceString(new Date()));
	}
}
