package doudou.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import doudou.dao.ChildDao;
import doudou.dao.DaoFactory;
import doudou.dao.TeacherClassDao;
import doudou.util.Constants;
import doudou.util.dao.DatabaseDao;
import doudou.util.tool.Base64;
import doudou.vo.Child;
import doudou.vo.DoudouInfoType;
import doudou.vo.SchoolClass;
import doudou.vo.TeacherClass;
import doudou.vo.User;
import doudou.vo.model.SessionData;
import doudou.vo.model.TagedInfo;

@Service
public class DoudouService {
	private Logger logger = Logger.getLogger(getClass());
	private final DatabaseDao myDatabaseDao; 
	
	private final TeacherClassDao teacherClassDao;
	private final ChildDao childDao;
	
	public DoudouService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		teacherClassDao = myDatabaseDao.getEntityDao(TeacherClassDao.class);
		childDao = myDatabaseDao.getEntityDao(ChildDao.class);
	}
	
	public String getToken(User user) {
		String tokenString = Constants.DOUDOU_TICKET + "/" + user.getId() + "/" + user.getLogin();
		System.out.println("Before Encode : " + tokenString);
		String token = Base64.encode(tokenString.getBytes());
		System.out.println("After Encode : token = " + token);
		return token;
	}
	
	public SessionData getSessionData(User user) {
		logger.info("GetSessionData.........");
		SessionData sessionData = new SessionData();
		sessionData.setUser(user);
		Map<String,TagedInfo> tagedInfoMap = new HashMap<String,TagedInfo>();
		if (user.isTeacher()) {
			List<TeacherClass> teacherClassList = teacherClassDao.getTeacherClassListByTeacherId(user.getId());
			for (TeacherClass teacherClass : teacherClassList) {
				SchoolClass sc = teacherClass.getSchoolClass();
				DoudouInfoType type = teacherClass.getTeacherType();
				System.out.println(sc.getSchoolClassName() + " " + type.getTypeName());
				String teacherType = teacherClass.getTeacherType().getTypeName();
				List<Child> childList = childDao.getChildListByClassId(sc.getId());
				logger.info(String.format("Add to session : user: %s,TeacherType: %s ,Class: %s, childList size: %d",user.getFirstName(),teacherType,sc.getSchoolClassName(),childList.size()));
				if (tagedInfoMap.containsKey(teacherType)) {
					tagedInfoMap.get(teacherType).addClassAndChildList(sc, childList);
				} else {
					TagedInfo tagedInfo = new TagedInfo();
					tagedInfo.addClassAndChildList(sc, childList);
					tagedInfoMap.put(teacherType, tagedInfo);
				}
			}
		}
		sessionData.setTagedInfoMap(tagedInfoMap);
		return sessionData;
	}
	
	public List<Integer> getChildIdListFromString(String idString) {
		logger.info("Parse idString : " + idString);
		String[] idArray = idString.split(",");
		List<Integer> result = new ArrayList<Integer>();
		for (String idS : idArray) {
			result.add(Integer.parseInt(idS));
		}
		return result;
	}
	
	public List<Integer> getClassIdListFromChildIdList(List<Integer> childIdList) {
		List<Integer> classIdList = childDao.getClassIdListByChildIdList(childIdList);
		logger.info(String.format("Get child class Id : %s",classIdList));
		return classIdList;
	}
	
}
