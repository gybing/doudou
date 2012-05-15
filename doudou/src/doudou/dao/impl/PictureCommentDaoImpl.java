package doudou.dao.impl;

import doudou.dao.PictureCommentDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.PictureComment;

public class PictureCommentDaoImpl extends BaseEntityDao<PictureComment, Integer> implements PictureCommentDao{

	public PictureCommentDaoImpl(DatabaseDao database) {
		super(database);
		
	}
	
	@Override
	public String getNamespace() {
		return "PictureComment";
	}
}
