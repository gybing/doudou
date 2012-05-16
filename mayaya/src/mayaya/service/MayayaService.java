package mayaya.service;

import java.util.Date;
import java.util.List;

import mayaya.vo.Child;
import mayaya.vo.Event;
import mayaya.vo.model.AnnouncePubTask;
import mayaya.vo.model.EvtPublishTask;
import mayaya.vo.model.MainPageVO;
import mayaya.vo.model.PhotoWallVO;
import mayaya.vo.model.PicPublishTask;
import mayaya.vo.model.SimpleMainPageVO;
import mayaya.vo.model.TagedChildInfo;

public interface MayayaService {
	void publishTask(PicPublishTask task);
	
	void publishTask(EvtPublishTask task);
	
	void publishTask(AnnouncePubTask task);
	
	List<PhotoWallVO> getPhotoWallData (Date date, int childID);
	
	MainPageVO getMainPageData(int childId, Date date);
	SimpleMainPageVO getSimpleMainPageData(int childId,Date date);
	
	List<Event> getEventByDate(int childId, Date date);
	
	int updateChildList(List<Child> childList);
	
	List<TagedChildInfo> getTagedChildInfo(int userId);
	
	List<Object> getNotificationVOListByUserId(int userId, int pageIndex, int pageSize);
}
