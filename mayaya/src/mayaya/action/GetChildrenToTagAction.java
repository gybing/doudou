package mayaya.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import mayaya.service.MayayaService;
import mayaya.service.impl.MayayaServiceImpl;
import mayaya.util.BaseServlet;
import mayaya.vo.model.TagedChildInfo;

/**
 * Servlet implementation class GetChildrenToTagAction
 */
public class GetChildrenToTagAction extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private MayayaService mayayaService;
    
    /**
     * @see BaseServlet#BaseServlet()
     */
    public GetChildrenToTagAction() {
        super();
        mayayaService = MayayaServiceImpl.getInstance();
    }

    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	int userId = (Integer)request.getAttribute("userId");
    	List<TagedChildInfo> childList = mayayaService.getTagedChildInfo(userId);
    	response.setContentType("text/x-json;charset=UTF-8");
    	PrintWriter writer = response.getWriter();
    	
    	JSONObject json = JSONObject.fromObject(childList);
    	writer.print(json);
    }

}
