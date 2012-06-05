package doudou.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import doudou.service.SchoolService;
import doudou.util.BaseServlet;
import doudou.vo.School;

@Controller
@RequestMapping("/School")
public class SchoolServlet extends BaseServlet {
	@Autowired
	SchoolService schoolService;
	
	@RequestMapping("/getSchoolList")
	public void getSchoolList(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		List<School> result = schoolService.getSchoolList();
		
		
		JSONObject jsonObj = JSONObject.fromObject(result);
		response.setContentType("text/x-json;charset=UTF-8");           
		response.getWriter().print(jsonObj);
	}
}
