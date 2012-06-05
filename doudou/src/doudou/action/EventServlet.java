package doudou.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import doudou.service.DoudouService;
import doudou.service.EventService;
import doudou.util.BaseServlet;
import doudou.util.tool.DateUtil;
import doudou.util.vo.ListResult;
import doudou.vo.Event;
import doudou.vo.model.SessionData;
import doudou.vo.type.PublishLevel;

@Controller
@RequestMapping("/Event")
public class EventServlet extends BaseServlet{

	@Autowired
	EventService eventService;
	@Autowired
	DoudouService doudouService;
	
	@RequestMapping("/addEvent")
    public void addEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionData sessionData = (SessionData) request.getSession().getAttribute("SessionData");
		String atChildList = getStringParameter(request, "atChildList", "");
        String title = getStringParameter(request, "title", "");
        String location = getStringParameter(request, "locale", "");
        String beginTime = getStringParameter(request, "beginTime", "");
        String endTime = getStringParameter(request, "endTime", "");
        boolean allday = getBoolParameter(request, "allday", false);
        String content = getStringParameter(request, "content", "");
        
        //PublishLevel publishLevel = sessionData.getCurrentPublishLevel();
        
        Event event = new Event();
        event.setTitle(title);
        event.setLocation(location);
        event.setBeginTime(DateUtil.getInstance().fromString(beginTime));
        event.setEndTime(DateUtil.getInstance().fromString(endTime));
        event.setAllday(allday);
        event.setContent(content);
        event.setPublishLevel(PublishLevel.Class);
        event.setAtChildList(atChildList);
        
        List<Integer> childIdList = doudouService.getChildIdListFromString(atChildList);
        List<Integer> classIdList = doudouService.getClassIdListFromChildIdList(childIdList);
        
        int result = eventService.addEvent(event , childIdList, classIdList, sessionData.getSchoolId());
        response.getWriter().print(result);
	}
	
	@RequestMapping("/getAllEvent")
	public void getAllEvent(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		SessionData sessionData = (SessionData)request.getSession().getAttribute("SessionData");
		int pageIndex = getIntParameter(request, "pageIndex", 1);
		int count = getIntParameter(request, "perPageCount", 20);
		int offset = (pageIndex-1)*count;
		ListResult<Event> result = eventService.getClassAllEventList(sessionData, offset, count);
		
		JSONArray jsonObj = JSONArray.fromObject(result);
		response.setContentType("text/x-json;charset=UTF-8");           
		response.getWriter().print(jsonObj);
	}
	
	@RequestMapping("/getClassEventListByDate")
	public void getClassEventListByDate(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		SessionData sessionData = (SessionData)request.getSession().getAttribute("SessionData");
		String dateString = getStringParameter(request, "date","2012-5-28");
		Date date = DateUtil.getInstance().getDateFromString(dateString);
		List<Event> result = eventService.getClassEventListByDate(sessionData, date);
		
		JSONArray jsonObj = JSONArray.fromObject(result);
		response.setContentType("text/x-json;charset=UTF-8");           
		response.getWriter().print(jsonObj);
	}
	
	@RequestMapping("/queryClassEventList")
	public void queryClassEventList(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		SessionData sessionData = (SessionData)request.getSession().getAttribute("SessionData");
		String title = getStringParameter(request, "title", "");
		String publishLevelString = getStringParameter(request, "publishLevel", "");
		PublishLevel publishLevel = PublishLevel.valueOf(publishLevelString);
		int pageIndex = getIntParameter(request, "pageIndex", 1);
		int count = getIntParameter(request, "perPageCount", 20);
		int offset = (pageIndex-1)*count;
		ListResult<Event> result = eventService.queryClassEventList(sessionData, title, publishLevel, offset, count);
		
		JSONObject jsonObj = JSONObject.fromObject(result);
		response.setContentType("text/x-json;charset=UTF-8");           
		response.getWriter().print(jsonObj);
	}
	
	@RequestMapping("/deleteEvent")
	public void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int eventId = getIntParameter(request, "eventId", 0);
		if (eventService.deleteEvent(eventId)) {
			SessionData sessionData = (SessionData) request.getSession().getAttribute("SessionData");
			//获取下一条
			Event event = eventService.getNextEvent(sessionData, eventId);
			JSONObject jsonObj = JSONObject.fromObject(event);
			
			response.setContentType("text/x-json;charset=UTF-8");
			response.getWriter().print(jsonObj);
		}
		else {
			response.getWriter().print("Failure");
		}
		
	}
	
	@RequestMapping("/updateEvent")
	public void updateEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionData sessionData = (SessionData) request.getSession().getAttribute("SessionData");
		String atChildList = getStringParameter(request, "atChildList", "");
        String title = getStringParameter(request, "title", "");
        String location = getStringParameter(request, "locale", "");
        String beginTime = getStringParameter(request, "beginTime", "");
        String endTime = getStringParameter(request, "endTime", "");
        boolean allday = getBoolParameter(request, "allday", false);
        String content = getStringParameter(request, "content", "");
		
		int eventId = getIntParameter(request, "eventId", 0);
		
		Event oldEvent = eventService.getEventById(eventId);
		int result = 0;
		//PublishLevel publishLevel = sessionData.getCurrentPublishLevel();
		
		//检查是否是自己发布的日历
		if (oldEvent.getUserId() != sessionData.getUser().getId()) {
			result = -1;
		} else {
			Event newEvent = new Event();
			newEvent.setAtChildList(atChildList);
			newEvent.setTitle(title);
			newEvent.setContent(content);
			newEvent.setUserId(sessionData.getUser().getId());
			
			List<Integer> newChildIdList = doudouService.getChildIdListFromString(atChildList);
			List<Integer> newClassIdList = doudouService.getClassIdListFromChildIdList(newChildIdList);
			result = eventService.updateEvent(newEvent , oldEvent, newChildIdList, newClassIdList , sessionData.getSchoolId());
		}
		
		response.getWriter().print(result);
	}
	
	@RequestMapping("getNextEvent")
	public void getNextMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int eventId = getIntParameter(request, "eventId", 0);
		SessionData sessionData = (SessionData) request.getSession().getAttribute("SessionData");
		
		Event event = eventService.getNextEvent(sessionData, eventId);
		JSONObject jsonObj = JSONObject.fromObject(event);
		
		response.setContentType("text/x-json;charset=UTF-8");
		response.getWriter().print(jsonObj);
	}
	
	@RequestMapping("getPreviousEvent")
	public void getPreviousMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int eventId = getIntParameter(request, "eventId", 0);
		SessionData sessionData = (SessionData) request.getSession().getAttribute("SessionData");
		
		Event event = eventService.getPreviousEvent(sessionData, eventId);
		JSONObject jsonObj = JSONObject.fromObject(event);
		
		response.setContentType("text/x-json;charset=UTF-8");
		response.getWriter().print(jsonObj);
	}
	
	@RequestMapping("getEventById")
	public void getEventById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int eventId = getIntParameter(request, "eventId", 0);
		
		Event event = eventService.getEventById(eventId);
		JSONObject jsonObj = JSONObject.fromObject(event);
		
		response.setContentType("text/x-json;charset=UTF-8");
		response.getWriter().print(jsonObj);
	}


}
