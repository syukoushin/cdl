package com.ibm.cdl.datamap.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.cdl.datamap.pojo.SubDataMap;
import com.ibm.cdl.datamap.service.SubDataMapService;
import com.ibm.core.action.DefaultBaseAction;
import com.ibm.core.orm.Page;

/**
 * @author kelvin
 */
public class SubDataMapAction extends DefaultBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SubDataMap subDataMap = new SubDataMap();
	private Page<SubDataMap> res = new Page<SubDataMap>();
	@Autowired
	private SubDataMapService subDataMapService;
	/**
	 * 跳转数据字典子页面
	 * @return
	 */
	public String list() {
		res = subDataMapService.list(res,subDataMap);
		return goUrl("list");
	}

	/**
	 * 跳转添加页面
	 */
	public String input() {
		return goUrl("input");
	}
	
	/**
	 * 添加
	 */
	public String save() {
		subDataMapService.save(subDataMap);
		return goAction("/datamap/SubDataMap_list.do?subDataMap.parent="+subDataMap.getParent());
	}
	/**
	 * 跳转到修改页面
	 * @return
	 */
	public String edit(){
		subDataMap = subDataMapService.findSubDataMapById(subDataMap.getId());
		return goUrl("edit");
	}
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		subDataMapService.update(subDataMap);
		return goAction("/datamap/SubDataMap_list.do?subDataMap.parent="+subDataMap.getParent());
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		String ids = this.getRequest().getParameter("ids");
		subDataMapService.delete(ids);
		return goAction("/datamap/SubDataMap_list.do?subDataMap.parent="+subDataMap.getParent());
	}
	

	public SubDataMap getSubDataMap() {
		return subDataMap;
	}

	public void setSubDataMap(SubDataMap subDataMap) {
		this.subDataMap = subDataMap;
	}

	public Page<SubDataMap> getRes() {
		return res;
	}

	public void setRes(Page<SubDataMap> res) {
		this.res = res;
	}
}
