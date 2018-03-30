package com.ibm.cdl.manage.action;

import com.ibm.cdl.attachment.domain.Attachment;
import com.ibm.cdl.attachment.service.AttachmentService;
import com.ibm.cdl.datamap.constants.Constants;
import com.ibm.cdl.manage.pojo.Invoice;
import com.ibm.cdl.manage.pojo.InvoiceExport;
import com.ibm.cdl.manage.pojo.User;
import com.ibm.cdl.manage.service.InvoiceService;
import com.ibm.core.action.DefaultBaseAction;
import com.ibm.core.orm.Page;
import com.ibm.core.util.DateJsonValueProcessor;
import com.ibm.core.util.excel.ExportExcel;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceAction extends DefaultBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private InvoiceService invoiceService	;
	@Autowired
	private AttachmentService attachmentService;
	private List<Attachment> attachmentList = new ArrayList<Attachment>();
	private Invoice entity = new Invoice();
	private Page<Invoice> res = new Page<Invoice>();   //分页对象
	private String resultJSON=null;
	private String result = null;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
    
    /**
	 * 跳转到订单列表页面
	 * @return
	 */
	public String toList(){
		getSession().setAttribute("CURRENT_MENU", Constants.MENU_INVOICE);
		return goUrl("list");
	}
	
	/**
	 * 获取分页列表
	 * @return
	 */
	public String ajaxInvoiceList(){
		JSONObject json = new JSONObject();
		try{
			User currentUser = getSessionUser();
			res.setPageNo(pageNo);
			res = invoiceService.findPage(entity ,res,currentUser);
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));
			JSONObject resJson =  JSONObject.fromObject(res, config);
			json.put("optSts", "0");
			json.put("data", resJson);
			json.put("optMsg", "获取列表成功！");
		}catch(Exception e){
			e.printStackTrace();
			json.put("optSts", "1");
			json.put("optMsg", "获取列表失败！");
		}finally{
			this.sendResponseMessage(json.toString());
		}
		return null;
	}
	
	/**
	 * 跳转到详情页面
	 * @return
	 */
	public String toInvoiceDetail(){
		String id = getParameter("id");
		try {
			entity = invoiceService.findEntityById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return  goAction("/manage/Invoice_toList.do?tagTopFlag=dbgl&tagFlag=wdjs");
		}
		// 获取附件信息
		attachmentList = attachmentService.findAttachmentsByBusinessId(id);
		return goUrl("detail");
	}
    
	/**
	 * 下载EXCEL
	 * @return
	 */
	public String exportExcel() {
		try{
			User currentUser = getSessionUser();
			List<Invoice> result = invoiceService.findListBy(entity,currentUser);
			ExportExcel tempExcel = new ExportExcel("",Invoice.class);
//			tempExcel.setDataList(transToExpostList(result));
			tempExcel.setDataList(result);
			String time = System.currentTimeMillis()+"";
			tempExcel.write(getResponse(),"发票列表-"+time+".xlsx");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<InvoiceExport> transToExpostList(List<Invoice> sourceList) {
		List<InvoiceExport> resultList = new ArrayList<InvoiceExport>();
		if(sourceList != null && sourceList.size() > 0 ){
			for(Invoice temp : sourceList){
				InvoiceExport result = new InvoiceExport();
				result.setBandNo(temp.getBandNo());
				result.setId(temp.getId());
				result.setBuyerName(temp.getBuyerName());
				result.setCreateTime(temp.getCreateTime());
				result.setDmsFlag(temp.getDmsFlag());
				result.setEnginNo(temp.getEnginNo());
				result.setFrameNo(temp.getFrameNo());
				result.setIdCard(temp.getIdCard());
				result.setInvoiceNo(temp.getInvoiceNo());
				result.setName(temp.getName());
				result.setOkNo(temp.getOkNo());
				result.setPrintDate(sdf.format(temp.getPrintDate()));
				result.setTax(temp.getTax());
				result.setCreateUser(temp.getCreateUser());
				resultList.add(result);
			}
		}
		return resultList;
	}
	
    
    
	public String getResultJSON() {
		return resultJSON;
	}

	public void setResultJSON(String resultJSON) {
		this.resultJSON = resultJSON;
	}


	public Invoice getEntity() {
		return entity;
	}

	public void setEntity(Invoice entity) {
		this.entity = entity;
	}

	public Page<Invoice> getRes() {
		return res;
	}

	public void setRes(Page<Invoice> res) {
		this.res = res;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
}
