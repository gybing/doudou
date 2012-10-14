package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.SchoolDao;
import com.doudoumobile.model.School;
import com.doudoumobile.model.SchoolType;

public class SchoolDaoHibernate extends HibernateDaoSupport implements SchoolDao{

	@Override
	public void addSchool(School s) {
		getHibernateTemplate().saveOrUpdate(s);
		getHibernateTemplate().flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<School> getAllSchool() {
		return getHibernateTemplate().find(
        "from School s order by id desc");
	}

	@Override
	public void updateSchool(School s) {
		getHibernateTemplate().update(s);
		getHibernateTemplate().flush();
	}

	@Override
	public List<SchoolType> getAllSchoolType() {
		return getHibernateTemplate().find(
        "from SchoolType st order by id desc");
	}
	
}
