package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.MaterialDao;
import com.doudoumobile.model.Material;

public class MaterialDaoHibernate extends HibernateDaoSupport implements MaterialDao {

	@Override
	public List<Material> getMaterialListByLessonId(long lessonId) {
		List materials = getHibernateTemplate().find("from Material where lessonId=? ",new Long[]{lessonId});			

	    return (List<Material>)(materials);
	}

	@Override
	public void addMaterial(Material material) {
		getHibernateTemplate().saveOrUpdate(material);
		getHibernateTemplate().flush();
	}

}
