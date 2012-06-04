package doudou.action;

import doudou.service.DoudouService;
import doudou.service.MessageService;
import doudou.util.BaseServlet;
import doudou.util.tool.DateUtil;
import doudou.util.vo.ListResult;
import doudou.vo.DoudouInfoType;
import doudou.vo.Message;
import doudou.vo.MessageUser;
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
		SessionData sessionData = (SessionData) request.getSession().getAttribute("SessionData");
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
		int result = messageService.addMessage(message , childIdList, classIdList , sessionData.getSchoolId());
		
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
	
	@RequestMapping("/getMessageById")
	public void getMessageById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int messageId = getIntParameter(request, "messageId" , -1);
		if (messageId > 0) {
			Message message = messageService.getMessageById(messageId);
			List<MessageUser> messageUserList = messageService.getListByMessageId(messageId);
			JSONObject jsonObj = JSONObject.fromObject(message);
			jsonObj.accumulate("messageUserList",messageUserList);
			
			response.setContentType("text/x-json;charset=UTF-8");
			response.getWriter().print(jsonObj);
		}
		else {
			//Not Existed messageId
			response.getWriter().print("-1");
		}
	}
	@RequestMapping("/deleteMessage")
	public void deleteMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int messageId = getIntParameter(request, "messageId", 0);
		messageService.deleteMessage(messageId);
		SessionData sessionData = (SessionData) request.getSession().getAttribute("SessionData");
		
		//获取下一条消息
		Message message = messageService.getNextMessage(sessionData, messageId);
		List<MessageUser> messageUserList = messageService.getListByMessageId(message.getId());
		JSONObject jsonObj = JSONObject.fromObject(message);
		jsonObj.accumulate("messageUserList",messageUserList);
		
		response.setContentType("text/x-json;charset=UTF-8");
		response.getWriter().print(jsonObj);
	}
	
	@RequestMapping("/updateMessage")
	public void updateMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionData sessionData = (SessionData) request.getSession().getAttribute("SessionData");
		String title = getStringParameter(request, "title", "");
		String content = getStringParameter(request, "content", "");
		int messageTypeId = getIntParameter(request, "messageType" , 0);
		String atChildList = getStringParameter(request, "atChildList", "");
		
		int messageId = getIntParameter(request, "messageId", 0);
		
		Message oldMessage = messageService.getMessageById(messageId);
		int result = 0;
		//PublishLevel publishLevel = sessionData.getCurrentPublishLevel();
		
		//检查是否是自己发布的消息
		if (oldMessage.getUserId() != sessionData.getUser().getId()) {
			result = -1;
		} else {
			Message newMessage = new Message();
			newMessage.setAtChildList(atChildList);
			newMessage.setTitle(title);
			newMessage.setContent(content);
			newMessage.setMessageTypeId(messageTypeId);
			newMessage.setUserId(sessionData.getUser().getId());
			
			List<Integer> newChildIdList = doudouService.getChildIdListFromString(atChildList);
			List<Integer> newClassIdList = doudouService.getClassIdListFromChildIdList(newChildIdList);
			result = messageService.updateMessage(newMessage , oldMessage, newChildIdList, newClassIdList , sessionData.getSchoolId());
		}
		
		response.getWriter().print(result);
	}
	
	@RequestMapping("getNextMessage")
	public void getNextMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int messageId = getIntParameter(request, "messageId", 0);
		SessionData sessionData = (SessionData) request.getSession().getAttribute("SessionData");
		
		Message message = messageService.getNextMessage(sessionData, messageId);
		List<MessageUser> messageUserList = messageService.getListByMessageId(message.getId());
		JSONObject jsonObj = JSONObject.fromObject(message);
		jsonObj.accumulate("messageUserList",messageUserList);
		
		response.setContentType("text/x-json;charset=UTF-8");
		response.getWriter().print(jsonObj);
	}
	
	@RequestMapping("getPreviousMessage")
	public void getPreviousMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int messageId = getIntParameter(request, "messageId", 0);
		SessionData sessionData = (SessionData) request.getSession().getAttribute("SessionData");
		
		Message message = messageService.getPreviousMessage(sessionData, messageId);
		List<MessageUser> messageUserList = messageService.getListByMessageId(message.getId());
		JSONObject jsonObj = JSONObject.fromObject(message);
		jsonObj.accumulate("messageUserList",messageUserList);
		
		response.setContentType("text/x-json;charset=UTF-8");
		response.getWriter().print(jsonObj);
	}
}
