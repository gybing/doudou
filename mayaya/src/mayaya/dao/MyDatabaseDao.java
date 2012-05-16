package mayaya.dao;

import java.util.HashMap;

import mayaya.dao.impl.AnnouncementDaoImpl;
import mayaya.dao.impl.ChildDaoImpl;
import mayaya.dao.impl.CommentDaoImpl;
import mayaya.dao.impl.DeviceTokenDaoImpl;
import mayaya.dao.impl.EventDaoImpl;
import mayaya.dao.impl.FriendsFromToDaoImpl;
import mayaya.dao.impl.PictureDaoImpl;
import mayaya.dao.impl.PushPictureUserDaoImpl;
import mayaya.dao.impl.RelationsChildUserDaoImpl;
import mayaya.dao.impl.RelationsEventUserDaoImpl;
import mayaya.dao.impl.TodoDaoImpl;
import mayaya.dao.impl.UserDaoImpl;
import mayaya.util.client.DatabaseClient;
import mayaya.util.dao.BaseDatabaseDao;
import mayaya.util.dao.EntityDao;

public class MyDatabaseDao extends BaseDatabaseDao {

	@SuppressWarnings("rawtypes")
	private final HashMap<Class, EntityDao> entities = new HashMap<Class, EntityDao>();
	
	public MyDatabaseDao(DatabaseClient client){
		super(client);
		entities.put(CommentDao.class, new CommentDaoImpl(this));
		entities.put(PictureDao.class, new PictureDaoImpl(this));
		entities.put(UserDao.class, new UserDaoImpl(this));
		entities.put(ChildDao.class, new ChildDaoImpl(this));
		entities.put(EventDao.class, new EventDaoImpl(this));
		entities.put(FriendsFromToDao.class, new FriendsFromToDaoImpl(this));
		entities.put(PushPictureUserDao.class, new PushPictureUserDaoImpl(this));
		entities.put(RelationsChildUserDao.class, new RelationsChildUserDaoImpl(this));
		entities.put(RelationsEventUserDao.class, new RelationsEventUserDaoImpl(this));
		entities.put(TodoDao.class, new TodoDaoImpl(this));
		entities.put(DeviceTokenDao.class, new DeviceTokenDaoImpl(this));
		entities.put(PushPictureUserDao.class, new PushPictureUserDaoImpl(this));
		entities.put(AnnouncementDao.class, new AnnouncementDaoImpl(this));
		
	}
		

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityDao(Class<T> t) {
		return (T) entities.get(t);
	}

}
