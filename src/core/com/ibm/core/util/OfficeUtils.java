package com.ibm.core.util;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.ibm.cdl.attachment.utils.UploadUtils;
import com.ibm.cdl.datamap.action.DataMapUtils;
import com.ibm.cdl.datamap.constants.Constants;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class OfficeUtils {
	
	public static void main(String[] args) throws Exception {
//		   String s = "<img src=\"data:image/png;base64,R0lGODdhVQAoAHcAACH+GlNvZnR3YXJlOiBNaWNyb3NvZnQgT2ZmaWNlACwAAAAAVQAoAIEAAAAGAAn79f7///8C/5yPqcsG+lJsY9IrzwO8T+8hlqOBo4il2VktY3RaH8WqtMu8ZIbGZmhrbHgQIKwU+tVqQd5GqUG1ptIms7mjZqOzaQwJxGFTM+Zw9z0fK0rTWCK7sHwOd+59g9mrXHjUK4R3BzgksyRyxgbCFyfYVeh1Ncj3tyUZdAT5t6ZV+cNI6UiimcX5RQRaiVfYZlnaU/cZCyW4FSiHulq7y9vr+wscLNzbVmx8jJycnCDQPPys4hGgTF1tvXwgAL1toz3QrA1u4JxNPm6OIH7u/f0dPswePz7fTi+fbp/tHv4efB+v7p8+fATreTsojF27ewMNNqTXEKHDX/3mCaw3EWM+jC8SJfpCB/FcOnTqOCoM6CylQnjcWlJY6TJmyZg0a9q8iTOnzp08e/r8CTSoUAYFAAA7\" style=\"height:40px; width:85px\" />N";
//		   
//			String questionStr ="<p>（2015•盘锦校级模拟）工业生产中常将两种金属放在一个容器中加热使之熔合，冷凝后得到具有金属特性的熔合物﹣﹣合金，试根据下表所列金属的熔点和沸点的数据（其他条件均已满足），判断不能制得的合金是</p>"+
//		   "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" class=\" subjectTable\"><tbody><tr><td> <p>&nbsp;</p> </td><td colspan=\"4\"> <p>纯金属</p> </td><td colspan=\"2\"> <p>合金</p> </td></tr><tr><td> <p>&nbsp;</p> </td><td> <p>铅</p> </td> <td> <p>镉</p> </td><td> <p>铋</p> </td> <td> <p>锡</p> </td><td> <p>焊锡</p> </td><td> <p>武德合金</p> </td></tr><tr> <td> <p>熔点/℃</p> </td> <td> <p>327</p> </td><td> <p>321</p> </td> <td> <p>271</p> </td><td> <p>232</p> </td><td> <p>183</p> </td><td> <p>70</p> </td>  </tr></tbody></table>";
//
//			getStrNode(questionStr,new ArrayList<WpBody>(),new ArrayList<String>(),new ArrayList<ImgBody>());
	}     
   
   
   public void createDoc(Map<String,Object> dataMap,String fileName,String templateName){  
	   Configuration configuration = new Configuration();  
	   configuration.setDefaultEncoding("utf-8");  
	   configuration.setClassForTemplateLoading(this.getClass(), "/com/ibm/core/util");
       //dataMap 要填入模本的数据文件  
       //设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，  
       //这里我们的模板是放在template包下面  
       Template t=null;  
       try {  
           //test.ftl为要装载的模板  
           t = configuration.getTemplate(templateName);  
       } catch (IOException e) {  
           e.printStackTrace(); 
       }  
       //输出文档路径及名称  
       String baseUrl = UploadUtils.getPath() + "/attachment/doc";
       //String baseUrl = "D:\\及第坐标\\test\\doc";
       File docFolder = new File(baseUrl);
       if(!docFolder.exists()){
    	   docFolder.mkdir();
       }
       File outFile = new File(baseUrl + "/" + fileName); 
       try {  
	       if(!outFile.exists()){
	    	   outFile.createNewFile();
	       }
       } catch (IOException e) {  
           e.printStackTrace(); 
       }  
       Writer out = null;  
       FileOutputStream fos=null;  
       try {  
           fos = new FileOutputStream(outFile);  
           OutputStreamWriter oWriter = new OutputStreamWriter(fos,"UTF-8");  
           //这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。  
           //out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));  
            out = new BufferedWriter(oWriter);   
       } catch (FileNotFoundException e1) {  
           e1.printStackTrace();  
       } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}  
          
       try {  
           t.process(dataMap, out);  
           out.close();  
           fos.close();  
       } catch (TemplateException e) {  
           e.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();  
       }  
   }
   
   
   public void createExcel(Map<String,Object> dataMap,String fileName,String templateName){  
	   Configuration configuration = new Configuration();  
	   configuration.setDefaultEncoding("utf-8");  
	   configuration.setClassForTemplateLoading(this.getClass(), "/com/ibm/core/util");
       Template t=null;  
       try {  
           //test.ftl为要装载的模板  
           t = configuration.getTemplate(templateName);  
       } catch (IOException e) {  
           e.printStackTrace(); 
       }  
       //输出文档路径及名称  
       String baseUrl = UploadUtils.getPath() + "/attachment/excel";
       //String baseUrl = "D:\\及第坐标\\test\\doc";
       File docFolder = new File(baseUrl);
       if(!docFolder.exists()){
    	   docFolder.mkdir();
       }
       File outFile = new File(baseUrl + "/" + fileName); 
       try {  
	       if(!outFile.exists()){
	    	   outFile.createNewFile();
	       }
       } catch (IOException e) {  
           e.printStackTrace(); 
       }  
       Writer out = null;  
       FileOutputStream fos=null;  
       try {  
           fos = new FileOutputStream(outFile);  
           OutputStreamWriter oWriter = new OutputStreamWriter(fos,"UTF-8");  
           //这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。  
           //out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));  
            out = new BufferedWriter(oWriter);   
       } catch (FileNotFoundException e1) {  
           e1.printStackTrace();  
       } catch (UnsupportedEncodingException e) {
    	   e.printStackTrace();
       }  
       try {  
           t.process(dataMap, out);  
           out.close();  
           fos.close();  
       } catch (TemplateException e) {  
           e.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();  
       }  
   }
}
