package doudou.action;

import doudou.service.EventService;
import doudou.util.BaseServlet;
import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Event")
public class EventServlet extends BaseServlet{

	@RequestMapping("/addEvent")
    public void addEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String atChildList = request.getParameter("atChildList");
        String title = request.getParameter("title");
        String location = request.getParameter("locale");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String allday = request.getParameter("allday");
        String content = request.getParameter("content");
        EventService es = new EventService();
    }



}
