package mayaya.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import mayaya.service.UserService;
import mayaya.service.impl.UserServiceImpl;
import mayaya.system.CacheManager;
import mayaya.util.BaseServlet;
import mayaya.vo.Child;
import mayaya.vo.model.ChildInfo;

/**
 * Servlet implementation class UserAction
 */
public class UserAction extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService;
    private Logger logger =Logger.getLogger(getClass());
    
    /**
     * @see BaseServlet#BaseServlet()
     */
    public UserAction() {
        super();
        userService = UserServiceImpl.getInstance();
    }
    
    // Get All Child info List by user Id
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	int userId = (Integer)request.getAttribute("userId");
    	logger.info("UserAction userId after filter = " + userId);
    	List<ChildInfo> childList = userService.getChildInfoListByUserId(userId);
    	
    	response.setContentType("text/x-json;charset=UTF-8");    
    	PrintWriter writer = response.getWriter();
    	JSONArray jsonArray = JSONArray.fromObject(childList);
		writer.print(jsonArray);
    }
    
    @Override
	protected long getLastModified(HttpServletRequest req) {
		int userId = (Integer)req.getAttribute("userId");
		long pic = CacheManager.getInstance().getPic(userId);
		long evt = CacheManager.getInstance().getEvt(userId);
		long bigger = pic > evt ? pic : evt;
		long child = CacheManager.getInstance().getChild(userId);
		return bigger > child ? bigger : child;
	}

}
