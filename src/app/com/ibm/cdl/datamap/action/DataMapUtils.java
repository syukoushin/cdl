package com.ibm.cdl.datamap.action;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibm.cdl.datamap.pojo.DataMap;
import com.ibm.cdl.datamap.pojo.SubDataMap;
import com.ibm.cdl.datamap.service.DataMapService;

/**
 * @author kelvin
 */
@Component
public class DataMapUtils {

	private static final Logger logger = Logger.getLogger(DataMapUtils.class);
	// 数据字典
    private static Map dictionaryMap = null;
	
	private static DataMapUtils dataMapUtils;
	@Autowired
	private DataMapService dataMapService;
	@PostConstruct
	public void init(){
		System.out.println("初始化数据字典");
		dataMapUtils = this;
		dataMapUtils.dataMapService = this.dataMapService;
	}
	
	public void setDataMapService(DataMapService dataMapService) {
		this.dataMapService = dataMapService;
	}

    public static void refresh() {
    	dictionaryMap = null;
    }
    
    /**
     * 得到BS对象
     * @return BS对象
     */
    public static DataMapService getBs() {
        return dataMapUtils.dataMapService;  //得到BS对象,受事务控制
    }
    
    /**
     * 通过关键字编号及选择值装配名称
     * @param key
     *            关键字编号
     * @param defaultSelect
     *            默认选项
     * @return 名称字符串
     */
    public static String getDictionaryName(String key, String defaultSelect) {
        // 定义返回选项的Name
        String returnName = "";
        if(defaultSelect==null || "".equals(defaultSelect)){
            return "";
        }
        // 查询字典
        DataMap dictClassVo = getDictionaryMap(key);
        if(dictClassVo==null){
            return "";
        }
        // 查询选项
        for (int i = 0; i < dictClassVo.getSubs().size(); i++) {
            if (((SubDataMap) dictClassVo.getSubs().get(i)).getCode().equals(defaultSelect)) {
                returnName = ((SubDataMap) dictClassVo.getSubs().get(i)).getName();
            }
        }
        // 返回数据字典
        return returnName;
    }    
    
    /**
     * 通过关键字编号获得数据字典
     * @param key
     * @return 数据字典
     * @throws Exception 
     */
    public static DataMap getDictionaryMap(String key) {
        // 判断是否已查询过，如果没有查询过则重新查询
        if (dictionaryMap == null) {
            try {
				dictionaryMap =  getBs().getDictionary();
			} catch (Exception e) {
				logger.error("通过关键字编号获得数据字典异常",e);
			}
        }
        //根据key获取
        DataMap dictClassVo = (DataMap) dictionaryMap.get(key);
        // 返回数据字典分类Vo
        return dictClassVo;
    }
    
    public static String getDataMapSub(String key,String subKey) {
    	
    	if (StringUtils.isEmpty(key)) return "";
    	if (StringUtils.isEmpty(subKey)) return "";
    	
    	DataMap dataMap = getDictionaryMap(key);
    	
    	if (dataMap == null) return "";
    	List<SubDataMap> subs = dataMap.getSubs();
    	
    	if (subs == null || subs.size() == 0) return "";
    	
    	for (SubDataMap sub : subs) {
    		if (subKey.equals(sub.getCode())) {
    			return sub.getName();
    		}
    	}
    	return "";
    }
    
    public static SubDataMap getSubDataMap(String key,String subKey) {
    	
    	if (StringUtils.isEmpty(key)) return null;
    	if (StringUtils.isEmpty(subKey)) return null;
    	
    	DataMap dataMap = getDictionaryMap(key);
    	
    	if (dataMap == null) return null;
    	List<SubDataMap> subs = dataMap.getSubs();
    	
    	if (subs == null || subs.size() == 0) return null;
    	
    	for (SubDataMap sub : subs) {
    		if (subKey.equals(sub.getCode())) {
    			return sub;
    		}
    	}
    	return null;
    }
    
    /**
     * 通过关键字编号获得数据字典列表选
     * @param key
     * @param type
     *            输出类型
     * @param defaultSelect
     *            默认选项
     * @return HTML字符串
     */
    public static String getDictionaryHtml(String key, String type, String defaultSelect) {
        // 定义返回选项的HTML
        String returnHtml = "";
        // 查询字典
        DataMap mydataVo = getDictionaryMap(key);
        // 转换HTML选项
        if (mydataVo != null) {
            // 带有全部选项
            if ("all".equals(type)) {
                returnHtml = returnHtml + "<option value=\"\"" + ("".equals(defaultSelect) ? " Selected" : "") + ">--请选择--</option>";
            }
            // 带有空选项
            else if ("nothing".equals(type)) {
                returnHtml = returnHtml + "<option value=\"\"" + ("".equals(defaultSelect) ? " Selected" : "") + "></option>";
            }
            // 组装子项
            for (int i = 0; i < mydataVo.getSubs().size(); i++) {
                returnHtml = returnHtml + "<option value=\"" + ((SubDataMap) mydataVo.getSubs().get(i)).getCode()+ "\"" + (((SubDataMap) mydataVo.getSubs().get(i)).getCode().equals(defaultSelect) ? " Selected" : "") + ">" + ((SubDataMap) mydataVo.getSubs().get(i)).getName() + "</option>";
            }
        }
        // 返回数据字典分类Vo
        return returnHtml;
    }
	
    /**
     * 筛选数据字典项
     * @param key
     *            关键字编号
     * @param type
     *            输出类型
     * @param defaultSelect
     *            默认选项
     * @return HTML字符串
     */
    public static String getDictionaryHtml4Filter(String key, String type, String defaultSelect,List list) {
        // 定义返回选项的HTML
        String returnHtml = "";
        // 查询字典
        DataMap mydataVo = getDictionaryMap(key);
        // 转换HTML选项
        if (mydataVo != null) {
            // 带有全部选项
            if ("all".equals(type)) {
                returnHtml = returnHtml + "<option value=\"\"" + ("".equals(defaultSelect) ? " Selected" : "") + ">--请选择--</option>";
            }
            // 带有空选项
            else if ("nothing".equals(type)) {
                returnHtml = returnHtml + "<option value=\"\"" + ("".equals(defaultSelect) ? " Selected" : "") + "></option>";
            }
            // 组装子项
            for (int i = 0; i < mydataVo.getSubs().size(); i++) {
	        	if(list!=null && !list.isEmpty() ){
	        	    if(list.contains(((SubDataMap) mydataVo.getSubs().get(i)).getCode())) continue;
	                returnHtml = returnHtml + "<option value=\"" + ((SubDataMap) mydataVo.getSubs().get(i)).getCode()+ "\"" + (((SubDataMap) mydataVo.getSubs().get(i)).getCode().equals(defaultSelect) ? " Selected" : "") + ">" + ((SubDataMap) mydataVo.getSubs().get(i)).getName() + "</option>";
	        	}
            }
        }
        // 返回数据字典串
        return returnHtml;
    }
    
    public static void main(String[] args) {
    	System.out.print(DataMapUtils.getDictionaryName("a1", "lenovo"));
    	System.out.print(DataMapUtils.getDictionaryHtml("a1", "all", ""));
	}
    
}
