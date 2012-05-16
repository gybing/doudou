package mayaya.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import mayaya.service.MayayaService;
import mayaya.service.impl.MayayaServiceImpl;
import mayaya.system.CacheManager;
import mayaya.util.BaseServlet;
import mayaya.vo.model.SimpleMainPageVO;

/**
 * Servlet implementation class SimpleMainPageAction
 */
public class SimpleMainPageAction extends BaseServlet {
	private static final long serialVersionUID = 1L;
	MayayaService mayayaService; 
    /**
     * @see BaseServlet#BaseServlet()
     */
    public SimpleMainPageAction() {
        super();
        mayayaService = MayayaServiceImpl.getInstance();
    }

    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	int childId = getIntParameter(request,"childId",2);
    	Date date = new Date();
    	SimpleMainPageVO vo = mayayaService.getSimpleMainPageData(childId, date);
    	response.setContentType("text/x-json;charset=UTF-8");
    	PrintWriter writer = response.getWriter();
    	
    	JSONObject json = JSONObject.fromObject(vo);
    	writer.print(json);
    }
    
    @Override
	protected long getLastModified(HttpServletRequest req) {
		int userId = (Integer)req.getAttribute("userId");
		long pic = CacheManager.getInstance().getPic(userId);
		long evt = CacheManager.getInstance().getEvt(userId);
		return pic > evt ? pic : evt;
	}

}
