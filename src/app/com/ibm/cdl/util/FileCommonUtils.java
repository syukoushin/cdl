package com.ibm.cdl.util;

import com.ibm.cdl.datamap.action.DataMapUtils;
import com.ibm.cdl.datamap.constants.Constants;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class FileCommonUtils {

	public static void deleteFile(String fileName) {
		
		try {
			File file = new File(fileName);
			
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void copyFile(File file,String fileName) {
		
		try {
			
			File saveFile = new File(fileName);
			
			if (file.exists()) file.createNewFile();
			org.apache.commons.io.FileUtils.copyFile(file, saveFile);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传文件
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String upload(String name,File file) throws Exception{
//		String weedfs = DataMapUtils.getDataMapSub(Constants.WEED_FS,Constants.BASE_URL);
		String weedfs = "http://172.19.222.2:9333/submit";
		PostMethod filePost = new PostMethod(weedfs);
		HttpClient client = new HttpClient();
		try {
			// 通过以下方法可以模拟页面参数提交
			Part[] parts = { (Part) new FilePart(name, file) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_CREATED || status == HttpStatus.SC_OK) {
				return filePost.getResponseBodyAsString();
			} else {
				return null;
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			filePost.releaseConnection();
		}
	}

	/**
	 * 下载文件
	 * @param fid
	 * @param localFileName
	 * @return
	 */
	public static File download(String fid,String localFileName){
		String weedfs = "http://139.196.92.65:9333/"+fid;
		PostMethod filePost = new PostMethod(weedfs);
		HttpClient client = new HttpClient();
		FileOutputStream output = null;
		File f = null;
		try{
			int i = client.executeMethod(filePost);
			System.out.print("下载文件：" +"i="+i);
			if(i == 301){
				Header[] hs = filePost.getResponseHeaders();
				for(Header h : hs){
					if("Location".equals(h.getName())){
						filePost = new PostMethod(h.getValue());
						client.executeMethod(filePost);
					}
				}
			}
			f = new File("d://temp/"+localFileName);
			FileUtils.writeByteArrayToFile(f, filePost.getResponseBody());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(output != null){
					output.close();
				}
			} catch (IOException e) {
					e.printStackTrace();
			}
			filePost.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
			return f;
		}
	}

	public static  void main (String[] args) throws Exception{
		File f = new File("D://2.png");
		byte[] bytes = FileUtils.readFileToByteArray(f);
		File temp = new File("D://f2.png");
		FileUtils.writeByteArrayToFile(temp, bytes);
	}

	/**
	 * 上传附件
	 * @param name
	 * @param bytes
	 * @param contentType
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String upload(String name,byte[] bytes,String contentType,String charset) throws Exception{
//		String weedfs = DataMapUtils.getDataMapSub(Constants.WEED_FS,Constants.BASE_URL);
		String weedfs = "http://172.19.222.2:9333/submit";
		PostMethod filePost = new PostMethod(weedfs);
		HttpClient client = new HttpClient();
		try {
			// 通过以下方法可以模拟页面参数提交
			Part[] parts = { (Part) new FilePart(name, new ByteArrayPartSource(name,bytes),contentType,charset) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_CREATED) {
				return filePost.getResponseBodyAsString();
			} else {
				return null;
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			filePost.releaseConnection();
		}
	}

	/**
	 * 根据文件存储id删除数据
	 * @param id
	 * @return
	 */
	public static boolean delFileById(String id) throws Exception{
		String weedfs = DataMapUtils.getDataMapSub(Constants.WEED_FS,Constants.BASE_URL);
		HttpClient client = new HttpClient();
		DeleteMethod delMethod = new DeleteMethod(weedfs);
		try {
			// 通过以下方法可以模拟页面参数提交
			int status = client.executeMethod(delMethod);
			if (status == HttpStatus.SC_ACCEPTED) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			delMethod.releaseConnection();
		}
	}

	/**
	 * 读取文件字符集
	 * @param file
	 * @return
	 */
	public static String getCharset(File file) {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1)
				return charset;
			if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1]
					== (byte) 0xFF) {
				charset = "UTF-16BE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1]
					== (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8";
				checked = true;
			}
			bis.reset();
			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					//单独出现BF以下的，也算是GBK
					if (0x80 <= read && read <= 0xBF)
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
							// (0x80 -
							// 0xBF),也可能在GB编码内
							continue;
						else
							break;
						// 也有可能出错，但是几率较小
					} else if (0xE0 <= read && read <= 0xEF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
				System.out.println(loc + " " + Integer.toHexString(read));
			}
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charset;
	}
}
