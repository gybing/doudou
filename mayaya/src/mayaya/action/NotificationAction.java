package mayaya.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mayaya.service.MayayaService;
import mayaya.service.impl.MayayaServiceImpl;
import mayaya.system.CacheManager;
import mayaya.util.BaseServlet;
import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class NotificationAction
 */
public class NotificationAction extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private MayayaService mayayaService;
    
	private Logger logger = Logger.getLogger(getClass());

    /**
     * @see BaseServlet#BaseServlet()
     */
    public NotificationAction() {
        super();
        mayayaService = MayayaServiceImpl.getInstance();
    }

    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	int userId = (Integer)request.getAttribute("userId");
    	int pageIndex = getIntParameter(request, "pageIndex", 0);
    	int pageSize = getIntParameter(request, "pageSize", 20);
    	logger.info(String.format("NotificationAction : userId : %d,pageIndex: %d,pageSize:%d", userId,pageIndex,pageSize));
    	List<Object> result = mayayaService.getNotificationVOListByUserId(userId,pageIndex,pageSize);
    	response.setContentType("text/x-json;charset=UTF-8");
    	PrintWriter writer = response.getWriter();
    	
    	JSONArray ja = JSONArray.fromObject(result);
    	writer.print(ja);
	
    }
    
    @Override
	protected long getLastModified(HttpServletRequest req) {
		int userId = (Integer)req.getAttribute("userId");
		long pic = CacheManager.getInstance().getPic(userId);
		long evt = CacheManager.getInstance().getEvt(userId);
		long com = CacheManager.getInstance().getComment(userId);
		long ann = CacheManager.getInstance().getAnnounce(userId);
		long a = pic > evt ? pic : evt;
		long b = com > ann ? com : ann;
		return a > b ? a : b;
	}
}
