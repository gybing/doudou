package mayaya.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.Notification;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import mayaya.system.CacheManager;
import mayaya.system.MayayaBackend;
import mayaya.util.BaseServlet;
import mayaya.vo.Announcement;
import mayaya.vo.model.AnnouncePubTask;
import mayaya.vo.model.EvtPublishTask;

/**
 * Servlet implementation class UploadNotification
 */
public class UploadNotification extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see BaseServlet#BaseServlet()
     */
    public UploadNotification() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	int userId = (Integer)request.getAttribute("userId");
		Object[] whoToPush = null;
		
		String jsonString = request.getParameter("jsonString");
		
		JSONObject object = JSONObject.fromObject(jsonString);
		whoToPush = object.getJSONArray("whoToPush").toArray();
		System.out.println(whoToPush);
		String content = object.getString("content");
		System.out.println("content = " + content);
		
		Announcement noti = new Announcement();
		noti.setContent(content);
		noti.setUserId(userId);
		
		
		AnnouncePubTask task = new AnnouncePubTask();
		
		task.setAnnouncement(noti);
		
		List<Integer> childIdList = new ArrayList<Integer>();
		for (Object o : whoToPush) {
			childIdList.add((Integer)o);
		}
		task.setChildrenList(childIdList);
		MayayaBackend.getInstance().publishTask(task);
		CacheManager.getInstance().putAnnounce(userId);
    }


	
}
