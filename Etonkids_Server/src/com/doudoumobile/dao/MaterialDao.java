package com.doudoumobile.dao;

import java.util.List;

import com.doudoumobile.model.Material;

public interface MaterialDao {
	public List<Material> getMaterialListByLessonId(long lessonId);
	
	public void addMaterial(Material material);
}
