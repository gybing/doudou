package mayaya.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import mayaya.service.UserService;
import mayaya.service.impl.UserServiceImpl;
import mayaya.system.CacheManager;
import mayaya.util.BaseServlet;
import mayaya.vo.User;

/**
 * Servlet implementation class UpdateUserAction
 */
public class UpdateUserAction extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;
	
    /**
     * @see BaseServlet#BaseServlet()
     */
    public UpdateUserAction() {
        super();
        userService = UserServiceImpl.getInstance();
    }
    
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	String jsonString = getStringParameter(request, "jsonString","");
    	System.out.println("UpdateUserAction -- > jsonString = " + jsonString);
    	JSONObject object = JSONObject.fromObject(jsonString);
    	
    	User user = new User();
    	user.setUserId((Integer)object.get("userId"));
    	
    	user.setFirstName((String)object.get("firstName"));
    	user.setLastName((String)object.get("lastName"));
    	user.setEmail((String)object.get("email"));

    	if (userService.update(user) > 0) {
    		response.getOutputStream().print("success");
    		System.out.println("Success");
    		int userId = (Integer)request.getAttribute("userId");
    		CacheManager.getInstance().putUser(userId);
    	} else {
    		response.getOutputStream().print("failure");
    		System.out.println("Failure");
    	}
    }

}
