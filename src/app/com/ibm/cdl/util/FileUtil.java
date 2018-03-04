/**
 * 文件名：FileUtil.java
 *
 * 创建人：[马佳] - [jia_ma@asdc.com.cn] // 根据个人情况在模板中进行修改
 *
 * 创建时间：2012-9-17 下午05:09:09
 *
 * 版权所有：IBM
 */
package com.ibm.cdl.util;

import com.ibm.cdl.datamap.action.DataMapUtils;
import com.ibm.cdl.datamap.constants.Constants;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.ArrayUtils;

import java.io.*;
import java.util.*;

/**
 * <p> Title: [名称]</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2012-9-17</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: IBM</p>
 * @author [马佳] - [jia_ma@asdc.com.cn] 
 * @version 1.0
 */
public class FileUtil {

	private static final String[] imgType = { "jpg", "png", "gif", "bmp", "tif", "jpeg" };// 图片
	private static final String[] appType = { "apk", "zip", "ipa","plist" };// 安装包
	private static final String[] textType = { "xls", "pdf", "doc", "txt", "wps","ppt","xlsx","docx","pptx" };// 文本
	private static final String[] videoType = { "rm", "avi", "mp4" };// 视频
	private static final String[] mediaType = { "mp3", "wav", "aif", "ram", "wma", "swf","amr" };// 音频
	private static final String[] compressedType = { "rar", "zip","jar" };// 压缩文件
	
