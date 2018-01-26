package com.ibm.cdl.common.util;
/**
 * @author chenc
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public interface IntEncrypt {
	public String getEncrStr();
	public String getEncrStrByUid(String uid);
	public String getEncrStrByEmploynum(String employeenum);
	public String getEncrStrByArr(String[] arr);
	public String getEncString(String data);
	public String getDesString (String data) throws Exception;
	public String getDesStringWithCode (String data, String code) throws Exception;
	public String getDesStringByUid (String data);
	public String getEncString (String data, String codeType);
	public String getDesString (String data, String codeType) throws Exception;
	public String getEncrStrByUidAndRemoteAppId(String uid, String remoteAppId) ;
}
