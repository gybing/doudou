package mayaya.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import mayaya.service.EventService;
import mayaya.service.impl.EventServiceImpl;
import mayaya.system.CacheManager;
import mayaya.system.MayayaBackend;
import mayaya.util.BaseServlet;
import mayaya.util.tool.DateUtil;
import mayaya.vo.Comment;
import mayaya.vo.Event;
import mayaya.vo.Picture;
import mayaya.vo.User;
import mayaya.vo.model.EvtPublishTask;
import mayaya.vo.model.PicPublishTask;

/**
 * Servlet implementation class UploadEventAction
 */
public class UploadEventAction extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	private EventService eventService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadEventAction() {
        super();
        eventService = EventServiceImpl.getInstance();
        
    }

    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	int userId = (Integer)request.getAttribute("userId");
		Object[] whoToPush = null;
		
		String jsonString = request.getParameter("jsonString");
		
		JSONObject object = JSONObject.fromObject(jsonString);
		whoToPush = object.getJSONArray("whoToPush").toArray();
		
		String content = object.getString("content");
		System.out.println("content = " + content);
		String beginTimeString = object.getString("beginTimeString");
		System.out.println("beginTimeString = " + beginTimeString);
		String title = object.getString("title");
		System.out.println("title = " + title);
		String endTimeString = object.getString("endTimeString");
		System.out.println("endTimeString = " + endTimeString);
		String location  = object.getString("location");
		System.out.println("location = " + location);
		boolean allday = object.getBoolean("allday");
		System.out.println("allday = " + allday );
		
		Event event = new Event();
		event.setContent(content);
		if (false == allday) {
			event.setBeginTime(DateUtil.getInstance().fromString(beginTimeString));
			event.setEndTime(DateUtil.getInstance().fromString(endTimeString));
		} else {
			event.setBeginTime(DateUtil.getInstance().getDateFromString(beginTimeString));
			event.setEndTime(DateUtil.getInstance().getDateFromString(endTimeString));
		}
		event.setTitle(title);
		event.setLocation(location);
		event.setUserID(userId);
		event.setAllday(allday);
		
		EvtPublishTask task = new EvtPublishTask();
		//TODO add the current child into the child List;
		
		task.setEvent(event);
		
		List<Integer> childIdList = new ArrayList<Integer>();
		for (Object o : whoToPush) {
			childIdList.add((Integer)o);
		}
		task.setChildIdList(childIdList);
		MayayaBackend.getInstance().publishTask(task);
		CacheManager.getInstance().putEvt(userId);
	}

}
