package com.ibm.cdl.attachment.utils;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * 
 * @create.date: 2011-4-14 下午04:25:58
 * @comment: <p>
 *           附件上传工具类
 *           </p>
 * @see: com.chinawsoft.portal.app.portal.attachment.utils
 * @author: songyuliang
 * @modify.by: songyuliang
 * @modify.date: 2011-4-14 下午04:25:58
 */
public class UploadUtils {
	private static final Logger log = Logger.getLogger(UploadUtils.class);
	private static final String path = "D:\\portal\\";// 本地测试附件存放路径

	/**
	 * 以时间毫秒命名文件名.
	 * 
	 * @param fileName
	 *            原文件名.
	 * @return
	 */
	public String reName(String fileName) {
		log.info("定制文件保存重命名: " + fileName);
		String encodeFileName = "";
		int rexIndex = fileName.lastIndexOf(".");
		if (rexIndex == -1) {
			log.info("文件无后缀名");
			encodeFileName = System.currentTimeMillis() + "";
		} else {
			encodeFileName = System.currentTimeMillis()
					+ fileName.substring(rexIndex);
		}
		return encodeFileName;
	}

	public String getFilePath(String folder) {
		String filePath = getPath() + "attachment/" + folder;
		return filePath;
	}

	/**
	 * 用于创建文件夹的方法
	 * 
	 * @param mkdirName
	 */

	public void mkdir(String mkdirName) {
		try {
			File dirFile = new File(mkdirName);
			boolean bFile = dirFile.exists();
			if (bFile == true) {
				log.info("The folder exists.");
			} else {
				log.info("The folder do not exist,now trying to create a one...");
				bFile = dirFile.mkdirs();
				if (bFile == true) {
					log.info("Create successfully!");
					log.info("创建文件夹");
				} else {
					log.info("Disable to make the folder,please check the disk is full or not.");
					log.info(" 文件夹创建失败，清确认磁盘没有写保护并且空件足够");
				}
			}
		} catch (Exception err) {
			log.info("ELS - Chart : 文件夹创建发生异常");
		}
	}
	
	public static String getPath() {
		String realPath= ServletActionContext.getServletContext().getRealPath("/");
		if (realPath != null && !realPath.endsWith("/"))
			return (new StringBuilder(String.valueOf(realPath))).append("/").toString();
		else
			return realPath;
	}

}
