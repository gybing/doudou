package doudou.service;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import doudou.dao.DaoFactory;
import doudou.dao.PictureClassDao;
import doudou.dao.PictureDao;
import doudou.system.DoudouBackend;
import doudou.util.dao.DatabaseDao;
import doudou.vo.Picture;
import doudou.vo.PictureClass;
import doudou.vo.model.PicPublishTask;
import doudou.vo.type.TodoType;


@Service
public class PictureService {
	
	private Logger logger = Logger.getLogger(getClass());
	private final DatabaseDao myDatabaseDao; 
	private final PictureDao pictureDao;
	private final PictureClassDao pictureClassDao;
	
	public PictureService() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		pictureDao = myDatabaseDao.getEntityDao(PictureDao.class);
		pictureClassDao = myDatabaseDao.getEntityDao(PictureClassDao.class);
	}
	
	/**
	 * 添加图片
	 * 
	 * */
	public int addPicture(Picture picture, List<Integer> childIdList, List<Integer> classIdList, int schoolId) {
		//完成对象属性填充...TOBE optimized
		//messageTask.setChildrenListString(messageTask.generateAtChildrenListString());
		
		int result = (Integer)pictureDao.create(picture);
		if (result > 0) {
			// 保证发布者能查看到新添加的事件
			for (Integer classId : classIdList) {
				PictureClass pictureClass = new PictureClass();
				pictureClass.setClassId(classId);
				pictureClass.setPictureId(picture.getId());
				pictureClass.setSchoolId(schoolId);
				pictureClassDao.create(pictureClass);
			}
			PicPublishTask task = new PicPublishTask();
			task.setPicture(picture);
			task.setTargetChildIdList(childIdList);
			task.setTodoType(TodoType.NewPicture);
			DoudouBackend.getInstance().publishTask(task);
		} 
		return result;
		
	}
	
}
