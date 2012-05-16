package mayaya.service;

import java.util.Date;
import java.util.List;

import mayaya.vo.Picture;

public interface PictureService {
	Picture getPictureById(int id);
	int addPicture(Picture picture);
	
	List<Picture> getPicturesByDate(Date date);
}
