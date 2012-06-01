package doudou.dao;

import doudou.util.dao.EntityDao;
import doudou.vo.MessageClass;

public interface MessageClassDao extends EntityDao<MessageClass, Integer> {
	public void updateMCUnavailable(MessageClass messageClass);
}
