package mayaya.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import mayaya.service.MayayaService;
import mayaya.service.impl.MayayaServiceImpl;
import mayaya.system.CacheManager;
import mayaya.util.BaseServlet;
import mayaya.util.tool.DateUtil;
import mayaya.vo.Child;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class UpdateChildAction
 */
public class UpdateChildAction extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private MayayaService mayayaService;
    private Logger logger = Logger.getLogger(getClass());
    
    /**
     * @see BaseServlet#BaseServlet()
     */
    public UpdateChildAction() {
        super();
        mayayaService = MayayaServiceImpl.getInstance();
    }
    
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	String jsonString = getStringParameter(request, "jsonString","");
    	logger.info("UpdateChildAction : " + jsonString);
    	JSONObject object = JSONObject.fromObject(jsonString);
    	
    	JSONArray objectArray = (JSONArray)object.get("arr");
    	List<Child> childList = new ArrayList<Child>();
    	
    	for (int i = 0; i < objectArray.size(); i++) {
    		Child child = new Child();
			JSONObject c = (JSONObject)objectArray.get(i);
			child.setFirstName((String)c.get("firstName"));
			child.setLastName((String)c.get("lastName"));
			child.setChildID((Integer)c.get("childID"));
			child.setClasses((String)c.get("classes"));
			child.setBirthDate(DateUtil.getInstance().getDateFromString((String)c.get("birthDate")));
			childList.add(child);
		}
    	
    	if (mayayaService.updateChildList(childList) > 0) {
    		response.getOutputStream().print("success");
    		int userId = (Integer)request.getAttribute("userId");
    		CacheManager.getInstance().putChild(userId);
    	} else {
    		response.getOutputStream().print("failure");
    	}
    	
    }
	
}
