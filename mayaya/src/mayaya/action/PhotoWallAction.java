package mayaya.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mayaya.service.MayayaService;
import mayaya.service.impl.MayayaServiceImpl;
import mayaya.system.CacheManager;
import mayaya.util.BaseServlet;
import mayaya.util.tool.DateUtil;
import mayaya.vo.model.PhotoWallVO;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class PhotoWallAction
 */
public class PhotoWallAction extends BaseServlet {
	private static final long serialVersionUID = 1L;
    MayayaService mayayaService;
    
    public PhotoWallAction() {
        super();
        mayayaService = MayayaServiceImpl.getInstance();
    }
    
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	int childId = getIntParameter(request, "childId", 1);
    	Date date = DateUtil.getInstance().fromFullString("1970-01-01 00:00:00");
    	List<PhotoWallVO> result = mayayaService.getPhotoWallData(date, childId);
    	
    	response.setContentType("text/x-json;charset=UTF-8");           
        response.setHeader("Cache-Control", "max-age");
        
        PrintWriter writer = response.getWriter();
		
		JSONArray jsonArray = JSONArray.fromObject(result);
		writer.print(jsonArray);
    }
    
    @Override
	protected long getLastModified(HttpServletRequest req) {
		int userId = (Integer)req.getAttribute("userId");
		return CacheManager.getInstance().getPic(userId);
	}

}
