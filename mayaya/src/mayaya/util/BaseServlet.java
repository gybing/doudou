package mayaya.util;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class BaseServlet extends HttpServlet {

    public static final String ERROR_PAGE = "/error.html";

    public static final String ERROR_MESSAGE = "errorMessage";
    
    private Logger logger = Logger.getLogger(getClass());
    
    public BaseServlet() {
    }

    @Override
    public void init(final ServletConfig sc) throws ServletException {
        super.init(sc);
        onInit();
    }

    protected void onInit() {
    }

    @Override
    protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Throwable th) {
        	logger.error(th.getMessage(),th.getCause());
            showError(request, response, "Exception occured : " + th.getMessage());
        }
    }

    @Override
    protected final void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Throwable th) {
        	logger.error(th.getMessage(),th.getCause());
            showError(request, response, "Exception occured : " + th.getMessage());
        }
    }

    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    }

    public int getIntParameter(final HttpServletRequest request, final String name, final int def) {
        final String s = getStringParameter(request, name);
        if (s != null) {
            try {
                return Integer.parseInt(s);
            } catch (final NumberFormatException nfe) {
            }
        }
        return def;
    }

    public Long getLongParameter(final HttpServletRequest request, final String name, final Long def) {
        final String s = getStringParameter(request, name);
        if (s != null) {
            try {
                return Long.valueOf(s);
            } catch (final NumberFormatException nfe) {
            }
        }
        return def;
    }

    public Double getDoubleParameter(final HttpServletRequest request, final String name, final Double def) {
        final String s = getStringParameter(request, name);
        if (s != null) {
            try {
                return Double.valueOf(s);
            } catch (final NumberFormatException nfe) {
            }
        }
        return def;
    }

    public boolean getBoolParameter(final HttpServletRequest request, final String name, final boolean def) {
        final String s = getStringParameter(request, name);
        if (s != null) {
            try {
                return Boolean.parseBoolean(s);
            } catch (final NumberFormatException nfe) {
            }
        }
        return def;
    }

    public String getStringParameter(final HttpServletRequest request, final String name) {
        return getStringParameter(request, name, null);
    }

    public String getStringParameter(final HttpServletRequest request, final String name, final String def) {
        final String s = request.getParameter(name);
        if ((s != null) && (s.length() > 0)) {
            return s;
        }
        return def;
    }

    public void showError(final HttpServletRequest request, final HttpServletResponse response, final String msg) throws ServletException {
        if (request.getAttribute(ERROR_MESSAGE) == null) {
        	System.out.println(msg);
            request.setAttribute(ERROR_MESSAGE, msg);
            doForward(request, response, ERROR_PAGE);
        }
    }

    public void doForward(final HttpServletRequest request, final HttpServletResponse response, final String page) throws ServletException {
        try {
            request.getRequestDispatcher(page).forward(request, response);
        } catch (final Exception e) {
            throw new ServletException(e);
        }
    }

    public void doRedirect(final HttpServletResponse response, final String URI) throws ServletException {
        try {
            response.sendRedirect(URI);
        } catch (final Exception e) {
            throw new ServletException(e);
        }
    }
}
