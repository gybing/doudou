package doudou.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import doudou.service.DoudouService;
import doudou.service.PictureService;
import doudou.system.DoudouBackend;
import doudou.util.BaseServlet;
import doudou.util.DoudouConfig;
import doudou.util.tool.DateUtil;
import doudou.vo.Picture;
import doudou.vo.model.SessionData;

@Controller
@RequestMapping("/Photo")
public class PhotoServlet extends BaseServlet{

	@Autowired
	DoudouService doudouService;
	@Autowired
	PictureService pictureService;
	
	@RequestMapping("/addPhoto")
	 public void upload(@RequestParam(value = "files[]", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {

	        String atChildList = getStringParameter(request, "atChildList","");
			String photoCaption = getStringParameter(request, "photoCaption","");
			SessionData sessionData = (SessionData)request.getSession().getAttribute("SessionData");
			logger.info(String.format("atChildList : %s, Caption : %s",atChildList,photoCaption));
			
			if (null != file) {
				Date now = new Date();
				String dirPath = DoudouConfig.getConfig().getUploadImgPath() + DateUtil.getInstance().toYearMonthString(now);
				File dir = new File(dirPath);
				if (!dir.isDirectory()) {
					dir.mkdir();
				}
				String fileName = file.getOriginalFilename();
				
				logger.info(String.format("文件名称：%s, 文件大小：%d, 文件类别：%s",fileName,file.getSize(),file.getContentType()));
				
				int pointIndex = fileName.lastIndexOf(".");
				String poster = fileName.substring(pointIndex, fileName.length());
				
				// 用于获取file中的文件名（不包含路径）
				String picName = "Pic_" + sessionData.getUser().getId() + "_" + sessionData.getUser().getFirstName() + "_" + DateUtil.getInstance().getDetailStringFromDate(now) + poster;
				File tempFile = new File(picName);
				// 建立文件内容
				File targetFile = new File(dirPath ,tempFile.getName());
				logger.info(String.format("文件地址：%s", targetFile.toString()));
				
				try {
		            file.transferTo(targetFile);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				
		        Picture pic = new Picture();
				pic.setUserId(sessionData.getUser().getId());
				pic.setDescription(photoCaption);
				pic.setPictureURL(DoudouConfig.getConfig().getUploadPath() + tempFile.getName());
				pic.setPublishTime(now);
				pic.setAtChildList(atChildList);
		        
				DoudouBackend.getInstance().addImageToQueue(targetFile.toString());
				
				List<Integer> childIdList = doudouService.getChildIdListFromString(atChildList);
				List<Integer> classIdList = doudouService.getClassIdListFromChildIdList(childIdList);
				int result = pictureService.addPicture(pic , childIdList, classIdList , sessionData.getSchoolId());
				
				response.getWriter().print(result);
				
			} else {
				logger.error("上传文件失败！");
				response.getOutputStream().print("上传文件失败！");
			}
	    }
	
}
