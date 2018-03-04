package com.ibm.cdl.datamap.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibm.cdl.common.util.HttpRequestPoster;
import com.ibm.cdl.datamap.pojo.DataMap;
import com.ibm.cdl.datamap.pojo.SubDataMap;
import com.ibm.cdl.datamap.service.DataMapService;
import com.ibm.core.action.DefaultBaseAction;
import com.ibm.core.orm.Page;

import net.sf.json.JSONObject;

/**
 * @author kelvin
 */
public class DataMapAction extends DefaultBaseAction {

	private DataMap dataMap = new DataMap();
	/**
	 * 查询出来的多个列表
	 */
	private Page<DataMap> res = new Page<DataMap>();
	private List<SubDataMap> subList = new ArrayList<SubDataMap>();
	private DataMapService dataMapService;
	
	private String result; //接受json值返回页面
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * 跳转数据字典管理列表
	 * @return
	 */
	public String list() {
		res = dataMapService.findAllDataMap(res);
		return goUrl("list");
	}
	/**
	 * 跳转添加页面
	 * @return
	 */
	public String input(){
		return goUrl("input");
	}
	/**
	 * 跳转编辑页面
	 * @return
	 */
	public String edit() {
		dataMap = dataMapService.findDataMapById(dataMap.getId());
		return goUrl("edit");
	}
	/**
	 * 保存字典信息
	 * @return
	 */
	public String save() {
		dataMapService.addDataMap(dataMap);
		return list();
	}
	/**
	 * 修改字典信息
	 * @return
	 */
	public String update() {
		dataMapService.updateDataMap(dataMap);
		return list();
	}

	/**
	 * 删除字典
	 * @return
	 */
	public String delete() {
		String ids = this.getRequest().getParameter("ids");
		dataMapService.deleteDataMapById(ids);
		return list();
	}
	
	/**
	 * 刷新
	 * @return
	 */
	public String flash(){
		DataMapUtils.refresh();
		//urls
		DataMapService dataMapService2 = DataMapUtils.getBs();
		Page<DataMap> findAllDataMap = dataMapService2.findAllDataMap(res);
		JSONObject json = new JSONObject();
		if(findAllDataMap == null){
			json.put("operStatus", "fail");
			this.sendResponseMessage(json.toString());
			return null;
		}
		List<DataMap> result2 = findAllDataMap.getResult();
		for(DataMap dm:result2){
			if(dm.getCode().equals("COM_COLLECTION")){
				List<SubDataMap> subs = dm.getSubs();
				for(SubDataMap sdm:subs){
					final String name = sdm.getName();
					new Thread(new Runnable() {
						public void run() {
							try {
								HttpRequestPoster.communicate(new HashMap(), name);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}).start();;
				}
			}
		}
		json.put("operStatus", "success");
		this.sendResponseMessage(json.toString());
		return null;
	}

	public DataMap getDataMap() {
		return dataMap;
	}

	public void setDataMap(DataMap dataMap) {
		this.dataMap = dataMap;
	}

	public Page<DataMap> getRes() {
		return res;
	}

	public void setRes(Page<DataMap> res) {
		this.res = res;
	}

	public List<SubDataMap> getSubList() {
		return subList;
	}

	public void setSubList(List<SubDataMap> subList) {
		this.subList = subList;
	}
	public void setDataMapService(DataMapService dataMapService) {
		this.dataMapService = dataMapService;
	}
}
