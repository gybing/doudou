package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.LessonDao;
import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.Lesson;

public class LessonDaoHibernate extends HibernateDaoSupport implements LessonDao {

	@Override
	public Lesson getLesson(Long id) {
		return (Lesson)getHibernateTemplate().get(Lesson.class, id);
	}

	@Override
	public Lesson addLesson(Lesson lesson) {
		getHibernateTemplate().saveOrUpdate(lesson);
		getHibernateTemplate().flush();
		return lesson;
	}

	@Override
	public List<Lesson> getRelatedLessonByCurrId(long curriculumId) {
		List lessons = getHibernateTemplate().find("from Lesson where curriculumId=? " +
				"and available = true and beginDate <= date_format(now(),'%Y-%m-%d') " +
				"and endDate >= date_format(now(),'%Y-%m-%d')",new Long[]{curriculumId});			
	
        return (List<Lesson>)(lessons);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lesson> getAllLessons() {
		
		return getHibernateTemplate().find(
		        "from Lesson l order by id desc");
		
	}

	@Override
	public void updateLesson(Lesson l) {
		getHibernateTemplate().update(l);
		getHibernateTemplate().flush();		
	}

	@Override
	public void delete(long id) {

		getHibernateTemplate().delete(getLesson(id));

	}
	
}
