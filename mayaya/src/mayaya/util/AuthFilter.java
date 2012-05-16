package mayaya.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import mayaya.system.MayayaBackend;
import mayaya.util.error.ErrorCode;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class AuthFilter
 */
public class AuthFilter implements Filter {
	FilterConfig fConfig;
	private static String ERROR_PAGE = "/mayaya/error.jsp";
	private static String NOT_AUTHED_PAGE = "/mayaya/notAuth.html";
	//private SMSUtil smsUtil = SMSUtil.getInstance();
	private Logger logger;

	
    /**
     * Default constructor. 
     */
    public AuthFilter() {
    	logger = Logger.getLogger(getClass());
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
		String token = request.getParameter("token");
		System.out.println("Filtered");
		if (null == token || "".equals(token)) {
			request.setAttribute("error", ErrorCode.NotAuthed);
			((HttpServletResponse) response).sendRedirect(NOT_AUTHED_PAGE);
			logger.info("Token verified Error! token = null or empty ");
		}
		else {
			int veriCode = MayayaBackend.getInstance().verify(token);
			if (veriCode != -1) {
				request.setAttribute("userId", veriCode);
				logger.info("Token verified Success! userId = " + veriCode);
				chain.doFilter(request, response);
			} else {
				request.setAttribute("error", ErrorCode.NotAuthed);
				((HttpServletResponse) response).sendRedirect(NOT_AUTHED_PAGE);
				logger.info("Token verified Error! token = " + token);
			}
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.fConfig = fConfig;
	}
	
	

}
