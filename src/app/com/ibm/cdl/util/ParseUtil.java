package com.ibm.cdl.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 转换工具类
 * @author xuxiangxun
 *
 */
public class ParseUtil {
	
	public static final Log logger = LogFactory.getLog(ParseUtil.class);
	
	
	
	/**
	 * 将XML转换成MAP结构
	 * @param xml
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	public static HashMap transMap(String xml) throws ParseException,Exception{
		
		//xml=URLDecoder.decode(xml,"UTF-8");
		if(xml!=null){
			xml=xml.replaceAll("&#", "##");
		}
		SAXReader reader = new SAXReader();
		byte[] tagetXml=xml.getBytes("utf-8");
		InputStream in = new ByteArrayInputStream(tagetXml); 
		InputStreamReader utf8In=new InputStreamReader(in,"utf-8");
		
		Document document = null;
		try {
			document = reader.read(utf8In);
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error("In XmlToObject::transform(Object _xml) - 文档结构异常："
					+ e.toString());
			logger.error(e);
			throw new ParseException("文档结构异常：" + e.toString());
		}finally{
			in.close();
			utf8In.close();
		}

		Element root = document.getRootElement();
		
		List propertyList=root.elements("property");	
		HashMap h = new HashMap();
		for(int i=0;i<propertyList.size();i++){
			Element emlt=(Element)propertyList.get(i);
			h.put(emlt.attribute("name").getText(), emlt.getText());
		}
		h = makeObject(root, h);
		return h;
	}
	
	private static HashMap makeObject(Element e, HashMap h) {
	
		List<Element> list = e.elements("list");
		if(list!=null&&list.size()>0){
			for(Element elem:list){
				String key =elem.attribute("name").getText();
				String value = null;
				if (list != null) {
						ArrayList _list = new ArrayList();
						List nodeList=elem.elements("node");
						if(nodeList!=null){
							for(int i=0;i<nodeList.size();i++){
								HashMap h11 = new HashMap();
								Element nodeObj = (Element) nodeList.get(i);
								for (Iterator j = nodeObj.elementIterator("property"); j.hasNext();) {
									Element property = (Element) j.next();
									h11.put(property.attribute("name").getText(), property.getText());
									
								}
								_list.add(h11);
							}
						}
						
						h.put(key, _list);
					}
			}
		}
		return h;
	}
	
	
	


}
