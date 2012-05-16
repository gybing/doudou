package mayaya.util.tool;

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
	private DateFormat excelFormat;
	
	private static DateUtil instance = new DateUtil();
	
	private DateUtil() {
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		detail = new SimpleDateFormat("yyyyMMddHHmmss");
		fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		yearWithMoth = new SimpleDateFormat("yyyy-MM");
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		announceFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a",Locale.ENGLISH);
		excelFormat = new SimpleDateFormat("yyyy/mm/dd");
	}
	
	public static DateUtil getInstance() {
		return instance;
	}
	
	public String getDetailStringFromDate(Date date) {
		if (null != date) {
			return detail.format(date);
		} else {
			return "";
		}
	}
	
	public String getStringFromDate(Date date) {
		if (null != date) {
			return sdf.format(date);
		} else {
			return "";
		}
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
		if (null != date) {
			return fullDateFormat.format(date);
		} else {
			return "";
		}
	}
	
	public String toYearMonthString(Date date) {
		if (null != date) {
			return yearWithMoth.format(date);
		} else {
			return ""; 
		}
	}
	
	public String getBeginTimeOfDate(Date date) {
		if (null != date) {
			return dateFormat.format(date) + " 00:00:00";
		} else {
			return "";
		}
	}
	
	public String getEndTimeOfDate(Date date) {
		if (null != date) {
			return dateFormat.format(date) + " 23:59:59";
		} else {
			return "";
		}
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
		if (null != date) {
			return dateFormat.format(date);			
		} else {
			return "";
		}
	}
	
	public String toAnnounceString(Date date) {
		if (null != date) {
			return announceFormat.format(date);
		} else {
			return "";
		}
	}
	
	public Date getDateFromExcelString(String dateString) {
		try {
			return excelFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getInstance().toAnnounceString(new Date()));
	}
}
