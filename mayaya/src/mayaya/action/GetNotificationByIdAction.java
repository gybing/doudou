package mayaya.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mayaya.service.AnnouncementService;
import mayaya.service.CommentService;
import mayaya.service.EventService;
import mayaya.service.MayayaService;
import mayaya.service.PictureService;
import mayaya.service.impl.AnnouncementServiceImpl;
import mayaya.service.impl.CommentServiceImpl;
import mayaya.service.impl.EventServiceImpl;
import mayaya.service.impl.MayayaServiceImpl;
import mayaya.service.impl.PictureServiceImpl;
import mayaya.util.BaseServlet;
import mayaya.vo.Comment;
import mayaya.vo.model.CommentWithPic;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class GetNotificationByIdAction
 */
public class GetNotificationByIdAction extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private AnnouncementService announcementService;
	private EventService eventService;
	private PictureService pictureService;
	private CommentService commentService;
	
	private Logger logger = Logger.getLogger(getClass());
    /**
     * @see BaseServlet#BaseServlet()
     */
    public GetNotificationByIdAction() {
        super();
        announcementService = AnnouncementServiceImpl.getInstance();
        eventService = EventServiceImpl.getInstance();
        pictureService = PictureServiceImpl.getInstance();
        commentService = CommentServiceImpl.getInstance();
    }
    
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	int contentId = getIntParameter(request, "contentId", 1);
    	String todoType = getStringParameter(request,"pushType");
    	logger.info(String.format("GetNotificationByIdAction : contentId : %d,todoType : %s",contentId, todoType));
    	Object o = null;
    	if ("Comment".equals(todoType)) {
    		CommentWithPic cwp = new CommentWithPic();
    		Comment c = commentService.getCommentById(contentId);
    		cwp.setComment(c);
    		if (c != null) {
    			cwp.setPicture(pictureService.getPictureById(c.getPictureID()));
			}
    		o = cwp;
		} else if ("EvtTag".equals(todoType)) {
			o = eventService.getEventById(contentId);
		} else if ("PicTag".equals(todoType)) {
			o = pictureService.getPictureById(contentId);
		} else if ("Announcement".equals(todoType)) {
			o = announcementService.getNotificationVOById(contentId);
		}
    	
    	logger.info("GetNotificationByIdAction : " + o);
    	response.setContentType("text/x-json;charset=UTF-8");
    	PrintWriter writer = response.getWriter();
    	
    	JSONObject json = JSONObject.fromObject(o);
    	writer.print(json);
    }

	

}
