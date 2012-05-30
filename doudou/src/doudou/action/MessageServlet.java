package doudou.action;

import doudou.service.DoudouService;
import doudou.service.MessageService;
import doudou.util.BaseServlet;
import doudou.util.tool.DateUtil;
import doudou.util.vo.ListResult;
import doudou.vo.DoudouInfoType;
import doudou.vo.Message;
import doudou.vo.model.SessionData;
import doudou.vo.type.PublishLevel;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Message")
public class MessageServlet extends BaseServlet {

    @Autowired
    MessageService messageService;
    @Autowired
    DoudouService doudouService;
    
	@RequestMapping("/addMessage")
	public void addMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionData sessionData = (SessionData) request.getAttribute("SessionData");
		String title = getStringParameter(request, "title", "");
		String content = getStringParameter(request, "content", "");
		int messageTypeId = getIntParameter(request, "messageType" , 0);
		String atChildList = getStringParameter(request, "atChildList", "");
		boolean mustFeedBack = getBoolParameter(request, "mustFeedBack", false);
		
		//PublishLevel publishLevel = sessionData.getCurrentPublishLevel();
		
		Message message = new Message();
		message.setAtChildList(atChildList);
		message.setTitle(title);
		message.setContent(content);
		message.setMessageTypeId(messageTypeId);
		message.setPublishLevel(PublishLevel.Class);
		message.setMustFeedBack(mustFeedBack);
		message.setUserId(sessionData.getUser().getId());
		
		List<Integer> childIdList = doudouService.getChildIdListFromString(atChildList);
		List<Integer> classIdList = doudouService.getClassIdListFromChildIdList(childIdList);
		int result = messageService.addMessage(message , childIdList, classIdList);
		
		response.getWriter().print(result);
	}
	
	@RequestMapping("/getMessageTypes")
	public void getMessageTypes (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionData sessionData = (SessionData) request.getSession().getAttribute("SessionData");
		List<DoudouInfoType> result = messageService.getMessageTypeList(sessionData.getSchoolId());

		JSONObject jsonObj = JSONObject.fromObject(result);
		response.setContentType("text/x-json;charset=UTF-8");
		response.getWriter().print(jsonObj);
	}
	
	@RequestMapping("/getClassMessageList")
	public void getClassMessageList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageIndex = getIntParameter(request, "pageIndex", 1);
		int count = getIntParameter(request, "perPageCount", 20);
		int offset = (pageIndex-1)*count;
		SessionData sessionData = (SessionData)request.getSession().getAttribute("SessionData");
		
		ListResult<Message> result = messageService.getClassMessageList(sessionData, offset, count);
		
		JSONObject jsonObj = JSONObject.fromObject(result);
		response.setContentType("text/x-json;charset=UTF-8");
		response.getWriter().print(jsonObj);
	}
	
	@RequestMapping("/queryClassMessageList")
	public void queryClassMessageList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageIndex = getIntParameter(request, "pageIndex", 1);
		int count = getIntParameter(request, "perPageCount", 20);
		int offset = (pageIndex-1)*count;
		SessionData sessionData = (SessionData)request.getSession().getAttribute("SessionData");
		boolean isUserSelf = getBoolParameter(request, "isUserSelf", false);
		boolean mustFeedBack = getBoolParameter(request, "mustFeedBack", false);
		String publishLevelString = getStringParameter(request, "publishLevel","");
		String beginTimeS = getStringParameter(request, "startTime", "");
		String endTimeS = getStringParameter(request, "endTime", "");
		String title = getStringParameter(request, "title","");
		
		Date beginTime = DateUtil.getInstance().fromFullString(beginTimeS);
		Date endTime = DateUtil.getInstance().fromFullString(endTimeS);
		PublishLevel publishLevel = PublishLevel.valueOf(publishLevelString);
		
		ListResult<Message> result = messageService.queryClassMessageList(sessionData, offset,
				count, title, publishLevel, beginTime, endTime, mustFeedBack, isUserSelf);
		
		JSONObject jsonObj = JSONObject.fromObject(result);
		response.setContentType("text/x-json;charset=UTF-8");
		response.getWriter().print(jsonObj);
	}
	
}
