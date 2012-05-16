package mayaya.dao;

import java.util.List;

import mayaya.util.dao.EntityDao;
import mayaya.vo.Comment;

public interface CommentDao extends EntityDao<Comment, Integer> {
	List<Comment> getCommentListByPictureId(int pictureID);
	List<Integer> getCommentUserIdListByPicId(int picId);
	
	Comment getNotificationVOById(int contentId);
}
