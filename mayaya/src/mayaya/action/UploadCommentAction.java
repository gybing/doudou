package mayaya.action;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mayaya.service.CommentService;
import mayaya.service.PictureService;
import mayaya.service.impl.CommentServiceImpl;
import mayaya.service.impl.PictureServiceImpl;
import mayaya.system.CacheManager;
import mayaya.util.BaseServlet;
import mayaya.vo.Comment;
import mayaya.vo.Picture;

/**
 * Servlet implementation class UploadCommentAction
 */
public class UploadCommentAction extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	private CommentService commentService;
	private PictureService picService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadCommentAction() {
        super();
        commentService = CommentServiceImpl.getInstance();
        picService = PictureServiceImpl.getInstance();
    }
	
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	int userId = (Integer)request.getAttribute("userId");
    	
    	String content = request.getParameter("content");
    	System.out.println("content = " + content);
    	System.out.println("userID = " + userId);
    	String pictureID = request.getParameter("pictureID");
    	System.out.println("pictureID = " + pictureID);
    	
    	int picId = Integer.parseInt(pictureID);
    	Comment c = new Comment();
    	c.setContent(content);
    	c.setUserID(userId);
    	c.setPictureID(picId);
    	c.setPublishTime(new Date());
    	
    	Picture p = picService.getPictureById(picId);
    	
    	commentService.addComment(c,p.getUserId(),picId);
    	CacheManager.getInstance().putComment(userId);
    }

}
