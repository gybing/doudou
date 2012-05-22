package doudou.dao;

import java.util.HashMap;

import doudou.dao.impl.*;
import doudou.util.client.DatabaseClient;
import doudou.util.dao.BaseDatabaseDao;
import doudou.util.dao.EntityDao;


public class MyDatabaseDao extends BaseDatabaseDao {

	@SuppressWarnings("rawtypes")
	private final HashMap<Class, EntityDao> entities = new HashMap<Class, EntityDao>();
	
	public MyDatabaseDao(DatabaseClient client){
		super(client);
//		entities.put(CommentDao.class, new CommentDaoImpl(this));
		entities.put(PictureDao.class, new PictureDaoImpl(this));
		entities.put(UserDao.class, new UserDaoImpl(this));
		entities.put(ChildDao.class, new ChildDaoImpl(this));
		entities.put(EventDao.class, new EventDaoImpl(this));
//		entities.put(FriendsFromToDao.class, new FriendsFromToDaoImpl(this));
		entities.put(PictureUserDao.class, new PictureUserDaoImpl(this));
//		entities.put(RelationsChildUserDao.class, new RelationsChildUserDaoImpl(this));
		entities.put(ParentsDao.class, new ParentsDaoImpl(this));
		entities.put(TodoDao.class, new TodoDaoImpl(this));
		entities.put(DeviceTokenDao.class, new DeviceTokenDaoImpl(this));
		entities.put(MessageDao.class, new MessageDaoImpl(this));
		
	}
		

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityDao(Class<T> t) {
		return (T) entities.get(t);
	}

}
