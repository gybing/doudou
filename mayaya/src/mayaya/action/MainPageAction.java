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
import mayaya.util.tool.DateUtil;
import mayaya.vo.model.MainPageVO;

/**
 * Servlet implementation class MainPageAction
 */
public class MainPageAction extends BaseServlet {
	private static final long serialVersionUID = 1L;
	MayayaService mayayaService;
	
    /**
     * @see BaseServlet#BaseServlet()
     */
    public MainPageAction() {
        super();
        mayayaService = MayayaServiceImpl.getInstance();
    }

    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	int childId = getIntParameter(request,"childId",2);
    	Date date = new Date();
    	MainPageVO vo = mayayaService.getMainPageData(childId, date);
    	response.setContentType("text/x-json;charset=UTF-8");
    	PrintWriter writer = response.getWriter();
    	
    	JSONObject json = JSONObject.fromObject(vo);
    	writer.print(json);
    }
    
}
