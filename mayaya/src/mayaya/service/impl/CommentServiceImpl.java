package mayaya.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mayaya.dao.CommentDao;
import mayaya.dao.DaoFactory;
import mayaya.dao.PushPictureUserDao;
import mayaya.dao.RelationsChildUserDao;
import mayaya.dao.UserDao;
import mayaya.service.CommentService;
import mayaya.system.CacheManager;
import mayaya.system.MayayaBackend;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.Comment;
import mayaya.vo.User;
import mayaya.vo.enums.TodoType;
import mayaya.vo.model.CommentInfo;
import mayaya.vo.model.PushVO;

public class CommentServiceImpl implements CommentService {

	private DatabaseDao myDatabaseDao;
	private CommentDao commentDao;
	private UserDao userDao;
	private PushPictureUserDao pushPictureUserDao;
	private final RelationsChildUserDao relationsChildUserDao;
	
	private static CommentService instance = new CommentServiceImpl();
	
	public static CommentService getInstance(){
		return instance;
	}
	
	private CommentServiceImpl() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		commentDao = myDatabaseDao.getEntityDao(CommentDao.class);
		userDao = myDatabaseDao.getEntityDao(UserDao.class);
		pushPictureUserDao = myDatabaseDao.getEntityDao(PushPictureUserDao.class);
		relationsChildUserDao = myDatabaseDao.getEntityDao(RelationsChildUserDao.class);
	}
	
	@Override
	public List<CommentInfo> getCommentInfoListByPictureId(int pictureID) {
		List<CommentInfo> result = new ArrayList<CommentInfo>();
		List<Comment> commentList = commentDao.getCommentListByPictureId(pictureID);
		for (Comment comment : commentList) {
			CommentInfo ci = new CommentInfo(comment);
			User user = userDao.read(comment.getUserID());
			ci.setUserPicUrl(user.getUserPicUrl());
			ci.setUserName(user.getFirstName());
			
			result.add(ci);
		}
		return result;
	}

	@Override
	public int addComment(Comment comment, int pushUserId, int pictureID) {
		int i = (Integer)commentDao.create(comment);
		//TODO 可以另起一个线程负责推评论的通知
		if (i > 0) {
			PushVO pushVO = new PushVO();
			pushVO.setTodoType(TodoType.Comment);
			int commId = comment.getCommentID();
			System.out.println("CommentId = " + commId);
			pushVO.setContentId(commId);
			Set<Integer> userIdSet = new HashSet<Integer>();
			userIdSet.add(pushUserId);
			//The commented userIdList
			List<Integer> userIdList = commentDao.getCommentUserIdListByPicId(pictureID);
			userIdSet.addAll(userIdList);
			
			// Push the taged child
			List<Integer> pushChildList = pushPictureUserDao.getPushChildIdListByPicId(pictureID);
			for (int childId : pushChildList) {
				List<Integer> relatedIdList = relationsChildUserDao.getRelatedUserIDByChildId(childId);
				userIdSet.addAll(relatedIdList);
			}
			userIdSet.remove(comment.getUserID());
			pushVO.setFromUser(userDao.read(comment.getUserID()).getFirstName());
			pushVO.setUserIdList(userIdSet);
			
			for (int userId : userIdSet) {
				CacheManager.getInstance().putComment(userId);
			}
			
			MayayaBackend.getInstance().addPushVO(pushVO);
			
		}
		return i;
	}

	@Override
	public Comment getCommentById(int commentId) {
		return commentDao.read(commentId);
	}

}
