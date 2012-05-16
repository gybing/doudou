package mayaya.dao.impl;

import java.util.List;

import mayaya.dao.CommentDao;
import mayaya.util.dao.BaseEntityDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Comment;

public class CommentDaoImpl extends BaseEntityDao<Comment, Integer> implements CommentDao {

	public CommentDaoImpl(DatabaseDao database) {
		super(database);
	}

	@Override
	public String getNamespace() {
		return "Comment";
	}

	@Override
	public List<Comment> getCommentListByPictureId(int pictureID) {
		
		return reads("getCommentListByPictureId", pictureID);
	}

	@Override
	public List<Integer> getCommentUserIdListByPicId(int picId) {
		
		return readObjects("getCommentUserIdListByPicId",picId);
	}

	@Override
	public Comment getNotificationVOById(int contentId) {
		return (Comment)readObject("getNotificationVOById",contentId);
	}

}
