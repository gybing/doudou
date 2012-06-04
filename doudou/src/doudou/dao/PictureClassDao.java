package doudou.dao;

import doudou.util.dao.EntityDao;
import doudou.vo.PictureClass;

public interface PictureClassDao extends EntityDao<PictureClass, Integer> {
	public void updatePCUnavailable(PictureClass pictureClass);
}
