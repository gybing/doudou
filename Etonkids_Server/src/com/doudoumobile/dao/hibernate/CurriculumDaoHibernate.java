package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.CurriculumDao;
import com.doudoumobile.model.Curriculum;

public class CurriculumDaoHibernate extends HibernateDaoSupport implements CurriculumDao {

	@Override
	public List<Curriculum> getRelatedCurriculums(long userId) {
		String hql = "select c.* FROM CurriculumToUser ctu left outer join " +
		"Curriculum c on ctu.curriculumId = c.id where ctu.userId = " + userId;
		SQLQuery query = getSession().createSQLQuery(hql.toString());
		query.addEntity("c", Curriculum.class);
		List result = query.list();
		
        return (List<Curriculum>) (result);
	}

	@Override
	public Curriculum addCurriculum(Curriculum c) {
		getHibernateTemplate().saveOrUpdate(c);
		getHibernateTemplate().flush();
		return c;
	}

}
