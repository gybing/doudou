package doudou.action;

import doudou.service.DoudouService;
import doudou.service.EventService;
import doudou.util.BaseServlet;
import doudou.util.tool.DateUtil;
import doudou.util.vo.ListResult;
import doudou.vo.Event;
import doudou.vo.model.SessionData;
import doudou.vo.type.PublishLevel;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Event")
public class EventServlet extends BaseServlet{

	@Autowired
	EventService eventService;
	@Autowired
	DoudouService doudouService;
	
	@RequestMapping("/addEvent")
    public void addEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String atChildList = getStringParameter(request, "atChildList", "");
        String title = getStringParameter(request, "title", "");
        String location = getStringParameter(request, "location", "");
        String beginTime = getStringParameter(request, "beginTime", "");
        String endTime = getStringParameter(request, "endTime", "");
        boolean allday = getBoolParameter(request, "allday", false);
        String content = getStringParameter(request, "content", "");
        
        Event event = new Event();
        event.setTitle(title);
        event.setLocation(location);
        event.setBeginTime(DateUtil.getInstance().fromString(beginTime));
        event.setEndTime(DateUtil.getInstance().fromString(endTime));
        event.setAllday(allday);
        event.setContent(content);
        
        //eventService;
        List<Integer> childIdList = doudouService.getChildIdListFromString(atChildList);
        List<Integer> classIdList = doudouService.getClassIdListFromChildIdList(childIdList);
        
        eventService.addEvent(event , childIdList, classIdList);
	}
	
	@RequestMapping("/getAllEvent")
	public void getAllEvent(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		SessionData sessionData = (SessionData)request.getSession().getAttribute("SessionData");
		int pageIndex = getIntParameter(request, "pageIndex", 1);
		int count = getIntParameter(request, "perPageCount", 20);
		int offset = (pageIndex-1)*count;
		ListResult<Event> result = eventService.getClassAllEventList(sessionData, offset, count);
		
		JSONObject jsonObj = JSONObject.fromObject(result);
		response.setContentType("text/x-json;charset=UTF-8");           
		response.getWriter().print(jsonObj);
	}
	
	@RequestMapping("/getClassEventListByDate")
	public void getClassEventListByDate(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		SessionData sessionData = (SessionData)request.getSession().getAttribute("SessionData");
		String dateString = getStringParameter(request, "date","2012-5-28");
		Date date = DateUtil.getInstance().getDateFromString(dateString);
		List<Event> result = eventService.getClassEventListByDate(sessionData, date);
		
		JSONObject jsonObj = JSONObject.fromObject(result);
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


}
