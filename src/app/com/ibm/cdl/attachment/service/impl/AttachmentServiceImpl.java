package com.ibm.cdl.attachment.service.impl;

import com.ibm.cdl.attachment.dao.impl.AttachmentDaoImpl;
import com.ibm.cdl.attachment.domain.Attachment;
import com.ibm.cdl.attachment.service.AttachmentService;
import com.ibm.cdl.datamap.action.DataMapUtils;
import com.ibm.cdl.datamap.constants.Constants;
import com.ibm.cdl.datamap.pojo.SubDataMap;
import com.ibm.cdl.datamap.service.SubDataMapService;
import com.ibm.cdl.util.FileUtil;
import com.ibm.cdl.util.PropertiesUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 附件服务接口--实现
   * @create.date: 2011-5-4 上午09:33:28     
   * @comment: <p>TODO</p>
   * @see: com.chinawsoft.portal.app.portal.attachment.service.impl
   * @author: zjg
   * @modify.by: zjg
   * @modify.date: 2011-5-4 上午09:33:28
 */
@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {

	private static SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
	@Autowired
	private AttachmentDaoImpl attachmentDao;
	@Autowired
	private SubDataMapService subDataMapService;

	private String type;

	private static String APP_FILE_BASE_PATH = PropertiesUtils.singlton().getProperty("APP_FILE_BASE_PATH");


	public List<Attachment> findAttachmentsByBusinessId(String businessId) {
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("businessId", businessId);
		return attachmentDao.find("from Attachment where businessId=:businessId order by createDate desc",
				pmap);
	}

	/**
	 * 保存附件
	 * @param businessId
	 * @param createUser
	 * @param file
	 * @param fileName
	 * @param fileType
	 * @return
	 */
	public String saveAttachment(String businessId, String createUser, File file, String fileName, String fileType) {
		// 保存附件到 文件服务器
		String fileUrl = createNewFile(file,fileName,"attachment/"+sdf.format(new Date())+"/"+createUser+"/",createUser);
		if(StringUtils.isEmpty(fileUrl)){
			System.out.print("保存附件失败");
			return  null;
		}
		Attachment attachment = new Attachment();
		attachment.setCreateUser(createUser);
		attachment.setBusinessId(businessId);
		attachment.setAttachType(fileType);
		attachment.setCreateDate(new Date());
		attachment.setRealName(fileName);
		String storeName = fileUrl.substring(fileUrl.lastIndexOf("/"), fileUrl.length());
		attachment.setStoreName(storeName);
		attachment.setStorePath(fileUrl);
		attachmentDao.save(attachment);
		return null;
	}

	public static void main(String[] args){
		File file = new File("d://2.png");
		String fileName ="2.png";
		String createUser ="admin";

		createNewFile(file,fileName,"attachment/"+sdf.format(new Date())+"/"+createUser+"/",createUser);
	}
	/**
	 * 删除附件
	 * @param businessId
	 * @return
	 */
	public void deleteAttachment(String businessId) {
		attachmentDao.deleteBySql("delete from attachment where BUSINESS_ID ='" + businessId+"'");
	}

	/**
	 * @Description: 保存附件For App包
	 * @Title: saveAttachmentForApp
	 * @Author zhuxiangxin
	 * @Date 2018/5/27 
	 * @Param [bussinessId, updateFlag, tempFile, tempFileName, tmepFileType, folderName]
	 * @return java.lang.String
	 */
	public String saveAttachmentForApp(String bussinessId,String updateFlag,File tempFile,String tempFileName,
                                       String tmepFileType,String createUser){
        String fileUrl = createNewFile(tempFile,tempFileName,"app/"+sdf.format(new Date())+"/"+createUser+"/",createUser);
        if(StringUtils.isEmpty(fileUrl)){
            System.out.print("保存附件失败");
            return  null;
        }
		/**保存附件信息*/
		Attachment attachment = new Attachment();
		attachmentDao.deleteBySql("DELETE FROM attachment WHERE BUSINESS_ID = '"+bussinessId+"'");
        attachment.setCreateUser(createUser);
        attachment.setBusinessId("APP-"+bussinessId);
        attachment.setAttachType(tmepFileType);
        attachment.setCreateDate(new Date());
        attachment.setRealName(tempFileName);
        String storeName = fileUrl.substring(fileUrl.lastIndexOf("/"), fileUrl.length());
        attachment.setStoreName(storeName);
        attachment.setStorePath(fileUrl);
        attachmentDao.save(attachment);
//		uploadUtils.mkdir(attachment.getStorePath());//创建文件夹
//		try {
//			FileUtils.copyFile(tempFile, new File(attachment.getStorePath() + File.separator + attachment.getStoreName()));//上传附件到指定路径
//		} catch (IOException e) {
//			throw new RuntimeException("上传文件出现异常！");
//		}
		SubDataMap sub = subDataMapService.findSubDataMapByCode(Constants.VERSION);
		if(sub != null){
			sub.setName("APP-"+ bussinessId+"-"+updateFlag);
		}
		subDataMapService.update(sub);
		DataMapUtils.refresh();
		return attachment.getId();
	}

	private static String createNewFile(File file,String newFileName,String filePath,String createUser) {
		String fileUrl ="";
		try {
			HashMap map = new HashMap();
			byte[] data = null;
			data = FileUtil.getBytes(file);
			map.put("fileName", newFileName);
			map.put("filePath", filePath);
			map.put("createUser",createUser);
			map.put("fileData", data);
			fileUrl = FileUtil.getFilePathNew(map);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return fileUrl;
		}
	}

	private void createNewFileByType(File file,String newFileName,String type) {

		try {
			HashMap map = new HashMap();
			byte[] data = null;
			data = FileUtil.getBytes(file);

			map.put("fileName", newFileName);
			map.put("filePath", type);
			map.put("fileData", data);

			FileUtil.getFilePathNew(map);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