	/**
	 * <p>Discription:[方法功能中文描述]</p>
	 * Created on 2012-9-17
	 * @author: [马佳] - [jia_ma@asdc.com.cn] 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static void main(String[] args) {
		delFolder("F:\\mail");
	}
	
		public static byte[] getBytes(File file ) {
			
			byte[] result = null;
			
			FileInputStream fileIn = null;
			ByteArrayOutputStream output= null;
			try {
				fileIn = new FileInputStream(file);
				output = new ByteArrayOutputStream();
				
				byte[] buffer = new byte[2048];
				int length;
				
				while ((length = fileIn.read(buffer)) != -1) {
					output.write(buffer, 0, length);
				}
				
				output.flush();
				result = output.toByteArray();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
				if (fileIn != null) {
					try {
						fileIn.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			return result;
			
		}
	
		/**
		 * 根据byte数组，生成文件
		 */
		public static String getFile(byte[] bfile, String filePath,String fileName) {
	        BufferedOutputStream bos = null;   
	        FileOutputStream fos = null;   
	        File file = null;
	        //重命名附件
	        Long csurrtime=new Date().getTime();
            String fileType=fileName.substring(fileName.lastIndexOf("."), fileName.length());
            String name=csurrtime+fileType;
	        try {   
	            File dir = new File(filePath);   
	           if(!dir.exists()){//判断文件目录是否存在   
	                dir.mkdirs();   
	            }   
	            file = new File(filePath+"/"+name);
	            fos = new FileOutputStream(file);
	            bos = new BufferedOutputStream(fos);
	            bos.write(bfile);   
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			return name;
		}
		/**
		 * 根据byte数组，生成文件   转发生成文件使用
		 */
		public static String getFile2(byte[] bfile, String filePath,String fileName) {
	        BufferedOutputStream bos = null;   
	        FileOutputStream fos = null;   
	        File file = null;
	        String fileURL= null;
	        //重命名附件
	        Long csurrtime=new Date().getTime();
            String fileType=fileName.substring(fileName.lastIndexOf("."), fileName.length());
            String name=csurrtime+fileType;
	        try {   
	            File dir = new File(filePath);   
	           if(!dir.exists()){//判断文件目录是否存在   
	                dir.mkdirs();   
	            }
	            fileURL=filePath+"/"+name;
	            file = new File(fileURL);
	            fos = new FileOutputStream(file);
	            bos = new BufferedOutputStream(fos);
	            bos.write(bfile);   
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			return fileURL;
		}

	/**
	 * 删除文件夹
	 * @param folderPath
	 */
	   public static void delFolder(String folderPath) { 
	       try { 
	           delAllFile(folderPath); //删除完里面所有内容 
	           String filePath = folderPath; 
	           filePath = filePath.toString(); 
	           File myFilePath = new File(filePath);
	           myFilePath.delete(); //删除空文件夹 

	       } 
	       catch (Exception e) { 
	           System.out.println("删除文件夹操作出错"); 
	           e.printStackTrace(); 

	       } 

	   } 
		/** 
	     * 删除文件夹里面的所有文件 
	     * @param path String 文件夹路径 如 
	     */ 
	   public static void delAllFile(String path){
	       File file = new File(path); 
	       if (!file.exists()) { 
	           return; 
	       } 
	       if (!file.isDirectory()) { 
	           return; 
	       } 
	       String[] tempList = file.list(); 
	       File temp = null; 
	       for (int i = 0; i < tempList.length; i++) { 
	           if (path.endsWith(File.separator)) { 
	               temp = new File(path + tempList[i]); 
	           }
	           else {
	               temp = new File(path + File.separator + tempList[i]); 
	           } 
	           if (temp.isFile()){ 
	               temp.delete(); 
	               System.out.println("删除文件:"+temp.getName());
	           } 
	       } 
	   }
	   
	   
		/**
		 * 附件转存的方法
		 */
		private String getFilePath(Map map) {
			
			Map resMap = new HashMap();
			
			PostMethod post = new PostMethod(DataMapUtils.getDataMapSub(Constants.SYS_PARAMS, Constants.FILE_PATH));
		    try
		    {
				HttpClient client = new HttpClient(); 
			    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    ObjectOutputStream oos = new ObjectOutputStream(baos);
			    oos.writeObject(map);
		
			    byte[] data = baos.toByteArray();
			    ByteArrayInputStream bis = new ByteArrayInputStream(data);
			    post.setRequestBody(bis);
	
		    	int i=client.executeMethod(post);
		    	String result = post.getResponseBodyAsString();
		    	return result;
		    }
		    catch (Throwable e)
		    {
		    	e.printStackTrace();
		    	return "";
		    }
		    finally
		    {
		    	post.releaseConnection();
		    }
		}
		
		/**
		 * 附件转存的方法
		 */
		public static String getFilePathNew(Map map,String url) {
			Map resMap = new HashMap();
			PostMethod post = new PostMethod(url);
			System.out.println("link address : " + url);
		    try
		    {
				HttpClient client = new HttpClient(); 
			    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    ObjectOutputStream oos = new ObjectOutputStream(baos);
			    oos.writeObject(map);
		
			    byte[] data = baos.toByteArray();
			    ByteArrayInputStream bis = new ByteArrayInputStream(data);
			    post.setRequestBody(bis);
	
		    	int i=client.executeMethod(post);
		    	String result = post.getResponseBodyAsString();
		    	return result;
		    }
		    catch (Throwable e)
		    {
		    	e.printStackTrace();
		    	return "";
		    }
		    finally
		    {
		    	post.releaseConnection();
		    }
		}
		
		/**
		 * 附件转存的方法(文件名称不发生变化)
		 */
		public static String getFilePathNew(Map map) {
			String url = DataMapUtils.getDataMapSub(Constants.SYS_PARAMS, Constants.FILE_CREATE_PATH);
			return getFilePathNew(map,url);
//			return getFilePathNew(map,"http://localhost:8081/cdlfile/file/index.do");
		}
		
		public static byte[] getBytes(InputStream is) throws Exception {
			byte[] data = null;

			Collection chunks = new ArrayList();
			byte[] buffer = new byte[1024 * 1000];
			int read = -1;
			int size = 0;

			while ((read = is.read(buffer)) != -1) {
				if (read > 0) {
					byte[] chunk = new byte[read];
					System.arraycopy(buffer, 0, chunk, 0, read);
					chunks.add(chunk);
					size += chunk.length;
				}
			}

			if (size > 0) {
				ByteArrayOutputStream bos = null;
				try {
					bos = new ByteArrayOutputStream(size);
					for (Iterator itr = chunks.iterator(); itr.hasNext();) {
						byte[] chunk = (byte[]) itr.next();
						bos.write(chunk);
					}
					data = bos.toByteArray();
				} finally {
					if (bos != null) {
						bos.close();
					}
				}
			}
			return data;
		}

		/**
		 * @title 判断文件是否是附件(附件包含图片,视频,音频,文件,压缩文件)
		 */
		public static boolean isAttach(String fileName) {
			boolean flag = false;
			String extendName = fileName.substring(fileName.lastIndexOf(".")+1);
			if (ArrayUtils.contains(imgType, extendName)) {
				flag = true;
			}else if (ArrayUtils.contains(videoType, extendName)) {
				flag = true;
			}else if (ArrayUtils.contains(mediaType, extendName)) {
				flag = true;
			}else if (ArrayUtils.contains(textType, extendName)) {
				flag = true;
			}else if (ArrayUtils.contains(compressedType, extendName)) {
				flag = true;
			}
			return flag;
		}

}
