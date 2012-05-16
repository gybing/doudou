package mayaya.system;

import java.io.FileOutputStream;
import java.net.URI;
import java.util.Date;
import java.util.GregorianCalendar;

import mayaya.util.MayayaConfig;
import mayaya.vo.Event;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

public class ICSCalendarManager {
	
	private static ICSCalendarManager instance = new ICSCalendarManager();
	private TimeZoneRegistry registry;
	private TimeZone timezone;
	private VTimeZone tz;
	
	private String ICSFilePath;
	
	private ICSCalendarManager() {
		ICSFilePath = MayayaConfig.getConfig().getICSFilePath();
		registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		timezone = registry.getTimeZone("Asia/Shanghai");
		tz = timezone.getVTimeZone();
	}
	
	public static ICSCalendarManager getInstance() {
		return instance;
	}
	
	public String icsProducer(Event e) {
		String path = "";
		try {
			java.util.Calendar startDate = new GregorianCalendar();
			startDate.setTimeZone(timezone);
			startDate.setTime(e.getBeginTime());
			java.util.Calendar endDate = new GregorianCalendar();
			endDate.setTimeZone(timezone);
			endDate.setTime(new Date());

			// Create the event
			DateTime start = new DateTime(startDate.getTime());
			DateTime end = new DateTime(endDate.getTime());
			VEvent meeting = new VEvent(start, end, e.getTitle());
			meeting.getProperties().add(new Description(e.getContent()));
			// add timezone info..
			meeting.getProperties().add(tz.getTimeZoneId());

			// generate unique identifier..
			UidGenerator ug = new UidGenerator("uidGen");
			Uid uid = ug.generateUid();
			meeting.getProperties().add(uid);

			// add attendees..
//			Attendee dev1 = new Attendee(URI.create("mailto:dev1@mycompany.com"));
//			dev1.getParameters().add(Role.REQ_PARTICIPANT);
//			dev1.getParameters().add(new Cn("Developer 1"));
//			meeting.getProperties().add(dev1);
//
//			Attendee dev2 = new Attendee(URI.create("mailto:dev2@mycompany.com"));
//			dev2.getParameters().add(Role.OPT_PARTICIPANT);
//			dev2.getParameters().add(new Cn("Developer 2"));
//			meeting.getProperties().add(dev2);

			// Create a calendar
			net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
			icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
			icsCalendar.getProperties().add(Version.VERSION_2_0);
			icsCalendar.getProperties().add(CalScale.GREGORIAN);


			// Add the event and print
			icsCalendar.getComponents().add(meeting);
			System.out.println(icsCalendar);
			path = ICSFilePath + "mycalendar_" + e.getEventID() +".ics";
			FileOutputStream fout = new FileOutputStream(path);

			CalendarOutputter outputter = new CalendarOutputter();
			outputter.output(icsCalendar, fout);
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		return path;
	}

}
