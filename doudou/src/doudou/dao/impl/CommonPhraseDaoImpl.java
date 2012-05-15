package doudou.dao.impl;

import doudou.dao.CommonPhraseDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.CommonPhrase;

public class CommonPhraseDaoImpl extends BaseEntityDao<CommonPhrase, Integer>
		implements CommonPhraseDao {

	public CommonPhraseDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "CommonPhrase";
	}

	

}
