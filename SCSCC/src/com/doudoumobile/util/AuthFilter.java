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
import com.doudoumobile.service.SCSCCService;

public class AuthFilter implements Filter {
	FilterConfig fConfig;
	private static String ERROR_PAGE = "/error.jsp";
	private static String LOGIN_PAGE = "/Login.jsp";
	private static String NOT_AUTHED_PAGE = "/403.jsp";

	private static HashMap<String , Long> ticketMap = new HashMap<String , Long>();
	
	private SCSCCService scsccService;
	
	private Log log;
	
    /**
     * Default constructor. 
     */
    public AuthFilter() {
    	log = LogFactory.getLog(getClass());
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
		String url = req.getQueryString();
		System.out.println(url);
		if (null != url && url.endsWith("loginForWeb")) {
			chain.doFilter(request, response);
			return;
		}
		String ticket = ServletRequestUtils.getStringParameter(req, "scsccTicket","");
		// ticketÁöÑÂèØËÉΩÊÉÖÂÜµÔºö1.appÁ´ØÁöÑË∫´‰ªΩËØÜÂà´ 2.ÁôªÈôÜActionÁöÑÊ†áÁ§∫ 
		if (!ticket.isEmpty()) {
			log.debug("App登陆");
			if ("login".equalsIgnoreCase(ticket)) {
				// go to login
				chain.doFilter(request, response);
			} else {
				//Ë∫´‰ªΩËØÜÂà´Ôºå‰ªé‰∏≠Ëé∑ÂæóÁî®Êà∑‰ø°ÊÅØ
				long userId = processAppTicket(ticket);
				if (userId != -1) {
					request.setAttribute("userId", userId);
					chain.doFilter(request, response);
					return;
				}
				// return illegal request
				resp.sendRedirect(NOT_AUTHED_PAGE);
			}
		} else {
			//ÊÑèÂë≥ÁùÄ‰ªéwebÁ´ØÁôªÈôÜ
			log.debug("WebÁ´ØÁôªÈôÜ");
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
	
	//Â¶ÇÊûúÊ≤°ÊúâcookieÈÇ£‰πàÊü•ÊâæurlÊòØÂê¶ÊúâÁõ∏Â∫îÁöÑDoudou_ticketÂèÇÊï∞
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
//	    //Êü•Êâæurl
//	    //ÂÜçÂà§Êñ≠1.Âà§Êñ≠ÂÜÖÂ≠òmapÈáåÊòØÂê¶ÊúâËøô‰∏™ticket,ÊúâÈÇ£Â∞±Áõ¥Êé•ÈÄöËøáËé∑ÂèñÁî®Êà∑id
//	    //2.Â¶ÇÊûúÊ≤°ÊúâÂÜçÂà∞Êï∞ÊçÆÂ∫ìÈáåÂéªÊü•ÊâæÔºåËé∑ÂæóÁî®Êà∑
	    if (ticket != null) {
	    	long userId = processWebTicket(ticket);
	    	if (userId != -1) {
	    		SessionData sessionData = new SessionData();
	    		sessionData.setEtonUser(scsccService.getUser(userId));
	    		
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
	
	private long processAppTicket(String str) {
		try {
			String token = new String(Base64.decode(str));
			
			log.info("decoded result : " + token);
			
			String[] tokenArray = token.split("/");
			if (tokenArray.length == 4 && tokenArray[2].equals("doudouTicket")) {
				long userId = Long.parseLong(tokenArray[0]);
				String loginName = tokenArray[1];
				String deviceToken = tokenArray[3];
				if (ticketMap.containsKey(str)) {
					scsccService.updateLoginTime(userId , deviceToken);
					return userId;
				} else {
					if (scsccService.getUser(userId).getUserName().equals(loginName)) {
						scsccService.updateLoginTime(userId , deviceToken);
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
	
	private long processWebTicket(String str) {
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
					if (scsccService.getUser(userId).getUserName().equals(loginName)) {
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

