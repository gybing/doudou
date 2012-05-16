package mayaya.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import mayaya.service.EventService;
import mayaya.service.MayayaService;
import mayaya.service.impl.EventServiceImpl;
import mayaya.service.impl.MayayaServiceImpl;
import mayaya.system.CacheManager;
import mayaya.util.BaseServlet;
import mayaya.util.tool.DateUtil;
import mayaya.vo.Event;

/**
 * Servlet implementation class GetEventsAction
 */
public class GetEventsAction extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private MayayaService mayayaService;
    private EventService eventService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEventsAction() {
        super();
        mayayaService = MayayaServiceImpl.getInstance();
        eventService = EventServiceImpl.getInstance();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/x-json;charset=UTF-8");           
		PrintWriter writer = response.getWriter();
		List<Event> eventList = null;
		
		int childId = getIntParameter(request, "childId", 1);
		String dateString = getStringParameter(request, "beginTimeString","1970-01-01");
		System.out.println("dateString = " + dateString);
		if (dateString.equals("1970-01-01")) {
			eventList = eventService.getEventsByChildId(childId);
		}
		else {
			Date date = DateUtil.getInstance().getDateFromString(dateString);
			eventList = mayayaService.getEventByDate(childId, date);
		}
		JSONArray jsonArray = JSONArray.fromObject(eventList);
		writer.print(jsonArray);
	}
	
	@Override
	protected long getLastModified(HttpServletRequest req) {
		int userId = (Integer)req.getAttribute("userId");
		return CacheManager.getInstance().getEvt(userId);
	}

}
