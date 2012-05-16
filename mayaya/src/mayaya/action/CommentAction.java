package mayaya.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import mayaya.service.CommentService;
import mayaya.service.impl.CommentServiceImpl;
import mayaya.system.CacheManager;
import mayaya.vo.Comment;
import mayaya.vo.model.CommentInfo;

/**
 * Servlet implementation class CommentAction
 */
public class CommentAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CommentService commentService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentAction() {
        super();
        commentService = CommentServiceImpl.getInstance();
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
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter writer = response.getWriter();
        
        String type = request.getParameter("type");
        
        int pictureID = Integer.parseInt(request.getParameter("pictureID"));
        
		List<CommentInfo> commentInfoList = commentService.getCommentInfoListByPictureId(pictureID);
		
		JSONArray commentListJson = JSONArray.fromObject(commentInfoList);
		writer.print(commentListJson);
		
	}
	
	@Override
	protected long getLastModified(HttpServletRequest req) {
		int userId = (Integer)req.getAttribute("userId");
		return CacheManager.getInstance().getComment(userId);
	}
}
