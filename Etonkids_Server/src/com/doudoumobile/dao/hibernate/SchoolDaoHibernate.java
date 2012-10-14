package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.SchoolDao;
import com.doudoumobile.model.EtonUser;
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

	@Override
	public School getSchoolById(long id) {
		return (School) getHibernateTemplate().get(School.class, id);
	}

	@Override
	public SchoolType getSchoolTypeById(long id) {
		return (SchoolType) getHibernateTemplate().get(SchoolType.class, id);
	}

	@Override
	public void delete(long id) {
		getHibernateTemplate().delete(getSchoolById(id));
	}

	@Override
	public void deleteSchoolType(long id) {
		getHibernateTemplate().delete(getSchoolTypeById(id));
	}

	@Override
	public void addSchoolType(SchoolType st) {
		getHibernateTemplate().saveOrUpdate(st);
		getHibernateTemplate().flush();
	}

	@Override
	public void updateSchoolType(SchoolType st) {
		getHibernateTemplate().update(st);
		getHibernateTemplate().flush();
	}
	
}
