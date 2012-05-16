package mayaya.dao;

import java.util.List;

import mayaya.util.dao.EntityDao;
import mayaya.vo.Child;

public interface ChildDao extends EntityDao<Child, Integer>{
	List<Child> getChildListByUserId(int userId);
	int updateHeadPic(int childId, String headPicUrl);
	int updateCover(int childId, String coverUrl);
}
