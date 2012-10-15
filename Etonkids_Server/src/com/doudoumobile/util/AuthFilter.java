package com.doudoumobile.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;

import com.doudoumobile.model.SessionData;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.ServiceLocator;
import com.doudoumobile.service.UserService;

public class AuthFilter implements Filter {
	FilterConfig fConfig;
	private static String ERROR_PAGE = "/error.jsp";
	private static String LOGIN_PAGE = "/login.jsp";
	private static String NOT_AUTHED_PAGE = "/403.jsp";

	private static HashMap<String , Long> ticketMap = new HashMap<String , Long>();
	
	private EtonService etonService;
	
	private Log log;
	
    /**
     * Default constructor. 
     */
    public AuthFilter() {
    	log = LogFactory.getLog(getClass());
    	etonService = (EtonService)ServiceLocator.getService("etonService");
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Auth Filterd");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		String ticket = ServletRequestUtils.getStringParameter(req, "doudouTicket","");
		// ticket的可能情况：1.app端的身份识别 2.登陆Action的标示 
		if (!ticket.isEmpty()) {
			log.debug("App端登陆");
			if ("login".equalsIgnoreCase(ticket)) {
				// go to login
				chain.doFilter(request, response);
			} else {
				//身份识别，从中获得用户信息
				long userId = processTicket(ticket);
				if (userId != -1) {
					request.setAttribute("userId", userId);
					chain.doFilter(request, response);
					return;
				}
				// return illegal request
				resp.sendRedirect(NOT_AUTHED_PAGE);
			}
		} else {
			//意味着从web端登陆
			log.debug("Web端登陆");
			HttpSession session = req.getSession(false);	
			if (null != session) {
				Object sessionData = session.getAttribute("sessionData");
				if (null != sessionData) {
					chain.doFilter(request, response);
				}
				else {
					setSessionData(req,resp,chain);
				}
			} else {
				setSessionData(req,resp,chain);
			}
		}
		
		//chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.fConfig = fConfig;
	}
	
	//如果没有cookie那么查找url是否有相应的Doudou_ticket参数
	private void setSessionData(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
		String ticket = null;
		Cookie[] cookies = req.getCookies();
	    if (null != cookies && cookies.length > 0) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("EtonKids_ITeach_Ticket")) {
	                ticket = cookie.getValue();
	                break;
	            }
	        }
	    }
//	    //查找url
//	    //再判断1.判断内存map里是否有这个ticket,有那就直接通过获取用户id
//	    //2.如果没有再到数据库里去查找，获得用户
	    if (ticket != null) {
	    	long userId = processTicket(ticket);
	    	if (userId != -1) {
	    		SessionData sessionData = new SessionData();
	    		sessionData.setEtonUser(etonService.getUser(userId));
	    		
	        	req.getSession(true).setAttribute("sessionData", sessionData);
				log.info("Filter cookie verified Success! userId = " + userId);
				chain.doFilter(req, resp);
			} else {
				resp.sendRedirect(NOT_AUTHED_PAGE);
				log.info("Filter cookie verified Error! token = " + ticket);
			}
			
		} else {
			// login 
			//request.getRequestDispatcher("login.jsp");
			resp.sendRedirect(req.getContextPath() + LOGIN_PAGE);
		}
	}
	
	private long processTicket(String str) {
		try {
			String token = new String(Base64.decode(str));
			
			log.info("decoded result : " + token);
			
			String[] tokenArray = token.split("/");
			if (tokenArray.length == 3 && tokenArray[2].equals("doudouTicket")) {
				long userId = Long.parseLong(tokenArray[0]);
				String loginName = tokenArray[1];
				if (ticketMap.containsKey(str)) {
					return userId;
				} else {
					if (etonService.getUser(userId).getUserName().equals(loginName)) {
						addTicket(str, userId);
						return userId;
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			// illegal
			log.error("Illegal ticket");
			e.printStackTrace();
			return -1;
		}
		return -1;
	}
	
	public static void addTicket(String ticket, long value) {
		ticketMap.put(ticket, value);
	}
	
	public static void removeTicket(String ticket) {
		ticketMap.remove(ticket);
	}

}

