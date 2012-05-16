package mayaya.util.tool;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import mayaya.dao.ChildDao;
import mayaya.dao.DaoFactory;
import mayaya.dao.RelationsChildUserDao;
import mayaya.dao.UserDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Child;
import mayaya.vo.Relations_Child_User;
import mayaya.vo.User;
import mayaya.vo.enums.UserType;

public class ChildInfoExcelReader {
	
	public static void main(String[] args) {
		DatabaseDao myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		ChildDao childDao = myDatabaseDao.getEntityDao(ChildDao.class);
		UserDao userDao = myDatabaseDao.getEntityDao(UserDao.class);
		RelationsChildUserDao relationsDao = myDatabaseDao.getEntityDao(RelationsChildUserDao.class);
		
		Workbook workbook = null;

		try {
			workbook = Workbook.getWorkbook(new File("d:/test.xlsx"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Sheet sheet = workbook.getSheet(0);
		Cell cell = null;

		int rowCount = sheet.getRows();
		System.out.println(rowCount);
		//myDatabaseDao.startTransaction();
//		try {
//			
			for (int i = 1; i < rowCount; i++) {
				Child child = new Child();
				User mother = new User();
				User father = new User();
				
				Relations_Child_User rcuMother = new Relations_Child_User();
				Relations_Child_User rcufather = new Relations_Child_User();
				
				String id = sheet.getCell(0,i).getContents();
				if (id.isEmpty()) {
					break;
				}
				String schoolName = sheet.getCell(1,i).getContents();
				String className = sheet.getCell(2,i).getContents();
				String childFirstName = sheet.getCell(3,i).getContents();
				String childLastName = sheet.getCell(4,i).getContents();
				String childGender = sheet.getCell(5,i).getContents();
				String childbirthDate = sheet.getCell(6,i).getContents();
				String motherFirstName = sheet.getCell(7,i).getContents();
				String motherLastName = sheet.getCell(8,i).getContents();
				String motherMobileNO = sheet.getCell(9,i).getContents();
				String motherEmail = sheet.getCell(10,i).getContents();
				String fatherFirstName = sheet.getCell(11,i).getContents();
				String fatherLastName = sheet.getCell(12,i).getContents();
				String fatherMobileNO = sheet.getCell(13,i).getContents();
				String fatherEmail = sheet.getCell(14,i).getContents();
				
				int mid = 0;
				int fid = 0;
				
				String cName = schoolName + "-" + className;
				child.setClasses(cName);
				child.setFirstName(childFirstName);
				child.setLastName(childLastName);
				child.setBirthDate(DateUtil.getInstance().getDateFromExcelString(childbirthDate));
				child.setGender(childGender);
				String photoURL = "user/" + className + "-" + childFirstName + ".png";
				photoURL = photoURL.replace(" ", "_");
				System.out.println(photoURL);
				child.setPhotoURL(photoURL);
				System.out.println("birthDate : " + DateUtil.getInstance().getDateFromExcelString(childbirthDate));
				
				int cid = (Integer)childDao.create(child);
				
				if (!motherFirstName.isEmpty()) {
					mother.setFirstName(motherFirstName);
					mother.setLastName(motherLastName);
					mother.setTelephone(motherMobileNO);
					mother.setEmail(motherEmail);
					if (!motherEmail.isEmpty()) {
						mother.setLogin(motherEmail);
					} else {
						mother.setLogin(motherFirstName + "@doudoumobile.com");
					}
					mother.setPasswd("21218cca77804d2ba1922c33e0151105");//888888
					mother.setUserType(UserType.Parents);
					mother.setGender("Female");
					int resultId;
					if ((resultId = userDao.isExistLogin(mother.getLogin())) > 0) {
						mid = resultId;
					} else {
						mid = (Integer)userDao.create(mother);
					}
					
					rcuMother.setChildID(cid);
					rcuMother.setUserID(mid);
					rcuMother.setUserType(UserType.Parents);
					relationsDao.create(rcuMother);
					
				}
				
				if (!fatherFirstName.isEmpty()) {
					father.setFirstName(fatherFirstName);
					father.setLastName(fatherLastName);
					father.setTelephone(fatherMobileNO);
					father.setEmail(fatherEmail);
					father.setGender("Male");
					if (!fatherEmail.isEmpty() && !fatherEmail.equals(motherEmail)) {
						father.setLogin(fatherEmail);
					} else {
						father.setLogin(fatherFirstName + "@doudoumobile.com");
					}
					father.setPasswd("21218cca77804d2ba1922c33e0151105");
					father.setUserType(UserType.Parents);
					
					int resultId;
					if ((resultId = userDao.isExistLogin(father.getLogin())) > 0) {
						fid = resultId;
					} else {
						fid = (Integer)userDao.create(father);
					}
					
					rcufather.setChildID(cid);
					rcufather.setUserID(fid);
					rcufather.setUserType(UserType.Parents);
					relationsDao.create(rcufather);
				}
				
				System.out.println("cid = " + cid);
				System.out.println("mid = " + mid);
				System.out.println("fid = " + fid);
				
			}
			//myDatabaseDao.commitTransaction();
			System.out.println("Transaction commit");
//		} catch (Exception e) {
//			myDatabaseDao.endTransaction();
//			System.out.println("Transaction abort");
//		}
		workbook.close();
	}
}
