package doudou.dao.impl;

import doudou.dao.PictureClassDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.PictureClass;

public class PictureClassDaoImpl extends BaseEntityDao<PictureClass, Integer> implements PictureClassDao {

	public PictureClassDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "PictureClass";
	}

	@Override
	public void updatePCUnavailable(PictureClass pictureClass) {
		update("updatePCUnavailable",pictureClass);
	}

	
}
