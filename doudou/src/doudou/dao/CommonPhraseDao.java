package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.CommonPhrase;

public interface CommonPhraseDao extends EntityDao<CommonPhrase, Integer> {
	List<CommonPhrase> getCommonPhraseListByClassId(int classId);
}
