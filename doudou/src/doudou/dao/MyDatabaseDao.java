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
		entities.put(UserDao.class, new UserDaoImpl(this));
		entities.put(ChildDao.class, new ChildDaoImpl(this));
		entities.put(EventDao.class, new EventDaoImpl(this));
		entities.put(ParentsDao.class, new ParentsDaoImpl(this));
		entities.put(TodoDao.class, new TodoDaoImpl(this));
		entities.put(DeviceTokenDao.class, new DeviceTokenDaoImpl(this));
		entities.put(MessageDao.class, new MessageDaoImpl(this));
		entities.put(TeacherClassDao.class, new TeacherClassDaoImpl(this));
		entities.put(SchoolDao.class, new SchoolDaoImpl(this));
		entities.put(SchoolClassDao.class, new SchoolClassDaoImpl(this));
		entities.put(ChildClassDao.class, new ChildClassDaoImpl(this));
		entities.put(TeacherDao.class, new TeacherDaoImpl(this));
		entities.put(DoudouInfoTypeDao.class, new DoudouInfoTypeDaoImpl(this));
		entities.put(TeacherClassDao.class, new TeacherClassDaoImpl(this));
		entities.put(MessageClassDao.class, new MessageClassDaoImpl(this));
		entities.put(EventClassDao.class, new EventClassDaoImpl(this));
		entities.put(MessageUserDao.class, new MessageUserDaoImpl(this));
		entities.put(PictureDao.class, new PictureDaoImpl(this));
		entities.put(PictureUserDao.class, new PictureUserDaoImpl(this));
		entities.put(PictureClassDao.class, new PictureClassDaoImpl(this));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityDao(Class<T> t) {
		return (T) entities.get(t);
	}

}
