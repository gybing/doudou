package mayaya.service.impl;

import java.util.Date;
import java.util.List;

import mayaya.service.PictureService;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Comment;
import mayaya.vo.Picture;
import mayaya.dao.CommentDao;
import mayaya.dao.DaoFactory;
import mayaya.dao.PictureDao;

public class PictureServiceImpl implements PictureService{
	
	private static PictureServiceImpl instance = new PictureServiceImpl(); 
	
	private DatabaseDao myDatabaseDao;
	private PictureDao pd;
	private CommentDao cd;

	public static PictureServiceImpl getInstance() {
		return instance;
	}
	
	public PictureServiceImpl() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		pd = myDatabaseDao.getEntityDao(PictureDao.class);
		cd = myDatabaseDao.getEntityDao(CommentDao.class);
	}
	
	@Override
	public Picture getPictureById(int id) {
		return pd.read(id);
	}

	@Override
	public int addPicture(Picture picture) {
		return (Integer)pd.create(picture);
	}

	@Override
	public List<Picture> getPicturesByDate(Date date) {
		return pd.getPicturesByDate(date);
	}
	
	

}
