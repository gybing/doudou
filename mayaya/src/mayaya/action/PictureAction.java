package mayaya.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mayaya.service.PictureService;
import mayaya.service.impl.PictureServiceImpl;
import mayaya.system.CacheManager;
import mayaya.util.tool.DateUtil;
import mayaya.vo.Picture;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class PictureAction
 */
public class PictureAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private PictureService picService;
    
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PictureAction() {
        super();
        picService = PictureServiceImpl.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/x-json;charset=UTF-8");           
        Date date = DateUtil.getInstance().fromFullString("1970-01-01 00:00:00");
        List<Picture> picList = picService.getPicturesByDate(date);
        
		PrintWriter writer = response.getWriter();

		JSONArray jsonArray = JSONArray.fromObject(picList);
		writer.print(jsonArray);
	}
	
	@Override
	protected long getLastModified(HttpServletRequest req) {
		int userId = (Integer)req.getAttribute("userId");
		return CacheManager.getInstance().getPic(userId);
	}
}
