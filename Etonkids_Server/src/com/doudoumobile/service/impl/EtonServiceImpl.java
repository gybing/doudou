package com.doudoumobile.service.impl;

import java.io.File;
import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.doudoumobile.dao.CurriculumDao;
import com.doudoumobile.dao.EtonUserDao;
import com.doudoumobile.dao.LessonDao;
import com.doudoumobile.dao.MaterialDao;
import com.doudoumobile.dao.SchoolDao;
import com.doudoumobile.dao.UserDao;
import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.CurriculumToUser;
import com.doudoumobile.model.EtonUser;
import com.doudoumobile.model.Lesson;
import com.doudoumobile.model.Material;
import com.doudoumobile.model.School;
import com.doudoumobile.model.SchoolType;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.util.Config;
import com.doudoumobile.util.ZipUtil;
import com.doudoumobile.xmpp.push.NotificationManager;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.xml.internal.bind.CycleRecoverable.Context;

public class EtonServiceImpl implements EtonService{

	EtonUserDao etonUserDao;
	MaterialDao materialDao;
	CurriculumDao curriculumDao;
	SchoolDao schoolDao;
	LessonDao lessonDao;
	UserDao userDao;
	SessionFactory sessionFactory;
	String zipDesPath = "";
	
	public void setZipDesPath(String zipDesPath) {
		this.zipDesPath = zipDesPath;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setLessonDao (LessonDao lessonDao) {
		this.lessonDao = lessonDao;
	}
	public void setEtonUserDao(EtonUserDao etonUserDao) {
		this.etonUserDao = etonUserDao;
	}
	
	public void setMaterialDao(MaterialDao materialDao) {
		this.materialDao = materialDao;
	}
	
	public void setCurriculumDao(CurriculumDao curriculumDao) {
		this.curriculumDao = curriculumDao;
	}
	
	public void setSchoolDao(SchoolDao schoolDao) {
		this.schoolDao = schoolDao;
	}
	
	@Override
	public EtonUser verifyEtonUser(String userName, String passWd) {
		return (EtonUser) etonUserDao.verifyEtonUser(userName, passWd);
	}

	@Override
	public int modifyPwd(long userId, String oldPwd, String newPwd) {
		EtonUser user = etonUserDao.getUserById(userId);
		if (null != user && user.getPassWd().equals(oldPwd)) {
			etonUserDao.modifyPwd(userId , newPwd);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public EtonUser getUser(long userId) {
		EtonUser user = etonUserDao.getUserById(userId);
		return user;
	}

	@Override
	public List<Material> getMaterialListByLessonId(long lessonId) {
		return materialDao.getMaterialListByLessonId(lessonId);
	}

	@Override
	public void addCurri(Curriculum c) {
		curriculumDao.addCurriculum(c);
	}

	@Override
	public EtonUser addEtonUser(EtonUser eu) {
		return etonUserDao.addUser(eu);
	}

	@Override
	public void addSchool(School s) {
		schoolDao.addSchool(s);
	}

	@Override
	public List<Curriculum> getAllCurriculumList() {
		return curriculumDao.getAllCurriculumList();
	}

	@Override
	public void updateCurriculum(Curriculum c) {
		curriculumDao.updateCurriculum(c);
	}

	@Override
	public List<EtonUser> getAllEtonUserList() {
		return etonUserDao.getAllUser();
	}

	@Override
	public void updateEtonUser(EtonUser eu) {
		etonUserDao.updateUser(eu);
	}

	@Override
	public List<School> getAllSchool() {
		return schoolDao.getAllSchool();
	}

	@Override
	public void updateSchool(School s) {
		schoolDao.updateSchool(s);
	}

	@Override
	public List<SchoolType> getSchoolTypeList() {
		return schoolDao.getAllSchoolType();
	}

	@Override
	public List<Curriculum> getFirstClassCurriculumList() {
		return curriculumDao.getFirstClassCurriculumList();
	}

	@Override
	public Curriculum getCurriculumById(long id) {
		return curriculumDao.getCurriculumById(id);
	}

	@Override
	public School getSchoolById(long id) {
		return schoolDao.getSchoolById(id);
	}

	@Override
	public void deleteUser(long id) {
		etonUserDao.delete(id);
	}

	@Override
	public void deleteCurriculum(long id) {
		curriculumDao.delete(id);
		curriculumDao.deleteChildCurriculumByParentId(id);
	}

	@Override
	public void deleteSchool(long id) {
		schoolDao.delete(id);
	}

	@Override
	public void deleteSchoolType(long id) {
		schoolDao.deleteSchoolType(id);
	}

	@Override
	public void addSchoolType(SchoolType schoolType) {
		schoolDao.addSchoolType(schoolType);
		
	}

	@Override
	public SchoolType getSchoolTypeById(long id) {
		return schoolDao.getSchoolTypeById(id);
	}

	@Override
	public void updateSchoolType(SchoolType st) {
		schoolDao.updateSchoolType(st);
	}

	@Override
	public List<School> getSchoolByTypeId(long typeId) {
		return schoolDao.getSchoolByTypeId(typeId);
	}

	@Override
	public void addCurriToEtonUser(CurriculumToUser ctu) {
		curriculumDao.addCurriculumToUser(ctu);
	}

	@Override
	public void deleteCurriculumToUserByUserId(long userId) {
		curriculumDao.deleteCurriculumToUserByUserId(userId);
	}

	@Override
	public void resetPwd(long id, String resetPwd) {
		etonUserDao.resetPwd(id, resetPwd);
	}

	@Override
	public boolean curriculaToolForEEE(String path) {
		//path = "D://etonkids//Dou Dou Mobile//EEE";
		File dir = new File(path);
		if (!dir.exists()) {
			System.out.println("Not exists : path = " + path);
			return false;
		}
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				System.out.println("Dir name : " + f.getName());
				for (File weeks : f.listFiles()) {
					System.out.println("\t dir name : " + weeks.getName());
					String newZipFileName = f.getName() + "-" + weeks.getName();
					newZipFileName = newZipFileName.replaceAll(" ", "");
					
					Lesson lesson = new Lesson();
					lesson.setAvailable(true);
					lesson.setBeginDate(new Date(System.currentTimeMillis()));
					lesson.setCreatedTime(new java.util.Date());
					lesson.setCurriculumId(getIdFromCurriName(f.getName()));
					lesson.setEndDate(new Date(113,0,0));
					lesson.setTitle(weeks.getName());
					lesson.setPdfPath("/" + newZipFileName + ".zip");
					lessonDao.addLesson(lesson);
					System.out.println("Add lesson success , id : " + lesson.getId());
					//add to db lesson
					for (File material : weeks.listFiles()) {
						Material newMaterial = new Material();
						if (material.getName().endsWith("jpg") || material.getName().endsWith("jpeg") || material.getName().endsWith("pdf") ) {
							String materialName = material.getName().replaceAll(" ", "");
							String newMaterialName = Base64.encode((f.getName() + weeks.getName() + materialName).getBytes());
							newMaterialName = newMaterialName.replaceAll("=", "XO");
							System.out.println("\t\thandle material name : " + material.getName() + " to " + newMaterialName);
							boolean result = material.renameTo(new File(weeks.getPath() + File.separator + newMaterialName));
							System.out.println("Rename : " + result);
							
							
							if (material.getName().endsWith("pdf")) {
								newMaterial.setType(3);
							} else {
								newMaterial.setType(2);
							}
							newMaterial.setPath("/"+weeks.getName()+"/" + newMaterialName);
							//add to db
						} else {
							newMaterial.setType(0);
							String newMName = f.getName()+weeks.getName()+"%" + material.getName();
							newMaterial.setPath("/"+weeks.getName()+"/" + newMName);
							material.renameTo(new File(weeks.getPath() + File.separator + newMName));
							System.out.println("Handle music : new Name = " + newMName);
						}
						newMaterial.setLessonId(lesson.getId());
						materialDao.addMaterial(newMaterial);
					}
					try {
						ZipUtil.compress(weeks,new File(f.getPath()+File.separator+newZipFileName+".zip"));
						System.out.println("Zip file Success -- " + newZipFileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println(f.getName() + " is a file");
			}
		}
		
		return false;
	}
	
	@Override
	public boolean curriculaToolForCD(String path) {
		//path = "D://etonkids//Dou Dou Mobile//Character Development";
		File dir = new File(path);
		if (!dir.exists()) {
			System.out.println("Not exists : path = " + path);
			return false;
		}
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				System.out.println("Dir name : " + f.getName());
				if ("Casa".equals(f.getName())) {
					for (File casaPdf : f.listFiles()) {
						Lesson lesson = new Lesson();
						lesson.setAvailable(true);
						lesson.setBeginDate(new Date(System.currentTimeMillis()));
						lesson.setCreatedTime(new java.util.Date());
						lesson.setCurriculumId((long)11);
						lesson.setEndDate(new Date(113,0,0));
						int titleIndex = casaPdf.getName().lastIndexOf(".pdf");
						String title = casaPdf.getName().substring(0, titleIndex);
						System.out.println(title);
						lesson.setTitle(title);
						String newZipFileName = title.replaceAll(" ", "");
						lesson.setPdfPath("/" + newZipFileName + ".zip");
						lessonDao.addLesson(lesson);
						System.out.println("Add lesson success , id : " + lesson.getId());
						
						Material newMaterial = new Material();
						newMaterial.setType(3);
						
						String materialName = casaPdf.getName().replaceAll(" ", "");
						String newMaterialName = Base64.encode(materialName.getBytes());
						newMaterialName = newMaterialName.replaceAll("=", "XO");
						System.out.println("\t\thandle material name : " + casaPdf.getName() + " to " + newMaterialName);
						boolean result = casaPdf.renameTo(new File(f.getPath() + File.separator + newMaterialName));
						System.out.println("Rename : " + result);
						
						newMaterial.setPath("/" + newMaterialName);
						
						newMaterial.setLessonId(lesson.getId());
						materialDao.addMaterial(newMaterial);
						try {
							ZipUtil.compress(new File(f.getPath() + File.separator + newMaterialName),new File(f.getPath()+File.separator+newZipFileName+".zip"));
							System.out.println("Zip file Success -- " + newZipFileName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					for (File nurseryPdf : f.listFiles()) {
						Lesson lesson = new Lesson();
						lesson.setAvailable(true);
						lesson.setBeginDate(new Date(System.currentTimeMillis()));
						lesson.setCreatedTime(new java.util.Date());
						lesson.setCurriculumId((long)12);
						lesson.setEndDate(new Date(113,0,0));
						int titleIndex = nurseryPdf.getName().lastIndexOf(".pdf");
						String title = nurseryPdf.getName().substring(0, titleIndex);
						System.out.println(title);
						lesson.setTitle(title);
						String newZipFileName = title.replaceAll(" ", "");
						lesson.setPdfPath("/" + newZipFileName + ".zip");
						lessonDao.addLesson(lesson);
						System.out.println("Add lesson success , id : " + lesson.getId());
						
						Material newMaterial = new Material();
						newMaterial.setType(3);
						
						String materialName = nurseryPdf.getName().replaceAll(" ", "");
						String newMaterialName = Base64.encode(materialName.getBytes());
						newMaterialName = newMaterialName.replaceAll("=", "XO");
						System.out.println("\t\thandle material name : " + nurseryPdf.getName() + " to " + newMaterialName);
						boolean result = nurseryPdf.renameTo(new File(f.getPath() + File.separator + newMaterialName));
						System.out.println("Rename : " + result);
						
						newMaterial.setPath("/" + newMaterialName);
						
						newMaterial.setLessonId(lesson.getId());
						materialDao.addMaterial(newMaterial);
						try {
							ZipUtil.compress(new File(f.getPath() + File.separator + newMaterialName),new File(f.getPath()+File.separator+newZipFileName+".zip"));
							System.out.println("Zip file Success -- " + newZipFileName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return false;
	}
	
	private long getIdFromCurriName(String name) {
		if ("EEE 1".equals(name)) {
			return 7;
		} else if ("EEE 2".equals(name)) {
			return 8;
		} else if ("EEE 3".equals(name)) {
			return 9;
		} else if ("EEE 4".equals(name)) {
			return 10;
		} else {
			return -1;
		}
	}
	@Override
	public boolean addLesson(Lesson lesson) {
		boolean result = handleLessonZip(lesson);
		if (result) {
			//TODO 根据时间
			System.out.println("Notify begins");
			notify(lesson.getCurriculumId());
		}
		Session session = sessionFactory.getCurrentSession();
		session.close();
		return result;
	}
	@Override
	public boolean notify(long curriculumId) {
		//curriculumDao.get
		List<Long> userIdList = curriculumDao.getUserIdListByCurriculumId(curriculumId);
		System.out.println("To notify -> user id : " + userIdList);
		for (long userId : userIdList) {
			List<String> apnUserNameList = userDao.getUserNameListByEtonId(userId);
			NotificationManager nm = new NotificationManager();
			String apiKey = Config.getString("apiKey", "");
			String title = "New lesson available";
			String message = "You have got a new lesson to download";
			for (String username : apnUserNameList) {
				nm.sendNotifications(apiKey, username,userId, title, message, "");
			}
		}
		return true;
	}

	@Override
	public boolean remoteWipe(String apn_userName) {
		String[] ss = apn_userName.split("_");
		long userId = Long.parseLong(ss[0]);
		System.out.println("To remoteWipe -> user name : " + apn_userName);
		userDao.updateUserUnavailable(apn_userName);
		NotificationManager nm = new NotificationManager();
		String apiKey = Config.getString("apiKey", "");
		String title = "remoteWipe";
		String message = "Remote Wipe Operation!";
		nm.sendNotifications(apiKey, apn_userName,userId, title, message, "");
		return true;
	}

	@Override
	public boolean handleLessonZip(Lesson lesson) {
		
//		Transaction transaction = null;
//		if (sessionFactory.getCurrentSession() != null) {
//			transaction = sessionFactory.getCurrentSession().beginTransaction();
//		}
		try {
			
			String path = lesson.getPdfPath();
			File zipFile = new File(path);
			
			String fileName = zipFile.getName();
			
			String dest = zipDesPath;
			if (!zipFile.exists()) {
				System.out.println("Not exists : path = " + path);
				return false;
			}
			
			ZipUtil.unZip(path);
			
			String dirPath = path.replaceAll(".zip" , "");
			
			File dirFile = new File(dirPath);
			
			String newZipFileName = fileName.replaceAll(" ", "");
			lesson.setPdfPath("/" + newZipFileName);
			lessonDao.addLesson(lesson);
			System.out.println("Add lesson success , id : " + lesson.getId());
			
			for (File material : dirFile.listFiles()) {
				System.out.println("Material : " + material.getName());
				Material newMaterial = new Material();
				if (material.getName().endsWith("jpg") || material.getName().endsWith("jpeg") || material.getName().endsWith("pdf") ) {
					String materialName = material.getName().replaceAll(" ", "");
					String newMaterialName = Base64.encode((lesson.getTitle()+"%" + materialName).getBytes());
					newMaterialName = newMaterialName.replaceAll("=", "XO");
					System.out.println("\t\thandle material name : " + material.getName() + " to " + newMaterialName);
					boolean result = material.renameTo(new File(dirFile.getPath() + File.separator + newMaterialName));
					System.out.println("Rename : " + result);
					
					
					if (material.getName().endsWith("pdf")) {
						newMaterial.setType(3);
					} else {
						newMaterial.setType(2);
					}
					newMaterial.setPath("/"+dirFile.getName()+"/" + newMaterialName);
					//add to db
				} else if(material.getName().endsWith("mp4") || material.getName().endsWith("avi")){
					newMaterial.setType(1);
					String newMName = dirFile.getName()+"%" + material.getName();
					newMaterial.setPath("/"+dirFile.getName()+"/" + newMName);
					material.renameTo(new File(dirFile.getPath() + File.separator + newMName));
					System.out.println("Handle video : new Name = " + newMName);
				} else {
					newMaterial.setType(0);
					String newMName = dirFile.getName()+"%" + material.getName();
					newMaterial.setPath("/"+dirFile.getName()+"/" + newMName);
					material.renameTo(new File(dirFile.getPath() + File.separator + newMName));
					System.out.println("Handle music : new Name = " + newMName);
				}
				newMaterial.setLessonId(lesson.getId());
				materialDao.addMaterial(newMaterial);
				
			}
			// zip & move
			dest = dest +File.separator+newZipFileName;
			ZipUtil.compress(dirFile,new File(dest));
			System.out.println("Zip file Success -- " + dest);
			
			//delete tmp file
			for (File material : dirFile.listFiles()) {
				material.delete();
			}
			dirFile.delete();
			//transaction.commit();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			//transaction.rollback();
			lessonDao.delete(lesson.getId());
			return false;
		} 
	}

	@Override
	public void updateLoginTime(long userId , String deviceToken) {
		userDao.updateLoginTime(userId, deviceToken);
	}

	@Override
	public boolean editLesson(Lesson lesson) {
		
//		Transaction transaction = null;
//		if (sessionFactory.getCurrentSession() != null) {
//			transaction = sessionFactory.getCurrentSession().beginTransaction();
//		}
		try {
			Lesson oldLesson = lessonDao.getLesson(lesson.getId());
			
			String path = lesson.getPdfPath();
			boolean result = false;
			if (path != null && !path.isEmpty()) {
				materialDao.removeByLessonId(lesson.getId());
				File zipFile = new File(path);
				
				String fileName = zipFile.getName();
				
				String dest = zipDesPath;
				if (!zipFile.exists()) {
					System.out.println("Not exists : path = " + path);
					return false;
				}
				
				ZipUtil.unZip(path);
				
				String dirPath = path.replaceAll(".zip" , "");
				
				File dirFile = new File(dirPath);
				
				String newZipFileName = fileName.replaceAll(" ", "");
				lesson.setPdfPath("/" + newZipFileName);
				
				for (File material : dirFile.listFiles()) {
					System.out.println("Material : " + material.getName());
					Material newMaterial = new Material();
					if (material.getName().endsWith("jpg") || material.getName().endsWith("jpeg") || material.getName().endsWith("pdf") ) {
						String materialName = material.getName().replaceAll(" ", "");
						String newMaterialName = Base64.encode((lesson.getTitle()+"%" + materialName).getBytes());
						newMaterialName = newMaterialName.replaceAll("=", "XO");
						System.out.println("\t\thandle material name : " + material.getName() + " to " + newMaterialName);
						result = material.renameTo(new File(dirFile.getPath() + File.separator + newMaterialName));
						System.out.println("Rename : " + result);
						
						if (material.getName().endsWith("pdf")) {
							newMaterial.setType(3);
						} else {
							newMaterial.setType(2);
						}
						newMaterial.setPath("/"+dirFile.getName()+"/" + newMaterialName);
						//add to db
					} else if(material.getName().endsWith("mp4") || material.getName().endsWith("avi")){
						newMaterial.setType(1);
						String newMName = dirFile.getName()+"%" + material.getName();
						newMaterial.setPath("/"+dirFile.getName()+"/" + newMName);
						material.renameTo(new File(dirFile.getPath() + File.separator + newMName));
						System.out.println("Handle video : new Name = " + newMName);
					} else {
						newMaterial.setType(0);
						String newMName = dirFile.getName()+"%" + material.getName();
						newMaterial.setPath("/"+dirFile.getName()+"/" + newMName);
						material.renameTo(new File(dirFile.getPath() + File.separator + newMName));
						System.out.println("Handle music : new Name = " + newMName);
					}
					newMaterial.setLessonId(lesson.getId());
					materialDao.addMaterial(newMaterial);
					
				}
				// zip & move
				dest = dest +File.separator+newZipFileName;
				ZipUtil.compress(dirFile,new File(dest));
				System.out.println("Zip file Success -- " + dest);
				
				//delete tmp file
				for (File material : dirFile.listFiles()) {
					material.delete();
				}
				dirFile.delete();
			} else {
				lesson.setPdfPath(oldLesson.getPdfPath());
			}
			
			lessonDao.updateLesson(lesson);
			System.out.println("Update lesson success , id : " + lesson.getId());
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			//transaction.rollback();
			lessonDao.delete(lesson.getId());
			return false;
		} 
	}
}
