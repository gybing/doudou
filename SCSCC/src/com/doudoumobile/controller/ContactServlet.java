package com.doudoumobile.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.doudoumobile.model.SCSCCUser;
import com.doudoumobile.service.SCSCCService;
import com.doudoumobile.system.ScsccBackend;
import com.doudoumobile.util.Cn2Char;
import com.doudoumobile.util.JsonHelper;
import com.doudoumobile.util.MapKeySorter;

public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	SCSCCService scsccService;
	
    public ContactServlet() {
        super();
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        if (null == scsccService) {
        	scsccService = (SCSCCService)context.getBean("scsccService");
		}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = ServletRequestUtils.getStringParameter(request, "userId", "");
		
		List<SCSCCUser> contactList = scsccService.getContactList(userId);
		
		HashMap<String, List> contactMap = new HashMap<String, List>();
		
		for(SCSCCUser user: contactList){
			
			String realname = user.getRealName();
			
			String firstAlpha = Cn2Char.getFirstCharacter(realname);
			
			if (!contactMap.containsKey(firstAlpha)){
				
				List<SCSCCUser> newList = new ArrayList<SCSCCUser>();
				newList.add(user);
				contactMap.put(firstAlpha, newList);
				
			}
			else {
				@SuppressWarnings("unchecked")
				ArrayList<SCSCCUser> theList = (ArrayList<SCSCCUser>) contactMap.get(firstAlpha);
				theList.add(user);
			}
			
		}
        Map<String, List> sortedContactMap = MapKeySorter.sort(contactMap);

		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        
        JSONArray array = JsonHelper.getInstance().getJsonArray(contactList);
    	JSONObject object = JsonHelper.getInstance().getJson(sortedContactMap);

        
    	writer.print(object);
	}
	
	@Override
	public long getLastModified(HttpServletRequest req) {
		return ScsccBackend.CONTACT_LAST_MODIFIED;
	}

}
