package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.CurriculumDao;
import com.doudoumobile.model.Curriculum;

public class CurriculumDaoHibernate extends HibernateDaoSupport implements CurriculumDao {

	@Override
	public List<Curriculum> getRelatedCurriculums(long userId) {
		String hql = "select c.* FROM CurriculumToUser ctu left outer join " +
		"Curriculum c on ctu.curriculumId = c.id where ctu.userId = " + userId;
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(hql.toString());
		query.addEntity("c", Curriculum.class);
		List result = query.list();
		session.close();
        return (List<Curriculum>) (result);
	}

	@Override
	public Curriculum addCurriculum(Curriculum c) {
		getHibernateTemplate().saveOrUpdate(c);
		getHibernateTemplate().flush();
		return c;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Curriculum> getAllCurriculumList() {
		return getHibernateTemplate().find(
        "from Curriculum c order by id desc");
	}

	@Override
	public void updateCurriculum(Curriculum c) {
		getHibernateTemplate().update(c);
		getHibernateTemplate().flush();
	}

}
