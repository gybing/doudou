package mayaya.service;

import java.util.List;

import mayaya.vo.Comment;
import mayaya.vo.model.CommentInfo;

public interface CommentService {
	public List<CommentInfo> getCommentInfoListByPictureId(int pictureID);
	
	int addComment(Comment comment, int pushUserId, int pictureID);
	
	Comment getCommentById(int commentId);
}
