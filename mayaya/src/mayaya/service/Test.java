package mayaya.service;

import mayaya.dao.DaoFactory;
import mayaya.dao.DeviceTokenDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.DeviceToken;

public class Test {
	
	DatabaseDao myDatabaseDao;
	DeviceTokenDao ud;
	/**
	 * @param args
	 */
	public Test() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		ud = myDatabaseDao.getEntityDao(DeviceTokenDao.class);
	}
	
	public static void main(String[] args) {
		Test t = new Test();
		for(int i = 0 ; i < 3 ; i++) {
			t.gosh(i);
		}
		
	}
	
	void gosh(int i){
		try {
			System.out.println(i + " start transaction!");
		    myDatabaseDao.startTransaction();
			
			DeviceToken user = new DeviceToken();
			user.setUserId(i);
			user.setDeviceTokenId("String");
			ud.create(user);
			
			myDatabaseDao.commitTransaction();
		}catch(Exception e) {
			e.printStackTrace();
			//myDatabaseDao.endTransaction();
		}
	}
	

}


