package doudou.util;

import java.io.IOException;

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

import doudou.dao.DaoFactory;
import doudou.dao.UserDao;

import doudou.system.DoudouBackend;
import doudou.util.dao.DatabaseDao;
import doudou.vo.model.SessionData;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class AuthFilter
 */
public class AuthFilter implements Filter {
	FilterConfig fConfig;
	private static String ERROR_PAGE = "/error.jsp";
	private static String LOGIN_PAGE = "/login.jsp";
	private static String NOT_AUTHED_PAGE = "/notAuth.html";
	private Logger logger;

	private UserDao userDao;
	private DatabaseDao myDatabaseDao;
	
    /**
     * Default constructor. 
     */
    public AuthFilter() {
    	logger = Logger.getLogger(getClass());
    	myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
    	userDao = myDatabaseDao.getEntityDao(UserDao.class);
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

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.fConfig = fConfig;
	}
	
	private void setSessionData(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
		String ticket = null;
		Cookie[] cookies = req.getCookies();
	    if (null != cookies && cookies.length > 0) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals(Constants.DOUDOU_TICKET)) {
	                ticket = cookie.getValue();
	                break;
	            }
	        }
	    }
	    if (ticket != null) {
	    	int veriCode = DoudouBackend.getInstance().getUserByCookie(ticket);
	    	if (veriCode != -1) {
	    		SessionData sessionData = new SessionData();
	    		sessionData.setUser(userDao.read(veriCode));
	    		
	        	req.getSession(true).setAttribute("sessionData", sessionData);
				logger.info("Filter cookie verified Success! userId = " + veriCode);
				chain.doFilter(req, resp);
			} else {
				resp.sendRedirect(NOT_AUTHED_PAGE);
				logger.info("Filter cookie verified Error! token = " + ticket);
			}
			
		} else {
			// login 
			//request.getRequestDispatcher("login.jsp");
			resp.sendRedirect(req.getContextPath() + LOGIN_PAGE);
		}
		
		
		
		
	}
	

}
