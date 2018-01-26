package com.ibm.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;


public class AttachmentAdapter {
	
	public static String getFileInfo(String filePath){
		StringBuffer sb=new StringBuffer();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
		String str=null; 
		try {
			while((str=br.readLine())!=null) 
			{ 
				sb.append(str);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	public static String getFileByBase64(String base64String,String fileNme){
		FileOutputStream out=null;
		try {
			out=new FileOutputStream(fileNme);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			com.jbm.util.Base64.decode(base64String, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileNme;
	}
	
	public static boolean GenerateImage(String imgStr,String filePath)
    {//对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try 
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
//            for(int i=0;i<b.length;++i)
//            {
//                if(b[i]<0)
//                {//调整异常数据
//                    b[i]+=256;
//                }
//            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(filePath);   
            out.write(b);
            out.flush();
            out.close();
            return true;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }

	public static void main(String[] args) {
//		String fileName="d:/img.txt";
//		String imgPath="d:/img.png";
//		AttachmentAdapter.getFileInfo(fileName);
//		
//		AttachmentAdapter.getFileByBase64(AttachmentAdapter.getFileInfo(fileName), imgPath);
//				
		long beginTime=System.currentTimeMillis();
		GenerateImage(getFileInfo("d:/file.txt"),"d:/moaportal.apk");
		System.out.println(System.currentTimeMillis()-beginTime);
	}
	
	

}
