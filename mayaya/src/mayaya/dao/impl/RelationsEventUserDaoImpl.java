package mayaya.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mayaya.dao.RelationsEventUserDao;
import mayaya.util.dao.BaseEntityDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.util.tool.DateUtil;
import mayaya.vo.Event;
import mayaya.vo.Relations_Event_User;

public class RelationsEventUserDaoImpl extends
		BaseEntityDao<Relations_Event_User, Integer> implements RelationsEventUserDao {

	public RelationsEventUserDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "Relations_Event_User";
	}

	@Override
	public List<Event> getMainPageEvents(int toChildId, Date date) {
		//取最近的事件列表
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("toChildId", toChildId);
		params.put("date", date);
		
		return readObjects("getMainPageEvents", params);
	}

	@Override
	public List<Event> getEventByDate(int childId, Date date) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("toChildId", childId);
		params.put("date", date);

		return readObjects("getEventByDate", params);
	}

	//这里不包含校历
	@Override
	public int getEventsCountByChildId(int childId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("toChildId", childId);
		return count("getEventsCountByChildId",params);
	}

	@Override
	public int getSchoolEventCount() {
		return count("getSchoolEventCount",null);
	}

}
